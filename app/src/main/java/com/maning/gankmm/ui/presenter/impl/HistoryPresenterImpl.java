package com.maning.gankmm.ui.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import com.maning.gankmm.http.gank.GankApi;
import com.maning.gankmm.http.callback.MyCallBack;
import com.maning.gankmm.ui.iView.IHistoryView;
import com.maning.gankmm.ui.presenter.IHistoryPresenter;

import java.util.List;

/**
 * Created by maning on 16/6/21.
 */
public class HistoryPresenterImpl extends BasePresenterImpl<IHistoryView> implements IHistoryPresenter {

    private Context context;

    public HistoryPresenterImpl(Context context, IHistoryView iHistoryView) {
        this.context = context;
        attachView(iHistoryView);
    }

    private MyCallBack httpCallBack = new MyCallBack() {
        @Override
        public void onSuccessList(int what, List results) {
            if(mView == null){
                return;
            }
            mView.overRefresh();
            mView.setHistoryList(results);
        }

        @Override
        public void onSuccess(int what, Object result) {

        }

        @Override
        public void onFail(int what, String result) {
            if(mView == null){
                return;
            }
            mView.overRefresh();
            if (!TextUtils.isEmpty(result)) {
                mView.showToast(result);
            }
        }
    };

    @Override
    public void getHistoryDatas() {
        GankApi.getHistoryData(0x001, httpCallBack);
    }

}
