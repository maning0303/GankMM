package com.maning.gankmm.ui.activity.mob;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobBankCard;
import com.maning.gankmm.bean.mob.MobItemEntity;
import com.maning.gankmm.http.mob.MobApi;
import com.maning.gankmm.http.callback.MyCallBack;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleMobQueryAdapter;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.ui.view.MClearEditText;
import com.maning.gankmm.utils.KeyboardUtils;
import com.maning.gankmm.utils.MySnackbar;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 银行卡查询
 */
public class BankCardActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.editTextInput)
    MClearEditText editTextInput;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private RecycleMobQueryAdapter recycleMobQueryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card);
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
            initToolBar(toolbar, "银行卡查询", R.drawable.gank_ic_back_white);
        } else {
            initToolBar(toolbar, "银行卡查询", R.drawable.gank_ic_back_night);
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

        //获取输入数据
        String number = editTextInput.getText().toString();

        if (TextUtils.isEmpty(number)) {
            MySnackbar.makeSnackBarRed(toolbar, "银行卡号不能为空");
            return;
        }

        showProgressDialog("正在查询...");
        MobApi.queryBankCard(number, 0x001, new MyCallBack() {
            @Override
            public void onSuccess(int what, Object object) {
                dissmissProgressDialog();
                if (object != null) {
                    MobBankCard result = (MobBankCard) object;
                    initAdapter(result);
                }
            }

            @Override
            public void onSuccessList(int what, List results) {

            }

            @Override
            public void onFail(int what, String result) {
                dissmissProgressDialog();
                MySnackbar.makeSnackBarRed(toolbar, result);
            }
        });

    }

    private void initAdapter(MobBankCard result) {

        HashMap<String, Object> mDatas = new HashMap<>();
        mDatas.put("0", new MobItemEntity("所属银行:", result.getBank()));
        mDatas.put("1", new MobItemEntity("卡名:", result.getCardName()));
        mDatas.put("2", new MobItemEntity("卡片类型:", result.getCardType()));
        mDatas.put("3", new MobItemEntity("卡号长度:", String.valueOf(result.getCardNumber())));
        mDatas.put("4", new MobItemEntity("bin码:", result.getBin()));

        if (recycleMobQueryAdapter == null) {
            recycleMobQueryAdapter = new RecycleMobQueryAdapter(this, mDatas);
            recyclerView.setAdapter(recycleMobQueryAdapter);
        } else {
            recycleMobQueryAdapter.updateDatas(mDatas);
        }

    }

}
