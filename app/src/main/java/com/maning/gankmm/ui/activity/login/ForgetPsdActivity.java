package com.maning.gankmm.ui.activity.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.maning.gankmm.R;
import com.maning.gankmm.app.MyApplication;
import com.maning.gankmm.http.callback.MyCallBack;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.utils.KeyboardUtils;
import com.maning.gankmm.utils.MySnackbar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 忘记密码,和修改密码 同一个页面
 */
@Deprecated
public class ForgetPsdActivity extends BaseActivity {

    public static final String IntentKey_Mode = "IntentKey_Mode";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.et_user_name)
    EditText mEtUserName;
    @Bind(R.id.btn_get_code)
    Button mBtnGetCode;
    @Bind(R.id.et_old_password)
    EditText mEtOldPassword;
    @Bind(R.id.et_new_password)
    EditText mEtNewPassword;
    @Bind(R.id.btn_ok)
    Button mBtnOk;
    @Bind(R.id.iv_yzm)
    ImageView mIvYzm;
    private MyCountDownTimer mMyCountDownTimer;
    private int mMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_psd);
        ButterKnife.bind(this);

        initIntent();

        initMyToolBar();

        initViews();
    }

    private void initIntent() {
        mMode = getIntent().getIntExtra(IntentKey_Mode, 1);
    }

    private void initViews() {
        if (mMode == 1) {
            mBtnGetCode.setVisibility(View.GONE);
            mEtOldPassword.setHint("请输入旧密码");
            mIvYzm.setBackgroundResource(R.drawable.icon_login_pw);
        } else {
            mIvYzm.setBackgroundResource(R.drawable.icon_user_yzm);
            mEtOldPassword.setHint("请输入验证码");
            mBtnGetCode.setVisibility(View.VISIBLE);
            mMyCountDownTimer = new MyCountDownTimer(60000, 1000);
        }
    }

    private void initMyToolBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(mToolbar, "修改密码", R.drawable.gank_ic_back_white);
        } else {
            initToolBar(mToolbar, "修改密码", R.drawable.gank_ic_back_night);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.btn_get_code)
    public void btn_get_code() {
        //隐藏键盘
        KeyboardUtils.hideSoftInput(this);
        //获取用户名
        String userName = mEtUserName.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            MySnackbar.makeSnackBarRed(mToolbar, "用户名不能为空");
            return;
        }
        //获取验证码
        mMyCountDownTimer.start();
        mBtnGetCode.setClickable(false);
        mBtnGetCode.setBackgroundColor(mContext.getResources().getColor(R.color.gray_light));
        mBtnGetCode.setText("60s 后再次发送");

        //发送
    }


    @OnClick(R.id.btn_ok)
    public void btn_ok() {
        //隐藏键盘
        KeyboardUtils.hideSoftInput(this);
        //获取用户名
        String userName = mEtUserName.getText().toString();
        //获取旧密码
        String oldPsd = mEtOldPassword.getText().toString();
        //获取新密码
        String newPsd = mEtNewPassword.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            MySnackbar.makeSnackBarRed(mToolbar, "用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(oldPsd)) {
            if (mMode == 1) {
                MySnackbar.makeSnackBarRed(mToolbar, "用户名不能为空");
            } else {
                MySnackbar.makeSnackBarRed(mToolbar, "验证码不能为空");
            }
            return;
        }

        if (TextUtils.isEmpty(newPsd)) {
            MySnackbar.makeSnackBarRed(mToolbar, "新密码不能为空");
            return;
        }

        if (newPsd.length() < 6) {
            MySnackbar.makeSnackBarRed(mToolbar, "新密码不能少于6位");
            return;
        }
    }


    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            mBtnGetCode.setText(l / 1000 + "s 后再次发送");
        }

        @Override
        public void onFinish() {
            resetBtn();

        }
    }

    private void resetBtn() {
        //重新给Button设置文字
        mBtnGetCode.setText("重新获取验证码");
        //设置可点击
        mBtnGetCode.setClickable(true);
        //恢复
        mBtnGetCode.setBackgroundColor(mContext.getResources().getColor(R.color.main_color));
    }


    private MyCallBack httpCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Object result) {
            dissmissProgressDialog();
            if (what == 0x001) {
                MySnackbar.makeSnackBarGreen(mToolbar, "密码追回的验证码已发送到您的邮箱,请到邮箱查看!");
            } else {
                showProgressSuccess("密码修改成功,请返回重新登录!");

                MyApplication.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                }, 500);
            }
        }

        @Override
        public void onSuccessList(int what, List results) {

        }

        @Override
        public void onFail(int what, String result) {
            dissmissProgressDialog();
            MySnackbar.makeSnackBarRed(mToolbar, result);
            if (what == 0x001) {
                //重置按钮
                mMyCountDownTimer.cancel();
                mMyCountDownTimer.onFinish();
            }
        }
    };

}
