package com.maning.gankmm.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;

import java.util.List;
import java.util.Random;


/**
 * @author : maning
 * @date : 2020-10-20
 * @desc : 权限管理，自由替换三方权限管理库
 */
public class PermissionManager {

    public interface OnPermissionCallback {
        void onGranted();

        void onDenied(boolean never);
    }

    private FragmentActivity mActivity;
    private OnPermissionCallback callback;
    private boolean auto2setting = false;

    private PermissionManager(FragmentActivity activity) {
        mActivity = activity;
    }

    public static PermissionManager with(FragmentActivity activity) {
        return new PermissionManager(activity);
    }

    /**
     * 是否自动跳转设置页面
     * 当用户选择了不在询问，就自动跳转应用详情页面
     */
    public PermissionManager auto2setting(boolean auto2setting) {
        this.auto2setting = auto2setting;
        return this;
    }

    public PermissionManager callback(OnPermissionCallback callback) {
        this.callback = callback;
        return this;
    }

    public void request(String... permissions) {
        requestWithXXPermission(permissions);
    }

    private void requestWithXXPermission(String[] permissions) {
        XXPermissions.with(mActivity)
                .permission(permissions)
                .request(new com.hjq.permissions.OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (callback == null) {
                            return;
                        }
                        if (all) {
                            callback.onGranted();
                        } else {
                            callback.onDenied(false);
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (callback == null) {
                            return;
                        }
                        if (never) {
                            //被永久拒绝授权
                            if (auto2setting) {
                                openAppSettingPage(mActivity);
                            }
                        }
                        callback.onDenied(never);
                    }
                });
    }

    /**
     * 打开应用详情页面
     *
     * @param activity
     */
    public static int openAppSettingPage(Activity activity) {
        int randomRequestCode = getRandomRequestCode();
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivityForResult(intent, randomRequestCode);
        return randomRequestCode;
    }

    /**
     * 检查权限是否被赋予
     */
    public static boolean isGranted(Activity activity, String permission) {
        if (!isAndroid6()) {
            return true;
        }
        return activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 获得随机的 RequestCode
     */
    private static int getRandomRequestCode() {
        // 请求码必须在 2 的 16 次方以内
        return new Random().nextInt((int) Math.pow(2, 16));
    }

    /**
     * 是否是 Android 6.0 及以上版本
     */
    private static boolean isAndroid6() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 开启权限页面
     *
     * @param context
     * @param str
     */
    public static void showPermissionDialog(Activity context, String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(str);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("前往", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getApplicationContext().getPackageName(), null);
                intent.setData(uri);
                context.startActivityForResult(intent, 0);
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

}
