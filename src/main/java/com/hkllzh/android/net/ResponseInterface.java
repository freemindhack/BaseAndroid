package com.hkllzh.android.net;

/**
 * 请求响应接口
 * <p/>
 * lizheng -- 2015/08/24
 * <p/>
 * FastWeiB
 */
public interface ResponseInterface {
    void reqStart(APIInterface apiInterface);

    void reqFailed(APIInterface apiInterface, String failedMessage);

    void reqSuccess(APIInterface apiInterface, String response);

    void reqFinish(APIInterface apiInterface);
}
