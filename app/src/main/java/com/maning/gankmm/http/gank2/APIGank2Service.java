package com.maning.gankmm.http.gank2;

import com.maning.gankmm.bean.RandomEntity;
import com.maning.gankmm.bean.gank2.Gank2CategoryListBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;


/**
 * 接口调用的工具类
 */
public interface APIGank2Service {


    //https://gank.io/api/v2/data/category/<category>/type/<type>/page/<page>/count/<count>
    @Headers("Cache-Control: public, max-age=120")
    @GET("data/category/{category}/type/{type}/page/{page}/count/{count}")
    Call<Gank2CategoryListBean> getCagetorys(@Path("category") String category,
                                             @Path("type") String type,
                                             @Path("page") int page,
                                             @Path("count") int count
    );

    //https://gank.io/api/v2/random/category/<category>/type/<type>/count/<count>
    @Headers("Cache-Control: public, max-age=300")
    @GET("random/category/{category}/type/{type}/count/{count}")
    Call<Gank2CategoryListBean> getRandomDatas(@Path("category") String category,
                                      @Path("type") String type,
                                      @Path("count") int count
    );

}
