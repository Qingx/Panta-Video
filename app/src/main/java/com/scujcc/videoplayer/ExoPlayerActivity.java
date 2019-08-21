package com.scujcc.videoplayer;

import android.app.usage.NetworkStatsManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Filter;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;


public class ExoPlayerActivity extends AppCompatActivity {

    public static ExoPlayerActivity instance;
    public static SimpleExoPlayer player;
    private PlayerView playerView;
    private DataSource.Factory factory;
    private String userAgent;
    private MediaSource mediaSource;
    private String TAG = "videoPlayer";
    private String tvUrl;
    private NetWork mNetWork;
    private RecyclerView pingLunList;
    private PingLunListAdapter mPingLunListAdapter;
    private List<PingLun> mPingLunData;
    public String tvName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_player_view);
        instance = this;

        checkNet2();
        userAgent = Util.getUserAgent(this, "videoPlayer");
        playerView =findViewById(R.id.playerView);
        tvUrl = getIntent().getStringExtra("tvUrl");
        Log.i("收到的URL",tvUrl);
        initPlayer();
        //检测网络是否连接
       // checkNet();

        //评论
        initPingLunDate();
        pingLunList =findViewById(R.id.recyclerView_pingLun);
        mPingLunListAdapter= new PingLunListAdapter(this.mPingLunData);
        pingLunList.setAdapter(mPingLunListAdapter);
        pingLunList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

    }
    @Override
    protected void onStart() {
        super.onStart();
        initPlayer();
        if (playerView != null) {
            playerView.onResume();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player == null) {
            initPlayer();
            if (playerView != null) {
                playerView.onResume();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (playerView != null) {
            playerView.onPause();
        }
        releasePlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (playerView != null) {
            playerView.onPause();
        }
        releasePlayer();
    }

    private void initPlayer(){
        if (player == null){
            player = ExoPlayerFactory.newSimpleInstance(this);
            player.addListener(new MyEventListener());
            player.setPlayWhenReady(true);
            playerView.setPlayer(player);
            factory = new DefaultDataSourceFactory(this, userAgent);
            mediaSource = new HlsMediaSource.Factory(factory)
                    .createMediaSource(Uri.parse(tvUrl));
        }
        player.prepare(mediaSource);
    }
    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
            mediaSource = null;
        }
    }

    class MyEventListener implements Player.EventListener {
        @Override
        public void onLoadingChanged(boolean isLoading) {
            Log.d(TAG, "onLoadingChanged isLoading=" + isLoading);
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            switch (playbackState) {
                case Player.STATE_BUFFERING:
                    Log.d(TAG, "正在缓冲...");
                    break;
                case Player.STATE_READY:
                    Log.d(TAG, "缓冲完成，可以播放了...");
                    break;
                case Player.STATE_IDLE:
                    Log.d(TAG, "闲置状态，无事可干...");
                    break;
                case Player.STATE_ENDED:
                    Log.d(TAG, "已经结束了。");
                    break;
                default:
                    Log.d(TAG, "无效状态:" + playbackState);
            }
            Log.d(TAG, "onPlayerStateChanged playWhenReady=" + playWhenReady);
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            Log.e(TAG, "onPlayerError 出错了，再次准备播放" + error);
            initPlayer();
        }

        @Override
        public void onPositionDiscontinuity(int reason) {
            Log.d(TAG, "onPositionDiscontinuity reason=" + reason);
        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            Log.d(TAG, "onPlaybackParametersChanged playbackParameters=" + playbackParameters);
        }
    }
    //未使用
    private void checkNet(){
        if (mNetWork.isNetConnected(this)){
             //Toast.makeText(this,"网络连接",Toast.LENGTH_SHORT).show();
                if (mNetWork.is3gConnected(this)){

                    player.setPlayWhenReady(false);//暂停播放。

                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setMessage("警告当前使用的是数据连接");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("我是土豪有钱", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            player.setPlayWhenReady(true);
                            dialog.cancel();
                        }
                    });
                    dialog.setNegativeButton("设置网络", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                            startActivity(intent);
                            dialog.cancel();
                             //bug 不设网络返回自动播放。
                        }
                    });
                    dialog.setNeutralButton("返回", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                }
        }else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("未连接网络。");
            dialog.setCancelable(false);
            dialog.setPositiveButton("返回", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    dialog.cancel();
                }

            });
            dialog.setNegativeButton("设置网络", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(intent);
                    dialog.cancel();
                }
            });
            dialog.show();
           // Toast.makeText(this,"未连接网络",Toast.LENGTH_SHORT).show();
        }
    }

    private void checkNet2(){
        IntentFilter filter = new IntentFilter();
          filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
          filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);

        registerReceiver(new NetworkConnectChangedReceiver(), filter);
    }

    private void initPingLunDate(){
        PingLunDataLab lab = new PingLunDataLab(this);

        tvName = getIntent().getStringExtra("tvName");
        Log.d(TAG,"传递的电视名字"+tvName);

        this.mPingLunData = lab.getPingLuns("pingLunDatas.json",tvName);
    }

}
