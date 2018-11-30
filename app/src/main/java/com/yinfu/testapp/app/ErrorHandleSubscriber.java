package com.yinfu.testapp.app;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.yinfu.common.http.ServerException;
import com.yinfu.testapp.app.util.CommonUtils;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public abstract class ErrorHandleSubscriber<T> implements Observer<T> {

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {
        addSubscribe(d);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        String msg = "未知错误";
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            msg = CommonUtils.convertStatusCode(httpException.code());
        } else if (throwable instanceof ServerException) {
            ServerException error = (ServerException) throwable;
            msg = error.message;
        } else if (throwable instanceof UnknownHostException) {
            msg = "网络不可用，请检查网络是否连接！";
        } else if (throwable instanceof SocketTimeoutException) {
            msg = "请求网络超时，请稍后再试！";
        } else if (throwable instanceof JsonParseException
                || throwable instanceof ParseException
                || throwable instanceof JSONException) {
            msg = "数据解析错误，请稍后再试！";
        } else if (throwable instanceof ConnectException) {
            msg = "连接服务器失败，请稍后再试！";
        }
        onError(msg);
    }

    @Override
    public void onNext(T t) {
        onResult(t);
    }

    public abstract void onResult(T result);

    public abstract void onError(String msg);

    public void addSubscribe(Disposable d) {

    }

}
