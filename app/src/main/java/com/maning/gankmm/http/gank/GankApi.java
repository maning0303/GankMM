package com.maning.gankmm.http.gank;

import com.maning.gankmm.R;
import com.maning.gankmm.app.MyApplication;
import com.maning.gankmm.bean.DayEntity;
import com.maning.gankmm.bean.HttpResult;
import com.maning.gankmm.http.BuildApi;
import com.maning.gankmm.http.callback.MyCallBack;
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

        Call<HttpResult<List<String>>> gankHistoryDate = BuildApi.getGankAPIService().getGankHistoryDate();

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

        Call<DayEntity> oneDayData = BuildApi.getGankAPIService().getOneDayData(year, month, day);

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

}
