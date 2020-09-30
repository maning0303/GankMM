package com.maning.gankmm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.socks.library.KLog;

/**
 * Created by maning on 16/3/5.
 */
public class GankMMHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "gankmm2";
    public static final String TABLE_NAME_COLLECT = "collect";
    public static final String TABLE_NAME_PUBLIC = "public";
    private static final int version = 1;
    //升级添加字段
    private static final String INSERT_URL_COLLECT = "ALTER TABLE collect ADD imageUrl TEXT default ''";
    private static final String INSERT_URL_PUBLIC = "ALTER TABLE public ADD imageUrl TEXT default ''";

    //    {
//        "_id": "5f12cc060bd5529b54e71328",
//        "author": "blue",
//        "category": "GanHuo",
//        "createdAt": "2020-07-18 18:16:38",
//        "desc": "类似京东、淘宝首页体验的嵌套滑动吸顶效果",
//        "images": [
//            "https://gank.io/images/15781a5c1376483ba1327c7b7fefec7b"
//        ],
//        "likeCounts": 0,
//        "publishedAt": "2020-07-18 18:16:38",
//        "stars": 1,
//        "title": "NestedCeilingEffect",
//        "type": "Android",
//        "url": "https://github.com/solartcc/NestedCeilingEffect",
//        "views": 22
//    }
    private static final String ID = "id";
    public static final String gankId = "gankId";
    public static final String author = "author";
    public static final String category = "category";
    public static final String createdAt = "createdAt";
    public static final String desc = "desc";
    public static final String likeCounts = "likeCounts";
    public static final String publishedAt = "publishedAt";
    public static final String stars = "stars";
    public static final String title = "title";
    public static final String type = "type";
    public static final String url = "url";
    public static final String views = "views";
    public static final String imgUrl = "imgUrl";


    //收藏表
    private static final String sql_collect = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME_COLLECT + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + author + " TEXT, "
            + category + " TEXT, "
            + createdAt + " TEXT, "
            + desc + " TEXT, "
            + likeCounts + " TEXT, "
            + publishedAt + " TEXT, "
            + stars + " TEXT, "
            + title + " TEXT, "
            + type + " TEXT, "
            + url + " TEXT, "
            + views + " views, "
            + imgUrl + " imgUrl, "
            + gankId + " TEXT)";

    //公共阅读表
    private static final String sql_public = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME_PUBLIC + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + author + " TEXT, "
            + category + " TEXT, "
            + createdAt + " TEXT, "
            + desc + " TEXT, "
            + likeCounts + " TEXT, "
            + publishedAt + " TEXT, "
            + stars + " TEXT, "
            + title + " TEXT, "
            + type + " TEXT, "
            + url + " TEXT, "
            + views + " views, "
            + imgUrl + " imgUrl, "
            + gankId + " TEXT)";

    public GankMMHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        KLog.e("数据库创建了");
        try {
            db.execSQL(sql_collect);
            db.execSQL(sql_public);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        KLog.e("onUpgrade-oldVersion:" + oldVersion + ",newVersion:" + newVersion);
        try {
            for (int i = oldVersion + 1; i <= newVersion; i++) {
                updateTableToVersion(db, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateTableToVersion(SQLiteDatabase db, int version) {
        switch (version) {
            case 2:
                KLog.e("数据库升级了2");
//                db.execSQL(INSERT_URL_COLLECT);
//                db.execSQL(INSERT_URL_PUBLIC);
                break;
        }
    }
}
