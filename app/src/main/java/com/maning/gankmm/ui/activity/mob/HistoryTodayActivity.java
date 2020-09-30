package com.maning.gankmm.ui.activity.mob;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maning.calendarlibrary.MNCalendar;
import com.maning.calendarlibrary.listeners.OnCalendarItemClickListener;
import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobHistoryTodayEntity;
import com.maning.gankmm.http.mob.MobApi;
import com.maning.gankmm.http.MyCallBack;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleHistoryTodayAdapter;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.utils.MySnackbar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 历史上今天
 */
public class HistoryTodayActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.mnCalendar)
    MNCalendar mnCalendar;
    @Bind(R.id.calendar_bg)
    RelativeLayout CalendarBg;

    private ArrayList<MobHistoryTodayEntity> mDatas;

    private Date currentDate = new Date();
    private SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("MM月dd日");
    private RecycleHistoryTodayAdapter recycleHistoryTodayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_today);
        ButterKnife.bind(this);

        initMyToolBar();

        initRecyclerView();

        initCalendar();

        queryData();

    }

    private void initCalendar() {
        mnCalendar.setOnCalendarItemClickListener(new OnCalendarItemClickListener() {
            @Override
            public void onClick(Date date) {
                currentDate = date;
                CalendarBg.setVisibility(View.GONE);
                queryData();
            }

            @Override
            public void onLongClick(Date date) {

            }
        });
    }

    private void queryData() {
        showProgressDialog("查询中...");

        String timeString = sdf.format(currentDate);
        String timeString2 = sdf2.format(currentDate);
        tvTime.setText(timeString2);

        MobApi.queryHistory(timeString, 0x001, new MyCallBack() {
            @Override
            public void onSuccess(int what, Object result) {

            }

            @Override
            public void onSuccessList(int what, List results) {
                dissmissProgressDialog();
                mDatas = (ArrayList<MobHistoryTodayEntity>) results;
                initAdapter();
            }

            @Override
            public void onFail(int what, String result) {
                MySnackbar.makeSnackBarRed(toolbar, result);
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

    @OnClick(R.id.tv_time)
    public void tv_time() {
        CalendarBg.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.calendar_bg)
    public void calendar_bg() {
        CalendarBg.setVisibility(View.GONE);
    }

}
