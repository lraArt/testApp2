package com.yinfu.testapp.mvp.contract;

import com.yinfu.common.mvp.IModel;
import com.yinfu.common.mvp.IView;

import java.util.List;

public interface MainContract {

    interface View extends IView {
        void setTextData(List<String> data);
    }

    interface Model extends IModel {
        List<String> getMainData();
    }
}
