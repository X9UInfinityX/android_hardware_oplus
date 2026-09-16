package com.oplus.wrapper.graphics.drawable;

import android.graphics.Xfermode;

/* loaded from: classes2.dex */
public class GradientDrawable {
    private final android.graphics.drawable.GradientDrawable mGradientDrawable;

    public static void setXfermode(android.graphics.drawable.GradientDrawable target, Xfermode mode) {
        target.setXfermode(mode);
    }

    public GradientDrawable(android.graphics.drawable.GradientDrawable gradientDrawable) {
        this.mGradientDrawable = gradientDrawable;
    }

    public void setXfermode(Xfermode mode) {
        this.mGradientDrawable.setXfermode(mode);
    }
}
