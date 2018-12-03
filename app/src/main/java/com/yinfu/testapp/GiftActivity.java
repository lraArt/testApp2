package com.yinfu.testapp;

import android.view.View;
import android.widget.FrameLayout;

import com.yinfu.common.base.BaseActivity;
import com.yinfu.common.mvp.BasePresenter;
import com.yinfu.testapp.app.webp.WebPGiftLoader;

import butterknife.BindView;

public class GiftActivity extends BaseActivity<BasePresenter> {

    @BindView(R.id.fl_gift)
    FrameLayout flGift;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gift;
    }

    @Override
    protected void initView() {
        WebPGiftLoader giftLoader = new WebPGiftLoader(flGift);
        giftLoader.loaderWebPImage(this, "file:///android_asset/frametime2001.webp");
        giftLoader.setOnFinishedListener(new WebPGiftLoader.OnFinishedListener() {
            @Override
            public void onFinished() {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

}
