package com.yinfu.testapp.app;

import com.yinfu.common.http.JsonResultModel;

import io.reactivex.Observable;

public interface ApiService {

    Observable<JsonResultModel<String>> getUser();

    Observable<JsonResultModel<String>> getBaseData();
}
