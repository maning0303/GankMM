package com.maning.gankmm.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.maning.gankmm.R;
import com.maning.gankmm.app.MyApplication;
import com.maning.gankmm.skin.SkinManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends Activity {

    @BindView(R.id.tv_app_version)
    TextView tv_app_version;
    @BindView(R.id.shade_bg)
    TextView shadeBg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (currentSkinType == SkinManager.THEME_NIGHT) {
            shadeBg.setVisibility(View.VISIBLE);
        }

        MyApplication.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        }, 2000);

        tv_app_version.setText(String.valueOf("V " + MyApplication.getVersionName()));


    }

}
