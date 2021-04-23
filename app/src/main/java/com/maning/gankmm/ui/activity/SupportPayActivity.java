package com.maning.gankmm.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.maning.gankmm.R;
import com.maning.gankmm.app.MyApplication;
import com.maning.gankmm.constant.Constants;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.utils.BitmapUtils;
import com.maning.gankmm.utils.MySnackbar;
import com.maning.gankmm.utils.PermissionUtils;

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

        PermissionUtils.checkWritePermission(mContext, new PermissionUtils.PermissionCallBack() {
            @Override
            public void onGranted() {
                //保存图片
                savePayImage();
            }

            @Override
            public void onDenied() {
                showProgressError("获取存储权限失败，请前往设置页面打开存储权限");
            }
        });
//        //先判断是否有权限。
//        if (AndPermission.hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            // 有权限，直接do anything.
//            savePayImage();
//        } else {
//            // 申请权限。
//            AndPermission.with(this)
//                    .requestCode(101)
//                    .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    .send();
//        }

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

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
//        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener);
//    }
//
//    private PermissionListener listener = new PermissionListener() {
//        @Override
//        public void onSucceed(int requestCode, List<String> grantedPermissions) {
//            MySnackbar.makeSnackBarBlack(toolbar, "权限申请成功");
//            // 权限申请成功回调。
//            savePayImage();
//        }
//
//        @Override
//        public void onFailed(int requestCode, List<String> deniedPermissions) {
//            // 权限申请失败回调。
//            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
//            if (AndPermission.hasAlwaysDeniedPermission(SupportPayActivity.this, deniedPermissions)) {
//                // 第二种：用自定义的提示语。
//                AndPermission.defaultSettingDialog(SupportPayActivity.this, 300)
//                        .setTitle("权限申请失败")
//                        .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
//                        .setPositiveButton("好，去设置")
//                        .show();
//            }
//        }
//    };

}
