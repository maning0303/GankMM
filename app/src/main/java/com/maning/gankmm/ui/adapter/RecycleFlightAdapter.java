package com.maning.gankmm.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobFlightEntity;
import com.maning.gankmm.listeners.OnItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 航班信息
 */
public class RecycleFlightAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<MobFlightEntity> mDatas;
    private LayoutInflater layoutInflater;

    public RecycleFlightAdapter(Context context, ArrayList<MobFlightEntity> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(this.context);
    }

    public void updateDatas(ArrayList<MobFlightEntity> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.item_flight, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) viewHolder;

            if (mOnItemClickLitener != null) {
                myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickLitener.onItemClick(view, position);
                    }
                });
            }

            MobFlightEntity mobFlightEntity = mDatas.get(position);

            //起点
            myViewHolder.tv_start_station.setText(mobFlightEntity.getFromCityName());
            myViewHolder.tv_end_station.setText(mobFlightEntity.getToCityName());
            myViewHolder.tv_start_time.setText(mobFlightEntity.getPlanTime());
            myViewHolder.tv_end_time.setText(mobFlightEntity.getPlanArriveTime());

            //航班
            myViewHolder.tv_flightNo.setText(mobFlightEntity.getFlightNo());
            myViewHolder.tv_air_lines.setText(mobFlightEntity.getAirLines());

            //历时
            myViewHolder.tv_lishi.setText(mobFlightEntity.getFlightTime());


        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_start_station)
        TextView tv_start_station;

        @BindView(R.id.tv_start_time)
        TextView tv_start_time;

        @BindView(R.id.tv_flightNo)
        TextView tv_flightNo;

        @BindView(R.id.tv_lishi)
        TextView tv_lishi;

        @BindView(R.id.tv_end_station)
        TextView tv_end_station;

        @BindView(R.id.tv_end_time)
        TextView tv_end_time;

        @BindView(R.id.tv_air_lines)
        TextView tv_air_lines;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    private OnItemClickListener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

}
