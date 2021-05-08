package com.maning.gankmm.ui.activity.tools;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobCookCategoryEntity;
import com.maning.gankmm.bean.mob.MobCookDetailEntity;
import com.maning.gankmm.listeners.OnItemClickListener;
import com.maning.gankmm.ui.adapter.RecycleCookDetailItemAdapter;
import com.maning.gankmm.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.maning.gankmm.R.id.swipeToLoadLayout;


/**
 * 菜谱列表页面
 */
@Deprecated
public class CookListActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.swipe_target)
    RecyclerView mRecyclerView;
    @BindView(swipeToLoadLayout)
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
    }

    private void loadMoreDatas() {
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
        initBackToolBar(mToolbar, mMobCookCategoryEntity.getCategoryInfo().getName());
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
