package com.maning.gankmm.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.maning.gankmm.bean.gank2.GankEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maning on 16/3/5.
 * <p/>
 * 公共表的操作
 */
public class PublicDao extends GankBaseDao {

    public PublicDao() {
        super();
    }

    /**
     * 往数据库中插入一条收藏数据
     */
    public synchronized void insertList(List<GankEntity> list, String category, String type) {
        if (list == null || list.size() == 0) {
            return;
        }
        SQLiteDatabase db = helper.getWritableDatabase();
        //插入
        db.beginTransaction();
        //删除
        try {
            db.delete(GankMMHelper.TABLE_NAME_PUBLIC, "type=? and category=?", new String[]{type, category});
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < list.size(); i++) {
            GankEntity gankResult = list.get(i);
            insert(db, GankMMHelper.TABLE_NAME_PUBLIC, gankResult);
        }
        //设置成功
        db.setTransactionSuccessful();
        //结束事务
        db.endTransaction();
        //关闭数据库
        db.close();
    }

    /**
     * 查询是否存在
     *
     * @param gankId
     * @return
     */
    private synchronized boolean queryByID(SQLiteDatabase db, String gankId) {
        Cursor cursor = db.query(GankMMHelper.TABLE_NAME_PUBLIC, null, GankMMHelper.gankId + "=?", new String[]{gankId}, null, null, null);
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
     * @return 集合数据
     */
    public synchronized ArrayList<GankEntity> queryAll(String categroyQuery, String typeQuery) {
        return query(helper.getReadableDatabase(),GankMMHelper.TABLE_NAME_PUBLIC, categroyQuery, typeQuery);
    }
}
