package com.maning.gankmm.ui.activity.tools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobCarEntity;
import com.maning.gankmm.http.mob.MobApi;
import com.maning.gankmm.http.callback.MyCallBack;
import com.maning.gankmm.listeners.OnItemClickListener;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleCarListAdapter;
import com.maning.gankmm.ui.adapter.RecycleCarListSonAdapter;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.ui.view.WaveSideBarView;
import com.maning.gankmm.utils.MySnackbar;
import com.maning.gankmm.utils.PinyinUtils;
import com.socks.library.KLog;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Deprecated
public class CarListActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.waveSideBarView)
    WaveSideBarView mWaveSideBarView;
    @Bind(R.id.iv_close)
    ImageView mIvClose;
    @Bind(R.id.tv_son_name)
    TextView mTvSonName;
    @Bind(R.id.recyclerViewSon)
    RecyclerView mRecyclerViewSon;
    @Bind(R.id.ll_son_view)
    LinearLayout mLlSonView;
    private List<MobCarEntity> mDatas;
    private RecycleCarListAdapter mRecycleCarListAdapter;
    private Animation mAnimation01;
    private Animation mAnimation02;
    private RecycleCarListSonAdapter mRecycleCarListSonAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        ButterKnife.bind(this);

        initMyToolBar();

        initViews();

        initDatas();

        initAnim();

    }

    private void initAnim() {
        mAnimation01 = AnimationUtils.loadAnimation(mContext, R.anim.translate_down);
        mAnimation02 = AnimationUtils.loadAnimation(mContext, R.anim.translate_up);
    }

    private void initViews() {
        initRecyclerView();

        initOtherView();

    }

    private void initOtherView() {
        mWaveSideBarView.setVisibility(View.GONE);
        mWaveSideBarView.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                if (mRecycleCarListAdapter == null) {
                    return;
                }
                int pos = mRecycleCarListAdapter.getLetterPosition(letter);
                if (pos != -1) {
                    mRecyclerView.scrollToPosition(pos);
                    LinearLayoutManager mLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                    mLayoutManager.scrollToPositionWithOffset(pos, 0);
                }
            }
        });
    }

    private void initDatas() {
        showProgressDialog("正在加载...");
        MobApi.queryCarList(0x001, new MyCallBack() {
            @Override
            public void onSuccess(int what, Object result) {

            }

            @Override
            public void onSuccessList(int what, List results) {
                dissmissProgressDialog();
                //处理数据
                mDatas = results;
                if (mDatas != null && mDatas.size() > 0) {
                    //排序处理
                    for (int i = 0; i < mDatas.size(); i++) {
                        MobCarEntity mobCarEntity = mDatas.get(i);
                        String name = mobCarEntity.getName();
                        String namePinYin = PinyinUtils.toPinyin(name);
                        mobCarEntity.setPinyin(namePinYin);
                    }
                    KLog.i("排序前:" + mDatas);
                    //排序
                    Collections.sort(mDatas, new Comparator<MobCarEntity>() {
                        @Override
                        public int compare(MobCarEntity l, MobCarEntity r) {
                            if (l == null || r == null) {
                                return 0;
                            }
                            String lhsSortLetters = l.getPinyin().toUpperCase();
                            String rhsSortLetters = r.getPinyin().toUpperCase();
                            return lhsSortLetters.compareTo(rhsSortLetters);
                        }
                    });
                    KLog.i("排序后:" + mDatas);

                    //初始化Adapter
                    initAdapter();

                }
            }

            @Override
            public void onFail(int what, String result) {
                dissmissProgressDialog();
                MySnackbar.makeSnackBarRed(mToolbar, result);
            }
        });
    }

    private void initAdapter() {
        mRecycleCarListAdapter = new RecycleCarListAdapter(this, mDatas);
        mRecyclerView.setAdapter(mRecycleCarListAdapter);
        mWaveSideBarView.setVisibility(View.VISIBLE);
        mRecycleCarListAdapter.setOnItemClickLitener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MobCarEntity mobCarEntity = mDatas.get(position);
                mTvSonName.setText(mobCarEntity.getName());
                initAdapter2(mobCarEntity.getSon());
                showSonView();
            }
        });
    }

    private void initAdapter2(List<MobCarEntity.SonBean> mSon) {
        mRecycleCarListSonAdapter = new RecycleCarListSonAdapter(this, mSon);
        mRecyclerViewSon.setAdapter(mRecycleCarListSonAdapter);
        mRecycleCarListSonAdapter.setOnItemClickLitener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MobCarEntity.SonBean itemData = mRecycleCarListSonAdapter.getItemData(position);
                Intent intent = new Intent(CarListActivity.this, CarItemsActivity.class);
                intent.putExtra(CarItemsActivity.IntentKey_Car, itemData);
                mContext.startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewSon.setLayoutManager(linearLayoutManager2);
        mRecyclerViewSon.setItemAnimator(new DefaultItemAnimator());
    }

    private void initMyToolBar() {
        initBackToolBar(mToolbar, "汽车品牌");
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


    @OnClick(R.id.iv_close)
    public void iv_close() {
        hideSonView();
    }

    private void showSonView() {
        mLlSonView.setVisibility(View.VISIBLE);
        mLlSonView.startAnimation(mAnimation02);

    }

    private void hideSonView() {
        mLlSonView.startAnimation(mAnimation01);
        mAnimation01.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mLlSonView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
