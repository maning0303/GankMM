package com.maning.gankmm.http.update;

import com.maning.gankmm.bean.AppUpdateInfo;
import com.maning.gankmm.http.BuildApi;
import com.maning.gankmm.http.callback.CommonHttpCallback;

/**
 * @author : maning
 * @date : 2020-09-30
 * @desc : 版本更新专用Api
 */
public class UpdateApi {

    public static void getAppUpdateInfo(CommonHttpCallback<AppUpdateInfo> httpCallback) {
        BuildApi.getUpdateAPIService().getTheLastAppInfo().enqueue(httpCallback);
    }

}
