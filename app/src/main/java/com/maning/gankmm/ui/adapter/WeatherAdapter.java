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
import com.maning.gankmm.bean.rolltools.HolidayBean;
import com.maning.gankmm.bean.rolltools.WeatherFuturedaysResultBean;
import com.maning.gankmm.bean.weather.WeatherInfoBean;
import com.maning.gankmm.bean.weather.zhixin.ZhixinSuggestionEntity;
import com.maning.gankmm.ui.view.ArcProgressView;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by maning on 2017/4/6.
 */

public class WeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private WeatherInfoBean weatherEntity;
    private HolidayBean holidayBean;
    private LayoutInflater layoutInflater;
    private ZhixinSuggestionEntity lifeSuggestionBean;
    //预测
    private List<WeatherFuturedaysResultBean.DataEntity.ForecastsEntity> weatherForecasts;


    public WeatherAdapter(Context context, WeatherInfoBean weatherEntity, HolidayBean holidayBean, ZhixinSuggestionEntity lifeSuggestionBean) {
        this.mContext = context;
        this.weatherEntity = weatherEntity;
        this.holidayBean = holidayBean;
        this.lifeSuggestionBean = lifeSuggestionBean;
        layoutInflater = LayoutInflater.from(this.mContext);
    }

    public void updateWeatherForecasts(List<WeatherFuturedaysResultBean.DataEntity.ForecastsEntity> weatherForecasts) {
        this.weatherForecasts = weatherForecasts;
        notifyDataSetChanged();
    }

    public void updateDatas(WeatherInfoBean weatherEntity, HolidayBean holidayBean, ZhixinSuggestionEntity lifeSuggestionBean) {
        this.weatherEntity = weatherEntity;
        this.holidayBean = holidayBean;
        this.lifeSuggestionBean = lifeSuggestionBean;
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
            View inflate = layoutInflater.inflate(R.layout.item_weather_suggestion, parent, false);
            return new WeatherAdapter.MyViewHolderSuggestion(inflate);
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
            myViewHolder01.tv_01.setText(weatherEntity.getTemperature() + "°");
            myViewHolder01.tv_02.setText(weatherEntity.getWeather_desc());
            myViewHolder01.tv_03.setText("湿度：" + weatherEntity.getHumidity() + "%");
            myViewHolder01.tv_04.setText("体感：" + weatherEntity.getFeels_like() + "°");
            myViewHolder01.tv_05.setText(weatherEntity.getWind_direction() + " " + weatherEntity.getWind_scale() + "级");

        } else if (holder instanceof MyViewHolder02) {
            final MyViewHolder02 myViewHolder02 = (MyViewHolder02) holder;

            //初始化RecycleView
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            myViewHolder02.recycle_later.setLayoutManager(linearLayoutManager);
            myViewHolder02.recycle_later.setItemAnimator(new DefaultItemAnimator());
            myViewHolder02.recycle_later.addItemDecoration(new VerticalDividerItemDecoration.Builder(mContext).color(mContext.getResources().getColor(R.color.lineColor)).build());
            if (weatherForecasts != null && weatherForecasts.size() > 0) {
                Weather2Adapter weather2Adapter = new Weather2Adapter(mContext, weatherForecasts);
                myViewHolder02.recycle_later.setAdapter(weather2Adapter);

            }
        } else if (holder instanceof MyViewHolderSuggestion) {
            final MyViewHolderSuggestion viewHolderSuggestion = (MyViewHolderSuggestion) holder;
            //生活指数
            if (lifeSuggestionBean != null) {
                viewHolderSuggestion.tv_suggestion_01.setText(lifeSuggestionBean.getCar_washing().getBrief());
                viewHolderSuggestion.tv_suggestion_02.setText(lifeSuggestionBean.getDressing().getBrief());
                viewHolderSuggestion.tv_suggestion_03.setText(lifeSuggestionBean.getFlu().getBrief());
                viewHolderSuggestion.tv_suggestion_04.setText(lifeSuggestionBean.getSport().getBrief());
                viewHolderSuggestion.tv_suggestion_05.setText(lifeSuggestionBean.getTravel().getBrief());
                viewHolderSuggestion.tv_suggestion_06.setText(lifeSuggestionBean.getUv().getBrief());
            } else {
                viewHolderSuggestion.tv_suggestion_01.setText("洗车");
                viewHolderSuggestion.tv_suggestion_02.setText("穿衣");
                viewHolderSuggestion.tv_suggestion_03.setText("感冒");
                viewHolderSuggestion.tv_suggestion_04.setText("运动");
                viewHolderSuggestion.tv_suggestion_05.setText("旅游");
                viewHolderSuggestion.tv_suggestion_06.setText("紫外线");
            }
        } else if (holder instanceof MyViewHolder04) {
            final MyViewHolder04 myViewHolder04 = (MyViewHolder04) holder;

            myViewHolder04.tv_01.setText(holidayBean.getDate());
            myViewHolder04.tv_06.setText(holidayBean.getTypeDes());
            myViewHolder04.tv_02.setText(holidayBean.getLunarCalendar());
            myViewHolder04.tv_03.setText(holidayBean.getYearTips() + " (" + holidayBean.getChineseZodiac() + ")  - " + holidayBean.getSolarTerms());
            myViewHolder04.tv_04.setText(holidayBean.getSuit());
            myViewHolder04.tv_05.setText(holidayBean.getAvoid());

        }


    }

    @Override
    public int getItemCount() {
        if (holidayBean == null) {
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

    public static class MyViewHolderSuggestion extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_suggestion_01)
        TextView tv_suggestion_01;
        @Bind(R.id.tv_suggestion_02)
        TextView tv_suggestion_02;
        @Bind(R.id.tv_suggestion_03)
        TextView tv_suggestion_03;
        @Bind(R.id.tv_suggestion_04)
        TextView tv_suggestion_04;
        @Bind(R.id.tv_suggestion_05)
        TextView tv_suggestion_05;
        @Bind(R.id.tv_suggestion_06)
        TextView tv_suggestion_06;

        public MyViewHolderSuggestion(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class MyViewHolderAir extends RecyclerView.ViewHolder {

        @Bind(R.id.arc_progress)
        ArcProgressView arc_progress;

        public MyViewHolderAir(View itemView) {
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
        @Bind(R.id.tv_06)
        TextView tv_06;

        public MyViewHolder04(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
