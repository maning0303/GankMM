package com.maning.gankmm.utils;

import com.maning.gankmm.app.MyApplication;
import com.maning.gankmm.bean.mob.CitysEntity;
import com.maning.gankmm.bean.mob.MobUserInfo;

/**
 * Created by maning on 2017/4/1.
 */

public class UserUtils {

    private static final String cache_citys = "Cache_Citys";
    private static final String cache_UserLogin = "cache_UserLogin";

    public static void saveCitysCache(CitysEntity citysEntity) {
        if (citysEntity != null) {
            MyApplication.getACache().put(cache_citys, citysEntity);
        }
    }

    public static CitysEntity getCitysCache() {
        CitysEntity citysEntity = (CitysEntity) MyApplication.getACache().getAsObject(cache_citys);
        return citysEntity;
    }

    //退出登录
    public static void quitLogin() {
        MyApplication.getACache().put(cache_UserLogin, new MobUserInfo());
    }

    //用户登录信息
    public static void saveUserCache(MobUserInfo userInfo) {
        if (userInfo != null) {
            MyApplication.getACache().put(cache_UserLogin, userInfo);
        }
    }

    //获取用户信息
    public static MobUserInfo getUserCache() {
        MobUserInfo userInfo = (MobUserInfo) MyApplication.getACache().getAsObject(cache_UserLogin);
        if(userInfo == null){
            userInfo = new MobUserInfo();
        }
        return userInfo;
    }

}
