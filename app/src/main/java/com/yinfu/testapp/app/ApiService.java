package com.yinfu.testapp.app;

import com.yinfu.common.http.JsonResultModel;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiService {

    Observable<JsonResultModel<String>> getUser();

    Observable<JsonResultModel<String>> getBaseData();
    @Streaming //大文件时要加不然会OOM
    @GET
    Observable<ResponseBody> downloadFileWithDynamicUrl(@Url String fileUrl);
}
