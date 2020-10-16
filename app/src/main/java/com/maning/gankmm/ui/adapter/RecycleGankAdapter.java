package com.maning.gankmm.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.gank2.GankEntity;

import java.util.List;

/**
 * Created by maning on 16/5/17.
 */
public class RecycleGankAdapter extends RecyclerView.Adapter<RecycleGankAdapter.MyViewHolder> {

    private Context context;
    private List<GankEntity> mDatas;
    private LayoutInflater layoutInflater;

    public RecycleGankAdapter(Context context, List<GankEntity> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(this.context);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.get(position).getType().equals("title")) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder;
        if (viewType == 0) {  //标题
            holder = new MyViewHolder(layoutInflater.inflate(R.layout.item_day_title, parent, false), viewType);
        } else {  //内容
            holder = new MyViewHolder(layoutInflater.inflate(R.layout.item_day_content, parent, false), viewType);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {

        if (getItemViewType(position) == 0) {
            viewHolder.tvTitle.setText(mDatas.get(position).getDesc());
        } else {
            if (mDatas.get(position).getType().equals("Android")) {
                viewHolder.ivTitle.setImageResource(R.drawable.gank_icon_android);
            } else if (mDatas.get(position).getType().equals("iOS")) {
                viewHolder.ivTitle.setImageResource(R.drawable.gank_icon_ios);
            } else if (mDatas.get(position).getType().equals("休息视频")) {
                viewHolder.ivTitle.setImageResource(R.drawable.gank_icon_video);
            } else if (mDatas.get(position).getType().equals("拓展资源")) {
                viewHolder.ivTitle.setImageResource(R.drawable.gank_icon_extend_resource);
            }
            viewHolder.tvTitle.setText(mDatas.get(position).getDesc());
        }

        //如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(viewHolder.itemView, position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        ImageView ivTitle;


        public MyViewHolder(View itemView, int type) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            ivTitle = (ImageView) itemView.findViewById(R.id.iv_title);

        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }
}
