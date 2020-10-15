package com.maning.gankmm.ui.activity.tools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobCarEntity;
import com.maning.gankmm.bean.mob.MobCarItemEntity;
import com.maning.gankmm.http.mob.MobApi;
import com.maning.gankmm.http.callback.MyCallBack;
import com.maning.gankmm.listeners.OnItemClickListener;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleCarItemAdapter;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.utils.MySnackbar;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CarItemsActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public static final String IntentKey_Car = "IntentKey_Car";
    private MobCarEntity.SonBean mSonBean = new MobCarEntity.SonBean();
    private ArrayList<MobCarItemEntity> mMDatas;
    private RecycleCarItemAdapter mRecycleCarItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_items);
        ButterKnife.bind(this);

        mSonBean = (MobCarEntity.SonBean) getIntent().getSerializableExtra(IntentKey_Car);

        initMyToolBar();

        initRecyclerView();

        initDatas();

    }

    private void initDatas() {
        showProgressDialog("加载中...");
        MobApi.queryCarItems(mSonBean.getType(), 0x001, new MyCallBack() {
            @Override
            public void onSuccess(int what, Object result) {

            }

            @Override
            public void onSuccessList(int what, List results) {
                dissmissProgressDialog();
                mMDatas = (ArrayList<MobCarItemEntity>) results;
                KLog.i(mMDatas.toString());

                initAdapter();
            }

            @Override
            public void onFail(int what, String result) {
                dissmissProgressDialog();
                MySnackbar.makeSnackBarRed(mToolbar, result);
            }
        });
    }

    private void initAdapter() {
        mRecycleCarItemAdapter = new RecycleCarItemAdapter(mContext, mMDatas);
        mRecyclerView.setAdapter(mRecycleCarItemAdapter);
        mRecycleCarItemAdapter.setOnItemClickLitener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //跳转详情页面
                MobCarItemEntity mobCarItemEntity = mMDatas.get(position);
                Intent intent = new Intent(CarItemsActivity.this, CarDetailActivity.class);
                intent.putExtra(CarDetailActivity.IntentKey_CarItemEntity, mobCarItemEntity);
                mContext.startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initMyToolBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(mToolbar, mSonBean.getType(), R.drawable.gank_ic_back_white);
        } else {
            initToolBar(mToolbar, mSonBean.getType(), R.drawable.gank_ic_back_night);
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
