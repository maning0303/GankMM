package com.maning.gankmm.http.weather;

import com.maning.gankmm.bean.weather.caiyun.CaiyunWeatherRealTimeBean;
import com.maning.gankmm.bean.weather.zhixin.ZhixinLifeSuggestionResultBean;
import com.maning.gankmm.bean.weather.zhixin.ZhixinWeatherRealtimeBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * 接口调用的工具类
 */
public interface APIWeatherService {


    //https://api.seniverse.com/v3/weather/now.json?key=SkAwmmz_N361lNLX3&location=beijing&language=zh-Hans&unit=c
    @Headers("Cache-Control: public, max-age=120")
    @GET("weather/now.json")
    Call<ZhixinWeatherRealtimeBean> getWeatherNow(
            @Query("key") String key,
            @Query("location") String location
    );

    //https://api.seniverse.com/v3/life/suggestion.json?key=SkAwmmz_N361lNLX3&location=shanghai&language=zh-Hans
    @Headers("Cache-Control: public, max-age=120")
    @GET("life/suggestion.json")
    Call<ZhixinLifeSuggestionResultBean> getLifeSuggestion(
            @Query("key") String key,
            @Query("location") String location
    );

    @Headers("Cache-Control: public, max-age=120")
    @GET("https://api.caiyunapp.com/v2.5/Ry1gwjRU7ru2kosN/{location}/realtime.json")
    Call<CaiyunWeatherRealTimeBean> getWeatherFromCaiyun(@Path("location") String location
    );

}
