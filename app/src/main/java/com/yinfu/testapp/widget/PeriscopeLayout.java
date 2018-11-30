/*
 * Copyright (C) 2015, 程序亦非猿
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yinfu.testapp.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yinfu.common.util.LoggerUtils;
import com.yinfu.testapp.R;

import java.util.Random;


public class PeriscopeLayout extends RelativeLayout {

    private Interpolator line = new LinearInterpolator();//线性

    private Interpolator accdec = new AccelerateDecelerateInterpolator();//先加速后减速

    private Interpolator[] interpolators;


    private LayoutParams lp;

    private Drawable[] drawables;


    private int dHeight;

    private int dWidth;

    private Button btnTitle;
    private Button btnTitle1;


    public PeriscopeLayout(Context context) {
        super(context);
        init();
    }

    public PeriscopeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PeriscopeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PeriscopeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        //初始化显示的图片
        drawables = new Drawable[1];
        Drawable red = getResources().getDrawable(R.mipmap.gift);
        drawables[0] = red;
        //获取图的宽高 用于后面的计算
        //注意 我这里3张图片的大小都是一样的,所以我只取了一个
        dHeight = red.getIntrinsicHeight();
        dWidth = red.getIntrinsicWidth();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_chat_room_header, this);
        btnTitle = view.findViewById(R.id.tv_anchor_name);
        btnTitle1 = view.findViewById(R.id.tv_anchor_name1);
        btnTitle.setText("确定");
        //底部 并且 水平居中
        lp = new LayoutParams(dWidth, dHeight);
//        lp.addRule(CENTER_HORIZONTAL, TRUE);//这里的TRUE 要注意 不是true
//        lp.addRule(BELOW, R.id.tv_anchor_name);
//        lp.addRule(ALIGN_PARENT_BOTTOM, TRUE);

        // 初始化插补器
        interpolators = new Interpolator[2];
        interpolators[0] = line;
        interpolators[1] = accdec;

        btnTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addHeart(btnTitle, btnTitle1);
            }
        });
        btnTitle1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commonOnClickListener != null) {
                    commonOnClickListener.btn1OnClick();
                }
//                addHeart(btnTitle1, btnTitle);
            }
        });
//        interpolators[1] = acc;
//        interpolators[2] = dce;
//        interpolators[3] = accdec;

    }


    private PointF pointF = new PointF();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        pointF.x = event.getX();
        pointF.y = event.getY();
        return super.onTouchEvent(event);
    }


    private float fromX = 0f;
    private float fromY = 0f;
    private float toX = 0f;
    private float toY = 0f;

    /**
     * 1. AnimatorSet.playSequentially() 函数里面干了些啥。(动画顺序播放)
     * <p>
     * 2. AnimatorSet.playTogether() 函数里面干了些啥。（动画同时播放）
     */

    public void addHeart(View fromView, View toView) {
        LoggerUtils.e("fromView.getX():" + fromView.getX() + "---fromView.getY()" + fromView.getY() + "-----toView.getX()" + toView.getX() + "-------toView.getY()" + toView.getY());
        fromX = fromView.getX() + fromView.getWidth() / 2;
        fromY = fromView.getY() + fromView.getHeight() / 2;
        toX = toView.getX() + toView.getWidth() / 2;
        toY = toView.getY() + toView.getHeight() / 2;
        LoggerUtils.e(fromX + "---" + fromY + "----" + toX + "-----" + toY);
        ImageView imageView = new ImageView(getContext());
        //随机选一个
        imageView.setImageDrawable(drawables[0]);
        imageView.setLayoutParams(lp);
        addView(imageView);
        Animator set = getAnimator(imageView);
        set.addListener(new AnimEndListener(imageView));
        set.start();
    }

    private Random random = new Random();

    private Animator getAnimator(View target) {
        AnimatorSet finalSet = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, 3f, 2f, 3f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, 3f, 2f, 3f);
        AnimatorSet enter = new AnimatorSet();
        enter.setDuration(2000);
        enter.playTogether(scaleX, scaleY);
        enter.setTarget(target);
//        finalSet.playSequentially(enterAnimator);

        finalSet.playSequentially(getSportsAnimator1(target), enter, getSportsAnimator2(target));

        finalSet.setInterpolator(interpolators[random.nextInt(2)]);
        finalSet.setTarget(target);
        return finalSet;
    }

    /**
     * 出现动画，从小变大
     *
     * @param target
     * @return
     */
    private AnimatorSet getEnterAnimator(final View target) {
//        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.ALPHA, 0.2f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, 0.5f, 3f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, 0.5f, 3f);
//        scaleY.setRepeatCount(ValueAnimator.INFINITE);
        AnimatorSet enter = new AnimatorSet();
        enter.setDuration(1000);
        enter.setInterpolator(new LinearInterpolator());
        enter.playTogether(scaleX, scaleY);
        enter.setTarget(target);
        return enter;
    }

    /**
     * 结束动画，从大变小
     *
     * @param target
     * @return
     */
    private AnimatorSet getOutAnimator(final View target) {
//        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.ALPHA, 1f, 0.2f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, 3f, 0.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, 3f, 0.5f);
        AnimatorSet enter = new AnimatorSet();
        enter.setDuration(1000);
        enter.setInterpolator(new LinearInterpolator());
        enter.playTogether(scaleX, scaleY);
        enter.setTarget(target);
        return enter;
    }

    private AnimatorSet getSportsAnimator1(final View target) {
        ObjectAnimator sportsX = ObjectAnimator.ofFloat(target, View.TRANSLATION_X, fromX, getWidth() / 2);
        ObjectAnimator sportsY = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, fromY, getHeight() / 3);
        AnimatorSet sports = new AnimatorSet();
        sports.setInterpolator(new LinearInterpolator());
        sports.setDuration(2000);
        sports.playTogether(getEnterAnimator(target), sportsX, sportsY);
        sports.setTarget(target);
        return sports;
    }

    private AnimatorSet getSportsAnimator2(final View target) {
        ObjectAnimator sportsX = ObjectAnimator.ofFloat(target, View.TRANSLATION_X, getWidth() / 2, toX);
        ObjectAnimator sportsY = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, getHeight() / 3, toY);
        AnimatorSet sports = new AnimatorSet();
        sports.setInterpolator(new LinearInterpolator());
        sports.setDuration(2000);
        sports.playTogether(getOutAnimator(target), sportsX, sportsY);
        sports.setTarget(target);
        return sports;
    }

    private class AnimEndListener extends AnimatorListenerAdapter {

        private View target;

        public AnimEndListener(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            //因为不停的add 导致子view数量只增不减,所以在view动画结束后remove掉
            removeView((target));
        }
    }

    private CommonOnClickListener commonOnClickListener;

    public void setCommonOnClickListener(CommonOnClickListener commonOnClickListener) {
        this.commonOnClickListener = commonOnClickListener;
    }

    public interface CommonOnClickListener {
        void btn1OnClick();
    }
}
