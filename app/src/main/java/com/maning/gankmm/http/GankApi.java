package com.maning.gankmm.http;

import com.maning.gankmm.R;
import com.maning.gankmm.app.MyApplication;
import com.maning.gankmm.bean.CalendarInfoEntity;
import com.maning.gankmm.bean.CitysEntity;
import com.maning.gankmm.bean.DayEntity;
import com.maning.gankmm.bean.HttpResult;
import com.maning.gankmm.bean.MobBaseEntity;
import com.maning.gankmm.bean.WeatherBeseEntity;
import com.maning.gankmm.constant.Constants;
import com.maning.gankmm.utils.UserUtils;
import com.socks.library.KLog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by maning on 16/3/2.
 * <p/>
 * 所有的网络请求
 */
public class GankApi {

    public final static String GET_DATA_FAIL = MyApplication.getIntstance().getString(R.string.gank_get_data_fail);
    public final static String NET_FAIL = MyApplication.getIntstance().getString(R.string.gank_net_fail);


    public static Call<HttpResult<List<String>>> getHistoryData(final int what, final MyCallBack myCallBack) {

        Call<HttpResult<List<String>>> gankHistoryDate = BuildApi.getAPIService().getGankHistoryDate();

        gankHistoryDate.enqueue(new Callback<HttpResult<List<String>>>() {
            @Override
            public void onResponse(Call<HttpResult<List<String>>> call, Response<HttpResult<List<String>>> response) {
                if (response.isSuccessful()) {
                    HttpResult<List<String>> httpResult = response.body();
                    if (httpResult != null) {
                        if (!httpResult.isError()) {
                            List<String> gankEntityList = httpResult.getResults();
                            KLog.i("getHistoryData---success：" + gankEntityList.toString());
                            myCallBack.onSuccessList(what, gankEntityList);
                        } else {
                            myCallBack.onFail(what, GET_DATA_FAIL);
                        }
                    } else {
                        myCallBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    myCallBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<HttpResult<List<String>>> call, Throwable t) {
                KLog.e("getHistoryData-----onFailure：" + t.toString());
                //数据错误
                myCallBack.onFail(what, NET_FAIL);
            }
        });

        return gankHistoryDate;

    }

    /**
     * 获取一天的数据
     *
     * @param year
     * @param month
     * @param day
     * @param what
     * @param myCallBack
     * @return
     */
    public static Call<DayEntity> getOneDayData(String year, String month, String day, final int what, final MyCallBack myCallBack) {

        Call<DayEntity> oneDayData = BuildApi.getAPIService().getOneDayData(year, month, day);

        oneDayData.enqueue(new Callback<DayEntity>() {
            @Override
            public void onResponse(Call<DayEntity> call, Response<DayEntity> response) {
                if (response.isSuccessful()) {
                    DayEntity body = response.body();
                    if (body != null) {
                        if (!body.isError()) {
                            KLog.i("getOneDayData---success：" + body.toString());
                            myCallBack.onSuccess(what, body);
                        } else {
                            myCallBack.onFail(what, GET_DATA_FAIL);
                        }
                    } else {
                        myCallBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    myCallBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<DayEntity> call, Throwable t) {
                KLog.e("getOneDayData-----onFailure：" + t.toString());
                //数据错误
                myCallBack.onFail(what, NET_FAIL);
            }
        });

        return oneDayData;
    }

    /***
     * 获取城市列表
     *
     * @param what
     * @param myCallBack
     * @return
     */
    public static Call<CitysEntity> getCitys(final int what, final MyCallBack myCallBack) {

        Call<CitysEntity> entityCall = BuildApi.getAPIService().getCitys(Constants.URL_APP_Key);

        entityCall.enqueue(new Callback<CitysEntity>() {
            @Override
            public void onResponse(Call<CitysEntity> call, Response<CitysEntity> response) {
                if (response.isSuccessful()) {
                    CitysEntity citysEntity = response.body();
                    if (citysEntity != null) {
                        //保存
                        UserUtils.saveCitysCache(citysEntity);
                        if (citysEntity.getMsg().equals("success")) {
                            KLog.i("getCitys---success：" + citysEntity.toString());
                            myCallBack.onSuccessList(what, citysEntity.getResult());
                        } else {
                            myCallBack.onFail(what, GET_DATA_FAIL);
                        }
                    } else {
                        myCallBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    myCallBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<CitysEntity> call, Throwable t) {
                KLog.e("getCitys-----onFailure：" + t.toString());
                //数据错误
                myCallBack.onFail(what, NET_FAIL);
            }
        });

        return entityCall;
    }


    /***
     * 获取城市天气信息
     *
     * @param city
     * @param province
     * @param what
     * @param myCallBack
     * @return
     */
    public static Call<WeatherBeseEntity> getCityWeather(String city, String province, final int what, final MyCallBack myCallBack) {

        Call<WeatherBeseEntity> weatherEntityCall = BuildApi.getAPIService().getCityWeather(Constants.URL_APP_Key, city, province);

        weatherEntityCall.enqueue(new Callback<WeatherBeseEntity>() {
            @Override
            public void onResponse(Call<WeatherBeseEntity> call, Response<WeatherBeseEntity> response) {
                if (response.isSuccessful()) {
                    WeatherBeseEntity weatherBeseEntity = response.body();
                    if (weatherBeseEntity != null) {
                        if (weatherBeseEntity.getMsg().equals("success")) {
                            KLog.i("getCityWeather---success：" + weatherBeseEntity.toString());
                            myCallBack.onSuccessList(what, weatherBeseEntity.getResult());
                        } else {
                            myCallBack.onFail(what, GET_DATA_FAIL);
                        }
                    } else {
                        myCallBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    myCallBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<WeatherBeseEntity> call, Throwable t) {
                KLog.e("getCityWeather-----onFailure：" + t.toString());
                //数据错误
                myCallBack.onFail(what, NET_FAIL);
            }
        });

        return weatherEntityCall;
    }

    /***
     * 获取万年历信息
     *
     * @param date
     * @param what
     * @param myCallBack
     * @return
     */
    public static Call<MobBaseEntity<CalendarInfoEntity>> getCalendarInfo(String date, final int what, final MyCallBack myCallBack) {
        Call<MobBaseEntity<CalendarInfoEntity>> calendarInfoCall = BuildApi.getAPIService().getCalendarInfo(Constants.URL_APP_Key, date);
        calendarInfoCall.enqueue(new Callback<MobBaseEntity<CalendarInfoEntity>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<CalendarInfoEntity>> call, Response<MobBaseEntity<CalendarInfoEntity>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<CalendarInfoEntity> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            KLog.i("getCalendarInfo---success：" + body.toString());
                            myCallBack.onSuccess(what, body.getResult());
                        } else {
                            myCallBack.onFail(what, GET_DATA_FAIL);
                        }
                    } else {
                        myCallBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    myCallBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<CalendarInfoEntity>> call, Throwable t) {
                KLog.e("getCalendarInfo-----onFailure：" + t.toString());
                //数据错误
                myCallBack.onFail(what, NET_FAIL);
            }
        });

        return calendarInfoCall;
    }

}
