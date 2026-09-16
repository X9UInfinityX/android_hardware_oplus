/*
 * Copyright (C) 2026 The Infinity-X Project
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.oplus.debug;

import android.os.SystemProperties;
import android.util.Log;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;

/**
 * Compatibility implementation of the ColorOS input logger.
 *
 * Proprietary OPlus components link against this class even when input debugging is disabled.
 * Logging defaults to the stock error-only level and can be changed with
 * {@code persist.sys.input_java_level}.
 */
public final class InputLog {
    public static final int LOG_LEVEL_DISABLE = 0;
    public static final int LOG_LEVEL_DEBUG = 1;
    public static final int LOG_LEVEL_VERBOSE = 2;
    public static final int LOG_LEVEL_INFO = 3;
    public static final int LOG_LEVEL_WARN = 4;
    public static final int LOG_LEVEL_ERROR = 5;

    private static final String TAG = "InputLog";
    private static final String LEVEL_PROPERTY = "persist.sys.input_java_level";
    private static volatile int sLevel = readLevel();

    private InputLog() {}

    private static int readLevel() {
        return SystemProperties.getInt(LEVEL_PROPERTY, LOG_LEVEL_ERROR);
    }

    public static void dynamicLog(int level) {
        SystemProperties.set(LEVEL_PROPERTY, Integer.toString(level));
        sLevel = level;
    }

    public static void updateLogLevel() {
        sLevel = readLevel();
    }

    public static int getCurrentLogSwitchValue() {
        return readLevel();
    }

    public static boolean isLevelDebug() {
        return sLevel == LOG_LEVEL_DEBUG;
    }

    public static boolean isLevelVerbose() {
        return sLevel >= LOG_LEVEL_DEBUG && sLevel <= LOG_LEVEL_VERBOSE;
    }

    public static boolean isVolumeKey(int keyCode) {
        return keyCode == KeyEvent.KEYCODE_VOLUME_UP
                || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN
                || keyCode == KeyEvent.KEYCODE_VOLUME_MUTE;
    }

    public static boolean isVerboseAction(int action) {
        return action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_UP
                || action == MotionEvent.ACTION_CANCEL;
    }

    public static boolean canBePrinted(InputEvent event) {
        if (isLevelDebug()) return true;
        if (!isLevelVerbose()) return false;
        if (event instanceof KeyEvent) return true;
        return event instanceof MotionEvent
                && isVerboseAction(((MotionEvent) event).getActionMasked());
    }

    // These hooks are part of the stock ABI.  Keeping them as no-ops at normal log levels avoids
    // proprietary callers crashing without adding input-dispatch overhead.
    public static void debugInputEventStart(String tag, InputEvent event,
            WindowManager.LayoutParams attrs) {}
    public static void debugInputEventEnqueue(String tag, InputEvent event,
            boolean immediately, boolean scheduled) {}
    public static void debugInputStageDeliverd(String tag, int flag, InputEvent event,
            String stage, String detail) {}
    public static void debugEventHandled(String tag, InputEvent event, String detail) {}
    public static void debugInputEventFinished(String tag, int flag, InputEvent event,
            WindowManager.LayoutParams attrs) {}

    public static void d(String tag, String msg) {
        if (isLevelDebug()) Log.d(TAG, tag + " : " + msg);
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (isLevelDebug()) Log.d(TAG, tag + " : " + msg, tr);
    }

    public static void v(String tag, String msg) {
        if (isLevelVerbose()) Log.v(TAG, tag + " : " + msg);
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (isLevelVerbose()) Log.v(TAG, tag + " : " + msg, tr);
    }

    public static void i(String tag, String msg) {
        if (sLevel >= LOG_LEVEL_DEBUG && sLevel <= LOG_LEVEL_INFO) {
            Log.i(TAG, tag + " : " + msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (sLevel >= LOG_LEVEL_DEBUG && sLevel <= LOG_LEVEL_INFO) {
            Log.i(TAG, tag + " : " + msg, tr);
        }
    }

    public static void w(String tag, String msg) {
        if (sLevel >= LOG_LEVEL_DEBUG && sLevel <= LOG_LEVEL_WARN) {
            Log.w(TAG, tag + " : " + msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (sLevel >= LOG_LEVEL_DEBUG && sLevel <= LOG_LEVEL_WARN) {
            Log.w(TAG, tag + " : " + msg, tr);
        }
    }

    public static void e(String tag, String msg) {
        if (sLevel != LOG_LEVEL_DISABLE) Log.e(TAG, tag + " : " + msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (sLevel != LOG_LEVEL_DISABLE) Log.e(TAG, tag + " : " + msg, tr);
    }

    public static void wtf(String tag, String msg) {
        if (sLevel != LOG_LEVEL_DISABLE) Log.wtf(TAG, tag + " : " + msg);
    }

    public static void wtf(String tag, String msg, Throwable tr) {
        if (sLevel != LOG_LEVEL_DISABLE) Log.wtf(TAG, tag + " : " + msg, tr);
    }
}
