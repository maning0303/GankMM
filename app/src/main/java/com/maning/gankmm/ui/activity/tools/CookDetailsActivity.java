package com.maning.gankmm.ui.activity.tools;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobCookDetailEntity;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleCookDetailsAdapter;
import com.maning.gankmm.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 做菜详情
 */
@Deprecated
public class CookDetailsActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public static final String IntentKey_Cook = "IntentKey_Cook";
    private MobCookDetailEntity.ListBean mData = new MobCookDetailEntity.ListBean();
    private RecycleCookDetailsAdapter mRecycleCookDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_details);
        ButterKnife.bind(this);

        initIntent();

        initMyToolBar();

        initView();

        initAdapter();
    }

    private void initAdapter() {
        mRecycleCookDetailsAdapter = new RecycleCookDetailsAdapter(mContext, mData);
        mRecyclerView.setAdapter(mRecycleCookDetailsAdapter);
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setVerticalScrollBarEnabled(true);
    }

    private void initIntent() {
        mData = (MobCookDetailEntity.ListBean) getIntent().getSerializableExtra(IntentKey_Cook);
    }

    private void initMyToolBar() {
        initBackToolBar(mToolbar, mData.getRecipe().getTitle());
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
