package com.yinfu.common.http;

import com.yinfu.common.util.GsonUtils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * Created by Android on 2018/5/5.
 */

public class JsonResultModel<T> implements Serializable {

    private String code;
    private String msg;
    private T data;
    private boolean success;

    public JsonResultModel() {
    }

    public JsonResultModel(boolean success) {
        this.success = true;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static <T> JsonResultModel<T> fromJson(String json, Type clazz) {
        Type objectType = type(JsonResultModel.class, clazz);
        return GsonUtils.parseObject(json, objectType);
    }

    protected static ParameterizedType type(final Class<?> raw, final Type... type) {
        return new ParameterizedType() {
            @Override
            public Type getRawType() {
                return raw;
            }

            @Override
            public Type[] getActualTypeArguments() {
                return type;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }

    @Override
    public String toString() {
        return "JsonResultModel{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", success=" + success +
                '}';
    }

}
