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
import com.maning.gankmm.bean.rolltools.DictionaryResultBean;
import com.maning.gankmm.http.callback.CommonHttpCallback;
import com.maning.gankmm.http.rolltools.RolltoolsApi;
import com.maning.gankmm.ui.adapter.RecycleCommonQueryAdapter;
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
    private RecycleCommonQueryAdapter recycleCommonQueryAdapter;

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
        initBackToolBar(toolbar, "新华字典查询");
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
        mDatas.put("0", new CommonItemEntity("原内容:", dataEntity.getWord()));
        mDatas.put("1", new CommonItemEntity("繁体:", dataEntity.getTraditional()));
        mDatas.put("2", new CommonItemEntity("拼音:", dataEntity.getPinyin()));
        mDatas.put("3", new CommonItemEntity("偏旁部首:", dataEntity.getRadicals()));
        mDatas.put("4", new CommonItemEntity("汉字释义:", dataEntity.getExplanation()));
        mDatas.put("5", new CommonItemEntity("汉字笔画数:", String.valueOf(dataEntity.getStrokes())));

        if (recycleCommonQueryAdapter == null) {
            recycleCommonQueryAdapter = new RecycleCommonQueryAdapter(this, mDatas);
            recyclerView.setAdapter(recycleCommonQueryAdapter);
        } else {
            recycleCommonQueryAdapter.updateDatas(mDatas);
        }

    }

}
