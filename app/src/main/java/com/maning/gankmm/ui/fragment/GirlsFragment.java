package com.maning.gankmm.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.maning.gankmm.R;
import com.maning.gankmm.bean.gank2.GankEntity;
import com.maning.gankmm.ui.activity.MainActivity;
import com.maning.gankmm.ui.adapter.RecyclePicAdapter;
import com.maning.gankmm.ui.base.BaseFragment;
import com.maning.gankmm.ui.iView.IWelFareView;
import com.maning.gankmm.ui.presenter.impl.GirlPresenterImpl;
import com.maning.gankmm.utils.MySnackbar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 福利Fragment
 */
public class GirlsFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener, IWelFareView {

    @Bind(R.id.swipe_target)
    RecyclerView swipeTarget;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private RecyclePicAdapter recyclePicAdapter;

    private GirlPresenterImpl welFarePresenter;

    public static GirlsFragment newInstance() {
        return new GirlsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wel_fare, container, false);
        ButterKnife.bind(this, view);

        initRefresh();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        welFarePresenter = new GirlPresenterImpl(getActivity(), this);

        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        }, 100);
    }

    private void initRecycleView(List<GankEntity> welFareList) {

        ((MainActivity) getActivity()).setPicList(welFareList);

        if (recyclePicAdapter == null) {
            recyclePicAdapter = new RecyclePicAdapter(context, welFareList);
            swipeTarget.setAdapter(recyclePicAdapter);
            //点击事件
            recyclePicAdapter.setOnItemClickLitener(new RecyclePicAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {
                    welFarePresenter.itemClick(view, position);
                }
            });
            //获取头条随机
            welFarePresenter.getRandomDatas();
        } else {
            recyclePicAdapter.updateDatas(welFareList);
        }

    }

    private void initRefresh() {
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        swipeTarget.setLayoutManager(staggeredGridLayoutManager);
        swipeTarget.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onRefresh() {
        welFarePresenter.getNewDatas();
        //重新定位
        ((MainActivity) getActivity()).refreshLocationInfo();
    }

    @Override
    public void setWelFareList(List<GankEntity> welFareList) {
        initRecycleView(welFareList);
    }

    @Override
    public void setRandomList(List<GankEntity> randomList) {
        if (recyclePicAdapter != null) {
            recyclePicAdapter.updateHeadLines(randomList);
        }
    }

    @Override
    public void showToast(String msg) {
        MySnackbar.makeSnackBarRed(swipeTarget, msg);
    }

    @Override
    public void overRefresh() {
        swipeToLoadLayout.setRefreshing(false);
        swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void setLoadMoreEnabled(boolean flag) {
        swipeToLoadLayout.setLoadMoreEnabled(flag);
    }

    @Override
    public void onLoadMore() {
        welFarePresenter.getMoreDatas();
    }


    @Override
    public void onDestroyView() {
        if (recyclePicAdapter != null) {
            recyclePicAdapter.destroyList();
        }
        welFarePresenter.detachView();
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        //刷新页面
        if (recyclePicAdapter != null) {
            recyclePicAdapter.notifyDataSetChanged();
        }
    }
}
