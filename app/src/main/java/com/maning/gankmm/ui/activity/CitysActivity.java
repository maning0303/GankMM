package com.maning.gankmm.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.CitysEntity;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.adapter.RecycleCitysAdapter;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.utils.UserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CitysActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recycle_province)
    RecyclerView recycleProvince;
    @Bind(R.id.recycle_city)
    RecyclerView recycleCity;


    private List<String> provinceList = new ArrayList<>();
    private List<String> citysList = new ArrayList<>();
    private Map<String, List<String>> citysMap = new HashMap<>();
    private RecycleCitysAdapter recycleCitysAdapter_province;
    private RecycleCitysAdapter recycleCitysAdapter_city;
    private String chooseProvinceName;
    private String chooseCityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citys);
        ButterKnife.bind(this);

        initMyToolBar();

        initViews();

        initAdapter();

    }

    private void initAdapter() {
        //获取数据
        CitysEntity citysCache = UserUtils.getCitysCache();
        if (citysCache != null) {
            List<CitysEntity.ResultBean> cityList = citysCache.getResult();
            for (int i = 0; i < cityList.size(); i++) {
                String province = cityList.get(i).getProvince();
                provinceList.add(province);
                List<String> citysNameList = new ArrayList<>();
                List<CitysEntity.ResultBean.CityBean> city = cityList.get(i).getCity();
                if (city != null) {
                    for (int j = 0; j < city.size(); j++) {
                        String cityName = city.get(j).getCity();
                        citysNameList.add(cityName);
                    }
                    citysMap.put(province, citysNameList);
                }
            }
            recycleCitysAdapter_province = new RecycleCitysAdapter(this, provinceList, 0);
            recycleProvince.setAdapter(recycleCitysAdapter_province);
            //点击事件
            recycleCitysAdapter_province.setOnItemClickLitener(new RecycleCitysAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {
                    chooseProvinceName = provinceList.get(position);
                    citysList = citysMap.get(chooseProvinceName);
                    //刷新第二个列表
                    if (recycleCitysAdapter_city != null) {
                        recycleCitysAdapter_city.updateDatas(citysList);
                    } else {
                        recycleCitysAdapter_city = new RecycleCitysAdapter(CitysActivity.this, citysList, 1);
                        recycleCity.setAdapter(recycleCitysAdapter_city);
                        //点击事件
                        recycleCitysAdapter_city.setOnItemClickLitener(new RecycleCitysAdapter.OnItemClickLitener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                chooseCityName = recycleCitysAdapter_city.getPositionValue(position);
                                //关闭界面
                                Intent intent = new Intent();
                                intent.putExtra("provinceName", chooseProvinceName);
                                intent.putExtra("cityName", chooseCityName);
                                setResult(100, intent);
                                finish();
                            }
                        });
                    }
                }
            });

        }

    }

    private void initViews() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycleProvince.setLayoutManager(linearLayoutManager);
        recycleProvince.setItemAnimator(new DefaultItemAnimator());


        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycleCity.setLayoutManager(linearLayoutManager2);
        recycleCity.setItemAnimator(new DefaultItemAnimator());

    }


    private void initMyToolBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(toolbar, "城市选择", R.drawable.gank_ic_back_white);
        } else {
            initToolBar(toolbar, "城市选择", R.drawable.gank_ic_back_night);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
