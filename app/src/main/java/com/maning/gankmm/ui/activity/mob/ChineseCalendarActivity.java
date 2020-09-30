package com.maning.gankmm.ui.activity.mob;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.CalendarInfoEntity;
import com.maning.gankmm.http.gank.GankApi;
import com.maning.gankmm.http.callback.MyCallBack;
import com.maning.gankmm.http.mob.MobApi;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 老黄历页面
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
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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
        MobApi.getCalendarInfo(dateString, 0x001, new MyCallBack() {
            @Override
            public void onSuccess(int what, Object result) {
                dissmissProgressDialog();
                CalendarInfoEntity calendarInfoEntity = (CalendarInfoEntity) result;
                RefreshView(calendarInfoEntity);
            }

            @Override
            public void onSuccessList(int what, List results) {

            }

            @Override
            public void onFail(int what, String result) {
                dissmissProgressDialog();
            }
        });
    }

    private void RefreshView(CalendarInfoEntity calendarInfoEntity) {
        if (calendarInfoEntity != null) {
            tv01.setText(calendarInfoEntity.getDate());
            tv02.setText(calendarInfoEntity.getLunar());
            tv03.setText(calendarInfoEntity.getLunarYear() + " (" + calendarInfoEntity.getZodiac() + ") " + calendarInfoEntity.getWeekday());
            tv04.setText(calendarInfoEntity.getSuit());
            tv05.setText(calendarInfoEntity.getAvoid());
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
