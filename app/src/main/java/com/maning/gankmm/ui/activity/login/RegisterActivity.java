package com.maning.gankmm.ui.activity.login;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;

import com.maning.gankmm.R;
import com.maning.gankmm.app.MyApplication;
import com.maning.gankmm.http.callback.MyCallBack;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.utils.AppValidationMgr;
import com.maning.gankmm.utils.KeyboardUtils;
import com.maning.gankmm.utils.MySnackbar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Deprecated
public class RegisterActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.et_user_name)
    EditText mEtUserName;
    @Bind(R.id.et_password)
    EditText mEtPassword;
    @Bind(R.id.et_email)
    EditText mEtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        initMyToolBar();
    }

    private void initMyToolBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(mToolbar, "注册", R.drawable.gank_ic_back_white);
        } else {
            initToolBar(mToolbar, "注册", R.drawable.gank_ic_back_night);
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

    @OnClick(R.id.ll_bg)
    public void ll_bg() {
        //隐藏输入法
        KeyboardUtils.hideSoftInput(this);
    }


    @OnClick(R.id.btn_register)
    public void btn_register() {
        //隐藏输入法
        KeyboardUtils.hideSoftInput(this);

        String userName = mEtUserName.getText().toString();
        String userPsd = mEtPassword.getText().toString();
        String userEmail = mEtEmail.getText().toString();

        //获取数据
        if (TextUtils.isEmpty(userName)) {
            MySnackbar.makeSnackBarRed(mToolbar, "用户名不可为空");
            return;
        }
        if (TextUtils.isEmpty(userPsd)) {
            MySnackbar.makeSnackBarRed(mToolbar, "密码不可为空");
            return;
        }
        if (TextUtils.isEmpty(userEmail)) {
            MySnackbar.makeSnackBarRed(mToolbar, "邮箱不可为空");
            return;
        }

        if (userPsd.length() < 6) {
            MySnackbar.makeSnackBarRed(mToolbar, "密码不能少于6位");
            return;
        }

        if (!AppValidationMgr.isEmail(userEmail)) {
            MySnackbar.makeSnackBarRed(mToolbar, "邮箱格式不正确");
            return;
        }
    }


    private MyCallBack mMyCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Object result) {
            dissmissProgressDialog();
            showProgressSuccess("注册成功,即将关闭页面!");

            //关闭页面
            MyApplication.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    closeActivity();
                }
            }, 1000);
        }

        @Override
        public void onSuccessList(int what, List results) {

        }

        @Override
        public void onFail(int what, String result) {
            dissmissProgressDialog();
            MySnackbar.makeSnackBarRed(mToolbar, result);
        }
    };

    private void closeActivity() {
        this.finish();
    }


}
