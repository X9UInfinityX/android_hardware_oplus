package com.oplus.wrapper.window;

import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class SurfaceSyncGroup {
    private final android.window.SurfaceSyncGroup mTarget;

    public SurfaceSyncGroup(android.window.SurfaceSyncGroup surfaceSyncGroup) {
        this.mTarget = surfaceSyncGroup;
    }

    public void addSyncCompleteCallback(Executor executor, Runnable runnable) {
        this.mTarget.addSyncCompleteCallback(executor, runnable);
    }
}
