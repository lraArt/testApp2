package com.yinfu.testapp.app;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.view.accessibility.AccessibilityEvent;

import com.yinfu.common.util.LoggerUtils;

/**
 * Created by Android on 2018/1/3.
 */

public class MyAccessibilityService extends AccessibilityService {

    @SuppressLint("NewApi")
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        String packageName = event.getPackageName().toString();
        String className = event.getClassName().toString();
        if (!"android.widget.ListView".equals(className) && !"android.widget.TextView".equals(className)) {
            LoggerUtils.e(packageName + "----------" + className);
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onInterrupt() {

    }
}
