package com.maning.gankmm.ui.presenter.impl;

import android.content.Context;
import android.view.View;

import com.maning.gankmm.bean.GankEntity;
import com.maning.gankmm.bean.gank2.Gank2CategoryTypeListBean;
import com.maning.gankmm.constant.Constants;
import com.maning.gankmm.http.gank2.GankApi2;
import com.maning.gankmm.http.callback.CommonHttpCallback;
import com.maning.gankmm.ui.iView.IWelFareView;
import com.maning.gankmm.ui.presenter.GirlPresenter;
import com.maning.gankmm.utils.IntentUtils;
import com.maning.gankmm.utils.SharePreUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maning on 16/6/21.
 */
public class GirlPresenterImpl extends BasePresenterImpl<IWelFareView> implements GirlPresenter {

    private Context context;

    private List<GankEntity> welFareLists;
    private List<GankEntity> randomLists;
    private int pageSize = 20;
    private int pageIndex = 1;
    private ArrayList<String> imagesList = new ArrayList<>();

    public GirlPresenterImpl(Context context, IWelFareView iWelFareView) {
        this.context = context;
        attachView(iWelFareView);
    }

    @Override
    public void getNewDatas() {
        getRandomDatas();
        //获取妹子图片
        GankApi2.getCategorys(Constants.FlagGirls, Constants.FlagGirls, pageIndex, pageSize, new CommonHttpCallback<Gank2CategoryTypeListBean>() {
            @Override
            public void onSuccess(Gank2CategoryTypeListBean result) {
                if (result == null || result.getData() == null) {
                    mView.overRefresh();
                    return;
                }
                pageIndex = 1;
                pageIndex++;
                welFareLists = result.getData();
                if (welFareLists.size() > 0) {
                    mView.setWelFareList(welFareLists);
                }
                mView.overRefresh();
            }

            @Override
            public void onFail(int code, String message) {
                onHttpFail(message);
            }
        });
    }

    @Override
    public void getMoreDatas() {
        GankApi2.getCategorys(Constants.FlagGirls, Constants.FlagGirls, pageIndex, pageSize, new CommonHttpCallback<Gank2CategoryTypeListBean>() {
            @Override
            public void onSuccess(Gank2CategoryTypeListBean result) {
                List<GankEntity> results = result.getData();
                if (results == null) {
                    mView.overRefresh();
                    return;
                }
                if (welFareLists == null) {
                    welFareLists = new ArrayList<>();
                }
                if (pageIndex == 1 && welFareLists.size() > 0) {
                    welFareLists.clear();
                }
                welFareLists.addAll(results);
                mView.setWelFareList(welFareLists);
                if (welFareLists == null || welFareLists.size() == 0 || welFareLists.size() < pageIndex * pageSize) {
                    mView.setLoadMoreEnabled(false);
                } else {
                    mView.setLoadMoreEnabled(true);
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
    public void getRandomDatas() {
        //查看配置的干活类型:默认Android
        String headLineType = SharePreUtil.getStringData(context, Constants.SPSwitcherDataType, "Android");
        GankApi2.getRandomDatas("GanHuo", headLineType, 10, new CommonHttpCallback<Gank2CategoryTypeListBean>() {
            @Override
            public void onSuccess(Gank2CategoryTypeListBean result) {
                randomLists = result.getData();
                mView.setRandomList(randomLists);
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
        mView.overRefresh();
        mView.showToast(message);
    }

    @Override
    public void itemClick(View view, int position) {
        imagesList.clear();
        for (int i = 0; i < welFareLists.size(); i++) {
            imagesList.add(welFareLists.get(i).getUrl());
        }
        IntentUtils.startToImageShow(context, imagesList, (ArrayList<GankEntity>) welFareLists, position, view);
    }

}
