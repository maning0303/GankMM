package com.maning.gankmm.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.maning.gankmm.R;
import com.maning.gankmm.skin.SkinManager;
import com.maning.mndialoglibrary.MProgressDialog;
import com.maning.mndialoglibrary.MStatusDialog;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by maning on 16/3/2.
 * <p/>
 * 父类
 */
public class BaseActivity extends AppCompatActivity {

    public Context mContext;
    public AppCompatActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置主题
        SkinManager.onActivityCreateSetSkin(this);
        super.onCreate(savedInstanceState);

        mContext = this;
        mActivity = this;

        initStatus();

    }

    private void initStatus() {
        //设置状态栏的颜色
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.main_color), 60);
        } else {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.main_color_night), 60);
        }
    }


    public void showProgressDialog() {
        MProgressDialog.showProgress(this);
    }

    public void showProgressDialog(String message) {
        MProgressDialog.showProgress(this, message);
    }

    public void showProgressSuccess(String message) {
        new MStatusDialog(this).show(message, getResources().getDrawable(R.drawable.mn_icon_dialog_success));
    }

    public void showProgressError(String message) {
        new MStatusDialog(this).show(message, getResources().getDrawable(R.drawable.mn_icon_dialog_fail));
    }

    public void dissmissProgressDialog() {
        MProgressDialog.dismissProgress();
    }

    public void initBackToolBar(Toolbar toolbar, String title) {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(toolbar, title, R.drawable.gank_ic_back_white);
        } else {
            initToolBar(toolbar, title, R.drawable.gank_ic_back_night);
        }
    }

    public void initToolBar(Toolbar toolbar, String title, int icon) {
        toolbar.setTitle(title);// 标题的文字需在setSupportActionBar之前，不然会无效
        if(icon > 0){
            toolbar.setNavigationIcon(icon);
        }
        setSupportActionBar(toolbar);
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.gank_text1_color));
            toolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
        } else {
            toolbar.setTitleTextColor(getResources().getColor(R.color.gank_text1_color_night));
            toolbar.setPopupTheme(R.style.ToolBarPopupThemeNight);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (MProgressDialog.isShowing()) {
                MProgressDialog.dismissProgress();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onDestroy() {
        dissmissProgressDialog();
        Glide.with(this.getApplicationContext()).pauseRequests();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // MainActivity 内嵌套Fragment，应在Fragment中统计页面
        String className = this.getClass().getSimpleName();
        if (!"MainActivity".equals(className)) {
            MobclickAgent.onPageStart(className);   // 统计页面
        }
        MobclickAgent.onResume(this);       //统计时长
    }

    @Override
    protected void onPause() {
        super.onPause();
        // MainActivity 内嵌套Fragment，应在Fragment中统计页面
        String className = this.getClass().getSimpleName();
        if (!"MainActivity".equals(className)) {
            MobclickAgent.onPageEnd(className);   // 统计页面
        }
        MobclickAgent.onPause(this);
    }


}
