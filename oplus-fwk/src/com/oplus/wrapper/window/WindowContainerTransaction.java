package com.oplus.wrapper.window;

import android.os.Parcelable;

/** Wrapper for a shell window-container transaction. */
public final class WindowContainerTransaction {
    private final android.window.WindowContainerTransaction mTarget;

    public WindowContainerTransaction() {
        mTarget = new android.window.WindowContainerTransaction();
    }

    public WindowContainerTransaction(android.window.WindowContainerTransaction target) {
        mTarget = target;
    }

    public android.window.WindowContainerTransaction get() {
        return mTarget;
    }

    public Parcelable getWindowContainerTransaction() {
        return mTarget;
    }
}
