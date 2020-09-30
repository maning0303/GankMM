package com.maning.gankmm.http.callback;

import java.util.List;

/**
 * Created by maning on 16/1/13.
 * <p/>
 * 网络回调
 */
public interface MyCallBack {

    /**
     * 成功的回调对象
     *
     * @param what
     * @param result
     */
    void onSuccess(int what, Object result) ;

    /**
     * 成功的回调集合
     *
     * @param what
     * @param results
     */
    void onSuccessList(int what, List results);

    /**
     * 失败的回调
     *
     * @param what
     * @param result
     */
    void onFail(int what, String result);

}
