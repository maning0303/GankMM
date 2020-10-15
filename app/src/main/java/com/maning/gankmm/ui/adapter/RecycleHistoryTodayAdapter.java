package com.maning.gankmm.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.maning.gankmm.R;
import com.maning.gankmm.bean.rolltools.HistoryTodayBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 历史上的今天
 */
public class RecycleHistoryTodayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<HistoryTodayBean.DataEntity> mDatas;
    private LayoutInflater layoutInflater;

    public RecycleHistoryTodayAdapter(Context context, ArrayList<HistoryTodayBean.DataEntity> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(this.context);
    }

    public void upddateDatas(ArrayList<HistoryTodayBean.DataEntity> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.item_history_today, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) viewHolder;

            HistoryTodayBean.DataEntity dataEntity = mDatas.get(position);

            myViewHolder.tv_title.setText(dataEntity.getTitle());

            myViewHolder.tv_time.setText(dataEntity.getYear() + "-" + dataEntity.getMonth() + "-" + dataEntity.getDay());

            myViewHolder.expand_text_view.setContent(dataEntity.getDetails());

            if (TextUtils.isEmpty(dataEntity.getPicUrl())) {
                myViewHolder.iv_show.setVisibility(View.GONE);
            } else {
                myViewHolder.iv_show.setVisibility(View.VISIBLE);

                RequestOptions options = new RequestOptions();
                options.fitCenter();
                options.placeholder(R.drawable.pic_gray_bg);
                Glide.with(context)
                        .load(dataEntity.getPicUrl())
                        .apply(options)
                        .into(myViewHolder.iv_show);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_time)
        TextView tv_time;
        @Bind(R.id.iv_show)
        ImageView iv_show;
        @Bind(R.id.expand_text_view)
        ExpandableTextView expand_text_view;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
