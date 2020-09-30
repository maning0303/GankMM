package com.maning.gankmm.http.gank2;

import com.maning.gankmm.bean.gank2.Gank2CategoryListBean;
import com.maning.gankmm.bean.gank2.Gank2CategoryTypeListBean;
import com.maning.gankmm.constant.Constants;
import com.maning.gankmm.http.BuildApi;
import com.maning.gankmm.http.callback.CommonHttpCallback;

/**
 * @author : maning
 * @date : 2020-09-29
 * @desc :
 */
public class GankApi2 {

    public static void getCategorys(String category, String type, int page, int pageSize, CommonHttpCallback<Gank2CategoryTypeListBean> httpCallback) {
        BuildApi.getGank2APIService().getCagetorys(category, type, page, pageSize).enqueue(httpCallback);
    }

    public static void getRandomDatas(String category, String type, int count, CommonHttpCallback<Gank2CategoryTypeListBean> httpCallback) {
        BuildApi.getGank2APIService().getRandomDatas(category, type, count).enqueue(httpCallback);
    }

    public static void getCategoryTitles(String category, CommonHttpCallback<Gank2CategoryListBean> httpCallback) {
        if (Constants.Catrgory_GankHuo.equals(category)) {
            getCategoriesGanHuo(httpCallback);
        } else if (Constants.Catrgory_Article.equals(category)) {
            getCategoriesArticle(httpCallback);
        } else {
            httpCallback.onFinish();
        }
    }

    public static void getCategoriesGanHuo(CommonHttpCallback<Gank2CategoryListBean> httpCallback) {
        BuildApi.getGank2APIService().getCategoriesGanHuo().enqueue(httpCallback);
    }

    public static void getCategoriesArticle(CommonHttpCallback<Gank2CategoryListBean> httpCallback) {
        BuildApi.getGank2APIService().getCategoriesArticle().enqueue(httpCallback);
    }

}
