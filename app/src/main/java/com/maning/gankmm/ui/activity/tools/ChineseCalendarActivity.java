package com.maning.gankmm.ui.activity.tools;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.rolltools.HolidayBean;
import com.maning.gankmm.bean.rolltools.HolidaySingleResultBean;
import com.maning.gankmm.http.callback.CommonHttpCallback;
import com.maning.gankmm.http.rolltools.RolltoolsApi;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 老黄历页面
 * TODO:添加一个日历控件黄历显示在下面
 */
public class ChineseCalendarActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_01)
    TextView tv01;
    @Bind(R.id.tv_02)
    TextView tv02;
    @Bind(R.id.tv_03)
    TextView tv03;
    @Bind(R.id.tv_04)
    TextView tv04;
    @Bind(R.id.tv_05)
    TextView tv05;

    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinese_calendar);
        ButterKnife.bind(this);

        initMyToolBar();

        //查询数据
        queryData(calendar.getTime());

    }

    private void queryData(Date date) {
        showProgressDialog("正在查询...");
        //获取当天日期
        String dateString = sdf.format(date);
        RolltoolsApi.getHolidaySingle(dateString, new CommonHttpCallback<HolidaySingleResultBean>() {
            @Override
            public void onSuccess(HolidaySingleResultBean result) {
                dissmissProgressDialog();
                HolidayBean holidayBean = result.getData();
                refreshView(holidayBean);
            }

            @Override
            public void onFail(int code, String message) {
                dissmissProgressDialog();
            }
        });
    }

    private void refreshView(HolidayBean holidayBean) {
        if (holidayBean != null) {
            tv01.setText(holidayBean.getDate());
            tv02.setText(holidayBean.getLunarCalendar());
            tv03.setText(holidayBean.getYearTips() + " (" + holidayBean.getChineseZodiac() + ")  - " + holidayBean.getSolarTerms());
            tv04.setText(holidayBean.getSuit());
            tv05.setText(holidayBean.getAvoid());
        }
    }


    private void initMyToolBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(toolbar, "老黄历", R.drawable.gank_ic_back_white);
        } else {
            initToolBar(toolbar, "老黄历", R.drawable.gank_ic_back_night);
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

    @OnClick(R.id.btn_lastDay)
    public void btn_lastDay() {
        //上一天
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        queryData(calendar.getTime());
    }

    @OnClick(R.id.btn_nextDay)
    public void btn_nextDay() {
        //下一天
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        queryData(calendar.getTime());
    }

}
