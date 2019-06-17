package com.liujie.loveyouapp.mvp.view;

import android.view.animation.Animation;
import android.view.animation.Transformation;

public class TrembleAnim extends Animation {
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        t.getMatrix().setTranslate(
                (float) Math.sin(interpolatedTime * 50) * 8,
                (float) Math.sin(interpolatedTime * 50) * 8
        );// 50越大频率越高，8越小振幅越小
        super.applyTransformation(interpolatedTime, t);
    }
}
