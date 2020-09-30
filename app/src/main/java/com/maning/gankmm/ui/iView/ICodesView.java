package com.maning.gankmm.ui.iView;

import com.maning.gankmm.bean.web.CategoryContentBean;
import com.maning.gankmm.bean.web.CategoryTitleBean;

import java.util.ArrayList;

/**
 * Created by maning on 16/6/23.
 */
public interface ICodesView {

    void setCodesTitleList(ArrayList<CategoryTitleBean> codesTitleList);

    void setCodesContentList(ArrayList<CategoryContentBean> codesContentList);

    void setRefreshEnabled(boolean flag);

    void setLoadMoreEnabled(boolean flag);

    void showToast(String msg);

    void overRefresh();

}
