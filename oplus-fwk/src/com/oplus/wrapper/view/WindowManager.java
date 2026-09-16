package com.oplus.wrapper.view;

/** Stable ColorOS names for hidden WindowManager transition constants. */
public final class WindowManager {
    public static final int TRANSIT_OPEN = 1;
    public static final int TRANSIT_CLOSE = 2;
    public static final int TRANSIT_TO_FRONT = 3;
    public static final int TRANSIT_TO_BACK = 4;
    public static final int TRANSIT_CHANGE = 6;
    public static final int TRANSIT_OLD_NONE = 0;

    private WindowManager() {}

    public static final class LayoutParams {
        public static final int INVALID_WINDOW_TYPE = -1;
        public static final int TYPE_DOCK_DIVIDER = 2034;
        public static final int TYPE_NAVIGATION_BAR = 2019;

        private LayoutParams() {}
    }
}
