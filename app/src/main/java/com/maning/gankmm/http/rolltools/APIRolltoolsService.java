package com.maning.gankmm.http.rolltools;

import com.maning.gankmm.bean.rolltools.HolidaySingleResultBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;


/**
 * 接口调用的工具类
 */
public interface APIRolltoolsService {


    @Headers({
            "app_id:olkgphoyitdqikvi",
            "app_secret:SFdTdFB5aGVoamlXU2dZblpPZTRjQT09"
    })
    @GET("holiday/single/{date}")
    Call<HolidaySingleResultBean> getHolidaySingle(
            @Path("date") String date
    );

}
