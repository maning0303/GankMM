package com.maning.gankmm.ui.iView;

import com.maning.gankmm.bean.mob.CalendarInfoEntity;
import com.maning.gankmm.bean.mob.WeatherBeseEntity;
import com.maning.gankmm.bean.rolltools.HolidayBean;
import com.maning.gankmm.bean.weather.WeatherInfoBean;
import com.maning.gankmm.bean.weather.zhixin.ZhixinSuggestionEntity;

/**
 * Created by maning on 16/6/21.
 */
public interface IWeatherView {

    void showToast(String msg);

    void initWeatherInfo(WeatherInfoBean weatherInfoBean);

    void initLifeSuggestionInfo(ZhixinSuggestionEntity suggestion);

    void overRefresh();

    void updateCalendarInfo(HolidayBean holidayBean);

}
