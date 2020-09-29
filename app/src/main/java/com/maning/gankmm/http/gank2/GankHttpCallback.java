package com.maning.gankmm.http.gank2;

import com.maning.gankmm.bean.gank2.Gank2BaseBean;

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
public abstract class GankHttpCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        onFinish();
        if (200 == response.code()) {
            T body = response.body();
            if (body instanceof Gank2BaseBean) {
                Gank2BaseBean gank2BaseBean = (Gank2BaseBean) response.body();
                if (gank2BaseBean.getStatus() == 100) {
                    onSuccess(response.body());
                } else {
                    onFail(gank2BaseBean.getStatus(), gank2BaseBean.getMsg());
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
