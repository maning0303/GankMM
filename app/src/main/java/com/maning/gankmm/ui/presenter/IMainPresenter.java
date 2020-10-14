package com.maning.gankmm.ui.presenter;

/**
 * Created by maning on 16/6/21.
 */
public interface IMainPresenter {

    void initFeedBack();

    void initAppUpdate();

    void getLocationInfo();

    void getCitys();

    void destroyLocation();

    void initDatas();

    void getCityWeather(String provinceName, String cityName,double longitude,double latitude);

}
