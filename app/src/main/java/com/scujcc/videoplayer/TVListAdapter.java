package com.scujcc.videoplayer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TVListAdapter extends RecyclerView.Adapter<TVListAdapter.itemViewHolder> {
    private List<TV> mTVList;

    public TVListAdapter(List<TV> tvList) {
        this.mTVList= tvList;
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        final View itemView =View.inflate(viewGroup.getContext(),R.layout.iteam_recyclerview_main,null);

        //单击事件
        final itemViewHolder itemViewHolder = new itemViewHolder(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = itemViewHolder.getAdapterPosition();
                TV tv2 =mTVList.get(index);
            }
        });
        return new itemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( final itemViewHolder itemViewHolder, int i) {
        TV tv=mTVList.get(i);

        itemViewHolder.TVTitle.setText(tv.getTvTitle());
        itemViewHolder.TVImage.setImageResource(tv.getIconId());
        itemViewHolder.TVDetail.setText(tv.getTvDetail());

        if (mOnItemClickListener != null) {
            itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = itemViewHolder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(itemViewHolder.itemView, pos);
                }
            });

            itemViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = itemViewHolder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(itemViewHolder.itemView, pos);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mTVList.size() ;
    }

    class itemViewHolder extends  RecyclerView.ViewHolder{


        private ImageView TVImage;
        private TextView TVTitle;
        private TextView TVDetail;

        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
            TVImage = itemView.findViewById(R.id.iv_icon);
            TVTitle = itemView.findViewById(R.id.tv_title);
            TVDetail = itemView.findViewById(R.id.tv_detail);
        }
    }


    public interface OnItemClickListener {
        //单击
        void onItemClick(View view, int position);

        //长按
        void onItemLongClick(View view, int position);
    }


    private OnItemClickListener mOnItemClickListener;


    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

}
