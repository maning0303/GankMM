package com.maning.gankmm.ui.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import com.maning.gankmm.bean.mob.CalendarInfoEntity;
import com.maning.gankmm.bean.weather.WeatherInfoBean;
import com.maning.gankmm.bean.weather.zhixin.ZhixinLifeSuggestionResultBean;
import com.maning.gankmm.bean.weather.zhixin.ZhixinSuggestionEntity;
import com.maning.gankmm.http.callback.CommonHttpCallback;
import com.maning.gankmm.http.callback.MyCallBack;
import com.maning.gankmm.http.mob.MobApi;
import com.maning.gankmm.http.weather.WeatherApi;
import com.maning.gankmm.ui.iView.IWeatherView;
import com.maning.gankmm.ui.presenter.IWeatherPresenter;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by maning on 2017/4/10.
 */

public class WeatherPresenterImpl extends BasePresenterImpl<IWeatherView> implements IWeatherPresenter {

    private Context context;

    private MyCallBack httpCallBack = new MyCallBack() {
        @Override
        public void onSuccessList(int what, List results) {
            mView.overRefresh();
        }

        @Override
        public void onSuccess(int what, Object result) {
            if (mView == null) {
                return;
            }
            if (result == null) {
                return;
            }
            switch (what) {
                case 0x001:
                    CalendarInfoEntity calendarInfoEntity = (CalendarInfoEntity) result;
                    //刷新界面
                    mView.updateCalendarInfo(calendarInfoEntity);
                    break;
            }
        }

        @Override
        public void onFail(int what, String result) {
            if (mView == null) {
                return;
            }
            mView.overRefresh();
            if (!TextUtils.isEmpty(result)) {
                mView.showToast(result);
            }
        }
    };

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
    public void getCalendarInfo() {
        //获取当天日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new java.util.Date());
        MobApi.getCalendarInfo(date, 0x001, httpCallBack);
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
