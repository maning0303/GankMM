package com.maning.gankmm.ui.activity.tools;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobLotteryEntity;
import com.maning.gankmm.http.mob.MobApi;
import com.maning.gankmm.http.callback.MyCallBack;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleLotteryDetailsAdapter;
import com.maning.gankmm.ui.adapter.RecycleLotteryNumberAdapter;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.utils.MySnackbar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 彩票详情
 */
public class LotteryDetailActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.tv_awardDateTime)
    TextView mTvAwardDateTime;
    @Bind(R.id.tv_sales)
    TextView mTvSales;
    @Bind(R.id.tv_pool)
    TextView mTvPool;
    @Bind(R.id.tv_period)
    TextView mTvPeriod;
    @Bind(R.id.recyclerViewLotteryDetails)
    RecyclerView mRecyclerViewLotteryDetails;


    private MobLotteryEntity mMobLotteryEntity = new MobLotteryEntity();
    public static final String IntentKey_LotteryName = "IntentKey_LotteryName";

    private String lotteryName = "";
    private RecycleLotteryNumberAdapter mRecycleLotteryNumberAdapter;
    private RecycleLotteryDetailsAdapter mRecycleLotteryDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_detail);
        ButterKnife.bind(this);

        initIntent();

        initView();

        initMyToolBar();

        loadData();
    }

    private void loadData() {
        showProgressDialog();
        MobApi.querylotteryDetail(lotteryName, 0x001, new MyCallBack() {
            @Override
            public void onSuccess(int what, Object result) {
                dissmissProgressDialog();
                mMobLotteryEntity = (MobLotteryEntity) result;

                //处理数据
                mTvPeriod.setText("第" + mMobLotteryEntity.getPeriod() + "期");
                mTvAwardDateTime.setText("开奖时间: " + mMobLotteryEntity.getAwardDateTime());
                mTvPool.setText(String.valueOf(mMobLotteryEntity.getPool()));
                mTvSales.setText(String.valueOf(mMobLotteryEntity.getSales()));

                //开奖号码
                initNumberAdpater();

                //中奖信息
                initDetailsAdpater();
            }

            @Override
            public void onSuccessList(int what, List results) {

            }

            @Override
            public void onFail(int what, String result) {
                dissmissProgressDialog();
                MySnackbar.makeSnackBarRed(mToolbar, result);
            }
        });
    }

    private void initNumberAdpater() {
        List<String> lotteryNumber = mMobLotteryEntity.getLotteryNumber();
        if (lotteryNumber != null && lotteryNumber.size() > 0) {
            mRecycleLotteryNumberAdapter = new RecycleLotteryNumberAdapter(this, lotteryNumber);
            mRecyclerView.setAdapter(mRecycleLotteryNumberAdapter);
        }
    }

    private void initDetailsAdpater() {
        List<MobLotteryEntity.LotteryDetailsBean> lotteryDetails = mMobLotteryEntity.getLotteryDetails();
        if (lotteryDetails != null && lotteryDetails.size() > 0) {
            mRecycleLotteryDetailsAdapter = new RecycleLotteryDetailsAdapter(this, lotteryDetails);
            mRecyclerViewLotteryDetails.setAdapter(mRecycleLotteryDetailsAdapter);
        }
    }

    private void initIntent() {
        lotteryName = getIntent().getStringExtra(IntentKey_LotteryName);
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewLotteryDetails.setLayoutManager(linearLayoutManager2);
        mRecyclerViewLotteryDetails.setItemAnimator(new DefaultItemAnimator());
    }

    private void initMyToolBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(mToolbar, lotteryName, R.drawable.gank_ic_back_white);
        } else {
            initToolBar(mToolbar, lotteryName, R.drawable.gank_ic_back_night);
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
