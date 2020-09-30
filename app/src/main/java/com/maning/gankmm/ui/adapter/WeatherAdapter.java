package com.maning.gankmm.ui.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.CalendarInfoEntity;
import com.maning.gankmm.bean.mob.WeatherBeseEntity;
import com.maning.gankmm.ui.view.ArcProgressView;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by maning on 2017/4/6.
 */

public class WeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private WeatherBeseEntity.WeatherBean weatherEntity;
    private CalendarInfoEntity calendarInfoEntity;
    private LayoutInflater layoutInflater;


    public WeatherAdapter(Context context, WeatherBeseEntity.WeatherBean weatherEntity, CalendarInfoEntity calendarInfoEntity) {
        this.mContext = context;
        this.weatherEntity = weatherEntity;
        this.calendarInfoEntity = calendarInfoEntity;
        layoutInflater = LayoutInflater.from(this.mContext);
    }

    public void updateDatas(WeatherBeseEntity.WeatherBean weatherEntity, CalendarInfoEntity calendarInfoEntity) {
        this.weatherEntity = weatherEntity;
        this.calendarInfoEntity = calendarInfoEntity;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View inflate = layoutInflater.inflate(R.layout.item_weather_header, parent, false);
            return new WeatherAdapter.MyViewHolder01(inflate);
        } else if (viewType == 1) {
            View inflate = layoutInflater.inflate(R.layout.item_weather_later, parent, false);
            return new WeatherAdapter.MyViewHolder02(inflate);
        } else if (viewType == 2) {
            View inflate = layoutInflater.inflate(R.layout.item_weather_air, parent, false);
            return new WeatherAdapter.MyViewHolder03(inflate);
        } else if (viewType == 3) {
            View inflate = layoutInflater.inflate(R.layout.item_weather_calendar, parent, false);
            return new WeatherAdapter.MyViewHolder04(inflate);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder01) {
            final MyViewHolder01 myViewHolder01 = (MyViewHolder01) holder;
            myViewHolder01.tv_01.setText(weatherEntity.getTemperature());
            myViewHolder01.tv_02.setText(weatherEntity.getWeather());
            myViewHolder01.tv_03.setText(weatherEntity.getFuture().get(0).getTemperature());
            myViewHolder01.tv_04.setText(weatherEntity.getAirCondition());
            myViewHolder01.tv_05.setText(weatherEntity.getWind());

        } else if (holder instanceof MyViewHolder02) {
            final MyViewHolder02 myViewHolder02 = (MyViewHolder02) holder;

            //初始化RecycleView
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            myViewHolder02.recycle_later.setLayoutManager(linearLayoutManager);
            myViewHolder02.recycle_later.setItemAnimator(new DefaultItemAnimator());
            myViewHolder02.recycle_later.addItemDecoration(new VerticalDividerItemDecoration.Builder(mContext).color(mContext.getResources().getColor(R.color.lineColor)).build());

            Weather2Adapter weather2Adapter = new Weather2Adapter(mContext, weatherEntity);
            myViewHolder02.recycle_later.setAdapter(weather2Adapter);

        } else if (holder instanceof MyViewHolder03) {
            final MyViewHolder03 myViewHolder03 = (MyViewHolder03) holder;
            String pollutionIndex = weatherEntity.getPollutionIndex();
            myViewHolder03.arc_progress.setCurrentCount(500, Integer.parseInt(pollutionIndex));
        } else if (holder instanceof MyViewHolder04) {
            final MyViewHolder04 myViewHolder04 = (MyViewHolder04) holder;

            myViewHolder04.tv_01.setText(calendarInfoEntity.getDate());
            myViewHolder04.tv_02.setText(calendarInfoEntity.getLunar());
            myViewHolder04.tv_03.setText(calendarInfoEntity.getLunarYear() + " (" + calendarInfoEntity.getZodiac() + ") " + calendarInfoEntity.getWeekday());
            myViewHolder04.tv_04.setText(calendarInfoEntity.getSuit());
            myViewHolder04.tv_05.setText(calendarInfoEntity.getAvoid());

        }


    }

    @Override
    public int getItemCount() {
        if (calendarInfoEntity == null) {
            return 3;
        }
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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

        public MyViewHolder01(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class MyViewHolder02 extends RecyclerView.ViewHolder {

        @Bind(R.id.recycle_later)
        RecyclerView recycle_later;

        public MyViewHolder02(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class MyViewHolder03 extends RecyclerView.ViewHolder {

        @Bind(R.id.arc_progress)
        ArcProgressView arc_progress;

        public MyViewHolder03(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public static class MyViewHolder04 extends RecyclerView.ViewHolder {

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

        public MyViewHolder04(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
