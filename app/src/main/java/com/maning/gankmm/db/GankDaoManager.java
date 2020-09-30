package com.maning.gankmm.db;

/**
 * @author : maning
 * @date : 2020-09-30
 * @desc :
 */
public class GankDaoManager {

    private static PublicDao mPublicDao;
    private static CollectDao mCollectDao;

    private GankDaoManager() {
    }

    public static PublicDao getPublicDao(){
        if (mPublicDao == null) {
            synchronized (GankDaoManager.class) {
                if (mPublicDao == null) {
                    mPublicDao = new PublicDao();
                }
            }
        }
        return mPublicDao;
    }

    public static CollectDao getCollectDao(){
        if (mCollectDao == null) {
            synchronized (GankDaoManager.class) {
                if (mCollectDao == null) {
                    mCollectDao = new CollectDao();
                }
            }
        }
        return mCollectDao;
    }

}
