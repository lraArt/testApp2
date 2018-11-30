package com.yinfu.testapp.app.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Android on 2018/5/7.
 */

public class CommonUtils {

    public static boolean isNetworkAvailable(Context c) {
        Context context = c.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (NetworkInfo aNetworkInfo : networkInfo) {
                    // 判断当前网络状态是否为连接状态
                    if (aNetworkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public static String convertStatusCode(int code) {
        String msg;
        if (code == 500) {
            msg = "请求失败，服务器发生错误" + code;
        } else if (code == 404) {
            msg = "请求失败，请求地址不存在" + code;
        } else if (code == 408) {
            msg = "请求超时，请稍后重试！" + code;
        } else if (code == 403) {
            msg = "请求失败，请求被服务器拒绝" + code;
        } else if (code == 307) {
            msg = "请求失败，请求被重定向到其他页面" + code;
        } else {
            if (code != 0) {
                msg = "请求失败，请稍后重试！" + code;
            } else {
                msg = "请求失败，请稍后重试！";
            }
        }
        return msg;
    }
}
