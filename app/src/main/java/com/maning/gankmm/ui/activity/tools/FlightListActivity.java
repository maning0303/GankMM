package com.maning.gankmm.ui.activity.tools;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobFlightEntity;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleFlightAdapter;
import com.maning.gankmm.ui.base.BaseActivity;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 航班列表查询
 */
public class FlightListActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private String intentTitle;
    private ArrayList<MobFlightEntity> mDatas;
    private RecycleFlightAdapter recycleAdapter;

    private SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_list);
        ButterKnife.bind(this);

        initIntent();

        initMyToolBar();

        initRecyclerView();

        //默认排序
        sortStartTime();

        initAdapter();
    }

    private void initAdapter() {
        if (recycleAdapter == null) {
            recycleAdapter = new RecycleFlightAdapter(this, mDatas);
            recyclerView.setAdapter(recycleAdapter);
        } else {
            recycleAdapter.updateDatas(mDatas);
        }
    }

    private void initIntent() {
        intentTitle = getIntent().getStringExtra("IntentTitle");
        mDatas = (ArrayList<MobFlightEntity>) getIntent().getSerializableExtra("IntentDatas");
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).color(Color.parseColor("#FFCCCCCC")).build());
    }

    private void initMyToolBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(toolbar, intentTitle, R.drawable.gank_ic_back_white);
        } else {
            initToolBar(toolbar, intentTitle, R.drawable.gank_ic_back_night);
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


    @OnClick(R.id.btn_sort_start_time)
    public void btn_sort_start_time() {
        //发车时间排序
        sortStartTime();
        //刷新
        initAdapter();
    }

    private void sortStartTime() {
        Collections.sort(mDatas, new Comparator<MobFlightEntity>() {
            @Override
            public int compare(MobFlightEntity mobFlightEntity01, MobFlightEntity mobFlightEntity02) {
                String startTime01 = mobFlightEntity01.getPlanTime();
                String startTime02 = mobFlightEntity02.getPlanTime();

                int result = 0;
                try {
                    if (sdf.parse(startTime01).getTime() > sdf.parse(startTime02).getTime()) {
                        result = 1;
                    } else if (sdf.parse(startTime01).getTime() < sdf.parse(startTime02).getTime()) {
                        result = -1;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return result;
            }
        });
    }

    @OnClick(R.id.btn_sort_end_time)
    public void btn_sort_end_time() {
        Collections.sort(mDatas, new Comparator<MobFlightEntity>() {
            @Override
            public int compare(MobFlightEntity mobFlightEntity01, MobFlightEntity mobFlightEntity02) {
                String startTime01 = mobFlightEntity01.getPlanArriveTime();
                String startTime02 = mobFlightEntity02.getPlanArriveTime();

                int result = 0;
                try {
                    if (sdf.parse(startTime01).getTime() > sdf.parse(startTime02).getTime()) {
                        result = 1;
                    } else if (sdf.parse(startTime01).getTime() < sdf.parse(startTime02).getTime()) {
                        result = -1;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return result;
            }
        });
        //刷新
        initAdapter();
    }

}
