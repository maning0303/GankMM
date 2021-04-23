package com.maning.gankmm.ui.activity.tools;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobCookCategoryEntity;
import com.maning.gankmm.listeners.OnItemClickListener;
import com.maning.gankmm.ui.adapter.RecycleCookCategoryAdapter;
import com.maning.gankmm.ui.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 菜谱首页
 */
@Deprecated
public class CookMenuActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerViewLeft)
    RecyclerView mRecyclerViewLeft;
    @BindView(R.id.recyclerViewRight)
    RecyclerView mRecyclerViewRight;
    private RecycleCookCategoryAdapter mRecycleCookCategoryAdapterLeft;
    private RecycleCookCategoryAdapter mRecycleCookCategoryAdapterRIght;
    private List<MobCookCategoryEntity> mLeftDatas;
    private List<MobCookCategoryEntity> mRightDatas;
    private MobCookCategoryEntity mMobCookCategoryEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_menu);
        ButterKnife.bind(this);

        initMyToolBar();

        initRecyclerView();

        initDatas();
    }

    private void initMyToolBar() {
        initBackToolBar(mToolbar, "我的厨房");
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

    public void initDatas() {

    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewLeft.setLayoutManager(linearLayoutManager);
        mRecyclerViewLeft.setItemAnimator(new DefaultItemAnimator());


        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewRight.setLayoutManager(linearLayoutManager2);
        mRecyclerViewRight.setItemAnimator(new DefaultItemAnimator());
    }

    private void initAdapterLeft() {
        mRecycleCookCategoryAdapterLeft = new RecycleCookCategoryAdapter(mContext, mLeftDatas, 0);
        mRecyclerViewLeft.setAdapter(mRecycleCookCategoryAdapterLeft);
        mRecycleCookCategoryAdapterLeft.setOnItemClickLitener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                updateRightList(position);

            }
        });

    }

    private void updateRightList(int position) {
        //刷新右边的Adapter
        mRightDatas = mLeftDatas.get(position).getChilds();
        initAdapterRight();
    }

    private void initAdapterRight() {
        mRecycleCookCategoryAdapterRIght = new RecycleCookCategoryAdapter(mContext, mRightDatas, 1);
        mRecyclerViewRight.setAdapter(mRecycleCookCategoryAdapterRIght);
        mRecycleCookCategoryAdapterRIght.setOnItemClickLitener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MobCookCategoryEntity mobCookCategoryEntity = mRightDatas.get(position);
                Intent intent = new Intent(CookMenuActivity.this, CookListActivity.class);
                intent.putExtra(CookListActivity.IntentKey_Cook, mobCookCategoryEntity);
                mContext.startActivity(intent);
            }
        });
    }

}
