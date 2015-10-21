package com.hkllzh.android.net.okhttp;

import android.support.v4.util.ArrayMap;

import com.hkllzh.android.net.APIInterface;
import com.hkllzh.android.net.ResponseInterface;
import com.hkllzh.android.net.impl.AbstractRequestImpl;
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
 * 基于{@link OkHttpClient}实现的网络请求类<br>
 * 对应的响应类为{@link OkHttpResponse}
 * <p/>
 * lizheng -- 2015/08/23
 * <p/>
 * FastWeiB
 */
public abstract class OkHttpRequest extends AbstractRequestImpl {
    public abstract OkHttpClient getOkHttpClient();

    // 记录所有请求的标记
    private HashSet<String> tags;

    @Override
    public void execute(APIInterface api, ResponseInterface responseInterface) {
        final OkHttpResponse okHttpResponse = new OkHttpResponse(responseInterface);

        okHttpResponse.sendStartMessage();

        if (null == tags) {
            tags = new HashSet<>();
        }
        tags.add(generateApiTag(api));
        Request request;
        switch (api.requestMethod()) {
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
            okHttpResponse.sendFailureMessage("无法生成请求！");
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
    public void cancel(APIInterface api) {
        tags.remove(generateApiTag(api));
        getOkHttpClient().cancel(generateApiTag(api));
    }

    @Override
    public void cancelAll() {
        for (String s : tags) {
            getOkHttpClient().cancel(s);
        }
        tags.clear();
    }

    /**
     * 生成{@link com.hkllzh.android.net.APIInterface.RequestMethod#GET}请求的{@link Request}
     *
     * @param api
     * @return
     */
    private Request get(APIInterface api) {
        return new Request.Builder()
                .url(getHttpUrl(api))
                .tag(generateApiTag(api))
                .get()
                .build();
    }

    /**
     * 生成{@link com.hkllzh.android.net.APIInterface.RequestMethod#POST}请求的{@link Request}
     *
     * @param api
     * @return
     */
    private Request post(APIInterface api) {
        MultipartBuilder builder = new MultipartBuilder();

        ArrayMap<String, String> ps = api.requestParams().getRequestParams();
        for (String k : ps.keySet()) {
            builder = builder.addFormDataPart(k, ps.get(k));
        }

        if (null != getDefaultPostParams()) {
            ArrayMap<String, String> postParams = getDefaultPostParams();
            for (String k : postParams.keySet()) {
                builder = builder.addFormDataPart(k, ps.get(k));
            }
        }


        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(getHttpUrl(api))
                .tag(generateApiTag(api))
                .post(requestBody)
                .build();
    }

    /**
     * 返回完整的http url
     *
     * @param api 请求Api
     * @return {@link HttpUrl}
     */
    private HttpUrl getHttpUrl(APIInterface api) {
        HttpUrl httpUrl = HttpUrl.parse(api.requestURL());
        HttpUrl.Builder builder = httpUrl.newBuilder();

        if (null != getDefaultUrlParams()) {
            ArrayMap<String, String> ps = getDefaultUrlParams();
            for (String k : ps.keySet()) {
                builder = builder.addQueryParameter(k, ps.get(k));
            }
        }

        if (api.requestMethod() == APIInterface.RequestMethod.GET) {
            ArrayMap<String, String> ps = api.requestParams().getRequestParams();
            for (String k : ps.keySet()) {
                builder = builder.addQueryParameter(k, ps.get(k));
            }
        }

        return builder.build();
    }

    /**
     * 针对每一个api的每次请求生成的tag
     * 根据api具体参数不同tag是不一样的，即，同一个api可以有多个tag
     *
     * @param apiInterface api信息
     * @return 此次请求的tag
     */
    private String generateApiTag(APIInterface apiInterface) {
        return MD5Util.generate(apiInterface.requestURL() + apiInterface.requestParams().toString());
    }
}
