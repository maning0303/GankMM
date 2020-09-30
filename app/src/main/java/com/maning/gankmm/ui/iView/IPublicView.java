package com.maning.gankmm.ui.iView;

import com.maning.gankmm.bean.gank2.GankEntity;

import java.util.List;

/**
 * Created by maning on 16/6/21.
 */
public interface IPublicView {

    void setPublicList(List<GankEntity> publicList);

    void showToast(String msg);

    void overRefresh();

    void setLoadMoreEnabled(boolean flag);

    void setRefreshing(boolean flag);

}
