package com.maning.gankmm.http;

import com.maning.gankmm.app.MyApplication;
import com.maning.gankmm.constant.Constants;
import com.maning.gankmm.http.gank.APIGankService;
import com.maning.gankmm.http.gank2.APIGank2Service;
import com.maning.gankmm.http.mob.APIMobService;
import com.maning.gankmm.http.update.APIUpdateService;
import com.maning.gankmm.http.weather.APIWeatherService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 获取网络框架类
 */
public class BuildApi {

    private static Map<String, Retrofit> retrofitMap = new HashMap<>();

    public static <T> T getInterface(String baseUrl, Class<T> classs) {
        return getRetrofit(baseUrl).create(classs);
    }

    private static Retrofit getRetrofit(String baseUrl) {
        if (retrofitMap.containsKey(baseUrl)) {
            return retrofitMap.get(baseUrl);
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(MyApplication.defaultOkHttpClient())
                .build();
        retrofitMap.put(baseUrl, retrofit);
        return retrofitMap.get(baseUrl);
    }


    public static APIGankService getGankAPIService() {
        return getInterface(Constants.BASEURL, APIGankService.class);
    }

    public static APIMobService getMobAPIService() {
        return getInterface(Constants.URL_Mob, APIMobService.class);
    }

    public static APIGank2Service getGank2APIService() {
        return getInterface(Constants.GANK2_BASEURL, APIGank2Service.class);
    }

    public static APIUpdateService getUpdateAPIService() {
        return getInterface(Constants.FIR_BASEURL, APIUpdateService.class);
    }

    public static APIWeatherService getWeatherAPIService() {
        return getInterface(Constants.BASEURL_WEATHER, APIWeatherService.class);
    }

}
