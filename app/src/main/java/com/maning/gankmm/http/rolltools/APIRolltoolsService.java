package com.maning.gankmm.http.rolltools;

import com.maning.gankmm.bean.rolltools.DictionaryResultBean;
import com.maning.gankmm.bean.rolltools.HistoryTodayBean;
import com.maning.gankmm.bean.rolltools.HolidaySingleResultBean;
import com.maning.gankmm.bean.rolltools.RubbishTypeResultBean;
import com.maning.gankmm.bean.rolltools.WeatherFuturedaysResultBean;
import com.maning.gankmm.constant.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * 接口调用的工具类
 */
public interface APIRolltoolsService {


    @Headers({
            "app_id:" + Constants.ROLL_TOOLS_APP_ID,
            "app_secret:" + Constants.ROLL_TOOLS_APP_SECRET
    })
    @GET("holiday/single/{date}")
    Call<HolidaySingleResultBean> getHolidaySingle(
            @Path("date") String date
    );


    @Headers({
            "app_id:" + Constants.ROLL_TOOLS_APP_ID,
            "app_secret:" + Constants.ROLL_TOOLS_APP_SECRET
    })
    @GET("weather/forecast/{cityName}")
    Call<WeatherFuturedaysResultBean> getCityWeatherFutureDays(
            @Path("cityName") String cityName
    );

    @Headers({
            "app_id:" + Constants.ROLL_TOOLS_APP_ID,
            "app_secret:" + Constants.ROLL_TOOLS_APP_SECRET
    })
    @GET("history/today?type=1")
    Call<HistoryTodayBean> getHistoryToday();


    @Headers({
            "app_id:" + Constants.ROLL_TOOLS_APP_ID,
            "app_secret:" + Constants.ROLL_TOOLS_APP_SECRET
    })
    @GET("convert/dictionary")
    Call<DictionaryResultBean> convertDictionary(
            @Query("content") String content
    );

    @Headers({
            "app_id:" + Constants.ROLL_TOOLS_APP_ID,
            "app_secret:" + Constants.ROLL_TOOLS_APP_SECRET
    })
    @GET("rubbish/type")
    Call<RubbishTypeResultBean> getRubbishType(
            @Query("name") String name
    );

}
