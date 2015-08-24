package com.hkllzh.android.net;

/**
 * 请求响应接口
 * <p/>
 * lizheng -- 2015/08/24
 * <p/>
 * FastWeiB
 */
public interface ResponseInterface {
    void start();

    void failed(String failedMessage);

    void success(String response);

    void finish();
}
