package com.hkllzh.android.net.okhttp;

import com.hkllzh.android.net.AsyncResponseHandler;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * 数据回调返回相应
 * <p>
 * lizheng -- 2015/08/23
 * <p>
 * FastWeiB
 */
public abstract class OkHttpResponse extends AsyncResponseHandler {

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
