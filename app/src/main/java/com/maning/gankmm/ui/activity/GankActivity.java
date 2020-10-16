package com.maning.gankmm.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.maning.gankmm.R;
import com.maning.gankmm.bean.gank2.GankEntity;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleGankAdapter;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.ui.iView.IGankView;
import com.maning.gankmm.ui.presenter.impl.GankPresenterImpl;
import com.maning.gankmm.ui.view.FullyLinearLayoutManager;
import com.maning.gankmm.ui.view.ProgressWheel;
import com.maning.gankmm.utils.DensityUtil;
import com.maning.gankmm.utils.IntentUtils;
import com.maning.gankmm.utils.MySnackbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 每日显示Gank
 */
public class GankActivity extends BaseActivity implements IGankView {


    private static final String TAG = GankActivity.class.getSimpleName();
    @Bind(R.id.iv_top)
    ImageView ivTop;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recycleView)
    RecyclerView myRecycleView;
    @Bind(R.id.progressbar)
    ProgressWheel progressbar;
    @Bind(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;

    private String dayDate;

    private ArrayList<String> images;

    private GankPresenterImpl gankPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_show);
        ButterKnife.bind(this);

        gankPresenter = new GankPresenterImpl(this, this);

        initIntent();

        initBar();

        initView();

        gankPresenter.getOneDayDatas(dayDate);

    }

    @OnClick(R.id.iv_top)
    void iv_top() {
        if (images != null && images.size() > 0) {
            IntentUtils.startToImageShow(this, images, 0, ivTop);
        }
    }

    private void initView() {
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myRecycleView.setLayoutManager(linearLayoutManager);
        myRecycleView.setItemAnimator(new DefaultItemAnimator());
        //设置图片的最大高度
        ivTop.setMaxHeight((int) (DensityUtil.getHeight(this) * 0.75));
    }

    private void initAdapter(final List<GankEntity> gankList) {
        RecycleGankAdapter recycleGankAdapter = new RecycleGankAdapter(this, gankList);
        myRecycleView.setAdapter(recycleGankAdapter);
        myRecycleView.setNestedScrollingEnabled(false);
        myRecycleView.setHasFixedSize(true);
        recycleGankAdapter.setOnItemClickLitener(new RecycleGankAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!gankList.get(position).getType().equals("title")) {
                    IntentUtils.startToWebActivity(GankActivity.this, gankList.get(position).getType(), gankList.get(position).getDesc(), gankList.get(position).getUrl());
                }
            }
        });
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

    private void initIntent() {
        Intent intent = getIntent();
        dayDate = intent.getStringExtra(IntentUtils.DayDate);
    }


    private void initBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(toolbar, dayDate, R.drawable.gank_ic_back_white);
            //设置CollapsingToolbarLayout扩张时的标题颜色
            collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.translate));
            //设置CollapsingToolbarLayout收缩时的标题颜色
            collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        } else {
            initToolBar(toolbar, dayDate, R.drawable.gank_ic_back_night);
            //设置CollapsingToolbarLayout扩张时的标题颜色
            collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.translate));
            //设置CollapsingToolbarLayout收缩时的标题颜色
            collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.gank_text1_color_night));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void onDestroy() {
        gankPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showToast(String msg) {
        MySnackbar.makeSnackBarBlack(toolbar, msg);
    }

    @Override
    public void showImageView(String url) {
        RequestOptions options = new RequestOptions();
        options.fitCenter();
        Glide.with(this)
                .load(url)
                .apply(options)
                .into(ivTop);
        //添加到集合
        images = new ArrayList<>();
        images.add(url);
    }

    @Override
    public void setGankList(List<GankEntity> gankList) {
        initAdapter(gankList);
    }

    @Override
    public void setProgressBarVisility(int visility) {
        progressbar.setVisibility(visility);
    }

    @Override
    public void showBaseProgressDialog(String msg) {
        showProgressDialog(msg);
    }

    @Override
    public void hideBaseProgressDialog() {
        dissmissProgressDialog();
    }
}
