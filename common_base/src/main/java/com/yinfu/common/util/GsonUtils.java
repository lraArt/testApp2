package com.yinfu.common.util;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Android on 2018/5/5.
 */

public class GsonUtils {
    private static Gson gson;

    static {
        gson = new Gson();
    }

    private GsonUtils() {
    }

    public static <T> String toJson(T t) {
        if (t instanceof String) return (String) t;
        return gson.toJson(t);
    }

    public static <T> T parseObject(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static <T> T parseObject(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        List<T> list = null;
        list = gson.fromJson(json, new TypeToken<List<T>>() {
        }.getType());
        return list;
    }

//    /**
//     * 转成list中有map的
//     *
//     * @param gsonString
//     * @return
//     */
//    public static <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
//        List<Map<String, T>> list = null;
//        if (gson != null) {
//            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
//            }.getType());
//        }
//        return list;
//    }
//
//    /**
//     * 转成map的
//     *
//     * @param gsonString
//     * @return
//     */
//    public static <T> Map<String, T> GsonToMaps(String gsonString) {
//        Map<String, T> map = null;
//        if (gson != null) {
//            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
//            }.getType());
//        }
//        return map;
//}
}
