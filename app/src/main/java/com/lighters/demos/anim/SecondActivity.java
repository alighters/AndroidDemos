/*
 * Copyright (C) 2016 david.wei (lighters)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lighters.demos.anim;

import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import butterknife.BindView;
import com.lighters.demos.R;
import com.lighters.demos.app.base.BaseActivity;
import com.lighters.demos.util.ColorUtil;

/**
 * Created by david on 16/7/26.
 * Email: huangdiv5@gmail.com
 * GitHub: https://github.com/alighters
 */
public class SecondActivity extends BaseActivity {

    @BindView(R.id.view_second) View mViewSecond;

    Drawable mBackground;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_second;
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        //mBackground = getResources().getDrawable(R.drawable.background);
        //mBackground.setAlpha(1);
        //getWindow().setBackgroundDrawable(mBackground);
    }

    @Override
    protected void initData() {
        startAnim();
    }

    private void startAnim() {
        final int colorFrom = getResources().getColor(R.color.red);
        final int colorTo = getResources().getColor(R.color.blue);
        //ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        //colorAnimation.setDuration(3000); // milliseconds
        //colorAnimation.addListener(new AnimatorListenerAdapter() {
        //    @Override
        //    public void onAnimationEnd(Animator animation) {
        //        super.onAnimationEnd(animation);
        //        //mBackground.setAlpha(1);
        //        //getWindow().setBackgroundDrawable(mBackground);
        //    }
        //});
        //colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        //
        //    @Override
        //    public void onAnimationUpdate(ValueAnimator animator) {
        //        mViewSecond.setBackgroundColor((int) animator.getAnimatedValue());
        //    }
        //});
        //colorAnimation.start();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d("animtion_value:", animation.getAnimatedValue().toString());
                int color = ColorUtil.getInterColor(colorFrom, colorTo, (Float) animation.getAnimatedValue());
                mViewSecond.setBackgroundColor(color);
            }
        });
        valueAnimator.setDuration(3000); // milliseconds
        valueAnimator.start();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
