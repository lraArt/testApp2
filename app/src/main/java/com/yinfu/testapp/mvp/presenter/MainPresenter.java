package com.yinfu.testapp.mvp.presenter;

import android.content.Context;

import com.yinfu.common.http.JsonResultModel;
import com.yinfu.common.http.ServerException;
import com.yinfu.common.mvp.BasePresenter;
import com.yinfu.common.util.LoggerUtils;
import com.yinfu.testapp.app.ApiService;
import com.yinfu.testapp.app.ErrorHandleSubscriber;
import com.yinfu.testapp.app.RetrofitProvider;
import com.yinfu.testapp.app.util.FileUtils;
import com.yinfu.testapp.mvp.contract.MainContract;
import com.yinfu.testapp.mvp.model.MainModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {

    public MainPresenter(MainContract.View rootView) {
        super(new MainModel(), rootView);
    }

    public void setData() {
        mView.showToast("aaaa");
        mView.setTextData(mModel.getMainData());
    }

    public void getUser() {

    }

    public void downFile(Context context) {
        Retrofit retrofit = RetrofitProvider.create();
        ApiService apiService = retrofit.create(ApiService.class);
        final String filePath = FileUtils.getCachePath(context) + "image200.webp";
        LoggerUtils.e(filePath);
        apiService.downloadFileWithDynamicUrl("https://raw.githubusercontent.com/mochengsheng/testApp2/master/app/src/main/assets/image200.webp")
                .subscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, InputStream>() {

                    @Override
                    public InputStream apply(ResponseBody responseBody) throws Exception {
                        return responseBody.byteStream();
                    }
                }).observeOn(Schedulers.computation())
                .map(new Function<InputStream, File>() {
                    @Override
                    public File apply(InputStream inputStream) throws Exception {
                        return writeFile(inputStream, filePath);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<File>() {
                    @Override
                    public void onResult(File result) {
                        mView.showLoading();
                    }

                    @Override
                    public void onError(String msg) {

                    }
                });
    }

    /**
     * 登录并获取基础数据
     *
     * @param map
     */
    private void login(Map<String, String> map) {
        Retrofit retrofit = RetrofitProvider.create();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getUser()
                .subscribeOn(Schedulers.io())//在线程请求网络
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//需要回到主线程处理结果
                .flatMap(new Function<JsonResultModel<String>, ObservableSource<JsonResultModel<String>>>() {
                    @Override
                    public ObservableSource<JsonResultModel<String>> apply(JsonResultModel<String> result) throws Exception {
                        if (result.isSuccess()) {
//                            CacheUtils.saveLoginVO(result.getData());
                            //处理完结果需要回到线程请求网络获取及基础数据
                            return RetrofitProvider.create().create(ApiService.class).getBaseData().subscribeOn(Schedulers.io());
                        } else {
                            return Observable.error(new ServerException(result.getCode(), result.getMsg()));
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//需要回到主线程处理结果
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                })
                .subscribe(new ErrorHandleSubscriber<JsonResultModel<String>>() {

                    @Override
                    public void onResult(JsonResultModel<String> result) {

                    }

                    @Override
                    public void onError(String msg) {
                        mView.showMessage(msg);
                    }
                });
    }


    /**
     * 将输入流写入文件
     *
     * @param inputString
     * @param filePath
     */
    private File writeFile(InputStream inputString, String filePath) {

        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);

            byte[] b = new byte[1024];

            int len;
            while ((len = inputString.read(b)) != -1) {
                fos.write(b, 0, len);
            }
            inputString.close();
            fos.close();
            return file;
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
        return null;
    }
}
