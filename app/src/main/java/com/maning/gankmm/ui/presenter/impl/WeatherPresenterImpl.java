package com.maning.gankmm.ui.presenter.impl;

import android.content.Context;

import com.maning.gankmm.bean.rolltools.HolidayBean;
import com.maning.gankmm.bean.rolltools.HolidaySingleResultBean;
import com.maning.gankmm.bean.rolltools.WeatherFuturedaysResultBean;
import com.maning.gankmm.bean.weather.WeatherInfoBean;
import com.maning.gankmm.bean.weather.zhixin.ZhixinLifeSuggestionResultBean;
import com.maning.gankmm.bean.weather.zhixin.ZhixinSuggestionEntity;
import com.maning.gankmm.http.callback.CommonHttpCallback;
import com.maning.gankmm.http.rolltools.RolltoolsApi;
import com.maning.gankmm.http.weather.WeatherApi;
import com.maning.gankmm.ui.iView.IWeatherView;
import com.maning.gankmm.ui.presenter.IWeatherPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by maning on 2017/4/10.
 */

public class WeatherPresenterImpl extends BasePresenterImpl<IWeatherView> implements IWeatherPresenter {

    private Context context;

    public WeatherPresenterImpl(Context context, IWeatherView iWeatherView) {
        this.context = context;
        attachView(iWeatherView);
    }

    @Override
    public void getCityWeather(String provinceName, String cityName, double longitude, double latitude) {
        WeatherApi.getWeatherFromCaiyun(longitude, latitude, new WeatherApi.OnWeatherCallback() {
            @Override
            public void onSuccess(WeatherInfoBean weatherInfoBean) {
                mView.initWeatherInfo(weatherInfoBean);
                mView.overRefresh();
            }

            @Override
            public void onFail(String msg) {
                mView.showToast(msg);
                mView.overRefresh();
            }
        });

    }

    @Override
    public void getCityWeatherFutureDays(String provinceName, String cityName, double longitude, double latitude) {
        RolltoolsApi.getCityWeatherFutureDays(cityName, new CommonHttpCallback<WeatherFuturedaysResultBean>() {
            @Override
            public void onSuccess(WeatherFuturedaysResultBean result) {
                List<WeatherFuturedaysResultBean.DataEntity.ForecastsEntity> forecasts = result.getData().getForecasts();
                if(forecasts != null){
                    mView.initWeatherForecasts(forecasts);
                }
            }

            @Override
            public void onFail(int code, String message) {
                mView.showToast(message);
            }
        });
    }

    @Override
    public void getCalendarInfo() {
        //获取当天日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        RolltoolsApi.getHolidaySingle(date, new CommonHttpCallback<HolidaySingleResultBean>() {
            @Override
            public void onSuccess(HolidaySingleResultBean result) {
                HolidayBean holidayBean = result.getData();
                mView.updateCalendarInfo(holidayBean);

            }

            @Override
            public void onFail(int code, String message) {
                mView.showToast(message);
            }
        });
    }

    @Override
    public void getLifeSuggestion(double longitude, double latitude) {
        WeatherApi.getLifeSuggestionFromZhixin(longitude, latitude, new CommonHttpCallback<ZhixinLifeSuggestionResultBean>() {
            @Override
            public void onSuccess(ZhixinLifeSuggestionResultBean result) {
                ZhixinLifeSuggestionResultBean.ResultsEntity resultsEntity = result.getResults().get(0);
                ZhixinSuggestionEntity suggestion = resultsEntity.getSuggestion();
                if (suggestion == null) {
                    return;
                }
                mView.initLifeSuggestionInfo(suggestion);
            }

            @Override
            public void onFail(int code, String message) {
                mView.showToast(message);
            }
        });
    }
}
