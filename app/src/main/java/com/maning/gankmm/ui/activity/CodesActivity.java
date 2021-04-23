package com.maning.gankmm.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.maning.gankmm.R;
import com.maning.gankmm.app.MyApplication;
import com.maning.gankmm.bean.web.CategoryContentBean;
import com.maning.gankmm.bean.web.CategoryTitleBean;
import com.maning.gankmm.constant.Constants;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleCodesContentAdapter;
import com.maning.gankmm.ui.adapter.RecycleCodesTitleAdapter;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.ui.iView.ICodesView;
import com.maning.gankmm.ui.presenter.impl.CodesPresenterImpl;
import com.maning.gankmm.utils.DensityUtil;
import com.maning.gankmm.utils.IntentUtils;
import com.maning.gankmm.utils.MySnackbar;
import com.maning.gankmm.utils.NetUtils;
import com.maning.gankmm.utils.SharePreUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 泡在网上的日子的数据的抓取
 * 网站异常，废弃
 */
public class CodesActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener, ICodesView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipe_target)
    RecyclerView recycleContent;
    @BindView(R.id.recycle_menu)
    RecyclerView recycleMenu;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.iv_top_quick)
    ImageView ivTopQuick;

    public static final String IntentType = "Type";
    public static final String IntentTypeName_Jcode = "泡在网上的日子";
    public static final String IntentTypeName_CocoaChina = "CocoaChina";
    private String IntentTypeNameCurrent = "";

    //当前页面的访问地址
    private String url_Current = "";
    private static final String baseUrl_Jcode = "http://www.jcodecraeer.com";
    private static final String baseUrl_CocoaChina = "http://code.cocoachina.com";

    private ArrayList<CategoryTitleBean> titles = new ArrayList<>();
    private ArrayList<CategoryContentBean> codes = new ArrayList<>();

    private RecycleCodesContentAdapter recycleContentAdapter;
    private RecycleCodesTitleAdapter recycleTitleAdapter;

    private Animation animation_up;
    private Animation animation_down;

    private CodesPresenterImpl codesPresenter;
    private int currentSkinType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codes);
        ButterKnife.bind(this);

        //获取Intent数据
        IntentTypeNameCurrent = getIntent().getStringExtra(IntentType);
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(toolbar, IntentTypeNameCurrent, R.drawable.gank_ic_back_white);
        } else {
            initToolBar(toolbar, IntentTypeNameCurrent, R.drawable.gank_ic_back_night);
        }

        initViews();

        initAnimation();

        codesPresenter = new CodesPresenterImpl(this, this, IntentTypeNameCurrent);

        //判断是CocoaChina的诗句还是泡在网上的日志的数据
        if (IntentTypeNameCurrent.equals(CodesActivity.IntentTypeName_Jcode)) {
            url_Current =  baseUrl_Jcode + "/plus/list.php?tid=31";
        }else if (IntentTypeNameCurrent.equals(CodesActivity.IntentTypeName_CocoaChina)){
            url_Current =  baseUrl_CocoaChina;
        }

        //加载数据
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        }, 100);

    }

    private void initAnimation() {
        animation_up = AnimationUtils.loadAnimation(this, R.anim.translate_up);
        animation_down = AnimationUtils.loadAnimation(this, R.anim.translate_down);
    }

    private void initViews() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        recycleMenu = (RecyclerView) findViewById(R.id.recycle_menu);
        recycleContent = (RecyclerView) findViewById(R.id.swipe_target);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycleMenu.setLayoutManager(linearLayoutManager);
        recycleMenu.setItemAnimator(new DefaultItemAnimator());

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycleContent.setLayoutManager(linearLayoutManager2);
        recycleContent.setItemAnimator(new DefaultItemAnimator());


        //添加分割线
        currentSkinType = SkinManager.getCurrentSkinType(this);
        if (currentSkinType == SkinManager.THEME_DAY) {
            recycleContent.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).color(Color.LTGRAY).build());
            recycleMenu.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).color(Color.LTGRAY).build());
        } else {
            recycleContent.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).color(getResources().getColor(R.color.lineColor_night)).build());
            recycleMenu.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).color(getResources().getColor(R.color.lineColor_night)).build());
        }

        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshEnabled(true);
        swipeToLoadLayout.setLoadMoreEnabled(true);
    }

    private void initMenuAdapter() {
        if (recycleTitleAdapter == null) {
            recycleTitleAdapter = new RecycleCodesTitleAdapter(this, titles);
            recycleMenu.setAdapter(recycleTitleAdapter);
            recycleTitleAdapter.setOnItemClickLitener(new RecycleCodesTitleAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {
                    drawerLayout.closeDrawers();
                    if (NetUtils.hasNetWorkConection(CodesActivity.this)) {
                        recycleTitleAdapter.setType(titles.get(position).getTitle());
                        url_Current = titles.get(position).getUrl();
                        scrollToTop();
                        //加载数据
                        swipeToLoadLayout.setRefreshing(true);
                    } else {
                        showToast(getString(R.string.gank_net_fail));
                    }

                }
            });
            //默认第一次展开菜单
            boolean booleanData = SharePreUtil.getBooleanData(MyApplication.getIntstance(), Constants.SPCodesMenu, false);
            if (!booleanData) {
                SharePreUtil.saveBooleanData(MyApplication.getIntstance(), Constants.SPCodesMenu, true);
                drawerLayout.openDrawer(GravityCompat.END);
            }

        }
    }

    private void initContentAdapter() {
        overRefresh();
        if (recycleContentAdapter == null) {
            recycleContentAdapter = new RecycleCodesContentAdapter(this, codes, Glide.with(this));
            recycleContent.setAdapter(recycleContentAdapter);
            recycleContentAdapter.setOnItemClickLitener(new RecycleCodesContentAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {
                    IntentUtils.startToWebActivity(CodesActivity.this, "codes", codes.get(position).getTitle(), codes.get(position).getUrl());
                }
            });
            recycleContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    boolean isSignificantDelta = Math.abs(dy) > DensityUtil.dip2px(CodesActivity.this, 4);
                    if (isSignificantDelta) {
                        if (dy > 0) {
                            onScrollUp();
                        } else {
                            onScrollDown();
                        }
                    }
                }
            });
        } else {
            recycleContentAdapter.setDatas(codes);
        }
    }

    private boolean dismissFlag = true;

    private void onScrollUp() {
        if (ivTopQuick.getVisibility() == View.VISIBLE) {
            if (!dismissFlag) {
                return;
            }
            dismissFlag = false;
            if (animation_down != null) {
                animation_down.cancel();
            }
            ivTopQuick.setVisibility(View.GONE);
            ivTopQuick.startAnimation(animation_down);
            MyApplication.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismissFlag = true;
                }
            }, 500);
        }
    }

    private boolean flag = true;

    private void onScrollDown() {
        if (!(ivTopQuick.getVisibility() == View.VISIBLE)) {
            if (!flag) {
                return;
            }
            flag = false;
            if (animation_up != null) {
                animation_up.cancel();
            }
            ivTopQuick.setVisibility(View.VISIBLE);
            ivTopQuick.startAnimation(animation_up);
            MyApplication.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    flag = true;
                }
            }, 500);
        }
    }

    @OnClick(R.id.iv_top_quick)
    void iv_top_quick() {
        scrollToTop();
    }

    @OnClick(R.id.iv_right_menu)
    void iv_right_menu() {
        drawerLayout.openDrawer(GravityCompat.END);
    }

    public void scrollToTop() {
        recycleContent.scrollToPosition(0);
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

    @Override
    protected void onDestroy() {
        recycleContentAdapter = null;
        recycleTitleAdapter = null;
        codesPresenter.detachView();
        animation_up.cancel();
        animation_up = null;
        animation_down.cancel();
        animation_down = null;
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        //获取数据
        if (NetUtils.hasNetWorkConection(CodesActivity.this)) {
            codesPresenter.getNewDatas(url_Current);
        } else {
            showToast(getString(R.string.gank_net_fail));
            overRefresh();
        }
    }

    @Override
    public void onLoadMore() {
        codesPresenter.getMoreDatas();
    }

    @Override
    public void setCodesTitleList(ArrayList<CategoryTitleBean> codesTitleList) {
        titles = codesTitleList;
        initMenuAdapter();
    }

    @Override
    public void setCodesContentList(ArrayList<CategoryContentBean> codesContentList) {
        codes = codesContentList;
        initContentAdapter();
    }

    @Override
    public void setRefreshEnabled(boolean flag) {
        swipeToLoadLayout.setRefreshEnabled(flag);
    }

    @Override
    public void setLoadMoreEnabled(boolean flag) {
        swipeToLoadLayout.setLoadMoreEnabled(flag);
    }

    @Override
    public void showToast(String msg) {
        MySnackbar.makeSnackBarRed(toolbar, msg);
    }

    @Override
    public void overRefresh() {
        if (swipeToLoadLayout == null) {
            return;
        }
        if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }
        if (swipeToLoadLayout.isLoadingMore()) {
            swipeToLoadLayout.setLoadingMore(false);
        }
    }
}
