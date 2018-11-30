package com.yinfu.testapp.app.webp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.yinfu.common.util.LoggerUtils;

public class WebPGiftLoader implements RequestListener<Drawable>, WebpDrawable.OnFinishedListener {


    public static void loaderWebPImage(Context context, ViewGroup container, String url) {
        ImageView imageView = new ImageView(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        imageView.setLayoutParams(params);
        container.addView(imageView);
        RequestOptions options = new RequestOptions().override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).diskCacheStrategy(DiskCacheStrategy.DATA);
        Glide.with(context).load(url).apply(options)
                .transition(new DrawableTransitionOptions().crossFade(200))
                .listener(new WebPGiftLoader(container)).into(imageView);
    }

    private ViewGroup container;

    private WebPGiftLoader(ViewGroup container) {
        this.container = container;
    }

    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
        return false;
    }

    @Override
    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
        // 计算动画时长
        if (resource instanceof WebpDrawable) {
            LoggerUtils.e("onResourceReady" + "-----true" + isFirstResource);
            WebpDrawable webpDrawable = (WebpDrawable) resource;
            webpDrawable.startFromFirstFrame();
            webpDrawable.setLoopCount(1);
            webpDrawable.setOnFinishedListener(WebPGiftLoader.this);
        }
        LoggerUtils.e("onResourceReady");
        return false;
    }

    @Override
    public void onFinished() {
        LoggerUtils.e("OnFinishedListener" + "-----onFinished" + Thread.currentThread());
        container.removeAllViews();
    }
}
