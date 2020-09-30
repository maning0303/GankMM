package com.maning.gankmm.ui.iView;

import com.maning.gankmm.bean.gank2.GankEntity;

import java.util.List;

/**
 * Created by maning on 16/6/21.
 */
public interface IWelFareView {

    void setWelFareList(List<GankEntity> welFareList);

    void setRandomList(List<GankEntity> randomList);

    void showToast(String msg);

    void overRefresh();

    void setLoadMoreEnabled(boolean flag);

}
