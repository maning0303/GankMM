package com.maning.gankmm.http.callback;

import android.text.TextUtils;

import com.maning.gankmm.bean.gank2.Gank2BaseBean;
import com.maning.gankmm.bean.rolltools.RollToolsBaseBean;
import com.maning.gankmm.bean.weather.caiyun.CaiyunWeatherBaseBean;
import com.maning.gankmm.bean.weather.zhixin.ZhixinBaseBean;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : maning
 * @date : 2020-09-29
 * @desc : 回调
 */
public abstract class CommonHttpCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        onFinish();
        if (200 == response.code()) {
            T body = response.body();
            if (body instanceof Gank2BaseBean) {
                //gankio
                Gank2BaseBean baseBean = (Gank2BaseBean) response.body();
                if (baseBean.getStatus() == 100) {
                    onSuccess(response.body());
                } else {
                    onFail(baseBean.getStatus(), baseBean.getMsg());
                }
            } else if (body instanceof CaiyunWeatherBaseBean) {
                //彩云天气
                CaiyunWeatherBaseBean baseBean = (CaiyunWeatherBaseBean) response.body();
                String status = baseBean.getStatus();
                String api_status = baseBean.getApi_status();
                if ("ok".equals(status) && "active".equals(api_status)) {
                    onSuccess(response.body());
                } else {
                    onFail(1000, "彩云天气接口出错啦~~~");
                }
            } else if (body instanceof ZhixinBaseBean) {
                ZhixinBaseBean baseBean = (ZhixinBaseBean) response.body();
                if (TextUtils.isEmpty(baseBean.getStatus())) {
                    onSuccess(response.body());
                } else {
                    onFail(1000, baseBean.getStatus());
                }
            } else if (body instanceof RollToolsBaseBean) {
                RollToolsBaseBean baseBean = (RollToolsBaseBean) response.body();
                if (baseBean.getCode() == 1) {
                    onSuccess(response.body());
                } else {
                    onFail(baseBean.getCode(), baseBean.getMsg());
                }
            } else {
                onSuccess(body);
            }
        } else {
            onFail(response.code(), HttpErrorConstants.ERR_NETEXCEPTION_ERROR);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (call.isCanceled()) {
            onFinish();
            return;
        }
        //处理错误信息
        int errorCode = HttpErrorConstants.ERR_NETEXCEPTION_ERROR_CODE;
        String errorMsg;
        if (t instanceof UnknownHostException || t instanceof ConnectException) {
            //网络未连接
            errorMsg = HttpErrorConstants.ERR_UNKNOWNHOSTEXCEPTION_ERROR;
        } else if (t instanceof SocketTimeoutException) {
            //连接超时
            errorMsg = HttpErrorConstants.ERR_SOCKETTIMEOUTEXCEPTION_ERROR;
        } else {
            //其他网络异常
            errorMsg = HttpErrorConstants.ERR_NETEXCEPTION_ERROR;
        }
        onFinish();
        onFail(errorCode, errorMsg);
    }

    public abstract void onSuccess(T result);

    public abstract void onFail(int code, String message);

    public void onFinish() {

    }

}
