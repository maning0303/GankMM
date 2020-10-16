package com.maning.gankmm.http.mob;

import android.text.TextUtils;

import com.maning.gankmm.R;
import com.maning.gankmm.app.MyApplication;
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
import com.maning.gankmm.http.BuildApi;
import com.maning.gankmm.http.callback.MyCallBack;
import com.maning.gankmm.utils.EncodeUtils;
import com.maning.gankmm.utils.UserUtils;
import com.socks.library.KLog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by maning on 2017/5/9.
 * Mob SDK 相关的API,接口已经被废弃
 */
@Deprecated
public class MobApi {

    public final static String GET_DATA_FAIL = MyApplication.getIntstance().getString(R.string.gank_get_data_fail);
    public final static String NET_FAIL = MyApplication.getIntstance().getString(R.string.gank_net_fail);


    public static Call<MobBaseEntity<MobOilPriceEntity>> queryOilPrice(final int what, final MyCallBack myCallBack) {

        Call<MobBaseEntity<MobOilPriceEntity>> call = BuildApi.getMobAPIService().queryOilPrice(Constants.URL_APP_Key);
        call.enqueue(new Callback<MobBaseEntity<MobOilPriceEntity>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<MobOilPriceEntity>> call, Response<MobBaseEntity<MobOilPriceEntity>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<MobOilPriceEntity> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            KLog.i("queryOilPrice---success：" + body.toString());
                            myCallBack.onSuccess(what, body.getResult());
                        } else {
                            myCallBack.onFail(what, body.getMsg());
                        }
                    } else {
                        myCallBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    myCallBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<MobOilPriceEntity>> call, Throwable t) {
                KLog.e("queryOilPrice-----onFailure：" + t.toString());
                //数据错误
                myCallBack.onFail(what, NET_FAIL);
            }
        });

        return call;

    }

    public static Call<MobBaseEntity<ArrayList<MobTrainNoEntity>>> queryByTrainNo(String trainNum, final int what, final MyCallBack myCallBack) {

        Call<MobBaseEntity<ArrayList<MobTrainNoEntity>>> call = BuildApi.getMobAPIService().queryByTrainNo(Constants.URL_APP_Key, trainNum);
        call.enqueue(new Callback<MobBaseEntity<ArrayList<MobTrainNoEntity>>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<ArrayList<MobTrainNoEntity>>> call, Response<MobBaseEntity<ArrayList<MobTrainNoEntity>>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<ArrayList<MobTrainNoEntity>> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            KLog.i("queryByTrainNo---success：" + body.toString());
                            myCallBack.onSuccessList(what, body.getResult());
                        } else {
                            myCallBack.onFail(what, body.getMsg());
                        }
                    } else {
                        myCallBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    myCallBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<ArrayList<MobTrainNoEntity>>> call, Throwable t) {
                KLog.e("queryByTrainNo-----onFailure：" + t.toString());
                //数据错误
                myCallBack.onFail(what, NET_FAIL);
            }
        });

        return call;

    }

    public static Call<MobBaseEntity<ArrayList<MobTrainEntity>>> queryByStationToStation(String start, String end, final int what, final MyCallBack myCallBack) {

        Call<MobBaseEntity<ArrayList<MobTrainEntity>>> call = BuildApi.getMobAPIService().queryByStationToStation(Constants.URL_APP_Key, start, end);
        call.enqueue(new Callback<MobBaseEntity<ArrayList<MobTrainEntity>>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<ArrayList<MobTrainEntity>>> call, Response<MobBaseEntity<ArrayList<MobTrainEntity>>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<ArrayList<MobTrainEntity>> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            KLog.i("queryByStationToStation---success：" + body.toString());
                            myCallBack.onSuccessList(what, body.getResult());
                        } else {
                            myCallBack.onFail(what, body.getMsg());
                        }
                    } else {
                        myCallBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    myCallBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<ArrayList<MobTrainEntity>>> call, Throwable t) {
                KLog.e("queryByStationToStation-----onFailure：" + t.toString());
                //数据错误
                myCallBack.onFail(what, NET_FAIL);
            }
        });

        return call;

    }

    public static Call<MobBaseEntity<ArrayList<MobFlightEntity>>> queryFlightLineList(String start, String end, final int what, final MyCallBack myCallBack) {

        Call<MobBaseEntity<ArrayList<MobFlightEntity>>> call = BuildApi.getMobAPIService().queryFlightLineList(Constants.URL_APP_Key, start, end);
        call.enqueue(new Callback<MobBaseEntity<ArrayList<MobFlightEntity>>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<ArrayList<MobFlightEntity>>> call, Response<MobBaseEntity<ArrayList<MobFlightEntity>>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<ArrayList<MobFlightEntity>> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            KLog.i("queryFlightLineList---success：" + body.toString());
                            myCallBack.onSuccessList(what, body.getResult());
                        } else {
                            myCallBack.onFail(what, body.getMsg());
                        }
                    } else {
                        myCallBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    myCallBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<ArrayList<MobFlightEntity>>> call, Throwable t) {
                KLog.e("queryFlightLineList-----onFailure：" + t.toString());
                //数据错误
                myCallBack.onFail(what, NET_FAIL);
            }
        });

        return call;

    }


    public static Call<MobBaseEntity<MobCookCategoryEntity>> queryCookCategory(final int what, final MyCallBack myCallBack) {

        Call<MobBaseEntity<MobCookCategoryEntity>> call = BuildApi.getMobAPIService().queryCookCategory(Constants.URL_APP_Key);
        call.enqueue(new Callback<MobBaseEntity<MobCookCategoryEntity>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<MobCookCategoryEntity>> call, Response<MobBaseEntity<MobCookCategoryEntity>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<MobCookCategoryEntity> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            KLog.i("queryCookCategory---success：" + body.toString());
                            myCallBack.onSuccess(what, body.getResult());
                        } else {
                            myCallBack.onFail(what, body.getMsg());
                        }
                    } else {
                        myCallBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    myCallBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<MobCookCategoryEntity>> call, Throwable t) {
                KLog.e("queryCookCategory-----onFailure：" + t.toString());
                //数据错误
                myCallBack.onFail(what, NET_FAIL);
            }
        });

        return call;

    }

    public static Call<MobBaseEntity<MobCookDetailEntity>> queryCookDetailsList(String cid, int pageIndex, int pageSize, final int what, final MyCallBack myCallBack) {

        Call<MobBaseEntity<MobCookDetailEntity>> call = BuildApi.getMobAPIService().queryCookDetailsList(Constants.URL_APP_Key, cid, pageIndex, pageSize);
        call.enqueue(new Callback<MobBaseEntity<MobCookDetailEntity>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<MobCookDetailEntity>> call, Response<MobBaseEntity<MobCookDetailEntity>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<MobCookDetailEntity> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            KLog.i("queryCookDetailsList---success：" + body.toString());
                            myCallBack.onSuccess(what, body.getResult());
                        } else {
                            myCallBack.onFail(what, body.getMsg());
                        }
                    } else {
                        myCallBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    myCallBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<MobCookDetailEntity>> call, Throwable t) {
                KLog.e("queryCookDetailsList-----onFailure：" + t.toString());
                //数据错误
                myCallBack.onFail(what, NET_FAIL);
            }
        });

        return call;

    }


    public static Call<MobBaseEntity<ArrayList<String>>> querylotteryList(final int what, final MyCallBack myCallBack) {

        Call<MobBaseEntity<ArrayList<String>>> call = BuildApi.getMobAPIService().querylotteryList(Constants.URL_APP_Key);
        call.enqueue(new Callback<MobBaseEntity<ArrayList<String>>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<ArrayList<String>>> call, Response<MobBaseEntity<ArrayList<String>>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<ArrayList<String>> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            KLog.i("querylotteryList---success：" + body.toString());
                            myCallBack.onSuccessList(what, body.getResult());
                        } else {
                            myCallBack.onFail(what, body.getMsg());
                        }
                    } else {
                        myCallBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    myCallBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<ArrayList<String>>> call, Throwable t) {
                KLog.e("querylotteryList-----onFailure：" + t.toString());
                //数据错误
                myCallBack.onFail(what, NET_FAIL);
            }
        });

        return call;

    }


    public static Call<MobBaseEntity<MobLotteryEntity>> querylotteryDetail(String name, final int what, final MyCallBack myCallBack) {

        Call<MobBaseEntity<MobLotteryEntity>> call = BuildApi.getMobAPIService().querylotteryDetail(Constants.URL_APP_Key, name);
        call.enqueue(new Callback<MobBaseEntity<MobLotteryEntity>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<MobLotteryEntity>> call, Response<MobBaseEntity<MobLotteryEntity>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity<MobLotteryEntity> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            KLog.i("querylotteryDetail---success：" + body.toString());
                            myCallBack.onSuccess(what, body.getResult());
                        } else {
                            myCallBack.onFail(what, body.getMsg());
                        }
                    } else {
                        myCallBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    myCallBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<MobLotteryEntity>> call, Throwable t) {
                KLog.e("querylotteryDetail-----onFailure：" + t.toString());
                //数据错误
                myCallBack.onFail(what, NET_FAIL);
            }
        });

        return call;

    }

    /* -----start------用户系统------------ */

    public static Call<MobBaseEntity> userRegister(String userName, String userPsd, String userEmail, final int what, final MyCallBack myCallBack) {

        Call<MobBaseEntity> call = BuildApi.getMobAPIService().userRegister(Constants.URL_APP_Key, userName, userPsd, userEmail);
        call.enqueue(new Callback<MobBaseEntity>() {
            @Override
            public void onResponse(Call<MobBaseEntity> call, Response<MobBaseEntity> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            KLog.i("userRegister---success：" + body.toString());
                            myCallBack.onSuccess(what, body.getResult());
                        } else {
                            myCallBack.onFail(what, body.getMsg());
                        }
                    } else {
                        myCallBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    myCallBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity> call, Throwable t) {
                KLog.e("userRegister-----onFailure：" + t.toString());
                //数据错误
                myCallBack.onFail(what, NET_FAIL);
            }
        });

        return call;

    }

    public static Call<MobBaseEntity<MobUserInfo>> userLogin(String userName, String userPsd, final int what, final MyCallBack myCallBack) {

        Call<MobBaseEntity<MobUserInfo>> call = BuildApi.getMobAPIService().userLogin(Constants.URL_APP_Key, userName, userPsd);
        call.enqueue(new Callback<MobBaseEntity<MobUserInfo>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<MobUserInfo>> call, Response<MobBaseEntity<MobUserInfo>> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            KLog.i("userLogin---success：" + body.toString());
                            myCallBack.onSuccess(what, body.getResult());
                        } else {
                            myCallBack.onFail(what, body.getMsg());
                        }
                    } else {
                        myCallBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    myCallBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<MobUserInfo>> call, Throwable t) {
                KLog.e("userLogin-----onFailure：" + t.toString());
                //数据错误
                myCallBack.onFail(what, NET_FAIL);
            }
        });

        return call;

    }


    public static Call<MobBaseEntity> userDataUpdate(String itemName, String value, final int what, final MyCallBack myCallBack) {
        //加密
        String itemNameBase64 = EncodeUtils.EncodeBase64(itemName);
        KLog.i("itemNameBase64:" + itemNameBase64);
        String valueBase64 = EncodeUtils.EncodeBase64(value);
        KLog.i("valueBase64:" + valueBase64);
        KLog.i("valueBase64.length():" + valueBase64.length());

        MobUserInfo userCache = UserUtils.getUserCache();
        Call<MobBaseEntity> call = BuildApi.getMobAPIService().userDataUpdate(Constants.URL_APP_Key, userCache.getToken(), userCache.getUid(), itemNameBase64, valueBase64);
        call.enqueue(new Callback<MobBaseEntity>() {
            @Override
            public void onResponse(Call<MobBaseEntity> call, Response<MobBaseEntity> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            KLog.i("userDataUpdate---success：" + body.toString());
                            //解密
                            Object result = body.getResult();
                            if (result != null) {
                                String resutStr = (String) result;
                                if (!TextUtils.isEmpty(resutStr)) {
                                    result = EncodeUtils.DecodeBase64(resutStr);
                                }
                            }
                            myCallBack.onSuccess(what, result);
                        } else {
                            myCallBack.onFail(what, body.getMsg());
                        }
                    } else {
                        myCallBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    myCallBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity> call, Throwable t) {
                KLog.e("userDataUpdate-----onFailure：" + t.toString());
                //数据错误
                myCallBack.onFail(what, NET_FAIL);
            }
        });

        return call;

    }


    public static Call<MobBaseEntity> userDataQuery(String itemName, final int what, final MyCallBack myCallBack) {
        //加密
        String itemNameBase64 = EncodeUtils.EncodeBase64(itemName);
        KLog.i("itemNameBase64:" + itemNameBase64);
        MobUserInfo userCache = UserUtils.getUserCache();
        Call<MobBaseEntity> call = BuildApi.getMobAPIService().userDataQuery(Constants.URL_APP_Key, userCache.getToken(), userCache.getUid(), itemNameBase64);
        call.enqueue(new Callback<MobBaseEntity>() {
            @Override
            public void onResponse(Call<MobBaseEntity> call, Response<MobBaseEntity> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            KLog.i("userDataQuery---success：" + body.toString());
                            //解密
                            Object result = body.getResult();
                            if (result != null) {
                                String resutStr = (String) result;
                                if (!TextUtils.isEmpty(resutStr)) {
                                    result = EncodeUtils.DecodeBase64(resutStr);
                                }
                            }
                            myCallBack.onSuccess(what, result);
                        } else {
                            myCallBack.onFail(what, body.getMsg());
                        }
                    } else {
                        myCallBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    myCallBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity> call, Throwable t) {
                KLog.e("userDataQuery-----onFailure：" + t.toString());
                //数据错误
                myCallBack.onFail(what, NET_FAIL);
            }
        });

        return call;

    }


    public static Call<MobBaseEntity> userGetVerificationCode(String userName, final int what, final MyCallBack myCallBack) {
        Call<MobBaseEntity> call = BuildApi.getMobAPIService().userGetVerificationCode(Constants.URL_APP_Key, userName);
        call.enqueue(new Callback<MobBaseEntity>() {
            @Override
            public void onResponse(Call<MobBaseEntity> call, Response<MobBaseEntity> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity body = response.body();
                    if (body != null) {
                        if (body.getRetCode().equals("200")) {
                            KLog.i("userGetVerificationCode---success：" + body.toString());
                            myCallBack.onSuccess(what, body.getMsg());
                        } else {
                            myCallBack.onFail(what, body.getMsg());
                        }
                    } else {
                        myCallBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    myCallBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity> call, Throwable t) {
                KLog.e("userGetVerificationCode-----onFailure：" + t.toString());
                //数据错误
                myCallBack.onFail(what, NET_FAIL);
            }
        });

        return call;

    }


    public static Call<MobBaseEntity> userModifyPsd(String userName, String oldPsd, String newPsd, String mode, final int what, final MyCallBack myCallBack) {

        Call<MobBaseEntity> call = BuildApi.getMobAPIService().userModifyPsd(Constants.URL_APP_Key, userName, oldPsd, newPsd, mode);
        call.enqueue(new Callback<MobBaseEntity>() {
            @Override
            public void onResponse(Call<MobBaseEntity> call, Response<MobBaseEntity> response) {
                if (response.isSuccessful()) {
                    MobBaseEntity body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
                            KLog.i("userModifyPsd---success：" + body.toString());
                            Object result = body.getResult();
                            myCallBack.onSuccess(what, result);
                        } else {
                            myCallBack.onFail(what, body.getMsg());
                        }
                    } else {
                        myCallBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    myCallBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity> call, Throwable t) {
                KLog.e("userModifyPsd-----onFailure：" + t.toString());
                //数据错误
                myCallBack.onFail(what, NET_FAIL);
            }
        });

        return call;

    }

    /* -----end------用户系统------------ */

}
