package com.maning.gankmm.ui.activity.tools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.maning.gankmm.R;
import com.maning.gankmm.app.MyApplication;
import com.maning.gankmm.bean.mob.MobCookCategoryEntity;
import com.maning.gankmm.bean.mob.MobCookDetailEntity;
import com.maning.gankmm.http.mob.MobApi;
import com.maning.gankmm.http.callback.MyCallBack;
import com.maning.gankmm.listeners.OnItemClickListener;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleCookDetailItemAdapter;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.utils.MySnackbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.maning.gankmm.R.id.swipeToLoadLayout;


/**
 * 菜谱列表页面
 */
public class CookListActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.swipe_target)
    RecyclerView mRecyclerView;
    @Bind(swipeToLoadLayout)
    SwipeToLoadLayout mSwipeToLoadLayout;

    public static final String IntentKey_Cook = "IntentKey_Cook";
    private MobCookCategoryEntity mMobCookCategoryEntity = new MobCookCategoryEntity();

    private int pageIndex = 1;
    private int pageSize = 20;

    private List<MobCookDetailEntity.ListBean> mDatas = new ArrayList<>();
    private RecycleCookDetailItemAdapter mRecycleCookDetailItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_cook_list);
        ButterKnife.bind(this);

        initIntent();

        initMyToolBar();

        initViews();

        //加载数据
        mSwipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeToLoadLayout.setRefreshing(true);
            }
        }, 100);
    }

    private void loadNewDatas() {
        pageIndex = 1;
        MobApi.queryCookDetailsList(mMobCookCategoryEntity.getCategoryInfo().getCtgId(), pageIndex, pageSize, 0x001, httpCallBack);
    }

    private void loadMoreDatas() {
        MobApi.queryCookDetailsList(mMobCookCategoryEntity.getCategoryInfo().getCtgId(), pageIndex, pageSize, 0x002, httpCallBack);
    }

    private void initIntent() {
        mMobCookCategoryEntity = (MobCookCategoryEntity) getIntent().getSerializableExtra(IntentKey_Cook);
    }

    private void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setVerticalScrollBarEnabled(true);

        mSwipeToLoadLayout.setOnRefreshListener(this);
        mSwipeToLoadLayout.setOnLoadMoreListener(this);
        mSwipeToLoadLayout.setRefreshEnabled(true);
        mSwipeToLoadLayout.setLoadMoreEnabled(true);
    }

    private void initMyToolBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(mToolbar, mMobCookCategoryEntity.getCategoryInfo().getName(), R.drawable.gank_ic_back_white);
        } else {
            initToolBar(mToolbar, mMobCookCategoryEntity.getCategoryInfo().getName(), R.drawable.gank_ic_back_night);
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

    private MyCallBack httpCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Object result) {
            MobCookDetailEntity mobCookDetailEntity = (MobCookDetailEntity) result;
            pageIndex++;
            if (what == 0x001) {
                mDatas.clear();
                mDatas = mobCookDetailEntity.getList();
                //延时展示刷新动画
                MyApplication.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeToLoadLayout.setRefreshing(false);
                    }
                }, 1000);
            } else {
                List<MobCookDetailEntity.ListBean> moreDatas = mobCookDetailEntity.getList();
                mDatas.addAll(moreDatas);
                mSwipeToLoadLayout.setLoadingMore(false);
            }

            //判断是不是还能加载更多
            if (mDatas.size() >= mobCookDetailEntity.getTotal()) {
                mSwipeToLoadLayout.setLoadMoreEnabled(false);
            } else {
                mSwipeToLoadLayout.setLoadMoreEnabled(true);
            }

            //刷新页面
            initAdapter();

        }

        @Override
        public void onSuccessList(int what, List results) {

        }

        @Override
        public void onFail(int what, String result) {
            if (what == 0x001) {
                mSwipeToLoadLayout.setRefreshing(false);
            } else {
                mSwipeToLoadLayout.setLoadingMore(false);
            }
            MySnackbar.makeSnackBarBlack(mToolbar, result);

        }
    };

    private void initAdapter() {
        if (mRecycleCookDetailItemAdapter == null) {
            mRecycleCookDetailItemAdapter = new RecycleCookDetailItemAdapter(mContext, mDatas);
            mRecyclerView.setAdapter(mRecycleCookDetailItemAdapter);
            mRecycleCookDetailItemAdapter.setOnItemClickLitener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    MobCookDetailEntity.ListBean listBean = mDatas.get(position);
                    Intent intent = new Intent(CookListActivity.this, CookDetailsActivity.class);
                    intent.putExtra(CookDetailsActivity.IntentKey_Cook, listBean);
                    CookListActivity.this.startActivity(intent);
                }
            });
        } else {
            mRecycleCookDetailItemAdapter.updateDatas(mDatas);
        }

    }


    @Override
    public void onRefresh() {
        loadNewDatas();
    }

    @Override
    public void onLoadMore() {
        loadMoreDatas();
    }
}
