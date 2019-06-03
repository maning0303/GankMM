package com.maning.gankmm.ui.activity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.maning.gankmm.R;
import com.maning.gankmm.bean.AppUpdateInfo;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.ui.iView.ISettingView;
import com.maning.gankmm.ui.presenter.impl.SettingPresenterImpl;
import com.maning.gankmm.ui.view.MySettingItemView;
import com.maning.gankmm.utils.DialogUtils;
import com.maning.gankmm.utils.IntentUtils;
import com.maning.gankmm.utils.MySnackbar;
import com.maning.gankmm.utils.NetUtils;
import com.maning.gankmm.utils.NotifyUtil;
import com.maning.gankmm.utils.PermissionUtils;
import com.maning.gankmm.utils.SharePreUtil;
import com.maning.updatelibrary.InstallUtils;
import com.socks.library.KLog;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity implements ISettingView {

    private static final String TAG = "SettingActivity";
    private Context context;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_push)
    ImageView ivPush;
    @Bind(R.id.iv_night_mode)
    ImageView ivNightMode;

    @Bind(R.id.item_gank_headline)
    MySettingItemView itemGankHeadline;
    @Bind(R.id.item_clean_cache)
    MySettingItemView itemCleanCache;
    @Bind(R.id.item_feedback)
    MySettingItemView itemFeedback;
    @Bind(R.id.item_app_update)
    MySettingItemView itemAppUpdate;
    @Bind(R.id.item_app_support)
    MySettingItemView itemAppSupport;

    private SettingPresenterImpl settingPresenter;
    private MaterialDialog dialogUpdate;

    private AppUpdateInfo appUpdateInfo;
    private NotifyUtil notifyUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        context = this;

        initMyToolBar();

        initOtherViews();

        initPresenter();

        settingPresenter.initPushState();

        settingPresenter.initNightModeState();

        settingPresenter.initCache();

        settingPresenter.initAppUpdateState();

        settingPresenter.initFeedBack();

        settingPresenter.initHeadLine();

    }

    private void initOtherViews() {
        itemAppSupport.setTitleColor(getResources().getColor(R.color.red));
    }

    private void initMyToolBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(toolbar, "设置", R.drawable.gank_ic_back_white);
        } else {
            initToolBar(toolbar, "设置", R.drawable.gank_ic_back_night);
        }
    }

    public void initPresenter() {
        settingPresenter = new SettingPresenterImpl(this, this);
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

    @OnClick(R.id.item_feedback)
    void item_feedback() {

        boolean feedbackKey_showAlertDialog = SharePreUtil.getBooleanData(this, "FeedbackKey_ShowAlertDialog", false);
        if (!feedbackKey_showAlertDialog) {
            DialogUtils.showMyDialog(this, "通知", "由于个人原因,暂时移除了之前版本的意见反馈,暂时把意见反馈链接到了GitHub项目的Issues地址,请你谅解!", "确定", "", new DialogUtils.OnDialogClickListener() {
                @Override
                public void onConfirm() {
                    //意见反馈地址
                    SharePreUtil.saveBooleanData(mContext, "FeedbackKey_ShowAlertDialog", true);
                    IntentUtils.startToWebActivity(mContext, "", "意见反馈", "https://github.com/maning0303/GankMM/issues");
                }

                @Override
                public void onCancel() {

                }
            });
        } else {
            //意见反馈地址
            SharePreUtil.saveBooleanData(mContext, "FeedbackKey_ShowAlertDialog", true);
            IntentUtils.startToWebActivity(mContext, "", "意见反馈", "https://github.com/maning0303/GankMM/issues");
        }

    }

    @OnClick(R.id.item_app_update)
    public void item_app_update() {
        settingPresenter.checkAppUpdate();
    }

    @OnClick(R.id.item_clean_cache)
    void item_clean_cache() {
        showCacheDialog();
    }


    private void showCacheDialog() {
        DialogUtils.showMyDialog(this, "缓存清理", "确定要清除图片的缓存吗？", "确定", "取消", new DialogUtils.OnDialogClickListener() {

            @Override
            public void onConfirm() {
                PermissionUtils.checkWritePermission(mContext, new PermissionUtils.PermissionCallBack() {
                    @Override
                    public void onGranted() {
                        settingPresenter.cleanCache();
                    }

                    @Override
                    public void onDenied() {
                        showProgressError("获取存储权限失败，请前往设置页面打开存储权限");
                    }
                });

            }

            @Override
            public void onCancel() {

            }
        });

    }

    @OnClick(R.id.item_app_open_frame)
    void item_app_open_frame() {
        //跳转开源框架展示页面
        startActivity(new Intent(this, OpenFrameActivity.class));
    }

    //配置头条
    @OnClick(R.id.item_gank_headline)
    void item_gank_headline() {
        settingPresenter.configurationHeadLine();
    }


    @OnClick(R.id.iv_push)
    void iv_push() {
        settingPresenter.changePushState();
    }

    @OnClick(R.id.iv_night_mode)
    void iv_night_mode() {
        settingPresenter.clickNightMode();
    }

    @OnClick(R.id.item_app_support)
    void item_app_support() {
        IntentUtils.startSupportPayActivity(this);
    }

    @OnClick(R.id.item_app_market)
    void item_app_market() {
        IntentUtils.goToMarket(this, getPackageName());
    }

    @Override
    public void openPush() {
        ivPush.setImageResource(R.drawable.gank_icon_switcher_on);
    }

    @Override
    public void closePush() {
        ivPush.setImageResource(R.drawable.gank_icon_switcher_off);
    }

    @Override
    public void openNightMode() {
        ivNightMode.setImageResource(R.drawable.gank_icon_switcher_on);
    }

    @Override
    public void closeNightMode() {
        ivNightMode.setImageResource(R.drawable.gank_icon_switcher_off);
    }

    @Override
    public void recreateActivity() {
        startActivity(new Intent(this.getApplicationContext(), SettingActivity.class));
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        this.finish();
    }

    @Override
    public void setCacheSize(String cacheSize) {
        itemCleanCache.setRightText(cacheSize);
    }

    @Override
    public void setFeedbackState(final boolean flag) {

    }

    @Override
    public void setAppUpdateState(boolean flag) {
        itemAppUpdate.setRedDot(flag);
    }

    @Override
    public void showAppUpdateDialog(AppUpdateInfo appUpdateInfo) {
        this.appUpdateInfo = appUpdateInfo;
        String title = "检测到新版本:V" + appUpdateInfo.getVersionShort();
        Double appSize = Double.parseDouble(appUpdateInfo.getBinary().getFsize() + "") / 1024 / 1024;
        DecimalFormat df = new DecimalFormat(".##");
        String resultSize = df.format(appSize) + "M";
        boolean isWifi = NetUtils.isWifiConnected(this);
        String content = appUpdateInfo.getChangelog() +
                "\n\n新版大小：" + resultSize +
                "\n当前网络：" + (isWifi ? "wifi" : "非wifi环境(注意)");

        DialogUtils.showMyDialog(this,
                title, content, "立马更新", "稍后更新",
                new DialogUtils.OnDialogClickListener() {
                    @Override
                    public void onConfirm() {
                        PermissionUtils.checkWritePermission(mContext, new PermissionUtils.PermissionCallBack() {
                            @Override
                            public void onGranted() {
                                //更新版本
                                showDownloadDialog(SettingActivity.this.appUpdateInfo);
                            }

                            @Override
                            public void onDenied() {
                                showProgressError("获取存储权限失败，请前往设置页面打开存储权限");
                            }
                        });
//                        // 先判断是否有权限。
//                        if (AndPermission.hasPermission(SettingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                            // 有权限，直接do anything.
//                            //更新版本
//                            showDownloadDialog(SettingActivity.this.appUpdateInfo);
//                        } else {
//                            // 申请权限。
//                            AndPermission.with(SettingActivity.this)
//                                    .requestCode(100)
//                                    .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                                    .send();
//                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }

    private void showDownloadDialog(AppUpdateInfo appUpdateInfo) {
        dialogUpdate = new MaterialDialog.Builder(SettingActivity.this)
                .title("正在下载最新版本")
                .content("请稍等")
                .canceledOnTouchOutside(false)
                .cancelable(false)
                .progress(false, 100, false)
                .negativeText("后台下载")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        startNotifyProgress();
                    }
                })
                .show();

        InstallUtils.with(context)
                .setApkUrl(appUpdateInfo.getInstall_url())
                .setCallBack(new InstallUtils.DownloadCallBack() {
                    @Override
                    public void onStart() {
                        KLog.i("installAPK-----onStart");
                        if (dialogUpdate != null) {
                            dialogUpdate.setProgress(0);
                        }
                    }

                    @Override
                    public void onComplete(String path) {
                        KLog.i("installAPK----onComplete:" + path);
                        /**
                         * 安装APK工具类
                         * @param context       上下文
                         * @param filePath      文件路径
                         * @param authorities   ---------Manifest中配置provider的authorities字段---------
                         * @param callBack      安装界面成功调起的回调
                         */
                        InstallUtils.installAPK(mActivity, path, new InstallUtils.InstallCallBack() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(context, "正在安装程序", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFail(Exception e) {
                                Toast.makeText(context, "安装失败:" + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        if (dialogUpdate != null && dialogUpdate.isShowing()) {
                            dialogUpdate.dismiss();
                        }
                        if (notifyUtils != null) {
                            notifyUtils.setNotifyProgressComplete();
                            notifyUtils.clear();
                        }
                    }

                    @Override
                    public void onLoading(long total, long current) {
                        KLog.i("installAPK-----onLoading:-----total:" + total + ",current:" + current);
                        int currentProgress = (int) (current * 100 / total);
                        if (dialogUpdate != null) {
                            dialogUpdate.setProgress(currentProgress);
                        }
                        if (notifyUtils != null) {
                            notifyUtils.setNotifyProgress(100, currentProgress, false);
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        if (dialogUpdate != null && dialogUpdate.isShowing()) {
                            dialogUpdate.dismiss();
                        }
                        if (notifyUtils != null) {
                            notifyUtils.clear();
                        }
                    }

                    @Override
                    public void cancle() {
                        if (notifyUtils != null) {
                            notifyUtils.clear();
                        }
                    }
                })
                .startDownload();

    }


    /**
     * 开启通知栏
     */
    private void startNotifyProgress() {
        //设置想要展示的数据内容
        Intent intent = new Intent(this, SettingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent rightPendIntent = PendingIntent.getActivity(this,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int smallIcon = R.mipmap.ic_launcher;
        String ticker = "正在下载干货营更新包...";
        //实例化工具类，并且调用接口
        notifyUtils = new NotifyUtil(this, 0);
        notifyUtils.notify_progress(rightPendIntent, smallIcon, ticker, "干货营 下载", "正在下载中...", false, false, false);
    }

    @Override
    public void showBaseProgressDialog(String msg) {
        showProgressDialog(msg);
    }

    @Override
    public void hideBaseProgressDialog() {
        dissmissProgressDialog();
    }

    @Override
    public void showBasesProgressSuccess(String msg) {
        showProgressSuccess(msg);
    }

    @Override
    public void showToast(String msg) {
        MySnackbar.makeSnackBarBlack(toolbar, msg);
    }

    @Override
    public void updateHeadLine(String type) {
        itemGankHeadline.setRightText(type);
    }


    @Override
    protected void onDestroy() {
        settingPresenter.detachView();
        super.onDestroy();
    }

}
