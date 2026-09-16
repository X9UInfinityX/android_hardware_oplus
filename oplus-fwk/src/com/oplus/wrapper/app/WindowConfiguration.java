package com.oplus.wrapper.app;

import android.graphics.Rect;

public class WindowConfiguration {
    public static final int WINDOWING_MODE_FULLSCREEN = 1;
    @Deprecated
    public static final int WINDOWING_MODE_SPLIT_SCREEN_PRIMARY = 0;
    @Deprecated
    public static final int WINDOWING_MODE_SPLIT_SCREEN_SECONDARY = 0;
    public static final int WINDOWING_MODE_UNDEFINED = 0;
    public static final int WINDOWING_MODE_PINNED = 2;
    public static final int WINDOWING_MODE_FREEFORM = 5;
    public static final int WINDOWING_MODE_MULTI_WINDOW = 6;
    public static final int ROTATION_UNDEFINED = -1;
    public static final int ACTIVITY_TYPE_UNDEFINED = 0;
    public static final int ACTIVITY_TYPE_STANDARD = 1;
    public static final int ACTIVITY_TYPE_HOME = 2;
    public static final int ACTIVITY_TYPE_RECENTS = 3;

    private final android.app.WindowConfiguration mTarget;

    public WindowConfiguration() {
        mTarget = new android.app.WindowConfiguration();
    }

    public WindowConfiguration(android.app.WindowConfiguration windowConfiguration) {
        mTarget = windowConfiguration;
    }

    public android.app.WindowConfiguration getmWindowConfiguration() {
        return mTarget;
    }

    public Rect getMaxBounds() {
        return mTarget.getMaxBounds();
    }

    public int getActivityType() {
        return mTarget.getActivityType();
    }

    public Rect getAppBounds() {
        return mTarget.getAppBounds();
    }

    public Rect getBounds() {
        return mTarget.getBounds();
    }

    public int getRotation() {
        return mTarget.getRotation();
    }

    public int getWindowingMode() {
        return mTarget.getWindowingMode();
    }

    public void setAppBounds(Rect rect) {
        mTarget.setAppBounds(rect);
    }

    public void setWindowingMode(int windowingMode) {
        mTarget.setWindowingMode(windowingMode);
    }
}
