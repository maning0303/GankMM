package com.maning.gankmm.http.gank;

import com.maning.gankmm.bean.gank.GankDayBean;
import com.maning.gankmm.bean.gank.GankHistoryListBean;
import com.maning.gankmm.constant.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;


/**
 * 接口调用的工具类
 */
public interface APIGankService {

    //这里填写全部路径就会覆盖掉Build得BaseUrl
    @Headers("Cache-Control: public, max-age=3600")
    @GET(Constants.URL_HistoryDate)
    Call<GankHistoryListBean> getGankHistoryDate();

    //http://gank.io/api/day/2015/08/06 --- 每日数据
    @GET("day/{year}/{month}/{day}")
    Call<GankDayBean> getOneDayData(@Path("year") String year,
                                    @Path("month") String month,
                                    @Path("day") String day
    );

}
