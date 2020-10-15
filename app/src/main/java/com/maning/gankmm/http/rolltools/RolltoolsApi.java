package com.maning.gankmm.http.rolltools;

import com.maning.gankmm.bean.rolltools.DictionaryResultBean;
import com.maning.gankmm.bean.rolltools.HistoryTodayBean;
import com.maning.gankmm.bean.rolltools.HolidaySingleResultBean;
import com.maning.gankmm.bean.rolltools.MobileLocationResultBean;
import com.maning.gankmm.bean.rolltools.RubbishTypeResultBean;
import com.maning.gankmm.bean.rolltools.WeatherFuturedaysResultBean;
import com.maning.gankmm.http.BuildApi;
import com.maning.gankmm.http.callback.CommonHttpCallback;

/**
 * @author : maning
 * @date : 2020-09-29
 * @desc :
 */
public class RolltoolsApi {


    /**
     * 获取指定日期的节假日及万年历信息
     *
     * @param date yyyyMMdd
     */
    public static void getHolidaySingle(String date, CommonHttpCallback<HolidaySingleResultBean> httpCallback) {
        BuildApi.getRooltoolsAPIService().getHolidaySingle(date).enqueue(httpCallback);
    }

    /**
     * 获取特定城市今天及未来天气信息
     *
     * @param cityName     {城市名} 传入你需要查询的城市，请尽量传入完整值，否则系统会自行匹配，可能会有误差
     * @param httpCallback
     */
    public static void getCityWeatherFutureDays(String cityName, CommonHttpCallback<WeatherFuturedaysResultBean> httpCallback) {
        BuildApi.getRooltoolsAPIService().getCityWeatherFutureDays(cityName).enqueue(httpCallback);
    }

    /**
     * 历史上的今天
     *
     * @param httpCallback
     */
    public static void getHistoryToday(CommonHttpCallback<HistoryTodayBean> httpCallback) {
        BuildApi.getRooltoolsAPIService().getHistoryToday().enqueue(httpCallback);
    }

    /**
     * 查询单个汉字的读音和含义。
     *
     * @param content
     * @param httpCallback
     */
    public static void convertDictionary(String content, CommonHttpCallback<DictionaryResultBean> httpCallback) {
        BuildApi.getRooltoolsAPIService().convertDictionary(content).enqueue(httpCallback);
    }

    /**
     * 垃圾分类
     * @param content
     * @param httpCallback
     */
    public static void getRubbishType(String content, CommonHttpCallback<RubbishTypeResultBean> httpCallback) {
        BuildApi.getRooltoolsAPIService().getRubbishType(content).enqueue(httpCallback);
    }

    /**
     * 手机号码归属地查询
     * @param phone
     * @param httpCallback
     */
    public static void getMobileLocation(String phone, CommonHttpCallback<MobileLocationResultBean> httpCallback) {
        BuildApi.getRooltoolsAPIService().getMobileLocation(phone).enqueue(httpCallback);
    }


}
