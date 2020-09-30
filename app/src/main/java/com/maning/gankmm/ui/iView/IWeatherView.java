package com.maning.gankmm.ui.iView;

import com.maning.gankmm.bean.mob.CalendarInfoEntity;
import com.maning.gankmm.bean.mob.WeatherBeseEntity;

/**
 * Created by maning on 16/6/21.
 */
public interface IWeatherView {

    void showToast(String msg);

    void initWeatherInfo(WeatherBeseEntity.WeatherBean weatherEntity);

    void overRefresh();

    void updateCalendarInfo(CalendarInfoEntity calendarInfoEntity);

}
