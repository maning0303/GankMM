package com.maning.gankmm.ui.iView;

import com.maning.gankmm.bean.fir.AppUpdateInfo;
import com.maning.gankmm.bean.mob.WeatherBeseEntity;
import com.maning.gankmm.bean.weather.WeatherBean;
import com.maning.gankmm.bean.weather.WeatherNowBean;

/**
 * Created by maning on 16/6/21.
 */
public interface IMainView {

    void showToast(String msg);

    void showAppUpdateDialog(AppUpdateInfo appUpdateInfo);

    void initWeatherInfo(WeatherBean weatherBean);

    void updateLocationInfo(String provinceName, String cityName);

}
