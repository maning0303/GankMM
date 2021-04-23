package com.maning.gankmm.ui.activity.tools;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.maning.gankmm.R;
import com.maning.gankmm.listeners.OnItemClickListener;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleLotteryAdapter;
import com.maning.gankmm.ui.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 彩票分类
 */
@Deprecated
public class LotteryCategoryActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private ArrayList<String> mDatas = new ArrayList<>();
    private RecycleLotteryAdapter mRecycleLotteryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_category);
        ButterKnife.bind(this);

        initMyToolBar();

        initView();

        loadData();
    }

    private void loadData() {

    }

    private void initAdapter() {
        mRecycleLotteryAdapter = new RecycleLotteryAdapter(this, mDatas);
        mRecyclerView.setAdapter(mRecycleLotteryAdapter);

        mRecycleLotteryAdapter.setOnItemClickLitener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //跳转详情页面
                String name = mDatas.get(position);
                Intent intent = new Intent(LotteryCategoryActivity.this, LotteryDetailActivity.class);
                intent.putExtra(LotteryDetailActivity.IntentKey_LotteryName, name);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initMyToolBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(mToolbar, "彩票", R.drawable.gank_ic_back_white);
        } else {
            initToolBar(mToolbar, "彩票", R.drawable.gank_ic_back_night);
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
