package com.maning.gankmm.utils;

import android.Manifest;
import android.content.Intent;

import androidx.fragment.app.FragmentActivity;

import com.google.zxing.client.android.MNScanManager;
import com.google.zxing.client.android.model.MNScanConfig;
import com.google.zxing.client.android.other.MNScanCallback;
import com.maning.gankmm.ui.activity.tools.ScanResultActivity;

/**
 * @author : maning
 * @date : 2020-10-16
 * @desc :
 */
public class ZxingScanUtils {

    private static final String COLOR_MAIN = "#62e203";

    public static void open(final FragmentActivity activity) {
        PermissionManager.with(activity)
                .callback(new PermissionManager.OnPermissionCallback() {
                    @Override
                    public void onGranted() {
                        //自定义扫描
                        MNScanConfig scanConfig = new MNScanConfig.Builder()
                                //设置完成震动
                                .isShowVibrate(false)
                                //扫描完成声音
                                .isShowBeep(true)
                                //显示相册功能
                                .isShowPhotoAlbum(true)
                                //显示闪光灯
                                .isShowLightController(true)
                                //自定义文案
                                .setScanHintText("请对准二维码/条形码识别")
                                //扫描线的颜色
                                .setScanColor(COLOR_MAIN)
                                //是否显示缩放控制器
                                .isShowZoomController(false)
                                //扫描线样式
                                .setLaserStyle(MNScanConfig.LaserStyle.Line)
                                //是否全屏范围扫描
                                .setFullScreenScan(true)
                                //二维码标记点
                                .isShowResultPoint(true)
                                //单位dp
                                .setResultPointConfigs(30, 30, 3, "#FFFFFFFF", "#7000A81F")
                                .builder();
                        MNScanManager.startScan(activity, scanConfig, new MNScanCallback() {
                            @Override
                            public void onActivityResult(int resultCode, Intent data) {
                                switch (resultCode) {
                                    case MNScanManager.RESULT_SUCCESS:
                                        String resultSuccess = data.getStringExtra(MNScanManager.INTENT_KEY_RESULT_SUCCESS);
                                        //扫描成功
                                        //跳转到扫码结果页面
                                        ScanResultActivity.open(activity, resultSuccess);
                                        break;
                                    case MNScanManager.RESULT_FAIL:
                                        String resultError = data.getStringExtra(MNScanManager.INTENT_KEY_RESULT_ERROR);
                                        MyToast.showShortToast(resultError);
                                        break;
                                    case MNScanManager.RESULT_CANCLE:
                                        break;
                                }
                            }
                        });
                    }

                    @Override
                    public void onDenied(boolean never) {
                        MyToast.showShortToast("权限被拒绝，无法扫码");
                    }
                }).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
    }

}
