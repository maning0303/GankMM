package com.maning.gankmm.ui.activity.mob;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.rolltools.HistoryTodayBean;
import com.maning.gankmm.http.callback.CommonHttpCallback;
import com.maning.gankmm.http.rolltools.RolltoolsApi;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleHistoryTodayAdapter;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.utils.MySnackbar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 历史上今天
 */
public class HistoryTodayActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private ArrayList<HistoryTodayBean.DataEntity> mDatas;

    private RecycleHistoryTodayAdapter recycleHistoryTodayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_today);
        ButterKnife.bind(this);

        initMyToolBar();

        initRecyclerView();

        queryData();

    }

    private void queryData() {
        showProgressDialog("查询中...");

        RolltoolsApi.getHistoryToday(new CommonHttpCallback<HistoryTodayBean>() {
            @Override
            public void onSuccess(HistoryTodayBean result) {
                dissmissProgressDialog();
                mDatas = (ArrayList<HistoryTodayBean.DataEntity>) result.getData();
                initAdapter();
            }

            @Override
            public void onFail(int code, String message) {
                MySnackbar.makeSnackBarRed(toolbar, message);
                dissmissProgressDialog();
            }
        });
    }

    private void initAdapter() {
        if (recycleHistoryTodayAdapter == null) {
            recycleHistoryTodayAdapter = new RecycleHistoryTodayAdapter(this, mDatas);
            recyclerView.setAdapter(recycleHistoryTodayAdapter);
        } else {
            recycleHistoryTodayAdapter.upddateDatas(mDatas);
        }

    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).color(Color.LTGRAY).build());
    }

    private void initMyToolBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(toolbar, "历史上今天", R.drawable.gank_ic_back_white);
        } else {
            initToolBar(toolbar, "历史上今天", R.drawable.gank_ic_back_night);
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

}
