package com.hkllzh.android.net.impl;

import com.hkllzh.android.net.APIInterface;
import com.hkllzh.android.net.RequestParams;
import com.hkllzh.android.util.datetime.TimeConstant;

/**
 * 一个请求的默认抽象实现
 * <p/>
 * lizheng -- 2015/08/23
 */
public abstract class AbstractApiImpl implements APIInterface {

    private static final long DEFAULT_CACHE_TIME = TimeConstant.ONE_DAY_UNIT_SECOND;

    @Override
    public abstract String requestURL();

    @Override
    public abstract RequestMethod requestMethod();

    @Override
    public abstract RequestParams requestParams();

    @Override
    public boolean isShowLog() {
        return true;
    }

    @Override
    public boolean isUseCache() {
        return false;
    }

    @Override
    public long cacheTime() {
        return DEFAULT_CACHE_TIME;
    }
}
