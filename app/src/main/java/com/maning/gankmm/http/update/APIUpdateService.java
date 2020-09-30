package com.maning.gankmm.http.update;

import com.maning.gankmm.bean.AppUpdateInfo;
import com.maning.gankmm.bean.CalendarInfoEntity;
import com.maning.gankmm.bean.CitysEntity;
import com.maning.gankmm.bean.DayEntity;
import com.maning.gankmm.bean.HttpResult;
import com.maning.gankmm.bean.MobBaseEntity;
import com.maning.gankmm.bean.WeatherBeseEntity;
import com.maning.gankmm.bean.mob.MobBankCard;
import com.maning.gankmm.bean.mob.MobCarDetailsEntity;
import com.maning.gankmm.bean.mob.MobCarEntity;
import com.maning.gankmm.bean.mob.MobCarItemEntity;
import com.maning.gankmm.bean.mob.MobCookCategoryEntity;
import com.maning.gankmm.bean.mob.MobCookDetailEntity;
import com.maning.gankmm.bean.mob.MobDictEntity;
import com.maning.gankmm.bean.mob.MobFlightEntity;
import com.maning.gankmm.bean.mob.MobHealthEntity;
import com.maning.gankmm.bean.mob.MobHistoryTodayEntity;
import com.maning.gankmm.bean.mob.MobIdCardEntity;
import com.maning.gankmm.bean.mob.MobIdiomEntity;
import com.maning.gankmm.bean.mob.MobIpEntity;
import com.maning.gankmm.bean.mob.MobLotteryEntity;
import com.maning.gankmm.bean.mob.MobOilPriceEntity;
import com.maning.gankmm.bean.mob.MobPhoneAddressEntity;
import com.maning.gankmm.bean.mob.MobPostCodeEntity;
import com.maning.gankmm.bean.mob.MobTrainEntity;
import com.maning.gankmm.bean.mob.MobTrainNoEntity;
import com.maning.gankmm.bean.mob.MobUserInfo;
import com.maning.gankmm.bean.mob.MobWxArticleListEntity;
import com.maning.gankmm.bean.mob.MobWxCategoryEntity;
import com.maning.gankmm.constant.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * 接口调用的工具类
 */
public interface APIUpdateService {

    //获取fir.im中的GankMM的最新版本
    @Headers("Cache-Control: public, max-age=3600")
    @GET(Constants.URL_AppUpdateInfo)
    Call<AppUpdateInfo> getTheLastAppInfo();

}
