package com.maning.gankmm.http.gank2;

/**
 * 错误代码表
 */
public final class HttpErrorConstants {

    //网络错误  90开头
    public static final String ERR_UNKNOWNHOSTEXCEPTION_ERROR = "网络连接失败,请检查网络设置";
    public static final String ERR_SOCKETTIMEOUTEXCEPTION_ERROR = "网络请求超时";

    public static final String ERR_NETEXCEPTION_ERROR = "网络通讯异常，请稍后再试";
    public static final int ERR_NETEXCEPTION_ERROR_CODE = 9000;

    public static final String ERR_HTTPRESPONSE_JSONPARSE_ERROR = "数据解析异常";
    public static final int ERR_HTTPRESPONSE_JSONPARSE_ERROR_CODE = 9002;

    public static final String ERR_HTTPRESPONSE_DATA_ERROR = "数据异常";
    public static final int ERR_HTTPRESPONSE_DATA_ERROR_CODE = 9003;

    public static final String ERR_HTTPRESPONSE_JSONFORMAT_ERROR = "数据格式异常";
    public static final int ERR_HTTPRESPONSE_JSONFORMAT_ERROR_CODE = 9004;

    private HttpErrorConstants() {
    }

}
