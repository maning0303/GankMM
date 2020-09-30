package com.maning.gankmm.ui.presenter.impl;

import android.content.Context;

import com.maning.gankmm.app.MyApplication;
import com.maning.gankmm.bean.gank2.GankEntity;
import com.maning.gankmm.bean.gank2.Gank2CategoryTypeListBean;
import com.maning.gankmm.db.GankDaoManager;
import com.maning.gankmm.http.callback.CommonHttpCallback;
import com.maning.gankmm.http.gank2.GankApi2;
import com.maning.gankmm.ui.iView.IPublicView;
import com.maning.gankmm.ui.presenter.IPublicPresenter;
import com.maning.gankmm.utils.IntentUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maning on 16/6/21.
 */
public class PublicPresenterImpl extends BasePresenterImpl<IPublicView> implements IPublicPresenter {

    private Context context;
    //标记来自哪个标签的
    //GanHuo,Article
    private String category;
    //Android,iOS,Flutter...
    private String type;

    private List<GankEntity> publicList;
    private int pageSize = 20;
    private int pageIndex = 1;

    public PublicPresenterImpl(Context context, IPublicView iPublicView, String category, String type) {
        this.context = context;
        this.category = category;
        this.type = type;
        attachView(iPublicView);
    }

    /**
     * 保存到数据库
     *
     * @param results
     */
    private void saveToDB(final List<GankEntity> results) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                GankDaoManager.getPublicDao().insertList(results, category, type);
            }
        }).start();
    }


    @Override
    public void getNewDatas() {
        GankApi2.getCategorys(category, type, 1, pageSize, new CommonHttpCallback<Gank2CategoryTypeListBean>() {
            @Override
            public void onSuccess(Gank2CategoryTypeListBean result) {
                pageIndex = 1;
                pageIndex++;
                publicList = result.getData();
                //保存到数据库
                saveToDB(publicList);
                if(mView != null){
                    mView.setPublicList(publicList);
                    mView.overRefresh();
                }
            }

            @Override
            public void onFail(int code, String message) {
                onHttpFail(message);
            }
        });
    }

    @Override
    public void getMoreDatas() {
        GankApi2.getCategorys(category, type, pageIndex, pageSize, new CommonHttpCallback<Gank2CategoryTypeListBean>() {
            @Override
            public void onSuccess(Gank2CategoryTypeListBean result) {
                if (publicList == null) {
                    publicList = new ArrayList<>();
                }
                List<GankEntity> gankEntityList = result.getData();
                //过滤一下数据,筛除重的
                if (publicList.size() > 0) {
                    for (int i = 0; i < gankEntityList.size(); i++) {
                        GankEntity resultEntity2 = gankEntityList.get(i);
                        for (int j = 0; j < publicList.size(); j++) {
                            GankEntity resultsEntity1 = publicList.get(j);
                            if (resultEntity2.get_id().equals(resultsEntity1.get_id())) {
                                //删除
                                gankEntityList.remove(i);
                            }
                        }
                    }
                }
                publicList.addAll(gankEntityList);
                if (publicList == null || publicList.size() == 0 || publicList.size() < pageIndex * pageSize) {
                    mView.setLoadMoreEnabled(false);
                } else {
                    mView.setLoadMoreEnabled(true);
                }
                pageIndex++;
                mView.setPublicList(publicList);
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
        mView.overRefresh();
        mView.showToast(message);
    }

    @Override
    public void getDBDatas() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取数据库的数据
                publicList = GankDaoManager.getPublicDao().queryAll(category, type);
                MyApplication.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView == null) {
                            return;
                        }
                        if (publicList != null && publicList.size() > 0) {
                            mView.setPublicList(publicList);
                        } else {
                            //自动刷新
                            mView.setRefreshing(true);
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void itemClick(int position) {
        GankEntity resultsEntity = publicList.get(position);
        IntentUtils.startToWebActivity(context, type, resultsEntity.getDesc(), resultsEntity.getUrl());
    }

}
