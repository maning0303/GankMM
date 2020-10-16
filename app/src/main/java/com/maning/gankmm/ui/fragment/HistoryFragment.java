package com.maning.gankmm.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.maning.gankmm.R;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleHistoryAdapter;
import com.maning.gankmm.ui.base.BaseFragment;
import com.maning.gankmm.ui.iView.IHistoryView;
import com.maning.gankmm.ui.presenter.impl.HistoryPresenterImpl;
import com.maning.gankmm.utils.IntentUtils;
import com.maning.gankmm.utils.MySnackbar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 历史时间
 */
public class HistoryFragment extends BaseFragment implements OnRefreshListener, IHistoryView {

    @Bind(R.id.swipe_target)
    RecyclerView swipeTarget;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;


    private HistoryPresenterImpl historyPresenter;

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time, container, false);
        ButterKnife.bind(this, view);

        initRefresh();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        historyPresenter = new HistoryPresenterImpl(getActivity(), this);
        //自动刷新
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });
    }


    private void initRecycleView(final List<String> historyList) {
        RecycleHistoryAdapter recyclePicAdapter = new RecycleHistoryAdapter(context, historyList);
        swipeTarget.setAdapter(recyclePicAdapter);
        //点击事件
        recyclePicAdapter.setOnItemClickLitener(new RecycleHistoryAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                IntentUtils.startDayShowActivity(getActivity(), historyList.get(position));
            }
        });
        overRefresh();
    }

    private void initRefresh() {
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setLoadMoreEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        swipeTarget.setLayoutManager(linearLayoutManager);
        swipeTarget.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        int currentSkinType = SkinManager.getCurrentSkinType(getActivity());
        if (currentSkinType == SkinManager.THEME_DAY) {
            swipeTarget.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).color(Color.LTGRAY).build());
        } else {
            swipeTarget.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).color(getResources().getColor(R.color.lineColor_night)).build());
        }

    }

    @Override
    public void onRefresh() {
        historyPresenter.getHistoryDatas();
    }

    @Override
    public void setHistoryList(List<String> historyList) {
        initRecycleView(historyList);
    }

    @Override
    public void showToast(String msg) {
        MySnackbar.makeSnackBarRed(swipeTarget, msg);
    }

    @Override
    public void overRefresh() {
        swipeToLoadLayout.setRefreshing(false);
    }

    @Override
    public void showBaseProgressDialog(String msg) {
        showProgressDialog();
    }

    @Override
    public void hideBaseProgressDialog() {
        dissmissProgressDialog();
    }


    @Override
    public void onDestroyView() {
        overRefresh();
        historyPresenter.detachView();
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
