package com.maning.gankmm.http.gank2;

import com.maning.gankmm.bean.gank2.Gank2CategoryListBean;
import com.maning.gankmm.http.BuildApi;

/**
 * @author : maning
 * @date : 2020-09-29
 * @desc :
 */
public class GankApi2 {

    public static void getCategorys(String category, String type, int page, int pageSize,GankHttpCallback<Gank2CategoryListBean> httpCallback) {
        BuildApi.getGank2APIService().getCagetorys(category, type, page, pageSize).enqueue(httpCallback);
    }

    public static void getRandomDatas(String category, String type, int count,GankHttpCallback<Gank2CategoryListBean> httpCallback) {
        BuildApi.getGank2APIService().getRandomDatas(category, type, count).enqueue(httpCallback);
    }

}
