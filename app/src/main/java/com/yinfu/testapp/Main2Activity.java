package com.yinfu.testapp;

import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;

import com.yinfu.common.base.BaseActivity;
import com.yinfu.common.util.ArmsUtils;
import com.yinfu.common.util.LoggerUtils;
import com.yinfu.testapp.app.webp.WebPGiftLoader;
import com.yinfu.testapp.mvp.contract.MainContract;
import com.yinfu.testapp.mvp.presenter.MainPresenter;
import com.yinfu.testapp.widget.PeriscopeLayout;

import java.util.List;

import butterknife.BindView;

public class Main2Activity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.periscope)
    PeriscopeLayout periscope;
    @BindView(R.id.ll_gift)
    FrameLayout flGift;

    @Override
    protected int getLayoutId() {
        DisplayMetrics DM = getResources().getDisplayMetrics();
        LoggerUtils.e("Dpi:" + DM.densityDpi);
        return R.layout.activity_main2;
    }

    @Override
    protected void initView() {
        periscope.setCommonOnClickListener(new PeriscopeLayout.CommonOnClickListener() {
            @Override
            public void btn1OnClick() {
                ArmsUtils.makeText(Main2Activity.this, "btn1OnClick");
                WebPGiftLoader.loaderWebPImage(Main2Activity.this, flGift, "file:///android_asset/image200.webp");
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

//    @OnClick({R.id.btn_confirm, R.id.tv_content})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.btn_confirm:
////                ArmsUtils.makeText(this, "onViewClicked");
////                mPresenter.setData();
//                periscope.addHeart();
//                break;
//            case R.id.tv_content:
//                break;
//        }
//    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showMessage(@NonNull String message) {
    }

    @Override
    public void showToast(@NonNull String message) {
        ArmsUtils.makeText(this, message);
    }

    @Override
    public void setTextData(List<String> data) {
        showToast("setTextData666");
//        tvContent.setText(data.toString());
    }
}
