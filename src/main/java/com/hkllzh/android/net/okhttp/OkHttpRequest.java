package com.hkllzh.android.net.okhttp;

import android.support.v4.util.ArrayMap;

import com.hkllzh.android.net.API;
import com.hkllzh.android.net.RequestInterface;
import com.hkllzh.android.net.ResponseInterface;
import com.hkllzh.android.util.md5.MD5Util;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashSet;

import okio.BufferedSink;

/**
 * 网络请求类
 * <p/>
 * lizheng -- 2015/08/23
 * <p/>
 * FastWeiB
 */
public abstract class OkHttpRequest implements RequestInterface {
    public abstract OkHttpClient getOkHttpClient();

    private HashSet<String> tags;

    @Override
    public void execute(API api, ResponseInterface responseInterface) {
        final OkHttpResponse handler = (OkHttpResponse) responseInterface;
        if (null == tags) {
            tags = new HashSet<>();
        }
        tags.add(MD5Util.generate(api.getRequestURL()));
        Request request = null;
        switch (api.getRequestMethod()) {
            case GET:
                request = new Request.Builder()
                        .url(getHttpUrl(api))
                        .tag(MD5Util.generate(api.getRequestURL()))
                        .get()
                        .build();
                break;
            case POST:
                RequestBody requestBody = new RequestBody() {
                    @Override
                    public MediaType contentType() {
                        return null;
                    }

                    @Override
                    public void writeTo(BufferedSink sink) throws IOException {

                    }
                };
                request = new Request.Builder()
                        .url(getHttpUrl(api))
                        .tag(MD5Util.generate(api.getRequestURL()))
                        .post(requestBody)
                        .build();
                break;
        }

        if (null == request) {
            return;
        }

        getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.onFailure(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                handler.onResponse(response);
            }
        });
    }

    @Override
    public void cancel(API api) {
        tags.remove(MD5Util.generate(api.getRequestURL()));
        getOkHttpClient().cancel(MD5Util.generate(api.getRequestURL()));
    }

    @Override
    public void cancelAll() {
        for (String s : tags) {
            getOkHttpClient().cancel(s);
        }
        tags.clear();
    }

    private HttpUrl getHttpUrl(API api) {
        HttpUrl httpUrl = HttpUrl.parse(api.getRequestURL());
        HttpUrl.Builder builder = httpUrl.newBuilder();

        if (null != getUrlParams(api)) {
            for (String k : getUrlParams(api).keySet()) {
                builder = builder.addQueryParameter(k, getUrlParams(api).get(k));
            }
        }

        return builder.build();
    }

    protected ArrayMap<String, String> getUrlParams(API api) {
        return api.getRequestParams().getRequestParams();
    }
}
