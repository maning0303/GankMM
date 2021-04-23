package com.maning.gankmm.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jaeger.library.StatusBarUtil;
import com.maning.gankmm.R;
import com.maning.gankmm.bean.gank2.GankEntity;
import com.maning.gankmm.bean.rolltools.HolidayBean;
import com.maning.gankmm.bean.rolltools.WeatherFuturedaysResultBean;
import com.maning.gankmm.bean.weather.WeatherInfoBean;
import com.maning.gankmm.bean.weather.zhixin.ZhixinSuggestionEntity;
import com.maning.gankmm.ui.adapter.WeatherAdapter;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.ui.iView.IWeatherView;
import com.maning.gankmm.ui.presenter.impl.WeatherPresenterImpl;
import com.maning.gankmm.utils.MySnackbar;
import com.socks.library.KLog;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.blurry.Blurry;

/**
 * 天气界面
 */
public class WeatherActivity extends BaseActivity implements OnRefreshListener, IWeatherView {

    public static final String intentKey_weatherBean = "intentKey_weatherBean";
    public static final String intentKey_weatherProvinceName = "intentKey_weatherProvinceName";
    public static final String intentKey_weatherCityName = "intentKey_weatherCityName";
    public static final String intentKey_bgUrls = "intentKey_bgUrls";
    private static final String TAG = "WeatherActivity";
    private static final float defaultAlpha = 0.0f;
    private static final float maxAlpha = 1.0f;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.iv_bg2)
    ImageView ivBg2;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.ll_bg_blur)
    LinearLayout llBgBlur;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private HolidayBean holidayBean;
    private WeatherInfoBean weatherInfoBean;
    private ZhixinSuggestionEntity lifeSuggestionBean;
    private List<WeatherFuturedaysResultBean.DataEntity.ForecastsEntity> weatherForecasts;
    private WeatherAdapter weatherAdapter;

    private String bgPicUrl;
    private String provinceName;
    private String cityName;
    private List<GankEntity> girlsList;
    private double longitude, latitude;

    private WeatherPresenterImpl weatherPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        StatusBarUtil.setTranslucentForImageView(this, 80, llContent);

        initIntent();

        initViews();

        initAdapter();

        initBackgroundPic();

        initPresenter();

    }

    public void initPresenter() {
        weatherPresenter = new WeatherPresenterImpl(this, this);
        weatherPresenter.getLifeSuggestion(longitude, latitude);
        weatherPresenter.getCalendarInfo();
        weatherPresenter.getCityWeatherFutureDays(provinceName, cityName, longitude, latitude);
    }

    private void initBackgroundPic() {

        if (girlsList != null && girlsList.size() > 0) {
            Random random = new Random();
            int randomIndex = random.nextInt(girlsList.size() - 1);
            bgPicUrl = girlsList.get(randomIndex).getUrl();
            if (!TextUtils.isEmpty(bgPicUrl)) {
                RequestOptions options = new RequestOptions();
                options.centerCrop();
                Glide.with(mContext)
                        .asBitmap()
                        .load(bgPicUrl)
                        .apply(options)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                ivBg.setImageBitmap(resource);
                                ivBg2.setImageBitmap(resource);
                                Blurry.with(WeatherActivity.this)
                                        .radius(25)
                                        .sampling(3)
                                        .async()
                                        .capture(ivBg2)
                                        .into(ivBg2);
                                llBgBlur.setAlpha(defaultAlpha);
                            }
                        });

            }
        }

    }

    private void initViews() {
        if (weatherInfoBean != null) {
            tvTitle.setText(weatherInfoBean.getCity_name());
        }

        //初始化RecycleView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        swipeTarget.setLayoutManager(linearLayoutManager);
        swipeTarget.setItemAnimator(new DefaultItemAnimator());
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeTarget.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //静止,没有滚动
//                public static final int SCROLL_STATE_IDLE = 0;

                //正在被外部拖拽,一般为用户正在用手指滚动
//                public static final int SCROLL_STATE_DRAGGING = 1;

                //自动滚动开始
//                public static final int SCROLL_STATE_SETTLING = 2;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int height = recyclerView.getHeight();
                totalDy -= dy;
                float alpha = Math.abs(totalDy) * maxAlpha / 700;
                if (alpha > maxAlpha) {
                    alpha = maxAlpha;
                }
                if (alpha < defaultAlpha) {
                    alpha = defaultAlpha;
                }
                KLog.i(TAG, "dy:" + dy + ",totalDy:" + totalDy + ",height:" + height + ",alpha:" + alpha);
                llBgBlur.setAlpha(alpha);
            }
        });

    }

    private void initIntent() {
        weatherInfoBean = (WeatherInfoBean) getIntent().getSerializableExtra(intentKey_weatherBean);
        girlsList = (List<GankEntity>) getIntent().getSerializableExtra(intentKey_bgUrls);
        provinceName = getIntent().getStringExtra(intentKey_weatherProvinceName);
        cityName = getIntent().getStringExtra(intentKey_weatherCityName);
        if (weatherInfoBean != null) {
            longitude = weatherInfoBean.getLongitude();
            latitude = weatherInfoBean.getLatitude();
        }
    }

    @OnClick(R.id.rl_back)
    public void backBtn() {
        this.finish();
    }

    private void initAdapter() {
        if (weatherAdapter == null) {
            weatherAdapter = new WeatherAdapter(this, weatherInfoBean, holidayBean, lifeSuggestionBean);
            swipeTarget.setAdapter(weatherAdapter);
        } else {
            weatherAdapter.updateDatas(weatherInfoBean, holidayBean, lifeSuggestionBean);
        }

    }

    @Override
    public void onRefresh() {
        if (weatherPresenter != null) {
            weatherPresenter.getCityWeather(provinceName, cityName, longitude, latitude);
            weatherPresenter.getCityWeatherFutureDays(provinceName, cityName, longitude, latitude);
            weatherPresenter.getLifeSuggestion(longitude, latitude);
            weatherPresenter.getCalendarInfo();
        }
    }

    @Override
    public void showToast(String msg) {
        MySnackbar.makeSnackBarBlack(ivBg, msg);
    }

    @Override
    public void initWeatherInfo(WeatherInfoBean weatherInfoBean) {
        this.weatherInfoBean = weatherInfoBean;
        initAdapter();
        initBackgroundPic();
    }

    @Override
    public void initLifeSuggestionInfo(ZhixinSuggestionEntity lifeSuggestionBean) {
        this.lifeSuggestionBean = lifeSuggestionBean;
        initAdapter();
    }

    @Override
    public void initWeatherForecasts(List<WeatherFuturedaysResultBean.DataEntity.ForecastsEntity> weatherForecasts) {
        this.weatherForecasts = weatherForecasts;
        if (weatherAdapter != null) {
            weatherAdapter.updateWeatherForecasts(this.weatherForecasts);
        }
    }

    @Override
    public void overRefresh() {
        swipeToLoadLayout.setRefreshing(false);
    }

    @Override
    public void updateCalendarInfo(HolidayBean holidayBean) {
        this.holidayBean = holidayBean;
        initAdapter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (weatherPresenter != null) {
            weatherPresenter.detachView();
            weatherPresenter = null;
        }
    }
}
