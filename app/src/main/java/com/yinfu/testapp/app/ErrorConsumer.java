package com.yinfu.testapp.app;

import android.app.Activity;
import android.content.Context;
import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.yinfu.common.http.ServerException;
import com.yinfu.common.util.ArmsUtils;
import com.yinfu.testapp.app.util.CommonUtils;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

/**
 * 错误统一处理
 * Created by Android on 2018/5/7.
 */

public class ErrorConsumer implements Consumer<Throwable> {

    private Context context;

    public ErrorConsumer(Context context) {
        this.context = context;
    }

    @Override
    public void accept(Throwable throwable) throws Exception {
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
        if (context instanceof Activity) {
//            DialogUtils.showDialog(context, msg);
        } else {
            ArmsUtils.makeText(context, msg);
        }
    }

}
