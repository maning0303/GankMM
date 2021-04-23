package com.maning.gankmm.ui.activity.tools;

import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.CommonItemEntity;
import com.maning.gankmm.bean.rolltools.MobileLocationResultBean;
import com.maning.gankmm.http.callback.CommonHttpCallback;
import com.maning.gankmm.http.rolltools.RolltoolsApi;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleCommonQueryAdapter;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.ui.view.MClearEditText;
import com.maning.gankmm.utils.GankUtils;
import com.maning.gankmm.utils.KeyboardUtils;
import com.maning.gankmm.utils.MySnackbar;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 手机号码归属地查询
 */
public class PhoneAddressActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.editTextPhone)
    MClearEditText editTextPhone;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private RecycleCommonQueryAdapter recycleCommonQueryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_address);
        ButterKnife.bind(this);

        initMyToolBar();

        initRecyclerView();

    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initMyToolBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(toolbar, "手机号码归属地查询", R.drawable.gank_ic_back_white);
        } else {
            initToolBar(toolbar, "手机号码归属地查询", R.drawable.gank_ic_back_night);
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

        KeyboardUtils.hideSoftInput(this);

        //获取手机号码
        String phoneNumber = editTextPhone.getText().toString();

        if (TextUtils.isEmpty(phoneNumber)) {
            MySnackbar.makeSnackBarRed(toolbar, "手机号码不能为空");
            return;
        }

        if (!GankUtils.isMobile(phoneNumber)) {
            MySnackbar.makeSnackBarRed(toolbar, "手机号码格式不正确");
            return;
        }

        showProgressDialog("正在查询...");
        RolltoolsApi.getMobileLocation(phoneNumber, new CommonHttpCallback<MobileLocationResultBean>() {
            @Override
            public void onSuccess(MobileLocationResultBean result) {
                dissmissProgressDialog();
                initAdapter(result.getData());
            }

            @Override
            public void onFail(int code, String message) {
                dissmissProgressDialog();
                MySnackbar.makeSnackBarRed(toolbar, message);
            }
        });

    }

    private void initAdapter(MobileLocationResultBean.DataEntity mobPhone) {
        HashMap<String, Object> mDatas = new HashMap<>();
        mDatas.put("0", new CommonItemEntity("手机号码:", mobPhone.getMobile()));
        mDatas.put("1", new CommonItemEntity("归属地省份:", mobPhone.getProvince()));
        mDatas.put("2", new CommonItemEntity("归属地描述:", mobPhone.getCarrier()));

        if (recycleCommonQueryAdapter == null) {
            recycleCommonQueryAdapter = new RecycleCommonQueryAdapter(this, mDatas);
            recyclerView.setAdapter(recycleCommonQueryAdapter);
        } else {
            recycleCommonQueryAdapter.updateDatas(mDatas);
        }

    }

}
