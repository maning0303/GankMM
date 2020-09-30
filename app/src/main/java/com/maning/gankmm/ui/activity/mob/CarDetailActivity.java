package com.maning.gankmm.ui.activity.mob;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobCarDetailsEntity;
import com.maning.gankmm.bean.mob.MobCarItemEntity;
import com.maning.gankmm.http.mob.MobApi;
import com.maning.gankmm.http.callback.MyCallBack;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleCarDetailsAdapter;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.utils.MySnackbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 车型详细配置
 */
public class CarDetailActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public static final String IntentKey_CarItemEntity = "IntentKey_CarItemEntity";
    private MobCarItemEntity mMobCarItemEntity = new MobCarItemEntity();

    private List<MobCarDetailsEntity.DetailItem> mDatas = new ArrayList<>();
    private RecycleCarDetailsAdapter mRecycleCarDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        ButterKnife.bind(this);

        mMobCarItemEntity = (MobCarItemEntity) getIntent().getSerializableExtra(IntentKey_CarItemEntity);

        initMyToolBar();

        initRecyclerView();

        initDatas();

    }

    private void initDatas() {

        showProgressDialog();

        MobApi.queryCarDetails(mMobCarItemEntity.getCarId(), 0x001, new MyCallBack() {
            @Override
            public void onSuccess(int what, Object result) {

            }

            @Override
            public void onSuccessList(int what, List results) {
                dissmissProgressDialog();
                ArrayList<MobCarDetailsEntity> mResult = (ArrayList<MobCarDetailsEntity>) results;
                initAdapter(mResult);
            }

            @Override
            public void onFail(int what, String result) {
                dissmissProgressDialog();
                MySnackbar.makeSnackBarRed(mToolbar, result);
            }
        });

    }

    private void initAdapter(ArrayList<MobCarDetailsEntity> mResult) {
        //处理集合数据
        if (mResult.size() <= 0) {
            return;
        }
        MobCarDetailsEntity mobCarDetailsEntity = mResult.get(0);
//        private List<MobCarDetailsEntity.DetailItem> baseInfo; //车型基本配置信息
//        private List<MobCarDetailsEntity.DetailItem> airConfig;  //空调/冰箱配置信息
//        private List<MobCarDetailsEntity.DetailItem> carbody;  //车身配置信息
//        private List<MobCarDetailsEntity.DetailItem> chassis;  //底盘配置信息
//        private List<MobCarDetailsEntity.DetailItem> controlConfig;  //操控配置信息
//        private List<MobCarDetailsEntity.DetailItem> engine;  //发动机配置信息
//        private List<MobCarDetailsEntity.DetailItem> exterConfig;  //外部配置信息
//        private List<MobCarDetailsEntity.DetailItem> glassConfig;  //玻璃/后视镜配置信息
//        private List<MobCarDetailsEntity.DetailItem> interConfig;  //内部配置信息
//        private List<MobCarDetailsEntity.DetailItem> lightConfig;  //灯光配置信息
//        private List<MobCarDetailsEntity.DetailItem> mediaConfig;  //多媒体配置信息
//        private List<MobCarDetailsEntity.DetailItem> safetyDevice;  //安全装置信息
//        private List<MobCarDetailsEntity.DetailItem> seatConfig;  //座椅配置信息
//        private List<MobCarDetailsEntity.DetailItem> techConfig;  //高科技配置信息
//        private List<MobCarDetailsEntity.DetailItem> transmission;  //变速箱信息
//        private List<MobCarDetailsEntity.DetailItem> wheelInfo;  //	车轮制动信息
//        private List<MobCarDetailsEntity.DetailItem> motorList;  //电动机配置信息

        List<MobCarDetailsEntity.DetailItem> baseInfo = mobCarDetailsEntity.getBaseInfo();
        for (int i = 0; i < baseInfo.size(); i++) {
            baseInfo.get(i).setType("车型基本配置信息");
        }
        List<MobCarDetailsEntity.DetailItem> airConfig = mobCarDetailsEntity.getAirConfig();
        for (int i = 0; i < airConfig.size(); i++) {
            airConfig.get(i).setType("空调/冰箱配置信息");
        }
        List<MobCarDetailsEntity.DetailItem> carbody = mobCarDetailsEntity.getCarbody();
        for (int i = 0; i < carbody.size(); i++) {
            carbody.get(i).setType("车身配置信息");
        }
        List<MobCarDetailsEntity.DetailItem> chassis = mobCarDetailsEntity.getChassis();
        for (int i = 0; i < chassis.size(); i++) {
            chassis.get(i).setType("底盘配置信息");
        }
        List<MobCarDetailsEntity.DetailItem> controlConfig = mobCarDetailsEntity.getControlConfig();
        for (int i = 0; i < controlConfig.size(); i++) {
            controlConfig.get(i).setType("操控配置信息");
        }
        List<MobCarDetailsEntity.DetailItem> engine = mobCarDetailsEntity.getEngine();
        for (int i = 0; i < engine.size(); i++) {
            engine.get(i).setType("发动机配置信息");
        }
        List<MobCarDetailsEntity.DetailItem> exterConfig = mobCarDetailsEntity.getExterConfig();
        for (int i = 0; i < exterConfig.size(); i++) {
            exterConfig.get(i).setType("外部配置信息");
        }
        List<MobCarDetailsEntity.DetailItem> glassConfig = mobCarDetailsEntity.getGlassConfig();
        for (int i = 0; i < glassConfig.size(); i++) {
            glassConfig.get(i).setType("玻璃/后视镜配置信息");
        }
        List<MobCarDetailsEntity.DetailItem> interConfig = mobCarDetailsEntity.getInterConfig();
        for (int i = 0; i < interConfig.size(); i++) {
            interConfig.get(i).setType("内部配置信息");
        }
        List<MobCarDetailsEntity.DetailItem> lightConfig = mobCarDetailsEntity.getLightConfig();
        for (int i = 0; i < lightConfig.size(); i++) {
            lightConfig.get(i).setType("灯光配置信息");
        }
        List<MobCarDetailsEntity.DetailItem> mediaConfig = mobCarDetailsEntity.getMediaConfig();
        for (int i = 0; i < mediaConfig.size(); i++) {
            mediaConfig.get(i).setType("多媒体配置信息");
        }
        List<MobCarDetailsEntity.DetailItem> safetyDevice = mobCarDetailsEntity.getSafetyDevice();
        for (int i = 0; i < safetyDevice.size(); i++) {
            safetyDevice.get(i).setType("安全装置信息");
        }
        List<MobCarDetailsEntity.DetailItem> seatConfig = mobCarDetailsEntity.getSeatConfig();
        for (int i = 0; i < seatConfig.size(); i++) {
            seatConfig.get(i).setType("座椅配置信息");
        }
        List<MobCarDetailsEntity.DetailItem> techConfig = mobCarDetailsEntity.getTechConfig();
        for (int i = 0; i < techConfig.size(); i++) {
            techConfig.get(i).setType("高科技配置信息");
        }
        List<MobCarDetailsEntity.DetailItem> transmission = mobCarDetailsEntity.getTransmission();
        for (int i = 0; i < transmission.size(); i++) {
            transmission.get(i).setType("变速箱信息");
        }
        List<MobCarDetailsEntity.DetailItem> wheelInfo = mobCarDetailsEntity.getWheelInfo();
        for (int i = 0; i < wheelInfo.size(); i++) {
            wheelInfo.get(i).setType("车轮制动信息");
        }
        List<MobCarDetailsEntity.DetailItem> motorList = mobCarDetailsEntity.getMotorList();
        for (int i = 0; i < motorList.size(); i++) {
            motorList.get(i).setType("电动机配置信息");
        }
        mDatas.addAll(baseInfo);
        mDatas.addAll(airConfig);
        mDatas.addAll(carbody);
        mDatas.addAll(chassis);
        mDatas.addAll(controlConfig);
        mDatas.addAll(engine);
        mDatas.addAll(exterConfig);
        mDatas.addAll(glassConfig);
        mDatas.addAll(interConfig);
        mDatas.addAll(lightConfig);
        mDatas.addAll(mediaConfig);
        mDatas.addAll(safetyDevice);
        mDatas.addAll(seatConfig);
        mDatas.addAll(techConfig);
        mDatas.addAll(transmission);
        mDatas.addAll(wheelInfo);
        mDatas.addAll(motorList);

        mRecycleCarDetailsAdapter = new RecycleCarDetailsAdapter(mContext, mDatas,mobCarDetailsEntity);
        mRecyclerView.setAdapter(mRecycleCarDetailsAdapter);
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initMyToolBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(mToolbar, mMobCarItemEntity.getSeriesName(), R.drawable.gank_ic_back_white);
        } else {
            initToolBar(mToolbar, mMobCarItemEntity.getSeriesName(), R.drawable.gank_ic_back_night);
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
