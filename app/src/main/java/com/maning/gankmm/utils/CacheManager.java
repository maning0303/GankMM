package com.maning.gankmm.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : maning
 * @date : 2020-10-16
 * @desc : 缓存管理
 */
public class CacheManager {

    private static final String KEY_SCAN_RESULT = "KEY_SCAN_RESULT";
    private static Gson gson = new Gson();

    public static void cleanScanResult() {
        MMKVUtils.getInstance().putString(KEY_SCAN_RESULT, "");
    }

    public static void saveScanResult(String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        List<String> caches = new ArrayList<>();
        String cache = MMKVUtils.getInstance().getString(KEY_SCAN_RESULT, "");
        if (TextUtils.isEmpty(cache)) {
            caches.add(content);
        } else {
            caches = gson.fromJson(cache, new TypeToken<List<String>>() {
            }.getType());
            caches.add(0, content);
        }
        MMKVUtils.getInstance().putString(KEY_SCAN_RESULT, gson.toJson(caches));
    }

    public static List<String> getScanResult() {
        String cache = MMKVUtils.getInstance().getString(KEY_SCAN_RESULT, "");
        if (TextUtils.isEmpty(cache)) {
            return new ArrayList<>();
        } else {
            return gson.fromJson(cache, new TypeToken<List<String>>() {
            }.getType());
        }
    }

}
