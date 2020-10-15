package com.maning.gankmm.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobTrainNoEntity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 火车详情信息
 */
public class RecycleTrainDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<MobTrainNoEntity> mDatas;
    private LayoutInflater layoutInflater;

    public RecycleTrainDetailsAdapter(Context context, ArrayList<MobTrainNoEntity> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(this.context);
    }

    public void updateDatas(ArrayList<MobTrainNoEntity> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.item_train_details, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) viewHolder;

            if (position == 0) {
                myViewHolder.tv_01.setTextColor(context.getResources().getColor(R.color.main_color));
                myViewHolder.tv_02.setTextColor(context.getResources().getColor(R.color.main_color));
                myViewHolder.tv_03.setTextColor(context.getResources().getColor(R.color.main_color));
                myViewHolder.tv_04.setTextColor(context.getResources().getColor(R.color.main_color));
                myViewHolder.tv_05.setTextColor(context.getResources().getColor(R.color.main_color));
                myViewHolder.tv_01.setText("站序");
                myViewHolder.tv_02.setText("站名");
                myViewHolder.tv_03.setText("到时");
                myViewHolder.tv_04.setText("发时");
                myViewHolder.tv_05.setText("停留");
            }else{
                MobTrainNoEntity mobTrainNoEntity = mDatas.get(position - 1);
                myViewHolder.tv_01.setTextColor(context.getResources().getColor(R.color.black_text2_color));
                myViewHolder.tv_02.setTextColor(context.getResources().getColor(R.color.black_text2_color));
                myViewHolder.tv_03.setTextColor(context.getResources().getColor(R.color.black_text2_color));
                myViewHolder.tv_04.setTextColor(context.getResources().getColor(R.color.black_text2_color));
                myViewHolder.tv_05.setTextColor(context.getResources().getColor(R.color.black_text2_color));
                myViewHolder.tv_01.setText(mobTrainNoEntity.getStationNo());
                myViewHolder.tv_02.setText(mobTrainNoEntity.getStationName());
                myViewHolder.tv_03.setText(mobTrainNoEntity.getArriveTime());
                myViewHolder.tv_04.setText(mobTrainNoEntity.getStartTime());
                myViewHolder.tv_05.setText(mobTrainNoEntity.getStopoverTime());
            }


        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_01)
        TextView tv_01;
        @Bind(R.id.tv_02)
        TextView tv_02;
        @Bind(R.id.tv_03)
        TextView tv_03;
        @Bind(R.id.tv_04)
        TextView tv_04;
        @Bind(R.id.tv_05)
        TextView tv_05;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
