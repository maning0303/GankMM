package com.maning.gankmm.ui.iView;

import com.maning.gankmm.bean.fir.AppUpdateInfo;
import com.maning.gankmm.bean.weather.WeatherInfoBean;

/**
 * Created by maning on 16/6/21.
 */
public interface IMainView {

    void showToast(String msg);

    void showAppUpdateDialog(AppUpdateInfo appUpdateInfo);

    void initWeatherInfo(WeatherInfoBean weatherInfoBean);

    void updateLocationInfo(String provinceName, String cityName, double longitude, double latitude);

}
