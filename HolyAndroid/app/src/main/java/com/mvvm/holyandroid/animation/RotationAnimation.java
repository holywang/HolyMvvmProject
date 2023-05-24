package com.mvvm.holyandroid.animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * @author rg wang
 * created on  2023/5/15
 */
public class RotationAnimation extends Animation {

    private float centerX;
    private float centerY;
    private float startAngle;
    private float endAngle;
    private float startScaleX;
    private float endScaleX;
    private float startScaleY;
    private float endScaleY;

    public RotationAnimation(float centerX, float centerY, float startAngle, float endAngle, float startScaleX, float endScaleX, float startScaleY, float endScaleY) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
        this.startScaleX = startScaleX;
        this.endScaleX = endScaleX;
        this.startScaleY = startScaleY;
        this.endScaleY = endScaleY;
    }
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float angle = startAngle + (endAngle - startAngle) * interpolatedTime;
        float scaleX = startScaleX + (endScaleX - startScaleX) * interpolatedTime;
        float scaleY = startScaleY + (endScaleY - startScaleY) * interpolatedTime;
        t.getMatrix().setRotate(angle, centerX, centerY);
        t.getMatrix().postScale(scaleX, scaleY, centerX, centerY);
    }
}
