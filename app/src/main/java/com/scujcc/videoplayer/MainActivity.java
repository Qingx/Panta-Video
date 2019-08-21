package com.scujcc.videoplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String[] TVNames;
    private String[] TVDetial;
    private String[] TVUrl;
    private List<TV> mTVList = new ArrayList<TV>();
    private TVListAdapter adapter;
    Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        initVideoDatas();

        List images = new ArrayList();

        images.add(R.drawable.cctv1);
        images.add(R.drawable.cctv2);
        images.add(R.drawable.cctv3);
        images.add(R.drawable.cctv4);
        banner= (Banner)findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        //增加点击事件
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //Toast.makeText(MainActivity.this, "position" + position, Toast.LENGTH_SHORT).show();
                switch (position){
                    case 0:
                        String tvUrl =mTVList.get(position).getTvUrl();
                        Log.i("传递的视频url",tvUrl);

                        Intent intent = new Intent(MainActivity.this, ExoPlayerActivity.class);
                        intent.putExtra("tvUrl",tvUrl);
                        String tvName = mTVList.get(position).getTvTitle();
                        intent.putExtra("tvName",tvName);
                        startActivity(intent);
                        break;
                    case 1:
                        String tvUrl2 =mTVList.get(position).getTvUrl();
                        Log.i("传递的视频url",tvUrl2);

                        Intent intent2 = new Intent(MainActivity.this, ExoPlayerActivity.class);
                        intent2.putExtra("tvUrl",tvUrl2);
                        String tvName1 = mTVList.get(position).getTvTitle();
                        intent2.putExtra("tvName",tvName1);
                        startActivity(intent2);
                        //startActivity(new Intent(MainActivity.this,ExoPlayerActivity.class));
                        break;
                    case 2:
                        String tvUrl3 =mTVList.get(position).getTvUrl();
                        Log.i("传递的视频url",tvUrl3);

                        Intent intent3 = new Intent(MainActivity.this, ExoPlayerActivity.class);
                        intent3.putExtra("tvUrl",tvUrl3);
                        String tvName2= mTVList.get(position).getTvTitle();
                        intent3.putExtra("tvName",tvName2);
                        startActivity(intent3);
                        break;
                    case 3:
                        String tvUrl4 =mTVList.get(position).getTvUrl();
                        Log.i("传递的视频url",tvUrl4);

                        Intent intent4 = new Intent(MainActivity.this, ExoPlayerActivity.class);
                        intent4.putExtra("tvUrl",tvUrl4);
                        String tvName3 = mTVList.get(position).getTvTitle();
                        intent4.putExtra("tvName",tvName3);
                        startActivity(intent4);
                        break;
                }


            }
        });

        //recyclerView的适配器 adapter
        adapter = new TVListAdapter(mTVList);
        //item点击
        adapter.setOnItemClickListener(new TVListAdapter.OnItemClickListener() {
            //单击
            @Override
            public void onItemClick(View view, int position) {
//               Toast.makeText(getApplicationContext(),
//                        "click: " + position, Toast.LENGTH_SHORT).show();

                String tvUrl =mTVList.get(position).getTvUrl();
                Log.i("传递的视频url",tvUrl);
                String tvName = mTVList.get(position).getTvTitle();

                Intent intent = new Intent(MainActivity.this, ExoPlayerActivity.class);
                intent.putExtra("tvUrl",tvUrl);
                intent.putExtra("tvName",tvName);
                startActivity(intent);
            }
            //长按
            @Override
            public void onItemLongClick(View view, int position) {
//                Toast.makeText(getApplicationContext(),
//                        "long click: " + position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
        //recyclerView布局管理
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

    }
    private void initVideoDatas(){
        //视频信息集合
        TVNames = getResources().getStringArray(R.array.TVNames);
        TVDetial =getResources().getStringArray(R.array.TVDetail);
        TVUrl = getResources().getStringArray(R.array.TVUrl);
        TV cctv1 = new TV(R.drawable.cctv1,TVNames[0],TVDetial[0],TVUrl[0]);
        mTVList.add(cctv1);
        TV ccvt2 = new TV(R.drawable.cctv2,TVNames[1],TVDetial[1],TVUrl[1]);
        mTVList.add(ccvt2);
        TV cctv3 = new TV(R.drawable.cctv3,TVNames[2],TVDetial[2],TVUrl[2]);
        mTVList.add(cctv3);
        TV cctv4 = new TV(R.drawable.cctv4,TVNames[3],TVDetial[3],TVUrl[3]);
        mTVList.add(cctv4);
        TV cctv5 = new TV(R.drawable.cctv5,TVNames[4],TVDetial[4],TVUrl[4]);
        mTVList.add(cctv5);
        TV cctv6 = new TV(R.drawable.cctv6,TVNames[5],TVDetial[5],TVUrl[5]);
        mTVList.add(cctv6);
        TV gameTV = new TV(R.drawable.game,TVNames[6],TVDetial[6],TVUrl[6]);
        mTVList.add(gameTV);
        TV gongfuTV= new TV(R.drawable.gongfu,TVNames[7],TVDetial[7],TVUrl[7]);
        mTVList.add(gongfuTV);
        TV cctv12=new TV(R.drawable.hunan,TVNames[9],TVDetial[9],TVUrl[8]);
        mTVList.add(cctv12);
    }


}
