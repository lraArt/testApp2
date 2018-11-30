package com.yinfu.testapp.app;


import com.yinfu.common.http.CommonInterceptor;
import com.yinfu.common.http.GlobalHttpHandler;
import com.yinfu.common.util.DataHelper;
import com.yinfu.common.util.StringUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.yinfu.test.common_base.BuildConfig.LOG_DEBUG;


/**
 * 提供Retrofit和OkHttpClient
 * Created by Android on 2018/5/5.
 */

public class RetrofitProvider {

    private static final String BASE_URL = "http://aliyizhan.com";//写上你的baseurl

    public static Retrofit create() {
        String baseUrl = "http://" + DataHelper.getStringSF("base_url_save");
        if (StringUtils.isEmpty(baseUrl)) baseUrl = BASE_URL;
        return new Retrofit.Builder().baseUrl(baseUrl)
                .client(getOkClient())
                .addConverterFactory(GsonConverterFactory.create())//加入自定义的转换器CustomConverterFactory
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 获取OkHttpClient
     *
     * @return OkHttpClient
     */
    private static OkHttpClient getOkClient() {
        return getOkClient(null);
    }

    static OkHttpClient getOkClient(GlobalHttpHandler mHandler) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(120, TimeUnit.SECONDS);
        builder.connectTimeout(120, TimeUnit.SECONDS);
        builder.writeTimeout(120, TimeUnit.SECONDS);
        if (LOG_DEBUG) {//打印请求日志
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        if (mHandler == null) {
            mHandler = new GlobalHttpHandlerImpl();
        }
        builder.addInterceptor(new CommonInterceptor(mHandler));//拦截器
        return builder.build();
    }
}
