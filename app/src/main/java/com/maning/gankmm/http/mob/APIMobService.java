package com.maning.gankmm.http.mob;

import com.maning.gankmm.bean.mob.CalendarInfoEntity;
import com.maning.gankmm.bean.mob.CitysEntity;
import com.maning.gankmm.bean.mob.MobBaseEntity;
import com.maning.gankmm.bean.mob.WeatherBeseEntity;
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

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


/**
 * 接口调用的工具类
 */
public interface APIMobService {

    //获取天气信息
    @Headers("Cache-Control: public, max-age=300")
    @GET(Constants.URL_Mob + "v1/weather/query")
    Call<WeatherBeseEntity> getCityWeather(@Query("key") String appkey,
                                           @Query("city") String city,
                                           @Query("province") String province
    );

    //城市列表查询接口
    //http://apicloud.mob.com/v1/weather/citys?key=appkey
    @Headers("Cache-Control: public, max-age=300")
    @GET(Constants.URL_Mob + "v1/weather/citys")
    Call<CitysEntity> getCitys(@Query("key") String appkey
    );

    //万年历查询
    //http://apicloud.mob.com/appstore/calendar/day?key=appkey&date=2015-05-01
    @Headers("Cache-Control: public, max-age=300")
    @GET(Constants.URL_Mob + "appstore/calendar/day")
    Call<MobBaseEntity<CalendarInfoEntity>> getCalendarInfo(@Query("key") String appkey,
                                                            @Query("date") String date
    );

    //手机号码归属地查询
    //http://apicloud.mob.com/v1/mobile/address/query?phone=xxxx&key=1c9dccb9a2434
    @GET(Constants.URL_Mob + "v1/mobile/address/query")
    Call<MobBaseEntity<MobPhoneAddressEntity>> queryMobileAddress(@Query("key") String appkey,
                                                                  @Query("phone") String phone
    );

    //邮编查询
    //http://apicloud.mob.com/v1/postcode/query?code=102629&key=1c9dccb9a2434
    @GET(Constants.URL_Mob + "v1/postcode/query")
    Call<MobBaseEntity<MobPostCodeEntity>> queryPostCode(@Query("key") String appkey,
                                                         @Query("code") String code
    );

    //身份证查询
    //http://apicloud.mob.com/idcard/query?cardno=xxx&key=1c9dccb9a2434
    @GET(Constants.URL_Mob + "idcard/query")
    Call<MobBaseEntity<MobIdCardEntity>> queryIdcard(@Query("key") String appkey,
                                                     @Query("cardno") String cardno
    );

    //IP查询
    //http://apicloud.mob.com/ip/query?key=1c9dccb9a2434&ip=123.123.123.123
    @GET(Constants.URL_Mob + "ip/query")
    Call<MobBaseEntity<MobIpEntity>> queryIp(@Query("key") String appkey,
                                             @Query("ip") String ip
    );

    //微信精选分类查询
    //http://apicloud.mob.com/wx/article/category/query?key=123456
    @GET(Constants.URL_Mob + "wx/article/category/query")
    Call<MobBaseEntity<ArrayList<MobWxCategoryEntity>>> queryWxArticleCategory(@Query("key") String appkey
    );

    //微信精选列表查询
    //http://apicloud.mob.com/wx/article/search?key=123456&cid=1
    @GET(Constants.URL_Mob + "wx/article/search")
    Call<MobBaseEntity<MobWxArticleListEntity>> queryWxArticleList(@Query("key") String appkey,
                                                                   @Query("cid") String cid,
                                                                   @Query("page") int page,
                                                                   @Query("size") int size
    );

    /*金融基金*/

    //银行卡信息查询
    //http://apicloud.mob.com/appstore/bank/card/query?card=6228482898203884775&key=1c9dccb9a2434
    @GET(Constants.URL_Mob + "appstore/bank/card/query")
    Call<MobBaseEntity<MobBankCard>> queryBankCradInfo(@Query("key") String appkey,
                                                       @Query("card") String card
    );


    /*便民服务*/

    //健康知识
    //http://apicloud.mob.com/appstore/health/search?key=123456&name=巴豆
    @GET(Constants.URL_Mob + "appstore/health/search")
    Call<MobBaseEntity<MobHealthEntity>> queryHealth(@Query("key") String appkey,
                                                     @Query("name") String name,
                                                     @Query("page") int page,
                                                     @Query("size") int size
    );

    //历史上今天
    //http://apicloud.mob.com/appstore/history/query?day=0401&key=1c9dccb9a2434
    @GET(Constants.URL_Mob + "appstore/history/query")
    Call<MobBaseEntity<ArrayList<MobHistoryTodayEntity>>> queryHistory(@Query("key") String appkey,
                                                                       @Query("day") String day
    );

    //成语大全
    //http://apicloud.mob.com/appstore/idiom/query?name=%E4%B8%A2%E4%B8%89%E8%90%BD%E5%9B%9B&key=1c9dccb9a2434
    @GET(Constants.URL_Mob + "appstore/idiom/query")
    Call<MobBaseEntity<MobIdiomEntity>> queryIdiom(@Query("key") String appkey,
                                                   @Query("name") String name
    );

    //新华字典查询
    //http://apicloud.mob.com/appstore/dictionary/query?key=123456&name=赵
    @GET(Constants.URL_Mob + "appstore/dictionary/query")
    Call<MobBaseEntity<MobDictEntity>> queryDict(@Query("key") String appkey,
                                                 @Query("name") String name
    );

    //全国今日油价查询
    //http://apicloud.mob.com/oil/price/province/query?key=appkey
    @GET(Constants.URL_Mob + "oil/price/province/query")
    Call<MobBaseEntity<MobOilPriceEntity>> queryOilPrice(@Query("key") String appkey
    );

    //火车车次查询
    //http://apicloud.mob.com/train/tickets/queryByTrainNo?key=123456&trainno=G2
    @GET(Constants.URL_Mob + "train/tickets/queryByTrainNo")
    Call<MobBaseEntity<ArrayList<MobTrainNoEntity>>> queryByTrainNo(@Query("key") String appkey,
                                                                    @Query("trainno") String trainno
    );

    //火车站站查询
    //http://apicloud.mob.com/train/tickets/queryByStationToStation?key=123456&start=北京&end=上海
    @GET(Constants.URL_Mob + "train/tickets/queryByStationToStation")
    Call<MobBaseEntity<ArrayList<MobTrainEntity>>> queryByStationToStation(@Query("key") String appkey,
                                                                           @Query("start") String start,
                                                                           @Query("end") String end
    );

    //航线查询航班信息
    //http://apicloud.mob.com/flight/line/query?key=appkey&start=上海&end=海口
    @GET(Constants.URL_Mob + "flight/line/query")
    Call<MobBaseEntity<ArrayList<MobFlightEntity>>> queryFlightLineList(@Query("key") String appkey,
                                                                        @Query("start") String start,
                                                                        @Query("end") String end
    );

    //查询汽车品牌
    //http://apicloud.mob.com/car/brand/query?key=520520test
    @GET(Constants.URL_Mob + "car/brand/query")
    Call<MobBaseEntity<ArrayList<MobCarEntity>>> queryCarList(@Query("key") String appkey
    );

    //车型信息查询
    //http://apicloud.mob.com/car/seriesname/query?name=%E5%A5%A5%E8%BF%AAQ5&key=520520test
    @GET(Constants.URL_Mob + "car/seriesname/query")
    Call<MobBaseEntity<ArrayList<MobCarItemEntity>>> queryCarItems(@Query("key") String appkey,
                                                                   @Query("name") String name
    );

    //车型详细信息查询
    //http://apicloud.mob.com/car/series/query?key=appkey&cid=1060133
    @GET(Constants.URL_Mob + "car/series/query")
    Call<MobBaseEntity<ArrayList<MobCarDetailsEntity>>> queryCarDetails(@Query("key") String appkey,
                                                                        @Query("cid") String cid
    );

    //菜谱分类标签查询
    //http://apicloud.mob.com/v1/cook/category/query?key=appkey
    @GET(Constants.URL_Mob + "v1/cook/category/query")
    Call<MobBaseEntity<MobCookCategoryEntity>> queryCookCategory(@Query("key") String appkey
    );

    //按标签查询菜谱接口
    //http://apicloud.mob.com/v1/cook/menu/search?key=appkey&cid=0010001007&page=1&size=20
    @GET(Constants.URL_Mob + "v1/cook/menu/search")
    Call<MobBaseEntity<MobCookDetailEntity>> queryCookDetailsList(@Query("key") String appkey,
                                                                  @Query("cid") String cid,
                                                                  @Query("page") int page,
                                                                  @Query("size") int size
    );

    //支持彩种列表
    //http://apicloud.mob.com/lottery/list?key=appkey
    @GET(Constants.URL_Mob + "lottery/list")
    Call<MobBaseEntity<ArrayList<String>>> querylotteryList(@Query("key") String appkey
    );

    //彩票开奖结果查询
    //http://apicloud.mob.com/lottery/query?key=appkey&name=大乐透
    @GET(Constants.URL_Mob + "lottery/query")
    Call<MobBaseEntity<MobLotteryEntity>> querylotteryDetail(@Query("key") String appkey,
                                                             @Query("name") String name
    );


    /* ----------------------用户系统接口---------------------- */
    //用户注册
    //http://apicloud.mob.com/user/rigister?key=123456&username=tangsir&password=4AC36A18EA02AC6C
    @GET(Constants.URL_Mob + "user/rigister")
    Call<MobBaseEntity> userRegister(@Query("key") String appkey,
                                     @Query("username") String username,
                                     @Query("password") String password,
                                     @Query("email") String email
    );

    //用户登录
    //http://apicloud.mob.com/user/login?key=123456&username=tangsir&password=4AC36A18EA02AC6C
    @GET(Constants.URL_Mob + "user/login")
    Call<MobBaseEntity<MobUserInfo>> userLogin(@Query("key") String appkey,
                                               @Query("username") String username,
                                               @Query("password") String password
    );

    //用户数据新增和更新
    //http://apicloud.mob.com/user/data/put?key=123456&token=d8b5403cb22f6e17e8e57d8d8a24e497&uid=e5b0d1b60461ea4605cf27947f739bce&item=5bm06b6E&value=5aeT5ZCN
    @GET(Constants.URL_Mob + "user/data/put")
    Call<MobBaseEntity> userDataUpdate(@Query("key") String appkey,
                                       @Query("token") String token,
                                       @Query("uid") String uid,
                                       @Query("item") String item,
                                       @Query("value") String value
    );

    //用户数据查询
    //http://apicloud.mob.com/user/data/query?key=123456&token=d8b5403cb22f6e17e8e57d8d8a24e497&uid=e5b0d1b60461ea4605cf27947f739bce&item=5bm06b6E
    @GET(Constants.URL_Mob + "user/data/query")
    Call<MobBaseEntity> userDataQuery(@Query("key") String appkey,
                                      @Query("token") String token,
                                      @Query("uid") String uid,
                                      @Query("item") String item
    );

    //找回密码-获取验证码
    //http://apicloud.mob.com/user/password/retrieve?key=123456&username=duyp
    @GET(Constants.URL_Mob + "user/password/retrieve")
    Call<MobBaseEntity> userGetVerificationCode(@Query("key") String appkey,
                                                @Query("username") String username
    );


    //修改密码
    //http://apicloud.mob.com/user/password/change?key=123456&username=duyp&oldPassword=123456789&newPassword=a0987654321
    @GET(Constants.URL_Mob + "user/password/change")
    Call<MobBaseEntity> userModifyPsd(@Query("key") String appkey,
                                      @Query("username") String token,
                                      @Query("oldPassword") String oldPassword,
                                      @Query("newPassword") String newPassword,
                                      @Query("mode") String mode    //模式：1-用户输入旧密码;2-由用户通过找回密码接口获取系统随机码，默认为1
    );
}
