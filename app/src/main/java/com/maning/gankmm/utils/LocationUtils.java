package com.maning.gankmm.utils;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.socks.library.KLog;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <pre>
 *     author : maning
 *     e-mail : xxx@xx
 *     time   : 2018/03/02
 *     desc   : 定位工具类
 *     version: 1.0
 * </pre>
 */
public class LocationUtils {
    //48cc89a5931e80321bced46714a03563
    //声明mlocationClient对象
    private static AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    private static AMapLocationClientOption mLocationOption = null;

    /**
     * 单次定位
     *
     * @param context
     * @param locationListener
     */
    public static void getLocation(Context context, final OnLocationListener locationListener) {
        mlocationClient = new AMapLocationClient(context);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (locationListener == null) {
                    return;
                }
                //完成
                locationListener.onComplete();
                if (aMapLocation == null) {
                    locationListener.onFail(10000, "定位失败");
                }
                if (aMapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    aMapLocation.getLatitude();//获取纬度
                    aMapLocation.getLongitude();//获取经度
                    aMapLocation.getAccuracy();//获取精度信息
                    aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    aMapLocation.getCountry();//国家信息
                    aMapLocation.getProvince();//省信息
                    aMapLocation.getCity();//城市信息
                    aMapLocation.getDistrict();//城区信息
                    aMapLocation.getStreet();//街道信息
                    aMapLocation.getStreetNum();//街道门牌号信息
                    aMapLocation.getCityCode();//城市编码
                    aMapLocation.getAdCode();//地区编码
                    aMapLocation.getAoiName();//获取当前定位点的AOI信息
                    aMapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                    aMapLocation.getFloor();//获取当前室内定位的楼层
                    //获取定位时间
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(aMapLocation.getTime());
                    df.format(date);
                    KLog.i("定位成功:" + aMapLocation.toString());

                    //定位获取成功
                    locationListener.onSuccess(aMapLocation);
                } else if (aMapLocation.getErrorCode() == 12) {
                    locationListener.onFail(aMapLocation.getErrorCode(), "请您打开手机系统设置，开启应用定位服务");
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    locationListener.onFail(aMapLocation.getErrorCode(), "定位失败");
                }
            }
        });
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    public static void destory(){
        //销毁定位客户端，同时销毁本地定位服务。
        if (mlocationClient != null) {
            mlocationClient.onDestroy();
        }
    }

    public interface OnLocationListener {

        void onSuccess(AMapLocation aMapLocation);

        void onFail(int errorCode, String errorMsg);

        void onComplete();
    }

}
