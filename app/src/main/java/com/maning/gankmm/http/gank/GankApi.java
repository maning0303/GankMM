package com.maning.gankmm.http.gank;

import com.maning.gankmm.bean.gank.GankDayBean;
import com.maning.gankmm.bean.gank.GankHistoryListBean;
import com.maning.gankmm.http.BuildApi;
import com.maning.gankmm.http.callback.CommonHttpCallback;

/**
 * Created by maning on 16/3/2.
 * gankid 第一版本接口
 */
public class GankApi {

    public static void getHistoryData(CommonHttpCallback<GankHistoryListBean> httpCallback) {
        BuildApi.getGankAPIService().getGankHistoryDate().enqueue(httpCallback);
    }

    /**
     * 获取一天的数据
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static void getOneDayData(String year, String month, String day, CommonHttpCallback<GankDayBean> httpCallback) {
        BuildApi.getGankAPIService().getOneDayData(year, month, day).enqueue(httpCallback);
    }

}
