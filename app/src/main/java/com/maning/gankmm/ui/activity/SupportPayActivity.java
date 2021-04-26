package com.maning.gankmm.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.maning.gankmm.R;
import com.maning.gankmm.app.MyApplication;
import com.maning.gankmm.constant.Constants;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.utils.BitmapUtils;
import com.maning.gankmm.utils.MySnackbar;
import com.maning.gankmm.utils.PermissionManager;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 请作者喝咖啡的页面
 */
public class SupportPayActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_pay_ewm)
    ImageView ivPayEwm;
    @BindView(R.id.btn_save)
    TextView btnSave;
    @BindView(R.id.btn_changge_type)
    TextView btnChanggeType;

    //type = 0 : 微信  / type = 1 : 支付宝
    private int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_pay);
        ButterKnife.bind(this);

        initMyToolBar();

    }

    private void initMyToolBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(toolbar, "请作者喝一杯咖啡", R.drawable.gank_ic_back_white);
        } else {
            initToolBar(toolbar, "请作者喝一杯咖啡", R.drawable.gank_ic_back_night);
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

    @OnClick(R.id.btn_changge_type)
    public void btn_changge_type() {
        //切换图片
        if (type == 0) {
            ivPayEwm.setImageResource(R.drawable.icon_support_zfb);
            type = 1;
            btnChanggeType.setText("切换微信支付");
        } else {
            ivPayEwm.setImageResource(R.drawable.icon_support_wx);
            type = 0;
            btnChanggeType.setText("切换支付宝支付");
        }
    }

    @OnClick(R.id.btn_save)
    public void btn_save() {
        PermissionManager.with(mActivity)
                .callback(new PermissionManager.OnPermissionCallback() {
                    @Override
                    public void onGranted() {
                        //保存图片
                        savePayImage();
                    }

                    @Override
                    public void onDenied(boolean never) {
                        showProgressError("获取存储权限失败，请前往设置页面打开存储权限");
                    }
                }).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    private void savePayImage() {
        //保存图片
        final Bitmap bitmapPay = getBitmapFromImageView(ivPayEwm);
        if (bitmapPay == null) {
            MySnackbar.makeSnackBarRed(ivPayEwm, "获取图片失败");
            return;
        }
        showProgressDialog("正在保存图片...");
        //save Image
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String picName;
                if (type == 0) {
                    picName = "pay.png";
                } else {
                    picName = "pay2.png";
                }
                final boolean saveBitmapToSD = BitmapUtils.saveBitmapToSD(bitmapPay, Constants.BasePath, picName, true);
                MyApplication.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        dissmissProgressDialog();
                        if (saveBitmapToSD) {
                            MySnackbar.makeSnackBarBlack(ivPayEwm, "保存成功");
                            // 最后通知图库更新
                            File file = new File(Constants.BasePath, picName);
                            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            Uri uri = Uri.fromFile(file);
                            intent.setData(uri);
                            mContext.sendBroadcast(intent);
                        } else {
                            MySnackbar.makeSnackBarBlack(ivPayEwm, "保存失败");
                        }
                    }
                });
            }
        }).start();
    }

    private Bitmap getBitmapFromImageView(ImageView imageView) {
        imageView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(imageView.getDrawingCache());
        imageView.setDrawingCacheEnabled(false);
        return bitmap;
    }

}
