package com.maning.gankmm.http.rolltools;

import com.maning.gankmm.bean.rolltools.HolidaySingleResultBean;
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


}
