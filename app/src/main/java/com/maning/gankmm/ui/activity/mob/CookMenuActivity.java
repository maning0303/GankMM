package com.maning.gankmm.ui.activity.mob;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobCookCategoryEntity;
import com.maning.gankmm.http.mob.MobApi;
import com.maning.gankmm.http.MyCallBack;
import com.maning.gankmm.listeners.OnItemClickListener;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleCookCategoryAdapter;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.utils.MySnackbar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 菜谱首页
 */
public class CookMenuActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recyclerViewLeft)
    RecyclerView mRecyclerViewLeft;
    @Bind(R.id.recyclerViewRight)
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
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(mToolbar, "我的厨房", R.drawable.gank_ic_back_white);
        } else {
            initToolBar(mToolbar, "我的厨房", R.drawable.gank_ic_back_night);
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

    public void initDatas() {

        showProgressDialog();

        MobApi.queryCookCategory(0x001, new MyCallBack() {
            @Override
            public void onSuccess(int what, Object result) {
                dissmissProgressDialog();
                mMobCookCategoryEntity = (MobCookCategoryEntity) result;
                if (mMobCookCategoryEntity != null) {
                    //刷新页面
                    mLeftDatas = mMobCookCategoryEntity.getChilds();
                    initAdapterLeft();
                    updateRightList(0);
                }
            }

            @Override
            public void onSuccessList(int what, List results) {

            }

            @Override
            public void onFail(int what, String result) {
                dissmissProgressDialog();
                MySnackbar.makeSnackBarRed(mToolbar, result);
            }
        });

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
