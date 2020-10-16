package com.maning.gankmm.ui.base;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.maning.gankmm.R;
import com.maning.mndialoglibrary.MProgressDialog;
import com.maning.mndialoglibrary.MStatusDialog;
import com.umeng.analytics.MobclickAgent;

public class BaseFragment extends Fragment {

    //统计名字判断
    public String className;

    public Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        className = this.getClass().getSimpleName();

        context = getActivity();

    }


    public void showProgressDialog() {
        MProgressDialog.showProgress(context);
    }

    public void showProgressDialog(String message) {
        MProgressDialog.showProgress(context, message);
    }

    public void showProgressSuccess(String message) {
        new MStatusDialog(context).show(message, getResources().getDrawable(R.drawable.mn_icon_dialog_success));
    }

    public void showProgressError(String message) {
        new MStatusDialog(context).show(message, getResources().getDrawable(R.drawable.mn_icon_dialog_fail));
    }

    public void dissmissProgressDialog() {
        MProgressDialog.dismissProgress();
    }

    @Override
    public void onResume() {
        super.onResume();
        //CategoryFragment 内嵌套Fragment,统计子页面
        if (!"CategoryFragment".equals(className)) {
            MobclickAgent.onPageStart(className);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //CategoryFragment 内嵌套Fragment,统计子页面
        if (!"CategoryFragment".equals(className)) {
            MobclickAgent.onPageEnd(className);
        }

    }

}
