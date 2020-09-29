package com.maning.gankmm.http;

import com.maning.gankmm.app.MyApplication;
import com.maning.gankmm.constant.Constants;
import com.maning.gankmm.http.gank2.APIGank2Service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 获取网络框架类
 */
public class BuildApi {

    private static Retrofit retrofit;
    private static Retrofit gank2_api;

    public static APIService getAPIService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASEURL) //设置Base的访问路径
                    .addConverterFactory(GsonConverterFactory.create()) //设置默认的解析库：Gson
                    .client(MyApplication.defaultOkHttpClient())
                    .build();
        }
        return retrofit.create(APIService.class);
    }

    public static APIGank2Service getGank2APIService() {
        if (gank2_api == null) {
            gank2_api = new Retrofit.Builder()
                    .baseUrl(Constants.GANK2_BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(MyApplication.defaultOkHttpClient())
                    .build();
        }
        return gank2_api.create(APIGank2Service.class);
    }

}
