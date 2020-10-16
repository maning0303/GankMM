package com.maning.gankmm.ui.presenter.impl;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.maning.gankmm.app.MyApplication;
import com.maning.gankmm.bean.gank2.GankEntity;
import com.maning.gankmm.bean.gank.GankDayBean;
import com.maning.gankmm.http.callback.CommonHttpCallback;
import com.maning.gankmm.http.gank.GankApi;
import com.maning.gankmm.ui.iView.IGankView;
import com.maning.gankmm.ui.presenter.IGankPresenter;
import com.maning.gankmm.utils.ThreadPoolUtils;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maning on 16/6/21.
 */
public class GankPresenterImpl extends BasePresenterImpl<IGankView> implements IGankPresenter {

    private Context context;

    public GankPresenterImpl(Context context, IGankView iGankView) {
        this.context = context;
        attachView(iGankView);
    }

    @Override
    public void getOneDayDatas(String timeStr) {
        if (TextUtils.isEmpty(timeStr)) {
            return;
        }
        //切割
        String[] dayArray = timeStr.split("-");
        if (dayArray.length > 2) {
            GankApi.getOneDayData(dayArray[0], dayArray[1], dayArray[2], new CommonHttpCallback<GankDayBean>() {
                @Override
                public void onSuccess(final GankDayBean result) {
                    if (mView == null) {
                        return;
                    }
                    if (result != null) {
                        GankDayBean.ResultsEntity results = result.getResults();
                        if (results != null) {
                            String topImage = getTopImage(results);
                            if (!TextUtils.isEmpty(topImage)) {
                                mView.setProgressBarVisility(View.GONE);
                                mView.showImageView(topImage);
                            } else {
                                mView.setProgressBarVisility(View.GONE);
                                mView.showToast("糟糕，图片没找到");
                            }
                            try {
                                //初始化数据
                                ThreadPoolUtils.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        initDatas(result);
                                    }
                                });
                            } catch (Exception e) {
                                mView.showToast("抱歉，出错了...");
                            }
                        } else {
                            mView.showToast("抱歉，当天数据没有...");
                        }
                    } else {
                        mView.showToast("抱歉，当天数据没有...");
                    }
                }

                @Override
                public void onFail(int code, String message) {
                    if (mView == null) {
                        return;
                    }
                    mView.hideBaseProgressDialog();
                    if (!TextUtils.isEmpty(message)) {
                        mView.showToast(message);
                    }
                }
            });
        }
    }

    private String getTopImage(GankDayBean.ResultsEntity resultsEntity) {
        String imageUrl = "";
        List<GankEntity> 福利 = resultsEntity.get福利();
        if (福利 != null && 福利.size() > 0) {
            List<String> images = 福利.get(0).getImages();
            if (images != null && images.size() > 0) {
                imageUrl = images.get(0);
            }
        }
        if (TextUtils.isEmpty(imageUrl)) {
            List<GankEntity> android = resultsEntity.getAndroid();
            if (android != null && android.size() > 0) {
                List<String> images = android.get(0).getImages();
                if (images != null && images.size() > 0) {
                    imageUrl = images.get(0);
                }
            }
        }
        if (TextUtils.isEmpty(imageUrl)) {
            List<GankEntity> ios = resultsEntity.getIOS();
            if (ios != null && ios.size() > 0) {
                List<String> images = ios.get(0).getImages();
                if (images != null && images.size() > 0) {
                    imageUrl = images.get(0);
                }
            }
        }
        return imageUrl;
    }


    private List<GankEntity> dayEntityArrayList = new ArrayList<>();

    private void initDatas(GankDayBean gankDayBean) {
        dayEntityArrayList.clear();

        List<GankEntity> androidEntityList = gankDayBean.getResults().getAndroid();
        if (androidEntityList != null && androidEntityList.size() > 0) {
            GankEntity gankEntity_title = new GankEntity();
            gankEntity_title.setType("title");
            gankEntity_title.setDesc("Android");
            dayEntityArrayList.add(gankEntity_title);
            dayEntityArrayList.addAll(androidEntityList);
        }


        List<GankEntity> iosEntityList = gankDayBean.getResults().getIOS();
        if (iosEntityList != null && iosEntityList.size() > 0) {
            GankEntity gankEntity_title = new GankEntity();
            gankEntity_title.setType("title");
            gankEntity_title.setDesc("iOS");
            dayEntityArrayList.add(gankEntity_title);
            dayEntityArrayList.addAll(iosEntityList);
        }


        List<GankEntity> 休息视频EntityList = gankDayBean.getResults().get休息视频();
        if (休息视频EntityList != null && 休息视频EntityList.size() > 0) {
            GankEntity gankEntity_title = new GankEntity();
            gankEntity_title.setType("title");
            gankEntity_title.setDesc("休息视频");
            dayEntityArrayList.add(gankEntity_title);
            dayEntityArrayList.addAll(休息视频EntityList);
        }


        List<GankEntity> 拓展资源EntityList = gankDayBean.getResults().get拓展资源();
        if (拓展资源EntityList != null && 拓展资源EntityList.size() > 0) {
            GankEntity gankEntity_title = new GankEntity();
            gankEntity_title.setType("title");
            gankEntity_title.setDesc("拓展资源");
            dayEntityArrayList.add(gankEntity_title);
            dayEntityArrayList.addAll(拓展资源EntityList);
        }

        KLog.i("dayEntityArrayList---" + dayEntityArrayList.size());
        MyApplication.getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (mView != null) {
                    mView.setGankList(dayEntityArrayList);
                }
            }
        });
    }

}
