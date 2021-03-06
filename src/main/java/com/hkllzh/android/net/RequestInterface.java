package com.hkllzh.android.net;

/**
 * 请求者，请求执行者。
 * <p/>
 * lizheng -- 2015/08/23
 * <p/>
 * FastWeiB
 */
public interface RequestInterface {
    void execute(APIInterface api, ResponseInterface handler);

    void cancel(APIInterface api);

    void cancelAll();
}
