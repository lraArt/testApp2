package com.yinfu.common.http;

/**
 * Created by Android on 2018/5/7.
 */

public class ServerException extends RuntimeException {

    public ServerException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code;
    public String message;
}
