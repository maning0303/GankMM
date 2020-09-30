package com.maning.gankmm.ui.activity.mob;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobTrainEntity;
import com.maning.gankmm.bean.mob.MobTrainNoEntity;
import com.maning.gankmm.http.mob.MobApi;
import com.maning.gankmm.http.MyCallBack;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleTrainAdapter;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.utils.MySnackbar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 火车列表查询
 */
public class TrainListActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private String intentTitle;
    private ArrayList<MobTrainEntity> mDatas;
    private RecycleTrainAdapter recycleTrainAdapter;

    private SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_list);
        ButterKnife.bind(this);

        initIntent();

        initMyToolBar();

        initRecyclerView();

        //默认排序
        sortStartTime();

        initAdapter();
    }

    private void initAdapter() {
        if (recycleTrainAdapter == null) {
            recycleTrainAdapter = new RecycleTrainAdapter(this, mDatas);
            recyclerView.setAdapter(recycleTrainAdapter);
        } else {
            recycleTrainAdapter.updateDatas(mDatas);
        }
    }

    private void initIntent() {
        intentTitle = getIntent().getStringExtra("IntentTitle");
        mDatas = (ArrayList<MobTrainEntity>) getIntent().getSerializableExtra("IntentDatas");
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

    public void queryTrainNo(int position) {
        MobTrainEntity mobTrainEntity = mDatas.get(position);
        mobTrainEntity.setShowDetails(!mobTrainEntity.isShowDetails());
        //刷新Adapter
        initAdapter();

        ArrayList<MobTrainNoEntity> trainDetails = mobTrainEntity.getTrainDetails();
        if (trainDetails == null) {
            MobApi.queryByTrainNo(mobTrainEntity.getStationTrainCode(), position, new MyCallBack() {

                @Override
                public void onSuccess(int what, Object result) {

                }

                @Override
                public void onSuccessList(int what, List results) {
                    ArrayList<MobTrainNoEntity> trainNums = (ArrayList<MobTrainNoEntity>) results;
                    mDatas.get(what).setTrainDetails(trainNums);
                    //刷新Adapter
                    initAdapter();
                }

                @Override
                public void onFail(int what, String result) {
                    MySnackbar.makeSnackBarRed(toolbar, result);
                }
            });
        }

    }


    @OnClick(R.id.btn_sort_start_time)
    public void btn_sort_start_time() {
        //发车时间排序
        sortStartTime();
        //刷新
        initAdapter();
    }


    private void sortStartTime() {
        Collections.sort(mDatas, new Comparator<MobTrainEntity>() {
            @Override
            public int compare(MobTrainEntity mobTrainEntity01, MobTrainEntity mobTrainEntity02) {
                String startTime01 = mobTrainEntity01.getStartTime();
                String startTime02 = mobTrainEntity02.getStartTime();

                int result = 0;
                try {
                    if (sdf.parse(startTime01).getTime() > sdf.parse(startTime02).getTime()) {
                        result = 1;
                    } else if (sdf.parse(startTime01).getTime() < sdf.parse(startTime02).getTime()){
                        result = -1;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return result;
            }
        });
    }

    @OnClick(R.id.btn_sort_lishi_time)
    public void btn_sort_lishi_time() {
        Collections.sort(mDatas, new Comparator<MobTrainEntity>() {
            @Override
            public int compare(MobTrainEntity mobTrainEntity01, MobTrainEntity mobTrainEntity02) {
                String startTime01 = mobTrainEntity01.getLishi();
                String startTime02 = mobTrainEntity02.getLishi();

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

    @OnClick(R.id.btn_sort_end_time)
    public void btn_sort_end_time() {
        Collections.sort(mDatas, new Comparator<MobTrainEntity>() {
            @Override
            public int compare(MobTrainEntity mobTrainEntity01, MobTrainEntity mobTrainEntity02) {
                String startTime01 = mobTrainEntity01.getArriveTime();
                String startTime02 = mobTrainEntity02.getArriveTime();

                int result = 0;
                try {
                    if (sdf.parse(startTime01).getTime() > sdf.parse(startTime02).getTime()) {
                        result = 1;
                    } else {
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
