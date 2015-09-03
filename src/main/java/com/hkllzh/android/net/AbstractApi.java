package com.hkllzh.android.net;

import com.hkllzh.android.util.datetime.TimeConstant;

/**
 * 一个请求的默认定义
 * <p/>
 * lizheng -- 2015/08/23
 * <p/>
 * FastWeiB
 */
public abstract class AbstractApi implements API {

    private static final long DEFAULT_CACHE_TIME = TimeConstant.ONE_DAY_UNIT_SECOND;

    @Override
    public abstract String getRequestURL();

    @Override
    public abstract RequestMethod getRequestMethod();

    @Override
    public abstract RequestParams getRequestParams();

    @Override
    public boolean isShowLog() {
        return true;
    }

    @Override
    public boolean isUseCache() {
        return false;
    }

    @Override
    public long getCacheTime() {
        return DEFAULT_CACHE_TIME;
    }
}
