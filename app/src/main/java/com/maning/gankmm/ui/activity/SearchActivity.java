package com.maning.gankmm.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.maning.gankmm.R;
import com.maning.gankmm.bean.gank2.Gank2SearchListBean;
import com.maning.gankmm.ui.adapter.RecycleSearchAdapter;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.ui.iView.ISearchView;
import com.maning.gankmm.ui.presenter.impl.SearchPresenterImpl;
import com.maning.gankmm.ui.view.MClearEditText;
import com.maning.gankmm.ui.view.ProgressWheel;
import com.maning.gankmm.utils.IntentUtils;
import com.maning.gankmm.utils.KeyboardUtils;
import com.maning.gankmm.utils.MySnackbar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 搜索页面
 */
public class SearchActivity extends BaseActivity implements ISearchView, OnLoadMoreListener {


    @Bind(R.id.btn_back)
    Button btnBack;
    @Bind(R.id.editTextSearch)
    MClearEditText editTextSearch;
    @Bind(R.id.swipe_target)
    RecyclerView swipeTarget;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @Bind(R.id.progressWheel)
    ProgressWheel progressWheel;

    private SearchPresenterImpl searchPresenter;

    private List<Gank2SearchListBean.DataEntity> resultList;
    private RecycleSearchAdapter recyclePicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        initViews();

        searchPresenter = new SearchPresenterImpl(this, this);

    }

    private void initViews() {

        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    startSearch();
                }
                return false;
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        swipeTarget.setLayoutManager(linearLayoutManager);
        swipeTarget.setItemAnimator(new DefaultItemAnimator());


        //添加分割线
        swipeTarget.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).color(Color.LTGRAY).build());
        swipeToLoadLayout.setRefreshEnabled(false);
        swipeToLoadLayout.setLoadMoreEnabled(false);
        swipeToLoadLayout.setOnLoadMoreListener(this);

        swipeTarget.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //关闭键盘
                KeyboardUtils.hideSoftInput(SearchActivity.this, editTextSearch);
            }
        });

    }

    @OnClick(R.id.btn_back)
    void backClick() {
        this.finish();
    }

    @OnClick(R.id.btn_search)
    void backSearch() {
        startSearch();
    }

    @Override
    public void setSearchList(List<Gank2SearchListBean.DataEntity> resultList) {
        this.resultList = resultList;
        initRecycleAdapter();
    }


    private void startSearch() {
        String result = editTextSearch.getText().toString();
        searchPresenter.searchDatas(result);
        //关闭键盘
        KeyboardUtils.hideSoftInput(this, editTextSearch);
    }

    private void initRecycleAdapter() {

        if (recyclePicAdapter == null) {
            recyclePicAdapter = new RecycleSearchAdapter(this, resultList);
            swipeTarget.setAdapter(recyclePicAdapter);
            //点击事件
            recyclePicAdapter.setOnItemClickLitener(new RecycleSearchAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {
                    //跳转页面
                    Gank2SearchListBean.DataEntity searchBean = resultList.get(position);
                    IntentUtils.startToWebActivity(SearchActivity.this, searchBean.getType(), searchBean.getDesc(), searchBean.getUrl());
                }
            });
        } else {
            recyclePicAdapter.setNewDatas(resultList);
        }

    }

    @Override
    public void showToast(String msg) {
        MySnackbar.makeSnackBarBlack(btnBack, msg);
    }

    @Override
    public void overRefresh() {
        if (swipeToLoadLayout == null) {
            return;
        }
        if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }
        if (swipeToLoadLayout.isLoadingMore()) {
            swipeToLoadLayout.setLoadingMore(false);
        }
    }

    @Override
    public void setLoadMoreEnabled(boolean flag) {
        swipeToLoadLayout.setLoadMoreEnabled(flag);
    }

    @Override
    public void showBaseProgressDialog(String msg) {
        progressWheel.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBaseProgressDialog() {
        progressWheel.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchPresenter.detachView();
    }

    @Override
    public void onLoadMore() {
        searchPresenter.loadMoreDatas();
    }
}
