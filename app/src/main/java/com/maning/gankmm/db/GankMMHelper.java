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

    private static final String DB_NAME = "GankMM";
    public static final String TABLE_NAME_COLLECT = "collect";
    public static final String TABLE_NAME_PUBLIC = "public";
    private static final int version = 3;
    //升级添加字段
    private static final String INSERT_URL_COLLECT = "ALTER TABLE collect ADD imageUrl TEXT default ''";
    private static final String INSERT_URL_PUBLIC = "ALTER TABLE public ADD imageUrl TEXT default ''";

    private static final String INSERT_CATEGORY_COLLECT = "ALTER TABLE public ADD category TEXT default ''";
    private static final String INSERT_CATEGORY_PUBLIC = "ALTER TABLE public ADD category TEXT default ''";

    /**
     * _id : 56d6481e6776592a03e624a4
     * _ns : ganhuo
     * createdAt : 2016-03-02T09:55:42.63Z
     * desc : 3.2
     * publishedAt : 2016-03-02T12:06:37.242Z
     * source : chrome
     * type : 福利
     * url : http://ww3.sinaimg.cn/large/7a8aed7bjw1f1ia8qj5qbj20nd0zkmzp.jpg
     * used : true
     * who : 张涵宇
     */
    private static final String ID = "id";
    public static final String GankID = "gankID";
    public static final String NS = "_ns";
    public static final String createdAt = "createdAt";
    public static final String desc = "desc";
    public static final String publishedAt = "publishedAt";
    public static final String source = "source";
    public static final String type = "type";
    public static final String url = "url";
    public static final String used = "used";
    public static final String who = "who";
    //版本2添加的新字段
    public static final String imageUrl = "imageUrl";
    //版本3添加新字段
    public static final String category = "category";


    //收藏表
    private static final String sql_collect = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME_COLLECT + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + GankID + " TEXT, "
            + NS + " TEXT, "
            + createdAt + " TEXT, "
            + desc + " TEXT, "
            + publishedAt + " TEXT, "
            + source + " TEXT, "
            + type + " TEXT, "
            + url + " TEXT, "
            + used + " TEXT, "
            + who + " TEXT)";

    //公共阅读表
    private static final String sql_public = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME_PUBLIC + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + GankID + " TEXT, "
            + NS + " TEXT, "
            + createdAt + " TEXT, "
            + desc + " TEXT, "
            + publishedAt + " TEXT, "
            + source + " TEXT, "
            + type + " TEXT, "
            + url + " TEXT, "
            + used + " TEXT, "
            + who + " TEXT)";

    public GankMMHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        KLog.e("数据库创建了");
        try {
            db.execSQL(sql_collect);
            db.execSQL(sql_public);
            //升级
            updateTableToVersion(db, 2);
            updateTableToVersion(db, 3);
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
                db.execSQL(INSERT_URL_COLLECT);
                db.execSQL(INSERT_URL_PUBLIC);
                break;
            case 3:
                KLog.e("数据库升级了3");
                db.execSQL(INSERT_CATEGORY_COLLECT);
                db.execSQL(INSERT_CATEGORY_PUBLIC);
                break;
        }
    }
}
