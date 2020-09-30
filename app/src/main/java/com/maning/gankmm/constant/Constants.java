package com.maning.gankmm.constant;

import android.os.Environment;

import java.io.File;

/**
 * 常量类
 * 一些接口地址等常量
 */
public class Constants {

    //接口请求的Url
    public static final String BASEURL = "http://gank.io/api/";

    public static final String GANK2_BASEURL = "https://gank.io/api/v2/";

    public static final String FIR_BASEURL = "http://api.fir.im/";

    //干活历史日期
    public static final String URL_HistoryDate = "http://gank.io/api/day/history";

    //获取APKinfo的地址：fir.im
    public static final String URL_AppUpdateInfo = "http://api.fir.im/apps/latest/56dd4bb7e75e2d27f2000046?api_token=78a2cea8e63eb7a96ba6ca2a3e500af2&type=android";

    //Mob官网API
    public static final String URL_Mob = "http://apicloud.mob.com/";
    //Mob ApiKey
    public static final String URL_APP_Key = "1c9dccb9a2434";

    //保存图片的地址
    public static final String BasePath = Environment.getExternalStorageDirectory() + "/GankMM";

    //更新APK地址
    public static final String UpdateAPKPath = Environment.getExternalStorageDirectory() + "/GankMM/update";


    //标签
    public static final String Catrgory_GankHuo = "GanHuo";
    public static final String Catrgory_Article = "Article";

    public static final String FlagFragment = "Flag";
    public static final String FlagWelFare = "福利";
    public static final String FlagGirls = "Girl";
    public static final String FlagAndroid = "Android";
    public static final String FlagIOS = "iOS";
    public static final String FlagVideo = "休息视频";
    public static final String FlagJS = "前端";
    public static final String FlagExpand = "拓展资源";
    public static final String FlagRecommend = "瞎推荐";
    public static final String FlagAPP = "App";
    public static final String FlagCollect = "收藏";

    //SharePreferences  ---- Key
    public static final String SPFeedback = "feedback";
    public static final String SPJpush = "jpush";
    public static final String SPAppUpdate = "update";
    public static final String SPCodesMenu = "codeMenu";
    public static final String SPSwitcherDataType = "SPSwitcherDataType"; //保存首页头条


}
