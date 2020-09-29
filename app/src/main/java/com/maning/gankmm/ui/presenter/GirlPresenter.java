package com.maning.gankmm.ui.presenter;

import android.view.View;

/**
 * Created by maning on 16/6/21.
 */
public interface GirlPresenter {

    void getNewDatas();

    void getMoreDatas();

    void getRandomDatas();

    void itemClick(View view,int position);

}
