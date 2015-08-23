package com.hkllzh.android.net;

import com.hkllzh.android.util.datetime.TimeConstant;

/**
 * 基本的请求者
 * <p/>
 * lizheng -- 2015/08/23
 * <p/>
 * FastWeiB
 */
public abstract class AbstractApi implements ApiInterface {

    private static final long DEFAULT_CACHE_TIME = TimeConstant.ONE_DAY_SECOND;

    @Override
    public abstract String getRequestURL();

    @Override
    public abstract RequestMethod getRequestMethod();

    @Override
    public abstract RequestParams getRequestParams();

    public abstract RequestInterface getBaseRequest();

    public void execute(final ResponseHandler handler) {
        getBaseRequest().execute(this, handler);
    }

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
