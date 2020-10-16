package com.maning.gankmm.ui.activity.tools;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.maning.gankmm.R;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleScanHistoryAdapter;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.utils.CacheManager;
import com.maning.gankmm.utils.ClipUtils;
import com.maning.gankmm.utils.MMKVUtils;
import com.maning.gankmm.utils.MySnackbar;
import com.maning.gankmm.utils.ThreadPoolUtils;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 扫码结果页面
 */
public class ScanResultActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.tv_show)
    TextView tvShow;
    private String content;
    private List<String> scanResult = new ArrayList<>();
    private RecycleScanHistoryAdapter recycleScanHistoryAdapter;

    public static void open(Context context, String content) {
        Intent intent = new Intent(context, ScanResultActivity.class);
        intent.putExtra("content", content);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);
        ButterKnife.bind(this);
        initIntent();
        initBackToolBar(toolbar, "扫码结果");
        initRecyclerView();
        initDatas();
    }

    private void initDatas() {
        tvShow.setText(content);

        //获取扫码历史
        ThreadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                scanResult = CacheManager.getScanResult();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recycleScanHistoryAdapter = new RecycleScanHistoryAdapter(mActivity, (ArrayList<String>) scanResult);
                        recyclerView.setAdapter(recycleScanHistoryAdapter);
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.item_delete:
                //TODO:删除提示


                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scan_history_toolbar, menu);
        return true;
    }

    private void initIntent() {
        content = getIntent().getStringExtra("content");
        if (!TextUtils.isEmpty(content)) {
            CacheManager.saveScanResult(content);
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).color(Color.LTGRAY).build());
    }

    @OnClick(R.id.btn_copy)
    public void btn_copy() {
        //复制到剪切板
        ClipUtils.copy(mActivity, tvShow.getText().toString());
        MySnackbar.makeSnackBarGreen(toolbar, "结果已经复制到剪切板");
    }
}
