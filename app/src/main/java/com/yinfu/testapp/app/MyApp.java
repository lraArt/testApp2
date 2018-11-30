package com.yinfu.testapp.app;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.ResourceDecoder;
import com.yinfu.common.base.BaseApplication;
import com.yinfu.testapp.app.webp.WebpBytebufferDecoder;
import com.yinfu.testapp.app.webp.WebpResourceDecoder;

import java.io.InputStream;
import java.nio.ByteBuffer;

public class MyApp extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        // webp support
        ResourceDecoder decoder = new WebpResourceDecoder(this);
        ResourceDecoder byteDecoder = new WebpBytebufferDecoder(this);
        // use prepend() avoid intercept by default decoder
        Glide.get(this).getRegistry()
                .prepend(InputStream.class, Drawable.class, decoder)
                .prepend(ByteBuffer.class, Drawable.class, byteDecoder);
    }
}
