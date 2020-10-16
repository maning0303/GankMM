package com.maning.gankmm.ui.activity.tools;

import android.graphics.Color;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.rolltools.RubbishTypeResultBean;
import com.maning.gankmm.http.callback.CommonHttpCallback;
import com.maning.gankmm.http.rolltools.RolltoolsApi;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleRubbishAdapter;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.ui.view.MClearEditText;
import com.maning.gankmm.utils.KeyboardUtils;
import com.maning.gankmm.utils.MySnackbar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 垃圾分类
 */
public class RubbishActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.editText)
    MClearEditText editText;
    @Bind(R.id.swipe_target)
    RecyclerView recyclerView;


    private ArrayList<RubbishTypeResultBean.DataEntity.AimEntity> mDatas = new ArrayList<>();
    private RecycleRubbishAdapter recycleRubbishAdapter;
    private String keyWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rubbish);
        ButterKnife.bind(this);


        initMyToolBar();

        initRecyclerView();

    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).color(Color.LTGRAY).build());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (Math.abs(dy) > 20) {
                    KeyboardUtils.hideSoftInput(RubbishActivity.this);
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void initMyToolBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(toolbar, "垃圾分类", R.drawable.gank_ic_back_white);
        } else {
            initToolBar(toolbar, "垃圾分类", R.drawable.gank_ic_back_night);
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


    @OnClick(R.id.btn_query)
    public void btnQuery() {
        showProgressDialog("查询中...");
        //获取关键字
        keyWord = editText.getText().toString();
        if (TextUtils.isEmpty(keyWord)) {
            MySnackbar.makeSnackBarBlack(toolbar, "查询内容不能为空");
            dissmissProgressDialog();
            return;
        }
        KeyboardUtils.hideSoftInput(this);
        //查询
        showProgressDialog("查询中...");
        RolltoolsApi.getRubbishType(keyWord, new CommonHttpCallback<RubbishTypeResultBean>() {
            @Override
            public void onSuccess(RubbishTypeResultBean result) {
                dissmissProgressDialog();
                RubbishTypeResultBean.DataEntity data = result.getData();
                RubbishTypeResultBean.DataEntity.AimEntity aimEntity = data.getAim();
                List<RubbishTypeResultBean.DataEntity.AimEntity> recommendList = data.getRecommendList();
                mDatas = new ArrayList<>();
                mDatas.add(aimEntity);
                mDatas.addAll(recommendList);
                initAdapter();
            }

            @Override
            public void onFail(int code, String message) {
                dissmissProgressDialog();
                MySnackbar.makeSnackBarRed(toolbar, message);
            }
        });
    }

    private void initAdapter() {
        if (recycleRubbishAdapter == null) {
            recycleRubbishAdapter = new RecycleRubbishAdapter(this, mDatas);
            recyclerView.setAdapter(recycleRubbishAdapter);
        } else {
            recycleRubbishAdapter.upddateDatas(mDatas);
        }
    }
}

