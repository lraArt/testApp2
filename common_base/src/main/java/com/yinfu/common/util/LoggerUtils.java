package com.yinfu.common.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Android on 2018/5/5.
 */

public class LoggerUtils {

    private static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static final String TAG = "app_demo_log";

    public static void name(boolean debug) {
        isDebug = debug;
    }

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug) {
            if (msg.length() > 3000) {
                for (int i = 0; i < msg.length(); i += 3000) {
                    if (i + 3000 < msg.length())
                        Log.i(TAG + i, msg.substring(i, i + 3000));
                    else
                        Log.i(TAG + i, msg.substring(i, msg.length()));
                }
            } else
                Log.i(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (isDebug) {
            if (msg.length() > 3000) {
                for (int i = 0; i < msg.length(); i += 3000) {
                    if (i + 3000 < msg.length())
                        Log.d(TAG + i, msg.substring(i, i + 3000));
                    else
                        Log.d(TAG + i, msg.substring(i, msg.length()));
                }
            } else
                Log.d(TAG, msg);
        }
//        Log.d(TAG, msg);
    }

    public static void dJsonFormat(String message) {
        if (isDebug && !TextUtils.isEmpty(message)) {
            Log.d(TAG, StringUtils.format(StringUtils.convertUnicode(message)));
        }
    }

    public static void e(String msg) {
        if (isDebug)
            if (msg.length() > 3000) {
                for (int i = 0; i < msg.length(); i += 3000) {
                    if (i + 3000 < msg.length())
                        Log.e(TAG + i, msg.substring(i, i + 3000));
                    else
                        Log.e(TAG + i, msg.substring(i, msg.length()));
                }
            } else
                Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            if (msg.length() > 3000) {
                for (int i = 0; i < msg.length(); i += 3000) {
                    if (i + 3000 < msg.length())
                        Log.d(tag + i, msg.substring(i, i + 3000));
                    else
                        Log.d(tag + i, msg.substring(i, msg.length()));
                }
            } else {
                Log.d(tag, msg);
            }
        }
    }

    public static void w(String tag, String msg) {
        if (isDebug)
            Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }

}
