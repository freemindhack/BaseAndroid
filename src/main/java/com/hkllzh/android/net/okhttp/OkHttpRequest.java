package com.hkllzh.android.net.okhttp;

import android.support.v4.util.ArrayMap;

import com.hkllzh.android.net.API;
import com.hkllzh.android.net.RequestInterface;
import com.hkllzh.android.net.ResponseInterface;
import com.hkllzh.android.util.md5.MD5Util;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashSet;

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
        final OkHttpResponse okHttpResponse = (OkHttpResponse) responseInterface;

        okHttpResponse.sendStartMessage();

        if (null == tags) {
            tags = new HashSet<>();
        }
        tags.add(MD5Util.generate(api.getRequestURL()));
        Request request = null;
        switch (api.getRequestMethod()) {
            case GET:
                request = get(api);
                break;
            case POST:
                request = post(api);
                break;
            default:
                request = get(api);
                break;
        }

        if (null == request) {
            okHttpResponse.sendFailureMessage("请求为空");
            okHttpResponse.sendFinishMessage();
            return;
        }

        getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                okHttpResponse.onFailure(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                okHttpResponse.onResponse(response);
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

    private Request get(API api) {
        return new Request.Builder()
                .url(getHttpUrl(api))
                .tag(MD5Util.generate(api.getRequestURL()))
                .get()
                .build();
    }

    private Request post(API api) {
        MultipartBuilder multipartBuilder = new MultipartBuilder();


        ArrayMap<String, String> ps = api.getRequestParams().getRequestParams();
        for (String k : ps.keySet()) {
            multipartBuilder = multipartBuilder.addFormDataPart(k, ps.get(k));
        }

        RequestBody requestBody = multipartBuilder.build();
        return new Request.Builder()
                .url(getHttpUrl(api))
                .tag(MD5Util.generate(api.getRequestURL()))
                .post(requestBody)
                .build();
    }

    private HttpUrl getHttpUrl(API api) {
        HttpUrl httpUrl = HttpUrl.parse(api.getRequestURL());
        HttpUrl.Builder builder = httpUrl.newBuilder();

        if (null != getDefaultUrlParams()) {
            ArrayMap<String, String> ps = getDefaultUrlParams();
            for (String k : ps.keySet()) {
                builder = builder.addQueryParameter(k, ps.get(k));
            }
        }

        if (api.getRequestMethod() == API.RequestMethod.GET) {
            ArrayMap<String, String> ps = api.getRequestParams().getRequestParams();
            for (String k : ps.keySet()) {
                builder = builder.addQueryParameter(k, ps.get(k));
            }
        }

        return builder.build();
    }

    protected ArrayMap<String, String> getDefaultUrlParams() {
        return null;
    }
}
