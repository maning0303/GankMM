package com.maning.gankmm.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.maning.gankmm.R;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 编辑个新签名页面
 */
@Deprecated
public class EditSignatureActivity extends BaseActivity {

    public static final String IntentKey_Signature = "IntentKey_Signature";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.et_input)
    EditText mEtInput;
    @Bind(R.id.tv_num_input)
    TextView mTvNumInput;

    private String mSignature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_signature);
        ButterKnife.bind(this);

        initIntent();

        initMyToolBar();

        initViews();

        initDate();
    }

    private void initViews() {
        mEtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTvNumInput.setText(mEtInput.getText().toString().length() + "/15");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initIntent() {
        mSignature = getIntent().getStringExtra(IntentKey_Signature);
        if("未填写".equals(mSignature)){
            mSignature = null;
        }
    }

    private void initDate() {
        if (!TextUtils.isEmpty(mSignature)) {
            mEtInput.setText(mSignature);

            mTvNumInput.setText(mSignature.length() + "/15");
        }
    }

    private void initMyToolBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(mToolbar, "个性签名", R.drawable.gank_ic_back_white);
        } else {
            initToolBar(mToolbar, "个性签名", R.drawable.gank_ic_back_night);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(IntentKey_Signature, mEtInput.getText().toString());
        setResult(RESULT_OK, intent);
        this.finish();
    }
}
