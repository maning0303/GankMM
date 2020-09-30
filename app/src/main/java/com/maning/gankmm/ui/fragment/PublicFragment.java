package com.maning.gankmm.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.maning.gankmm.R;
import com.maning.gankmm.bean.gank2.GankEntity;
import com.maning.gankmm.ui.adapter.RecyclePublicAdapter;
import com.maning.gankmm.ui.base.BaseFragment;
import com.maning.gankmm.ui.iView.IPublicView;
import com.maning.gankmm.ui.presenter.impl.PublicPresenterImpl;
import com.maning.gankmm.utils.MySnackbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 公用的Fragment：Android，ios，休息视频，前端，拓展资源，瞎推荐，App
 */
public class PublicFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener, IPublicView {

    @Bind(R.id.swipe_target)
    RecyclerView swipeTarget;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private RecyclePublicAdapter recyclePublicAdapter;

    private String category;
    private String type;

    public static PublicFragment newInstance(String category, String type) {
        PublicFragment publicFragment = new PublicFragment();
        Bundle args = new Bundle();
        args.putString("category", category);
        args.putString("type", type);
        publicFragment.setArguments(args);
        return publicFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString("category");
            type = getArguments().getString("type");
            //这个页面单独统计
            className = "CategoryFragment-" + type;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common, container, false);
        ButterKnife.bind(this, view);

        initRefresh();

        return view;
    }

    private PublicPresenterImpl publicPresenter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        publicPresenter = new PublicPresenterImpl(getActivity(), this, category, type);

        publicPresenter.getDBDatas();
    }

    private void initAdapter(List<GankEntity> publicList) {
        if(publicList == null){
            publicList = new ArrayList<>();
        }
        if (recyclePublicAdapter == null) {
            recyclePublicAdapter = new RecyclePublicAdapter(context, publicList);
            swipeTarget.setAdapter(recyclePublicAdapter);
            //点击事件
            recyclePublicAdapter.setOnItemClickLitener(new RecyclePublicAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {
                    publicPresenter.itemClick(position);
                }
            });

        } else {
            recyclePublicAdapter.updateDatas(publicList);
        }

    }


    private void initRefresh() {
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        swipeTarget.setLayoutManager(linearLayoutManager);
        swipeTarget.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onDestroyView() {
        publicPresenter.detachView();
        super.onDestroyView();
        overRefresh();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        publicPresenter.getNewDatas();
    }

    @Override
    public void onLoadMore() {
        publicPresenter.getMoreDatas();
    }

    @Override
    public void setPublicList(List<GankEntity> publicList) {
        initAdapter(publicList);
    }

    @Override
    public void showToast(String msg) {
        MySnackbar.makeSnackBarRed(swipeTarget, msg);
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
    public void setRefreshing(boolean flag) {
        //自动刷新
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });
    }

}
