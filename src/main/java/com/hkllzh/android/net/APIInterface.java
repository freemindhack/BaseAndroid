package com.hkllzh.android.net;

/**
 * 一个Api。定义这个请求，不包含动作<br/>
 * <p/>
 * lizheng -- 2015/08/23
 * <p/>
 * FastWeiB
 */
public interface APIInterface {

    /**
     * 返回请求URL
     */
    String requestURL();

    /**
     * 返回请求方法
     */
    RequestMethod requestMethod();

    /**
     * 返回请求参数列表
     */
    RequestParams requestParams();

    /**
     * 是否打印请求信息至log
     *
     * @return true 打印、false 不打印
     */
    boolean isShowLog();

    /**
     * 是否使用缓存，适用于{@link APIInterface.RequestMethod#GET}请求
     *
     * @return true 使用、false 不使用
     */
    boolean isUseCache();

    /**
     * 用缓存时，缓存的时间。秒为单位
     *
     * @return 缓存时间，秒为单位
     */
    long cacheTime();


    enum RequestMethod {
        /**
         * Get请求
         */
        GET,
        /**
         * Post请求
         */
        POST
    }
}
