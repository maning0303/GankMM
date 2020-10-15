package com.maning.gankmm.ui.iView;

import com.maning.gankmm.bean.rolltools.HolidayBean;
import com.maning.gankmm.bean.rolltools.WeatherFuturedaysResultBean;
import com.maning.gankmm.bean.weather.WeatherInfoBean;
import com.maning.gankmm.bean.weather.zhixin.ZhixinSuggestionEntity;

import java.util.List;

/**
 * Created by maning on 16/6/21.
 */
public interface IWeatherView {

    void showToast(String msg);

    void initWeatherInfo(WeatherInfoBean weatherInfoBean);

    void initLifeSuggestionInfo(ZhixinSuggestionEntity suggestion);

    void initWeatherForecasts(List<WeatherFuturedaysResultBean.DataEntity.ForecastsEntity> weatherForecasts);

    void overRefresh();

    void updateCalendarInfo(HolidayBean holidayBean);

}
