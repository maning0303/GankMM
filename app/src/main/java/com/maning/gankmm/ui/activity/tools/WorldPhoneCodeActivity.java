package com.maning.gankmm.ui.activity.tools;

import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.CommonItemEntity;
import com.maning.gankmm.bean.rolltools.WorldPhoneCodeResultBean;
import com.maning.gankmm.http.callback.CommonHttpCallback;
import com.maning.gankmm.http.rolltools.RolltoolsApi;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleCommonQueryAdapter;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.utils.MySnackbar;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 世界电话区号列表
 */
public class WorldPhoneCodeActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private RecycleCommonQueryAdapter recycleCommonQueryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_phone_code);
        ButterKnife.bind(this);

        initMyToolBar();

        initRecyclerView();

        queryDatas();

    }

    private void queryDatas() {
        showProgressDialog();
        RolltoolsApi.getWorldPhoneCode(new CommonHttpCallback<WorldPhoneCodeResultBean>() {
            @Override
            public void onSuccess(WorldPhoneCodeResultBean result) {
                dissmissProgressDialog();
                List<WorldPhoneCodeResultBean.DataEntity> data = result.getData();

                HashMap<String, Object> mDatas = new HashMap<>();
                for (int i = 0; i < data.size(); i++) {
                    WorldPhoneCodeResultBean.DataEntity dataEntity = data.get(i);
                    mDatas.put("" + i, new CommonItemEntity(dataEntity.getZhCn() + "(" + dataEntity.getEnUs() + ")", dataEntity.getPhoneCode()));
                }
                initAdapter(mDatas);
            }

            @Override
            public void onFail(int code, String message) {
                dissmissProgressDialog();
                MySnackbar.makeSnackBarRed(toolbar, message);
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initMyToolBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(toolbar, "世界电话区号", R.drawable.gank_ic_back_white);
        } else {
            initToolBar(toolbar, "世界电话区号", R.drawable.gank_ic_back_night);
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


    private void initAdapter(HashMap<String, Object> mDatas) {
        if (recycleCommonQueryAdapter == null) {
            recycleCommonQueryAdapter = new RecycleCommonQueryAdapter(this, mDatas, 1);
            recyclerView.setAdapter(recycleCommonQueryAdapter);
        } else {
            recycleCommonQueryAdapter.updateDatas(mDatas);
        }

    }

}
