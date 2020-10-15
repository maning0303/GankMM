package com.maning.gankmm.ui.presenter;

/**
 * Created by maning on 16/6/21.
 */
public interface IWeatherPresenter {

    void getCityWeather(String provinceName, String cityName, double longitude, double latitude);

    void getCityWeatherFutureDays(String provinceName, String cityName, double longitude, double latitude);

    void getCalendarInfo();

    void getLifeSuggestion(double longitude, double latitude);

}
