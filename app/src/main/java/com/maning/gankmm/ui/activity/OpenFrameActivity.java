package com.maning.gankmm.ui.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.OpenFrameEntity;
import com.maning.gankmm.listeners.OnItemClickListener;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleOpenFrameAdapter;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.utils.IntentUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 开源框架页面展示
 */
public class OpenFrameActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recyclerViewOpenFrame)
    RecyclerView mRecyclerViewOpenFrame;
    private RecycleOpenFrameAdapter mRecycleOpenFrameAdapter;

    private ArrayList<OpenFrameEntity> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_frame);
        ButterKnife.bind(this);

        initMyToolBar();

        initViews();

        initDatas();

        initAdapter();
    }

    private void initDatas() {
//        ###### 注解框架 [butterknife](https://github.com/JakeWharton/butterknife)
//        ###### Json解析 [Gson](https://github.com/google/gson)
//        ###### 网络框架 [retrofit](https://github.com/square/retrofit)  [okhttp](https://github.com/square/okhttp)
//        ###### 打印日志框架 [klog](https://github.com/ZhaoKaiQiang/KLog)
//        ###### 图片加载 [glide](https://github.com/bumptech/glide)
//        ###### 刷新框架 [SwipeToLoadLayout](https://github.com/Aspsine/SwipeToLoadLayout)
//        ###### 解析Html [jsoup](https://github.com/jhy/jsoup)
//        ###### 权限管理库 [AndPermission](https://github.com/yanzhenjie/AndPermission)
//        ###### 提示框  [material-dialogs](https://github.com/afollestad/material-dialogs)  [Android-SVProgressHUD](https://github.com/saiwu-bigkoo/Android-SVProgressHUD)
//        ###### RecycleView分割线 [RecyclerView-FlexibleDivider](https://github.com/yqritc/RecyclerView-FlexibleDivider)
//        ###### ViewPager的标题控件 [smarttablayout](https://github.com/ogaclejapan/SmartTabLayout)
//        ###### 广告轮播控件 [SwitcherView](https://github.com/maning0303/SwitcherView)
//        ###### 收藏按钮 [ThumbUp](https://github.com/ldoublem/ThumbUp)
//        ###### 模糊控件 [Blurry](https://github.com/wasabeef/Blurry)
//        ###### 网络请求监控 [chuck](https://github.com/jgilfelt/chuck)
//        ###### 表格控件 [scrollablepanel](https://github.com/Kelin-Hong/ScrollablePanel)
//        ###### 可以展开的文字 [expandableTextView](https://github.com/Manabu-GT/ExpandableTextView)
//        ###### 自定义日历控件 [MNCalendar](https://github.com/maning0303/MNCalendar)
//        ###### 日志监听 [MNCrashMonitor](https://github.com/maning0303/MNCrashMonitor)
//        ###### 图片缩放 [PhotoView](https://github.com/chrisbanes/PhotoView)
//        ###### APK升级安装 [MNUpdateAPK](https://github.com/maning0303/MNUpdateAPK)
//        ###### 夜间模式 [MNChangeSkin](https://github.com/maning0303/MNChangeSkin)
//        ###### 图片浏览 [MNImageBrowser](https://github.com/maning0303/MNImageBrowser)
//        ###### 汉字转拼音 [TinyPinyin](https://github.com/promeG/TinyPinyin)
//        ###### 快速跳跃分组的侧边栏控件 [WaveSideBar](https://github.com/Solartisan/WaveSideBar)
//        ###### 背景可以移动的View [KenBurnsView](https://github.com/flavioarfaria/KenBurnsView)
//        ###### 圆形图片 [CircleImageView](https://github.com/hdodenhof/CircleImageView)
//        ###### 滚轮选择器 [Android-PickerView](https://github.com/Bigkoo/Android-PickerView)
//        ###### 图片选择器 [PictureSelector](https://github.com/LuckSiege/PictureSelector)


        mDatas.add(new OpenFrameEntity("ButterKnife", "https://github.com/JakeWharton/butterknife"));
        mDatas.add(new OpenFrameEntity("Gson", "https://github.com/google/gson"));
        mDatas.add(new OpenFrameEntity("Retrofit", "https://github.com/square/retrofit"));
        mDatas.add(new OpenFrameEntity("okhttp", "https://github.com/square/okhttp"));
        mDatas.add(new OpenFrameEntity("KLog", "https://github.com/ZhaoKaiQiang/KLog"));
        mDatas.add(new OpenFrameEntity("Glide", "https://github.com/bumptech/glide"));
        mDatas.add(new OpenFrameEntity("SwipeToLoadLayout", "https://github.com/Aspsine/SwipeToLoadLayout"));
        mDatas.add(new OpenFrameEntity("jsoup", "https://github.com/jhy/jsoup"));
        mDatas.add(new OpenFrameEntity("AndPermission", "https://github.com/yanzhenjie/AndPermission"));
        mDatas.add(new OpenFrameEntity("material-dialogs", "https://github.com/afollestad/material-dialogs"));
        mDatas.add(new OpenFrameEntity("Android-SVProgressHUD", "https://github.com/saiwu-bigkoo/Android-SVProgressHUD"));
        mDatas.add(new OpenFrameEntity("RecyclerView-FlexibleDivider", "https://github.com/yqritc/RecyclerView-FlexibleDivider"));
        mDatas.add(new OpenFrameEntity("SmartTabLayout", "https://github.com/ogaclejapan/SmartTabLayout"));
        mDatas.add(new OpenFrameEntity("SwitcherView", "https://github.com/maning0303/SwitcherView"));
        mDatas.add(new OpenFrameEntity("ThumbUp", "https://github.com/ldoublem/ThumbUp"));
        mDatas.add(new OpenFrameEntity("Blurry", "https://github.com/wasabeef/Blurry"));
        mDatas.add(new OpenFrameEntity("chuck", "https://github.com/jgilfelt/chuck"));
        mDatas.add(new OpenFrameEntity("ScrollablePanel", "https://github.com/Kelin-Hong/ScrollablePanel"));
        mDatas.add(new OpenFrameEntity("ExpandableTextView", "https://github.com/Manabu-GT/ExpandableTextView"));
        mDatas.add(new OpenFrameEntity("MNCalendar", "https://github.com/maning0303/MNCalendar"));
        mDatas.add(new OpenFrameEntity("MNCrashMonitor", "https://github.com/maning0303/MNCrashMonitor"));
        mDatas.add(new OpenFrameEntity("MNUpdateAPK", "https://github.com/maning0303/MNUpdateAPK"));
        mDatas.add(new OpenFrameEntity("MNChangeSkin", "https://github.com/maning0303/MNChangeSkin"));
        mDatas.add(new OpenFrameEntity("MNImageBrowser", "https://github.com/maning0303/MNImageBrowser"));
        mDatas.add(new OpenFrameEntity("PhotoView", "https://github.com/chrisbanes/PhotoView"));
        mDatas.add(new OpenFrameEntity("TinyPinyin", "https://github.com/promeG/TinyPinyin"));
        mDatas.add(new OpenFrameEntity("WaveSideBar", "https://github.com/Solartisan/WaveSideBar"));
        mDatas.add(new OpenFrameEntity("KenBurnsView", "https://github.com/flavioarfaria/KenBurnsView"));
        mDatas.add(new OpenFrameEntity("CircleImageView", "https://github.com/hdodenhof/CircleImageView"));
        mDatas.add(new OpenFrameEntity("Android-PickerView", "https://github.com/Bigkoo/Android-PickerView"));
        mDatas.add(new OpenFrameEntity("PictureSelector", "https://github.com/LuckSiege/PictureSelector"));

    }

    private void initViews() {
        //初始化RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewOpenFrame.setLayoutManager(linearLayoutManager);

    }

    private void initAdapter() {
        mRecycleOpenFrameAdapter = new RecycleOpenFrameAdapter(this, mDatas);
        mRecyclerViewOpenFrame.setAdapter(mRecycleOpenFrameAdapter);

        mRecycleOpenFrameAdapter.setOnItemClickLitener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                OpenFrameEntity openFrameEntity = mDatas.get(position);
                IntentUtils.startToWebActivity(mContext, null, "GitHub:" + openFrameEntity.getFrameName(), openFrameEntity.getFrameGithubUrl());
            }
        });
    }

    private void initMyToolBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(mToolbar, "开源框架", R.drawable.gank_ic_back_white);
        } else {
            initToolBar(mToolbar, "开源框架", R.drawable.gank_ic_back_night);
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
}
