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
import com.maning.gankmm.bean.rolltools.IpResultBean;
import com.maning.gankmm.http.callback.CommonHttpCallback;
import com.maning.gankmm.http.rolltools.RolltoolsApi;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleCommonQueryAdapter;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.ui.view.MClearEditText;
import com.maning.gankmm.utils.KeyboardUtils;
import com.maning.gankmm.utils.MySnackbar;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * IP查询
 */
public class IPQueryActivity extends BaseActivity {

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
        setContentView(R.layout.activity_ip_query);
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
            initToolBar(toolbar, "IP查询", R.drawable.gank_ic_back_white);
        } else {
            initToolBar(toolbar, "IP查询", R.drawable.gank_ic_back_night);
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


    @OnClick({R.id.btn_query_self})
    public void btnQuerySelf() {
        showProgressDialog("正在查询...");
        RolltoolsApi.getIpLocationSelf(new CommonHttpCallback<IpResultBean>() {
            @Override
            public void onSuccess(IpResultBean result) {
                dissmissProgressDialog();
                IpResultBean.DataEntity data = result.getData();
                initAdapter(data);
            }

            @Override
            public void onFail(int code, String message) {
                dissmissProgressDialog();
                MySnackbar.makeSnackBarRed(toolbar, message);
            }
        });
    }

    @OnClick({R.id.btn_query})
    public void btnQuery() {

        KeyboardUtils.hideSoftInput(this);

        //获取输入
        String number = editTextPhone.getText().toString();

        if (TextUtils.isEmpty(number)) {
            MySnackbar.makeSnackBarRed(toolbar, "IP号码不能为空");
            return;
        }
        showProgressDialog("正在查询...");
        RolltoolsApi.getIpLocation(number, new CommonHttpCallback<IpResultBean>() {
            @Override
            public void onSuccess(IpResultBean result) {
                dissmissProgressDialog();
                IpResultBean.DataEntity data = result.getData();
                initAdapter(data);
            }

            @Override
            public void onFail(int code, String message) {
                dissmissProgressDialog();
                MySnackbar.makeSnackBarRed(toolbar, message);
            }
        });


    }

    private void initAdapter(IpResultBean.DataEntity data) {

        HashMap<String, Object> mDatas = new HashMap<>();
        mDatas.put("0", new CommonItemEntity("IP:", data.getIp()));
        mDatas.put("1", new CommonItemEntity("服务商名称:", data.getIsp()));
        mDatas.put("2", new CommonItemEntity("IP描述:", data.getDesc()));
        mDatas.put("3", new CommonItemEntity("城市:", data.getProvince() + " " + data.getCity()));

        if (recycleCommonQueryAdapter == null) {
            recycleCommonQueryAdapter = new RecycleCommonQueryAdapter(this, mDatas);
            recyclerView.setAdapter(recycleCommonQueryAdapter);
        } else {
            recycleCommonQueryAdapter.updateDatas(mDatas);
        }

    }

}
