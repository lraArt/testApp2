package com.yinfu.common.http;

import com.yinfu.common.util.DataHelper;
import com.yinfu.common.util.LoggerUtils;
import com.yinfu.common.util.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpMethod;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by Android on 2018/5/5.
 */

public class CommonInterceptor implements Interceptor {

    private GlobalHttpHandler mHandler;

    public CommonInterceptor(GlobalHttpHandler mHandler) {
        this.mHandler = mHandler;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder().build();
        String httpParams = parseParams(request);
        if (mHandler != null) {
            request = mHandler.onHttpRequestBefore(chain, request);
        }
        Response response = chain.proceed(createRequest(request, httpParams));
        DataHelper.saveCookie(response.headers("Set-Cookie"));
        Response newResponse;
        String httpResult = parseContent(response);
        LoggerUtils.d("retrofit_response", "请求url:" + response.request().url().toString() + (StringUtils.isNotEmpty(httpParams) ? "\n参数:" + httpParams : "") + "\n" + httpResult + "\nend>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>end");
        if (response.body() != null) {
            newResponse = response.newBuilder().body(ResponseBody.create(response.body().contentType(), httpResult)).build();
        } else {
            newResponse = response;
        }
        if (mHandler != null)
            return mHandler.onHttpResultResponse(httpResult, chain, newResponse);
        return newResponse;
    }

    /**
     * 解析服务器响应的内容
     *
     * @param response
     * @return String
     */
    private String parseContent(Response response) throws IOException {
        ResponseBody responseBody = response.newBuilder().build().body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Buffer clone = buffer.clone();
        MediaType contentType = responseBody.contentType();
        String subtype = "";
        Charset charset = Charset.forName("UTF-8");
        if (contentType != null) {
            subtype = contentType.subtype();
            charset = contentType.charset(charset);
        }
        if ("wxt".equals(subtype)) {
            byte[] b = clone.readByteArray();
            AES aes = new AES();
            byte[] a = aes.decrypt(b);
            return new String(a);
        } else {
            return clone.readString(charset);
        }
    }

    /**
     * 解析请求服务器的请求参数
     *
     * @param request
     * @return String
     * @throws IOException
     */
    private static String parseParams(Request request) throws IOException {
        RequestBody body = request.newBuilder().build().body();
        if (body == null) return "";
        Buffer requestbuffer = new Buffer();
        body.writeTo(requestbuffer);
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = body.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        return requestbuffer.readString(charset);
    }

    private Request createRequest(Request request, String httpParams) {
        Request.Builder builder = request.newBuilder();
        RequestBody requestBody = request.body();
        if (HttpMethod.permitsRequestBody(request.method()) && requestBody != null) {
            MediaType contentType = requestBody.contentType();
            String subtype = "";
            if (contentType != null) {
                subtype = contentType.subtype();
            }
            if ("wxt".equals(subtype)) {
                AES aes = new AES();
                byte[] a = aes.encrypt(httpParams.getBytes());
                requestBody = RequestBody.create(contentType, a);
            } else {
                requestBody = RequestBody.create(contentType, httpParams);
            }
            builder.method(request.method(), requestBody);
        }
        List<String> list = DataHelper.getCookies();
        for (String s : list) {
            builder.addHeader("cookie", s);//统一添加这里有多少就传多少（多cookie）
        }
        return builder.build();
    }
}
