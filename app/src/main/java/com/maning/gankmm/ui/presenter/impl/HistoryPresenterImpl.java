package com.maning.gankmm.ui.presenter.impl;

import android.content.Context;

import com.maning.gankmm.bean.gank.GankHistoryListBean;
import com.maning.gankmm.http.callback.CommonHttpCallback;
import com.maning.gankmm.http.gank.GankApi;
import com.maning.gankmm.ui.iView.IHistoryView;
import com.maning.gankmm.ui.presenter.IHistoryPresenter;

/**
 * Created by maning on 16/6/21.
 */
public class HistoryPresenterImpl extends BasePresenterImpl<IHistoryView> implements IHistoryPresenter {

    private Context context;

    public HistoryPresenterImpl(Context context, IHistoryView iHistoryView) {
        this.context = context;
        attachView(iHistoryView);
    }

    @Override
    public void getHistoryDatas() {
        GankApi.getHistoryData(new CommonHttpCallback<GankHistoryListBean>() {
            @Override
            public void onSuccess(GankHistoryListBean result) {
                if (mView == null) {
                    return;
                }
                mView.overRefresh();
                mView.setHistoryList(result.getResults());
            }

            @Override
            public void onFail(int code, String message) {
                if (mView == null) {
                    return;
                }
                mView.overRefresh();
                mView.showToast(message);
            }
        });
    }

}
