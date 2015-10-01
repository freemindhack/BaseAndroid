package com.hkllzh.android.net.okhttp;

import com.hkllzh.android.net.impl.AbstractAsyncResponseImpl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * 基于{@link OkHttpClient}实现的网络请求相应类<br>
 * 对应的请求类为{@link OkHttpRequest}
 * <p/>
 * lizheng -- 2015/08/23
 * <p/>
 * FastWeiB
 */
public abstract class OkHttpResponse extends AbstractAsyncResponseImpl {

    public void onFailure(Request request, IOException e) {
        sendFailureMessage("failed");
        sendFinishMessage();
    }

    public void onResponse(Response response) {
        try {
            sendSuccessMessage(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendFinishMessage();
    }
}
