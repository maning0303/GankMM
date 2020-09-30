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

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobWxArticleListEntity;
import com.maning.gankmm.bean.mob.MobWxCategoryEntity;
import com.maning.gankmm.http.mob.MobApi;
import com.maning.gankmm.http.callback.MyCallBack;
import com.maning.gankmm.listeners.OnItemClickListener;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleWxArticleAdapter;
import com.maning.gankmm.ui.adapter.RecycleWxCategoryItemAdapter;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.utils.IntentUtils;
import com.maning.gankmm.utils.MySnackbar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 微信精选
 */
public class WXArticleActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.btn_category)
    RelativeLayout btnCategory;
    @Bind(R.id.swipe_target)
    RecyclerView swipeTarget;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @Bind(R.id.recyclerViewCategory)
    RecyclerView recyclerViewCategory;
    @Bind(R.id.rl_bg_category)
    RelativeLayout rlBgCategory;

    private ArrayList<MobWxCategoryEntity> mobWxCategoryEntityArrayList = new ArrayList<>();
    private ArrayList<MobWxArticleListEntity.ListBean> mobWxArticleListEntityList = new ArrayList<>();

    private int pageIndex = 1;
    private int pageSize = 20;
    private MobWxCategoryEntity mobCurrentWxCategoryEntity;
    private RecycleWxArticleAdapter recycleWxArticleAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxarticle);
        ButterKnife.bind(this);

        initMyToolBar();

        initViews();

        queryWXCategory();
    }

    private void initViews() {

        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewCategory.setItemAnimator(new DefaultItemAnimator());

        swipeTarget.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        swipeTarget.setItemAnimator(new DefaultItemAnimator());
        swipeTarget.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).color(Color.parseColor("#FFCCCCCC")).build());

        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshEnabled(true);
        swipeToLoadLayout.setLoadMoreEnabled(true);

    }

    private void initMyToolBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(toolbar, "微信精选", R.drawable.gank_ic_back_white);
        } else {
            initToolBar(toolbar, "微信精选", R.drawable.gank_ic_back_night);
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

    public void queryWXCategory() {
        showProgressDialog("加载中...");
        MobApi.queryWxArticleCategory(0x001, httpCallBack);
    }

    private MyCallBack httpCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Object result) {
            if (what == 0x002) {    //加载新数据
                MobWxArticleListEntity mobWxArticleListEntity = (MobWxArticleListEntity) result;
                if (mobWxArticleListEntity != null) {
                    mobWxArticleListEntityList.clear();
                    mobWxArticleListEntityList = (ArrayList<MobWxArticleListEntity.ListBean>) mobWxArticleListEntity.getList();
                }
                refreshAdapter();
                swipeToLoadLayout.setRefreshing(false);
            } else if (what == 0x003) { //加载下一页数据
                MobWxArticleListEntity mobWxArticleListEntity = (MobWxArticleListEntity) result;
                if (mobWxArticleListEntity != null) {
                    mobWxArticleListEntityList.addAll(mobWxArticleListEntity.getList());
                }
                refreshAdapter();
                swipeToLoadLayout.setLoadingMore(false);
            }
        }

        @Override
        public void onSuccessList(int what, List results) {
            if (what == 0x001) {
                dissmissProgressDialog();
                if (results != null && results.size() > 0) {
                    mobWxCategoryEntityArrayList = (ArrayList<MobWxCategoryEntity>) results;
                    //初始化分类的列表
                    initCategoryAdapter();
                }
            }
        }

        @Override
        public void onFail(int what, String result) {
            dissmissProgressDialog();
            MySnackbar.makeSnackBarRed(toolbar, result);
            if (what == 0x002) {
                swipeToLoadLayout.setRefreshing(false);
            } else if (what == 0x003) {
                swipeToLoadLayout.setLoadingMore(false);
            }
        }
    };

    private void initCategoryAdapter() {
        RecycleWxCategoryItemAdapter recycleWxCategoryItemAdapter = new RecycleWxCategoryItemAdapter(this, mobWxCategoryEntityArrayList);
        recyclerViewCategory.setAdapter(recycleWxCategoryItemAdapter);
        recycleWxCategoryItemAdapter.setOnItemClickLitener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mobCurrentWxCategoryEntity = mobWxCategoryEntityArrayList.get(position);
                tvTitle.setText(mobCurrentWxCategoryEntity.getName());
                hideCategroyRecyclerView();
                //加载列表
                loadNewDatas();
            }
        });
    }

    private void loadNewDatas() {
        //清空
        mobWxArticleListEntityList.clear();
        refreshAdapter();

        swipeToLoadLayout.setRefreshing(true);

        pageIndex = 1;
        MobApi.queryWxArticleList(mobCurrentWxCategoryEntity.getCid(), pageIndex, pageSize, 0x002, httpCallBack);

    }

    private void loadMoreDatas() {
        MobApi.queryWxArticleList(mobCurrentWxCategoryEntity.getCid(), pageIndex, pageSize, 0x003, httpCallBack);
    }

    private void refreshAdapter() {
        pageIndex++;

        //刷新列表
        if (recycleWxArticleAdapter == null) {
            recycleWxArticleAdapter = new RecycleWxArticleAdapter(this, mobWxArticleListEntityList);
            swipeTarget.setAdapter(recycleWxArticleAdapter);
            recycleWxArticleAdapter.setOnItemClickLitener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    MobWxArticleListEntity.ListBean listBean = mobWxArticleListEntityList.get(position);
                    IntentUtils.startToWebActivity(mContext, "微信", listBean.getTitle(), listBean.getSourceUrl());
                }
            });
        } else {
            recycleWxArticleAdapter.updateDatas(mobWxArticleListEntityList);
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

    @OnClick(R.id.btn_category)
    public void btnCategory() {
        //隐藏显示列表
        if (rlBgCategory.getVisibility() == View.GONE) {
            rlBgCategory.setVisibility(View.VISIBLE);
        } else {
            hideCategroyRecyclerView();
        }
    }

    @OnClick(R.id.rl_bg_category)
    public void rl_bg_category() {
        //隐藏显示列表
        hideCategroyRecyclerView();
    }

    private void hideCategroyRecyclerView() {
        rlBgCategory.setVisibility(View.GONE);
    }

}
