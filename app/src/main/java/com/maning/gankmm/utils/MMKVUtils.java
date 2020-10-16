package com.maning.gankmm.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.tencent.mmkv.MMKV;

/**
 * @author : maning
 * @date : 2020-10-16
 * @desc :
 */
public class MMKVUtils {

    private static final String TIME_FLAG = "--&T&--";
    private static MMKV defaultMMKV;

    public static MMKV getInstance() {
        if (defaultMMKV == null) {
            defaultMMKV = MMKV.defaultMMKV();
        }
        return defaultMMKV;
    }

    public static <T> T getObject(String key, Class<T> classOfT) {
        String result = getInstance().decodeString(key);
        if (TextUtils.isEmpty(result)) {
            return null;
        }
        return new Gson().fromJson(result, classOfT);
    }

    public static boolean saveObject(String key, String value) {
        return getInstance().encode(key, value);
    }

    /**
     * 保存数据
     *
     * @param key      key
     * @param value    value
     * @param saveTime 保存时间长度，秒
     */
    public static void saveWithTime(String key, String value, int saveTime) {
        //创建带有时间标记的value+保存到的时间
        value = value + TIME_FLAG + (System.currentTimeMillis() / 1000 + saveTime);
        getInstance().encode(key, value);
    }

    public static String getWithTime(String key) {
        String result = getInstance().decodeString(key);
        if (TextUtils.isEmpty(result)) {
            return "";
        }
        if (result.contains(TIME_FLAG)) {
            String[] split = result.split(TIME_FLAG);
            if (split.length > 1) {
                String time = split[1];
                String value = split[0];
                if (!TextUtils.isEmpty(time)) {
                    long current = System.currentTimeMillis() / 1000;
                    long saveTime = Long.parseLong(time);
                    if (current > saveTime) {
                        //过期
                        getInstance().removeValueForKey(key);
                        result = "";
                    } else {
                        result = value;
                    }
                }
            }
        }
        return result;
    }


}
