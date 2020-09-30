package com.maning.gankmm.db;

import android.database.sqlite.SQLiteDatabase;

import com.maning.gankmm.bean.GankEntity;

import java.util.ArrayList;

/**
 * Created by maning on 16/3/5.
 * <p/>
 * 收藏表的操作
 */
public class CollectDao extends GankBaseDao {


    public CollectDao() {
    }

    /**
     * 往数据库中插入一条收藏数据
     *
     * @param gankResult
     * @return 是否插入成功
     */
    public synchronized boolean insertOneCollect(GankEntity gankResult) {
        SQLiteDatabase db = helper.getWritableDatabase();
        boolean insert = insert(db, GankMMHelper.TABLE_NAME_COLLECT, gankResult);
        db.close();
        return insert;
    }

    /**
     * 删除一条数据
     *
     * @param gankId
     * @return
     */
    public synchronized boolean deleteOneCollect(String gankId) {
        return delete(GankMMHelper.TABLE_NAME_COLLECT, gankId);
    }

    /**
     * 查询是否存在
     *
     * @param gankId
     * @return
     */
    public synchronized boolean queryOneCollectByID(String gankId) {
        return query(GankMMHelper.TABLE_NAME_COLLECT, gankId);
    }

    /**
     * 查询每个标签的收藏数据
     *
     * @return 集合数据
     */
    public synchronized ArrayList<GankEntity> queryAllCollectByType(String categoryQuery, String typeQuery) {
        return query(helper.getWritableDatabase(), GankMMHelper.TABLE_NAME_COLLECT, categoryQuery, typeQuery);
    }
}
