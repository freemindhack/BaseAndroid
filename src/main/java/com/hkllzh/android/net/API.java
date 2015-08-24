package com.hkllzh.android.net;

/**
 * 一个Api。定义这个请求，不包含动作<br/>
 * <p/>
 * lizheng -- 2015/08/23
 * <p/>
 * FastWeiB
 */
public interface API {

    /**
     * 返回请求URL
     */
    String getRequestURL();

    /**
     * 返回请求方法
     */
    RequestMethod getRequestMethod();

    /**
     * 返回请求参数列表
     */
    RequestParams getRequestParams();

    /**
     * 是否打印请求信息至log
     *
     * @return true 打印、false 不打印
     */
    boolean isShowLog();

    /**
     * 是否使用缓存，适用于{@link API.RequestMethod#GET}请求
     *
     * @return true 使用、false 不使用
     */
    boolean isUseCache();

    /**
     * 用缓存时，缓存的时间。秒为单位
     *
     * @return 缓存时间，秒为单位
     */
    long getCacheTime();


    enum RequestMethod {
        GET, POST
    }
}
