package com.maning.gankmm.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.maning.gankmm.bean.GankEntity;
import com.maning.gankmm.ui.activity.AboutActivity;
import com.maning.gankmm.ui.activity.GankActivity;
import com.maning.gankmm.ui.activity.MoreActivity;
import com.maning.gankmm.ui.activity.QRCodeActivity;
import com.maning.gankmm.ui.activity.SettingActivity;
import com.maning.gankmm.ui.activity.SupportPayActivity;
import com.maning.gankmm.ui.activity.WebActivity;
import com.maning.gankmm.ui.imagebrowser.MNImageBrowser;

import java.util.ArrayList;

/**
 * Created by maning on 16/3/3.
 * <p/>
 * 页面跳转
 */
public class IntentUtils {

    public static final String ImagePositionForImageShow = "PositionForImageShow";
    public static final String ImageArrayList = "BigImageArrayList";
    public static final String WebTitleFlag = "WebTitleFlag";
    public static final String WebTitle = "WebTitle";
    public static final String WebUrl = "WebUrl";
    public static final String DayDate = "DayDate";

    public static final String PushMessage = "PushMessage";

    public static void startToImageShow(Context context, ArrayList<String> mDatas, int position, View view) {
        MNImageBrowser.showImageBrowser(context, view, position, mDatas, null);
    }

    public static void startToImageShow(final Context context, ArrayList<String> mDatas, final ArrayList<GankEntity> gankEntityList, int position, View view) {
        MNImageBrowser.showImageBrowser(context, view, position, mDatas, gankEntityList);
    }

    public static void startToWebActivity(Context context, String titleFlag, String title, String url) {
        Intent intent = new Intent(context.getApplicationContext(), WebActivity.class);
        intent.putExtra(WebTitleFlag, titleFlag);
        intent.putExtra(WebTitle, title);
        intent.putExtra(WebUrl, url);
        context.startActivity(intent);
    }

    public static void startAboutActivity(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), AboutActivity.class);
        context.startActivity(intent);
    }

    public static void startSettingActivity(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), SettingActivity.class);
        context.startActivity(intent);
    }

    public static void startDayShowActivity(Context context, String date) {
        Intent intent = new Intent(context.getApplicationContext(), GankActivity.class);
        intent.putExtra(DayDate, date);
        context.startActivity(intent);
    }

    public static void startAppShareText(Context context, String shareTitle, String shareText) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain"); // 纯文本
        shareIntent.putExtra(Intent.EXTRA_TITLE, shareTitle);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        //设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    public static void startAppShareImage(Context context, String shareTitle, String shareText, Uri uri) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.putExtra(Intent.EXTRA_TITLE, shareTitle);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        //设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }


    public static void startQRCodeActivity(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), QRCodeActivity.class);
        context.startActivity(intent);
    }

    public static void startSupportPayActivity(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), SupportPayActivity.class);
        context.startActivity(intent);
    }

    public static void startMoreActivity(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), MoreActivity.class);
        context.startActivity(intent);
    }

    public static void goToMarket(Context context, String packageName) {
        //判断是否安装应用宝
        if (!GankUtils.isAppInstalled(context, "com.tencent.android.qqdownloader")) {
            MyToast.showShortToast("请先安装应用宝市场");
            return;
        }
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            goToMarket.setClassName("com.tencent.android.qqdownloader", "com.tencent.pangu.link.LinkProxyActivity");
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

}
