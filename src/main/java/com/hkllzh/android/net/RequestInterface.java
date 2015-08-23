package com.hkllzh.android.net;

/**
 * TODO
 * <p/>
 * lizheng -- 2015/08/23
 * <p/>
 * FastWeiB
 */
public interface RequestInterface {
    void execute(ApiInterface ApiInterface, final ResponseHandler handler);

    void cancel(ApiInterface ApiInterface);

    void cancelAll();
}
