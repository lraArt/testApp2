package com.yinfu.testapp.app;


import com.yinfu.common.http.GlobalHttpHandler;
import com.yinfu.common.util.LoggerUtils;

import org.json.JSONObject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求前、请求后的回调，可做一些操作
 * Created by Android on 2018/5/8.
 */

public class GlobalHttpHandlerImpl implements GlobalHttpHandler {

    private static final String TAG = "retrofit_response";

    private final static int AUTHORIZE_NUMBER = 3;//没登录时重连次数

    private int requestNumber = 0;

    @Override
    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
        try {
            JSONObject object = new JSONObject(httpResult);
            boolean success = object.getBoolean("success");
            String code = object.getString("code");
            if (!success && "U1008".equals(code) && requestNumber < AUTHORIZE_NUMBER) {//最多重连三次，三次后直接返回数据提示抛异常
                LoggerUtils.d(TAG, "重新登录requestNumber：" + (requestNumber + 1));
                requestNumber++;
                reLogin();//重新登录后重新请求需要传入当前GlobalHttpHandlerImpl，否则requestNumber一直为0，无限循环
                return RetrofitProvider.getOkClient(this).newCall(chain.request().newBuilder().build()).execute();
            } else {
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return response;
        }
    }

    @Override
    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
        return request;
    }

    private void reLogin() {
//        LoginVO loginVO = CacheUtils.getLoginVO();
//        if (loginVO != null) {
//            try {
//                Map<String, String> params = new HashMap<>();
//                params.put("uid", loginVO.getUser().getId());
//                params.put("authorizedCode", loginVO.getAuthorizedCode());
//                retrofit2.Response<JsonResultModel<LoginVO>> response = RetrofitProvider.create().create(UserService.class).userAuthorize1(params).execute();
//                if (response.isSuccessful()) {
//                    JsonResultModel<LoginVO> newLoginVO = response.body();
//                    if (newLoginVO != null)
//                        CacheUtils.updateLoginVO(newLoginVO.getData());
//                    DataHelper.saveCookie(response.headers().values("Set-Cookie"));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

}
