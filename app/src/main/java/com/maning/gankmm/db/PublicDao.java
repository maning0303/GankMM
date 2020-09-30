package com.maning.gankmm.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.maning.gankmm.app.MyApplication;
import com.maning.gankmm.bean.GankEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maning on 16/3/5.
 * <p/>
 * 公共表的操作
 */
public class PublicDao {

    private GankMMHelper helper;

    private SQLiteDatabase db;

    public PublicDao() {
        helper = new GankMMHelper(MyApplication.getIntstance());
    }

    /**
     * 往数据库中插入一条收藏数据
     */
    public synchronized void insertList(List<GankEntity> list,String category,String type) {
        if(list == null || list.size() == 0){
            return;
        }
        db = helper.getWritableDatabase();
        //插入
        db.beginTransaction();

        //删除
        deleteAll(category,type);

        for (int i = 0; i < list.size(); i++) {
            GankEntity gankResult = list.get(i);
            //查看数据库中有没有
            boolean queryByID = queryByID(gankResult.get_id());
            if (!queryByID) {
                ContentValues values = new ContentValues();
                values.put(GankMMHelper.GankID, gankResult.get_id());
                values.put(GankMMHelper.createdAt, gankResult.getCreatedAt());
                values.put(GankMMHelper.desc, gankResult.getDesc());
                values.put(GankMMHelper.publishedAt, gankResult.getPublishedAt());
                values.put(GankMMHelper.source, gankResult.getSource());
                values.put(GankMMHelper.type, gankResult.getType());
                values.put(GankMMHelper.url, gankResult.getUrl());
                values.put(GankMMHelper.who, gankResult.getWho());
                values.put(GankMMHelper.category, gankResult.getCategory());
                if (gankResult.isUsed()) {
                    values.put(GankMMHelper.used, "true");
                } else {
                    values.put(GankMMHelper.used, "false");
                }

                String imageUrl = "";
                if (gankResult.getImages() != null && gankResult.getImages().size() > 0) {
                    imageUrl = gankResult.getImages().get(0);
                }
                values.put(GankMMHelper.imageUrl, imageUrl);

                db.insert(GankMMHelper.TABLE_NAME_PUBLIC, null, values);
            }
        }
        //设置成功
        db.setTransactionSuccessful();
        //结束事务
        db.endTransaction();
        //关闭数据库
        db.close();
    }

    //删除之前的所有，在保存（保证每次只保存最新刷新的20条即可）
    private synchronized void deleteAll(String categroy,String type) {
        try {
            db.delete(GankMMHelper.TABLE_NAME_PUBLIC, "type=? and category=?", new String[]{type, categroy});
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 查询是否存在
     *
     * @param GankID
     * @return
     */
    private synchronized boolean queryByID(String GankID) {
        Cursor cursor = db.query(GankMMHelper.TABLE_NAME_PUBLIC, null, GankMMHelper.GankID + "=?", new String[]{GankID}, null, null, null);
        boolean result = false;
        if (cursor.moveToNext()) {
            result = true;
        }
        cursor.close();
        return result;
    }

    /**
     * 查询每个标签的收藏数据
     *
     * @param type
     * @return 集合数据
     */
    public synchronized ArrayList<GankEntity> queryAllCollectByType(String categroy,String type) {
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(GankMMHelper.TABLE_NAME_PUBLIC, null, "type=? and category=?", new String[]{type,categroy}, null, null, null);

        ArrayList<GankEntity> collectList = new ArrayList<>();
        GankEntity collect;
        while (cursor.moveToNext()) {
            //查询
            String createdAt = cursor.getString(cursor.getColumnIndex(GankMMHelper.createdAt));
            String desc = cursor.getString(cursor.getColumnIndex(GankMMHelper.desc));
            String GankID = cursor.getString(cursor.getColumnIndex(GankMMHelper.GankID));
            String publishedAt = cursor.getString(cursor.getColumnIndex(GankMMHelper.publishedAt));
            String source = cursor.getString(cursor.getColumnIndex(GankMMHelper.source));
            String url = cursor.getString(cursor.getColumnIndex(GankMMHelper.url));
            String who = cursor.getString(cursor.getColumnIndex(GankMMHelper.who));
            String imageUrl = cursor.getString(cursor.getColumnIndex(GankMMHelper.imageUrl));
            String category = cursor.getString(cursor.getColumnIndex(GankMMHelper.category));

            collect = new GankEntity();
            collect.set_id(GankID);
            collect.setCreatedAt(createdAt);
            collect.setDesc(desc);
            collect.setPublishedAt(publishedAt);
            collect.setSource(source);
            collect.setType(type);
            collect.setUrl(url);
            collect.setWho(who);
            collect.setCategory(category);

            List<String> images = new ArrayList<>();
            images.add(imageUrl);
            collect.setImages(images);

            collectList.add(collect);
        }
        //关闭游标
        cursor.close();
        db.close();
        return collectList;
    }
}
