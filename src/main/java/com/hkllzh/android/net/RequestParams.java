package com.hkllzh.android.net;

import android.support.v4.util.ArrayMap;

import java.util.Map;

/**
 * {@link com.squareup.okhttp.OkHttpClient}请求参数类
 * <p/>
 * lizheng -- 2015/08/23
 * <p/>
 * FastWeiB
 */
public class RequestParams {
    private ArrayMap<String, String> urlParams = new ArrayMap<>();

    public RequestParams() {
    }

    public RequestParams(Map<String, String> source) {
        if (source != null) {
            for (Map.Entry<String, String> entry : source.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    public void put(String key, String value) {
        if (key != null && value != null) {
            urlParams.put(key, value);
        }
    }

    public RequestParams(ArrayMap<String, String> urlParams) {
        this.urlParams = urlParams;
    }

    public ArrayMap<String, String> getRequestParams() {
        return urlParams;
    }

}
