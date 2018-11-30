package com.yinfu.common.http;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * * 处理 Http 请求和响应结果的处理类
 * Created by Android on 2018/5/8.
 */

public interface GlobalHttpHandler {

    Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response);

    Request onHttpRequestBefore(Interceptor.Chain chain, Request request);

}
