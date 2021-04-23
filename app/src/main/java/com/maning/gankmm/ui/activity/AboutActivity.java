package com.maning.gankmm.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.maning.gankmm.R;
import com.maning.gankmm.app.MyApplication;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.utils.IntentUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.tv_app_version)
    TextView tvAppVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        initBar();

        initAppVersionName();

    }

    private void initAppVersionName() {

        tvAppVersion.setText("当前版本号：" + MyApplication.getVersionName());

    }

    private void initBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(toolbar, getString(R.string.about), R.drawable.gank_ic_back_white);
            //设置CollapsingToolbarLayout扩张时的标题颜色
            collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.translate));
            //设置CollapsingToolbarLayout收缩时的标题颜色
            collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        } else {
            initToolBar(toolbar, getString(R.string.about), R.drawable.gank_ic_back_night);
            //设置CollapsingToolbarLayout扩张时的标题颜色
            collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.translate));
            //设置CollapsingToolbarLayout收缩时的标题颜色
            collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.gank_text1_color_night));
        }
        collapsingToolbar.setTitle(getString(R.string.about));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //点击事件
    @OnClick(R.id.tvdownload)
    void tvdownload() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(getString(R.string.download_url)));
        startActivity(intent);
    }

    @OnClick(R.id.tvMyGithub)
    void tvMyGithub() {
        IntentUtils.startToWebActivity(this, null, getString(R.string.app_name), getString(R.string.github_my));
    }

    @OnClick(R.id.tvGank)
    void tvGank() {
        IntentUtils.startToWebActivity(this, null, getString(R.string.gankio), getString(R.string.gankio));
    }

    @OnClick(R.id.tvOpenFrame)
    void tvOpenFrame() {
        startActivity(new Intent(mContext, OpenFrameActivity.class));
    }

}
