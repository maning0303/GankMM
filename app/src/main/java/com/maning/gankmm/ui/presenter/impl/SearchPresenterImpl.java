package com.maning.gankmm.ui.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import com.maning.gankmm.bean.gank2.Gank2SearchListBean;
import com.maning.gankmm.http.callback.CommonHttpCallback;
import com.maning.gankmm.http.gank2.GankApi2;
import com.maning.gankmm.ui.iView.ISearchView;
import com.maning.gankmm.ui.presenter.ISearchPresenter;

import java.util.List;

/**
 * Created by maning on 2017/3/1.
 */

public class SearchPresenterImpl extends BasePresenterImpl<ISearchView> implements ISearchPresenter {

    private Context context;
    private int pageSize = 20;
    private int pageIndex = 1;
    private String keyWord;
    private List<Gank2SearchListBean.DataEntity> gankSearchList;

    public SearchPresenterImpl(Context context, ISearchView iSearchView) {
        this.context = context;
        attachView(iSearchView);
    }

    @Override
    public void searchDatas(String keyWords) {
        if (mView == null) {
            return;
        }

        //开始搜索
        if (TextUtils.isEmpty(keyWords)) {
            mView.showToast("关键字不能为空");
            return;
        }
        keyWord = keyWords;
        //获取数据
        mView.showBaseProgressDialog("搜索中...");
        GankApi2.getSearchDatas(keyWord, 1, pageSize, new CommonHttpCallback<Gank2SearchListBean>() {
            @Override
            public void onSuccess(Gank2SearchListBean result) {
                mView.hideBaseProgressDialog();
                gankSearchList = result.getData();
                if (gankSearchList != null && gankSearchList.size() > 0) {
                    mView.setSearchList(gankSearchList);
                }
                //判断是不是可以使用上啦加载更多功能
                if (gankSearchList == null || gankSearchList.size() == 0 || gankSearchList.size() < pageIndex * pageSize) {
                    mView.setLoadMoreEnabled(false);
                } else {
                    mView.setLoadMoreEnabled(true);
                }
                pageIndex = 1;
                pageIndex++;
                mView.overRefresh();
            }

            @Override
            public void onFail(int code, String message) {
                onHttpFail(message);
            }
        });
    }

    private void onHttpFail(String message) {
        if (mView == null) {
            return;
        }
        mView.hideBaseProgressDialog();
        mView.overRefresh();
        mView.showToast(message);
    }

    @Override
    public void loadMoreDatas() {
        GankApi2.getSearchDatas(keyWord, pageIndex, pageSize, new CommonHttpCallback<Gank2SearchListBean>() {
            @Override
            public void onSuccess(Gank2SearchListBean result) {
                gankSearchList.addAll(result.getData());
                if (gankSearchList != null && gankSearchList.size() > 0) {
                    mView.setSearchList(gankSearchList);
                }
                pageIndex++;
                mView.overRefresh();
            }

            @Override
            public void onFail(int code, String message) {
                onHttpFail(message);
            }
        });
    }

    @Override
    public void itemClick(int position) {

    }
}
