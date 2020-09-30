package com.maning.gankmm.http.update;

import com.maning.gankmm.bean.AppUpdateInfo;
import com.maning.gankmm.constant.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;


/**
 * 接口调用的工具类
 */
public interface APIUpdateService {

    //获取fir.im中的GankMM的最新版本
    @Headers("Cache-Control: public, max-age=3600")
    @GET(Constants.URL_AppUpdateInfo)
    Call<AppUpdateInfo> getTheLastAppInfo();

}
