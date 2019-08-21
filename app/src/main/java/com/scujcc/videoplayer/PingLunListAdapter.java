package com.scujcc.videoplayer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PingLunListAdapter extends RecyclerView.Adapter<PingLunListAdapter.MyViewHolder> {
    private List<PingLun> mPingLunList;
    private ViewGroup mViewGroup;
    private int mI;

    public PingLunListAdapter(List<PingLun> pingLunList){
        this.mPingLunList = pingLunList;
        Log.d("TAG","6");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View row = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.iteam_recyclerview_pinglun,viewGroup,false);
        MyViewHolder holder = new MyViewHolder(row);
        Log.d("TAG","5");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position) {
            Log.d("TAG","4");
            holder.bind(mPingLunList.get(position));

//        PingLun pingLun = mPingLunList.get(i);
//
//        myViewHolder.id.setText(pingLun.getId());
//        myViewHolder.time.setText(pingLun.getTime());
//        myViewHolder.text.setText(pingLun.getText());
//        myViewHolder.zan.setText(pingLun.getZan());
//        myViewHolder.cai.setText(pingLun.getCai());
//        myViewHolder.huifu.setText(pingLun.getHuifu());

    }

    @Override
    public int getItemCount() {
        Log.d("TAG","3");
        return mPingLunList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView touxiang;
        private TextView id;
        private TextView time;
        private TextView text;
        private TextView zan;
        private TextView cai;
        private TextView huifu;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            touxiang = itemView.findViewById(R.id.pingLunTouXiang);
            id = itemView.findViewById(R.id.pingLunID);
            time =itemView.findViewById(R.id.pingLunTime);
            text = itemView.findViewById(R.id.pingLunText);
            zan = itemView.findViewById(R.id.pingLunZan);
            cai = itemView.findViewById(R.id.pingLunCai);
            huifu = itemView.findViewById(R.id.pingLunHuiFu);
            Log.d("TAG","2");
        }

        public void bind(PingLun pingLun) {

            touxiang.setImageResource(getDrawableId(pingLun.getTouxiang()));
            id.setText(pingLun.getId());
            Log.d("TAG","t1");
            time.setText(pingLun.getTime());
            Log.d("TAG","t2");
            text.setText(pingLun.getText());
            Log.d("TAG","t6");
            zan.setText(pingLun.getZan());
            Log.d("TAG","t3");
            cai.setText(pingLun.getCai());
            Log.d("TAG","t4");
            huifu.setText(pingLun.getHuifu());
            Log.d("TAG","1");
        }
        //根据图片名获得它的资源id
        public int getDrawableId(String tuPianMing){
            try {
                Field field = R.drawable.class.getField(tuPianMing);
                int DrawableId= field.getInt(new R.drawable());
                return DrawableId;
            } catch (NoSuchFieldException | IllegalAccessException e) {
                return 0;
            }
        }
    }
}

