package com.maning.gankmm.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maning.gankmm.R;
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
            return new WeatherAdapter.ViewHolderWeatherInfo(inflate);
        } else if (viewType == 1) {
            View inflate = layoutInflater.inflate(R.layout.item_weather_later, parent, false);
            return new WeatherAdapter.ViewHolderWeatherForecast(inflate);
        } else if (viewType == 2) {
            View inflate = layoutInflater.inflate(R.layout.item_weather_suggestion, parent, false);
            return new WeatherAdapter.ViewHolderSuggestion(inflate);
        } else if (viewType == 3) {
            View inflate = layoutInflater.inflate(R.layout.item_weather_calendar, parent, false);
            return new WeatherAdapter.ViewHolderHoliday(inflate);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderWeatherInfo) {
            final ViewHolderWeatherInfo viewHolderWeatherInfo = (ViewHolderWeatherInfo) holder;
            viewHolderWeatherInfo.tv_01.setText(weatherEntity.getTemperature() + "°");
            viewHolderWeatherInfo.tv_02.setText(weatherEntity.getWeather_desc());
            viewHolderWeatherInfo.tv_03.setText("湿度：" + weatherEntity.getHumidity() + "%");
            viewHolderWeatherInfo.tv_04.setText("体感：" + weatherEntity.getFeels_like() + "°");
            viewHolderWeatherInfo.tv_05.setText(weatherEntity.getWind_direction() + " " + weatherEntity.getWind_scale() + "级");

        } else if (holder instanceof ViewHolderWeatherForecast) {
            final ViewHolderWeatherForecast viewHolderWeatherForecast = (ViewHolderWeatherForecast) holder;

            //初始化RecycleView
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            viewHolderWeatherForecast.recycle_later.setLayoutManager(linearLayoutManager);
            viewHolderWeatherForecast.recycle_later.setItemAnimator(new DefaultItemAnimator());
            viewHolderWeatherForecast.recycle_later.addItemDecoration(new VerticalDividerItemDecoration.Builder(mContext).color(mContext.getResources().getColor(R.color.lineColor)).build());
            if (weatherForecasts != null && weatherForecasts.size() > 0) {
                Weather2Adapter weather2Adapter = new Weather2Adapter(mContext, weatherForecasts);
                viewHolderWeatherForecast.recycle_later.setAdapter(weather2Adapter);

            }
        } else if (holder instanceof ViewHolderSuggestion) {
            final ViewHolderSuggestion viewHolderSuggestion = (ViewHolderSuggestion) holder;
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
        } else if (holder instanceof ViewHolderHoliday) {
            final ViewHolderHoliday viewHolderHoliday = (ViewHolderHoliday) holder;

            viewHolderHoliday.tv_01.setText(holidayBean.getDate());
            viewHolderHoliday.tv_06.setText(holidayBean.getTypeDes());
            viewHolderHoliday.tv_02.setText(holidayBean.getLunarCalendar());
            viewHolderHoliday.tv_03.setText(holidayBean.getYearTips() + " (" + holidayBean.getChineseZodiac() + ")  - " + holidayBean.getSolarTerms());
            viewHolderHoliday.tv_04.setText(holidayBean.getSuit());
            viewHolderHoliday.tv_05.setText(holidayBean.getAvoid());

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

    public static class ViewHolderWeatherInfo extends RecyclerView.ViewHolder {

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

        public ViewHolderWeatherInfo(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class ViewHolderWeatherForecast extends RecyclerView.ViewHolder {

        @Bind(R.id.recycle_later)
        RecyclerView recycle_later;
        @Bind(R.id.ll_root_weather_forecast)
        LinearLayout ll_root_weather_forecast;

        public ViewHolderWeatherForecast(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class ViewHolderSuggestion extends RecyclerView.ViewHolder {

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

        public ViewHolderSuggestion(View itemView) {
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


    public static class ViewHolderHoliday extends RecyclerView.ViewHolder {

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

        public ViewHolderHoliday(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
