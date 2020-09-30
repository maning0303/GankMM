package com.maning.gankmm.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.maning.gankmm.app.MyApplication;
import com.maning.gankmm.bean.gank2.GankEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : maning
 * @date : 2020-09-30
 * @desc :
 */
public class GankBaseDao {

    protected GankMMHelper helper;


    public GankBaseDao() {
        helper = new GankMMHelper(MyApplication.getIntstance());
    }

    /**
     * 往数据库中插入一条收藏数据
     *
     * @param gankResult
     * @return 是否插入成功
     */
    public synchronized boolean insert(SQLiteDatabase db,String tableName, GankEntity gankResult) {
        ContentValues values = new ContentValues();
        values.put(GankMMHelper.gankId, gankResult.get_id());
        values.put(GankMMHelper.createdAt, gankResult.getCreatedAt());
        values.put(GankMMHelper.desc, gankResult.getDesc());
        values.put(GankMMHelper.title, gankResult.getTitle());
        values.put(GankMMHelper.author, gankResult.getAuthor());
        values.put(GankMMHelper.publishedAt, gankResult.getPublishedAt());
        values.put(GankMMHelper.type, gankResult.getType());
        values.put(GankMMHelper.url, gankResult.getUrl());
        values.put(GankMMHelper.category, gankResult.getCategory());
        values.put(GankMMHelper.stars, gankResult.getStars());
        values.put(GankMMHelper.views, gankResult.getViews());
        values.put(GankMMHelper.likeCounts, gankResult.getLikeCounts());
        String imageUrl = "";
        if (gankResult.getImages() != null && gankResult.getImages().size() > 0) {
            imageUrl = gankResult.getImages().get(0);
        }
        values.put(GankMMHelper.imgUrl, imageUrl);

        long insert = db.insert(tableName, null, values);
        return insert != (-1);
    }

    public synchronized boolean delete(String tableName,String gankId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int i = db.delete(tableName, GankMMHelper.gankId + "=?", new String[]{gankId});
        db.close();
        return i > 0;
    }

    public synchronized boolean query(String tableName,String gankId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query(tableName, null, GankMMHelper.gankId + "=?", new String[]{gankId}, null, null, null);
        boolean result = false;
        if (cursor.moveToNext()) {
            result = true;
        }
        cursor.close();
        db.close();
        return result;
    }

    public synchronized ArrayList<GankEntity> query(SQLiteDatabase db,String tableName, String categroyQuery, String typeQuery) {
        Cursor cursor = db.query(tableName, null, "type=? and category=?", new String[]{typeQuery, categroyQuery}, null, null, null);

        ArrayList<GankEntity> saveList = new ArrayList<>();
        GankEntity collect;
        while (cursor.moveToNext()) {
            //查询
            String gankId = cursor.getString(cursor.getColumnIndex(GankMMHelper.gankId));
            String author = cursor.getString(cursor.getColumnIndex(GankMMHelper.author));
            String category = cursor.getString(cursor.getColumnIndex(GankMMHelper.category));
            String createdAt = cursor.getString(cursor.getColumnIndex(GankMMHelper.createdAt));
            String desc = cursor.getString(cursor.getColumnIndex(GankMMHelper.desc));
            String likeCounts = cursor.getString(cursor.getColumnIndex(GankMMHelper.likeCounts));
            String publishedAt = cursor.getString(cursor.getColumnIndex(GankMMHelper.publishedAt));
            String stars = cursor.getString(cursor.getColumnIndex(GankMMHelper.stars));
            String title = cursor.getString(cursor.getColumnIndex(GankMMHelper.title));
            String type = cursor.getString(cursor.getColumnIndex(GankMMHelper.type));
            String url = cursor.getString(cursor.getColumnIndex(GankMMHelper.url));
            String views = cursor.getString(cursor.getColumnIndex(GankMMHelper.views));
            String imgUrl = cursor.getString(cursor.getColumnIndex(GankMMHelper.imgUrl));

            collect = new GankEntity();
            collect.set_id(gankId);
            collect.setAuthor(author);
            collect.setCategory(category);
            collect.setCreatedAt(createdAt);
            collect.setDesc(desc);
            collect.setLikeCounts(likeCounts);
            collect.setPublishedAt(publishedAt);
            collect.setStars(stars);
            collect.setTitle(title);
            collect.setType(type);
            collect.setUrl(url);
            collect.setViews(views);

            List<String> images = new ArrayList<>();
            images.add(imgUrl);
            collect.setImages(images);

            saveList.add(collect);
        }
        //关闭游标
        cursor.close();
        db.close();
        return saveList;
    }

}
