package com.maning.gankmm.ui.activity.tools;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobItemEntity;
import com.maning.gankmm.bean.rolltools.DictionaryResultBean;
import com.maning.gankmm.http.callback.CommonHttpCallback;
import com.maning.gankmm.http.rolltools.RolltoolsApi;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleMobQueryAdapter;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.ui.view.MClearEditText;
import com.maning.gankmm.utils.KeyboardUtils;
import com.maning.gankmm.utils.MySnackbar;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新华字典查询
 */
public class DictionaryActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.editText)
    MClearEditText editText;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private RecycleMobQueryAdapter recycleMobQueryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
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
            initToolBar(toolbar, "新华字典查询", R.drawable.gank_ic_back_white);
        } else {
            initToolBar(toolbar, "新华字典查询", R.drawable.gank_ic_back_night);
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

        //获取输入
        String content = editText.getText().toString();

        if (TextUtils.isEmpty(content)) {
            MySnackbar.makeSnackBarRed(toolbar, "输入内容不能为空");
            return;
        }

        showProgressDialog("正在查询...");
        RolltoolsApi.convertDictionary(content, new CommonHttpCallback<DictionaryResultBean>() {
            @Override
            public void onSuccess(DictionaryResultBean result) {
                dissmissProgressDialog();
                DictionaryResultBean.DataEntity dataEntity = result.getData().get(0);
                initAdapter(dataEntity);
            }

            @Override
            public void onFail(int code, String message) {
                dissmissProgressDialog();
                MySnackbar.makeSnackBarRed(toolbar, message);
            }
        });

    }

    private void initAdapter(DictionaryResultBean.DataEntity dataEntity) {
        HashMap<String, Object> mDatas = new HashMap<>();
        mDatas.put("0", new MobItemEntity("原内容:", dataEntity.getWord()));
        mDatas.put("1", new MobItemEntity("繁体:", dataEntity.getTraditional()));
        mDatas.put("2", new MobItemEntity("拼音:", dataEntity.getPinyin()));
        mDatas.put("3", new MobItemEntity("偏旁部首:", dataEntity.getRadicals()));
        mDatas.put("4", new MobItemEntity("汉字释义:", dataEntity.getExplanation()));
        mDatas.put("5", new MobItemEntity("汉字笔画数:", String.valueOf(dataEntity.getStrokes())));

        if (recycleMobQueryAdapter == null) {
            recycleMobQueryAdapter = new RecycleMobQueryAdapter(this, mDatas);
            recyclerView.setAdapter(recycleMobQueryAdapter);
        } else {
            recycleMobQueryAdapter.updateDatas(mDatas);
        }

    }

}
