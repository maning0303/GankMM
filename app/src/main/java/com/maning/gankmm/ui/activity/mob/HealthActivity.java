package com.maning.gankmm.ui.activity.mob;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobHealthEntity;
import com.maning.gankmm.http.mob.MobApi;
import com.maning.gankmm.http.callback.MyCallBack;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleHealthAdapter;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.ui.view.MClearEditText;
import com.maning.gankmm.utils.KeyboardUtils;
import com.maning.gankmm.utils.MySnackbar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 健康知识
 */
public class HealthActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.editText)
    MClearEditText editText;
    @Bind(R.id.swipe_target)
    RecyclerView recyclerView;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;


    private int pageIndex = 1;
    private int pageSize = 20;

    private ArrayList<MobHealthEntity.ListBean> mDatas = new ArrayList<>();
    private RecycleHealthAdapter recycleHealthAdapter;
    private String keyWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        ButterKnife.bind(this);


        initMyToolBar();

        initRecyclerView();

    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).color(Color.LTGRAY).build());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (Math.abs(dy) > 20) {
                    KeyboardUtils.hideSoftInput(HealthActivity.this);
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshEnabled(false);
        swipeToLoadLayout.setLoadMoreEnabled(false);
    }

    private void initMyToolBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(toolbar, "健康知识", R.drawable.gank_ic_back_white);
        } else {
            initToolBar(toolbar, "健康知识", R.drawable.gank_ic_back_night);
        }
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


    @OnClick(R.id.btn_query)
    public void btnQuery() {
        showProgressDialog("查询中...");
        //获取关键字
        keyWord = editText.getText().toString();
        if (TextUtils.isEmpty(keyWord)) {
            MySnackbar.makeSnackBarBlack(toolbar, "查询内容不能为空");
            dissmissProgressDialog();
            return;
        }
        KeyboardUtils.hideSoftInput(this);
        //查询
        onRefresh();
    }

    private void queryDatas(int what) {
        MobApi.queryHealth(keyWord, pageIndex, pageSize, what, httpCallBack);
    }


    private void initAdapter() {
        if (recycleHealthAdapter == null) {
            recycleHealthAdapter = new RecycleHealthAdapter(this, mDatas);
            recyclerView.setAdapter(recycleHealthAdapter);
        } else {
            recycleHealthAdapter.upddateDatas(mDatas);
        }
    }


    private MyCallBack httpCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Object result) {
            dissmissProgressDialog();
            MobHealthEntity mobHealthEntity = (MobHealthEntity) result;
            if (mobHealthEntity != null) {
                List<MobHealthEntity.ListBean> list = mobHealthEntity.getList();
                if (what == 0x001) {
                    mDatas = (ArrayList<MobHealthEntity.ListBean>) list;
                } else {
                    mDatas.addAll(list);
                }
                initAdapter();

                if (mDatas.size() < pageIndex * pageSize) {
                    swipeToLoadLayout.setLoadMoreEnabled(false);
                } else {
                    swipeToLoadLayout.setLoadMoreEnabled(true);
                }

                if (mDatas.size() > 0) {
                    swipeToLoadLayout.setRefreshEnabled(true);
                } else {
                    swipeToLoadLayout.setRefreshEnabled(false);
                }
                pageIndex++;
                overRefresh();
            }
        }

        @Override
        public void onSuccessList(int what, List results) {

        }

        @Override
        public void onFail(int what, String result) {
            dissmissProgressDialog();
            MySnackbar.makeSnackBarRed(toolbar, result);
            overRefresh();
        }
    };

    private void overRefresh() {
        swipeToLoadLayout.setRefreshing(false);
        swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        queryDatas(0x001);
    }

    @Override
    public void onLoadMore() {
        queryDatas(0x002);
    }
}

