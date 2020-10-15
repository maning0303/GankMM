package com.maning.gankmm.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.WeatherBeseEntity;
import com.maning.gankmm.bean.rolltools.WeatherFuturedaysResultBean;
import com.maning.gankmm.utils.SharePreUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by maning on 2017/4/6.
 */

public class Weather2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<WeatherFuturedaysResultBean.DataEntity.ForecastsEntity> weatherForecasts;
    private LayoutInflater layoutInflater;


    public Weather2Adapter(Context context, List<WeatherFuturedaysResultBean.DataEntity.ForecastsEntity> weatherForecasts) {
        this.mContext = context;
        this.weatherForecasts = weatherForecasts;
        layoutInflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.item_weather_later_item, parent, false);
        return new Weather2Adapter.MyViewHolder01(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder01) {
            final MyViewHolder01 myViewHolder01 = (MyViewHolder01) holder;


            myViewHolder01.tv_01.setText("--");
            myViewHolder01.tv_02.setText("--");
            myViewHolder01.tv_03.setText("--");
            myViewHolder01.tv_04.setText("--");
            myViewHolder01.tv_05.setText("--");
            myViewHolder01.tv_06.setText("--");
            myViewHolder01.tv_07.setText("--");
            myViewHolder01.tv_08.setText("--");
            myViewHolder01.tv_09.setText("--");
            myViewHolder01.tv_10.setText("--");

            WeatherFuturedaysResultBean.DataEntity.ForecastsEntity futureBean = weatherForecasts.get(position);
            myViewHolder01.tv_01.setText(futureBean.getDayOfWeek());
            myViewHolder01.tv_02.setText(futureBean.getDate().substring(5));


            //白天
            myViewHolder01.tv_03.setText(futureBean.getDayWeather());
            myViewHolder01.tv_04.setText(futureBean.getDayTemp());
            myViewHolder01.tv_09.setText(futureBean.getDayWindPower());
            myViewHolder01.tv_10.setText(futureBean.getDayWindDirection());


            //夜间
            myViewHolder01.tv_05.setText(futureBean.getNightTemp());
            myViewHolder01.tv_06.setText(futureBean.getNightWeather());
            myViewHolder01.tv_07.setText(futureBean.getNightWindPower());
            myViewHolder01.tv_08.setText(futureBean.getNightWindDirection());

            myViewHolder01.iv_01.setImageDrawable(mContext.getResources().getDrawable(SharePreUtil.getIntData(mContext, futureBean.getDayWeather(), R.drawable.icon_weather_none)));
            myViewHolder01.iv_02.setImageDrawable(mContext.getResources().getDrawable(SharePreUtil.getIntData(mContext, futureBean.getNightWeather(), R.drawable.icon_weather_none)));


        }

    }

    @Override
    public int getItemCount() {
        return weatherForecasts.size();
    }


    public static class MyViewHolder01 extends RecyclerView.ViewHolder {

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
        @Bind(R.id.tv_06)
        TextView tv_06;
        @Bind(R.id.tv_07)
        TextView tv_07;
        @Bind(R.id.tv_08)
        TextView tv_08;
        @Bind(R.id.tv_09)
        TextView tv_09;
        @Bind(R.id.tv_10)
        TextView tv_10;
        @Bind(R.id.iv_01)
        ImageView iv_01;
        @Bind(R.id.iv_02)
        ImageView iv_02;

        public MyViewHolder01(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
