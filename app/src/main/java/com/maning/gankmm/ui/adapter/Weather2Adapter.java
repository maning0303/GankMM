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
import com.maning.gankmm.utils.SharePreUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by maning on 2017/4/6.
 */

public class Weather2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private WeatherBeseEntity.WeatherBean weatherEntity;
    private LayoutInflater layoutInflater;


    public Weather2Adapter(Context context, WeatherBeseEntity.WeatherBean weatherEntity) {
        this.mContext = context;
        this.weatherEntity = weatherEntity;
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


            WeatherBeseEntity.WeatherBean.FutureBean futureBean = weatherEntity.getFuture().get(position);
            myViewHolder01.tv_01.setText(futureBean.getWeek());
            myViewHolder01.tv_02.setText(futureBean.getDate().substring(5));

            myViewHolder01.tv_03.setText(futureBean.getDayTime());
            //最高温度和最低温度
            String temperature = futureBean.getTemperature();
            if (!TextUtils.isEmpty(temperature)) {
                if (temperature.length() > 1 && temperature.contains("/")) {
                    String[] tempers = temperature.split("/");
                    if (tempers.length > 0) {
                        myViewHolder01.tv_04.setText(tempers[0].replace(" ", ""));
                        myViewHolder01.tv_05.setText(tempers[1].replace(" ", ""));
                    }
                } else {
                    myViewHolder01.tv_04.setText(temperature);
                    myViewHolder01.tv_05.setText("");
                }
            }

            myViewHolder01.tv_06.setText(futureBean.getNight());

            //风向和风速
            String wind = futureBean.getWind();
            if (!TextUtils.isEmpty(wind)) {
                if (wind.length() > 1 && wind.contains(" ")) {
                    String[] winds = wind.split(" ");
                    if (winds.length > 0) {
                        myViewHolder01.tv_07.setText(winds[0].replace(" ", ""));
                        myViewHolder01.tv_08.setText(winds[1].replace(" ", ""));
                    }
                }
            }

            myViewHolder01.iv_01.setImageDrawable(mContext.getResources().getDrawable(SharePreUtil.getIntData(mContext, futureBean.getDayTime(), R.drawable.icon_weather_none)));
            myViewHolder01.iv_02.setImageDrawable(mContext.getResources().getDrawable(SharePreUtil.getIntData(mContext, futureBean.getNight(), R.drawable.icon_weather_none)));

            if (TextUtils.isEmpty(futureBean.getDayTime())) {
                myViewHolder01.iv_01.setVisibility(View.GONE);
            } else {
                myViewHolder01.iv_01.setVisibility(View.VISIBLE);
            }

        }

    }

    @Override
    public int getItemCount() {
        return weatherEntity.getFuture().size();
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
