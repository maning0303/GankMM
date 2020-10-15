package com.maning.gankmm.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.CommonItemEntity;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 更多功能的Adapter
 */
public class RecycleMobQueryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private HashMap<String, Object> mDatas;

    public RecycleMobQueryAdapter(Context context, HashMap<String, Object> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(this.context);
    }

    public void updateDatas(HashMap<String, Object> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.item_mob_query, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof MyViewHolder) {
            final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
            CommonItemEntity commonItemEntity = (CommonItemEntity) mDatas.get(String.valueOf(position));
            if (commonItemEntity != null) {
                myViewHolder.tvTitle.setText(commonItemEntity.getTitle());
                myViewHolder.tvDesc.setText(commonItemEntity.getDesc());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_desc)
        TextView tvDesc;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
