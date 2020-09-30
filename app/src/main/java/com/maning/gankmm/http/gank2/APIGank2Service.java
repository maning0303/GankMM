package com.maning.gankmm.http.gank2;

import com.maning.gankmm.bean.gank2.Gank2CategoryListBean;
import com.maning.gankmm.bean.gank2.Gank2CategoryTypeListBean;
import com.maning.gankmm.bean.gank2.Gank2SearchListBean;

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
    Call<Gank2CategoryTypeListBean> getCagetorys(@Path("category") String category,
                                                 @Path("type") String type,
                                                 @Path("page") int page,
                                                 @Path("count") int count
    );

    //https://gank.io/api/v2/random/category/<category>/type/<type>/count/<count>
    @Headers("Cache-Control: public, max-age=300")
    @GET("random/category/{category}/type/{type}/count/{count}")
    Call<Gank2CategoryTypeListBean> getRandomDatas(@Path("category") String category,
                                                   @Path("type") String type,
                                                   @Path("count") int count
    );

    //获取干货所有子分类
    //https://gank.io/api/v2/categories/GanHuo
    @GET("categories/GanHuo")
    Call<Gank2CategoryListBean> getCategoriesGanHuo();

    //获取文章所有子分类
    //https://gank.io/api/v2/categories/Article
    @GET("categories/Article")
    Call<Gank2CategoryListBean> getCategoriesArticle();

    //搜索
    //https://gank.io/api/v2/search/android/category/All/type/All/page/1/count/10
    @Headers("Cache-Control: public, max-age=120")
    @GET("search/{keyword}/category/All/type/All/page/{page}/count/{count}")
    Call<Gank2SearchListBean> getSearchDatas(@Path("keyword") String keyword,
                                            @Path("page") int page,
                                            @Path("count") int count
    );

}
