package com.maning.gankmm.ui.iView;

import com.maning.gankmm.bean.gank2.Gank2SearchListBean;

import java.util.List;

/**
 * Created by maning on 2017/3/1.
 */

public interface ISearchView extends IBaseView {

    void setSearchList(List<Gank2SearchListBean.DataEntity> resultList);

    void showToast(String msg);

    void overRefresh();

    void setLoadMoreEnabled(boolean flag);

}
