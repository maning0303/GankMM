package com.maning.gankmm.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobUserInfo;
import com.maning.gankmm.http.mob.MobApi;
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
 * 登录页面
 */
@Deprecated
public class LoginActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.et_user_name)
    EditText mEtUserName;
    @Bind(R.id.et_password)
    EditText mEtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initMyToolBar();
    }

    private void initMyToolBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(mToolbar, "登录", R.drawable.gank_ic_back_white);
        } else {
            initToolBar(mToolbar, "登录", R.drawable.gank_ic_back_night);
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

    @OnClick(R.id.btn_login)
    public void btn_login() {
        //隐藏输入法
        KeyboardUtils.hideSoftInput(this);

        //获取数据
        String userName = mEtUserName.getText().toString();
        String userPsd = mEtPassword.getText().toString();

        //判空
        if (TextUtils.isEmpty(userName)) {
            MySnackbar.makeSnackBarRed(mToolbar, "用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(userPsd)) {
            MySnackbar.makeSnackBarRed(mToolbar, "密码不能为空");
            return;
        }

        showProgressDialog("正在登录...");
        MobApi.userLogin(userName, userPsd, 0x001, new MyCallBack() {
            @Override
            public void onSuccess(int what, Object result) {
                dissmissProgressDialog();
                showProgressSuccess("登录成功!");
                MobUserInfo userInfo = (MobUserInfo) result;
                String userName = mEtUserName.getText().toString();
                String userPsd = mEtPassword.getText().toString();
                userInfo.setUserName(userName);
                userInfo.setUserPsd(userPsd);

                //保存用户信息
//                UserUtils.saveUserCache(userInfo);

                //关闭当前页面。
                closeAcitivity();


            }

            @Override
            public void onSuccessList(int what, List results) {

            }

            @Override
            public void onFail(int what, String result) {
                dissmissProgressDialog();
                MySnackbar.makeSnackBarRed(mToolbar, result);
            }
        });


    }

    private void closeAcitivity() {
        //跳转到个人信息修改页面
        startActivity(new Intent(this, UserInfoActivity.class));
        this.finish();
    }

    @OnClick(R.id.btn_forget)
    public void btn_forget() {
        Intent intent = new Intent(this, ForgetPsdActivity.class);
        intent.putExtra(ForgetPsdActivity.IntentKey_Mode, 2);
        startActivity(intent);
    }

    @OnClick(R.id.btn_register)
    public void btn_register() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @OnClick(R.id.ll_bg)
    public void ll_bg() {
        //隐藏输入法
        KeyboardUtils.hideSoftInput(this);
    }

}
