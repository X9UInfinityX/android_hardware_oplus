package com.oplus.wrapper.window;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Parcelable;
import android.view.SurfaceControl;
import java.util.ArrayList;
import java.util.List;

/** ColorOS ABI wrapper around the platform shell transition model. */
public final class TransitionInfo {
    public static final int FLAG_NONE = android.window.TransitionInfo.FLAG_NONE;
    public static final int FLAG_SHOW_WALLPAPER =
            android.window.TransitionInfo.FLAG_SHOW_WALLPAPER;
    public static final int FLAG_IS_WALLPAPER =
            android.window.TransitionInfo.FLAG_IS_WALLPAPER;
    public static final int FLAG_TRANSLUCENT =
            android.window.TransitionInfo.FLAG_TRANSLUCENT;
    public static final int FLAG_STARTING_WINDOW_TRANSFER_RECIPIENT =
            android.window.TransitionInfo.FLAG_STARTING_WINDOW_TRANSFER_RECIPIENT;
    public static final int FLAG_IN_TASK_WITH_EMBEDDED_ACTIVITY =
            android.window.TransitionInfo.FLAG_IN_TASK_WITH_EMBEDDED_ACTIVITY;
    public static final int FLAG_WILL_IME_SHOWN =
            android.window.TransitionInfo.FLAG_WILL_IME_SHOWN;
    public static final int FLAG_MOVED_TO_TOP =
            android.window.TransitionInfo.FLAG_MOVED_TO_TOP;
    public static final int FLAG_FIRST_CUSTOM =
            android.window.TransitionInfo.FLAG_FIRST_CUSTOM;

    private final android.window.TransitionInfo mTarget;

    public TransitionInfo(android.window.TransitionInfo target) {
        mTarget = target;
    }

    public android.window.TransitionInfo get() {
        return mTarget;
    }

    public List<Change> getChanges() {
        ArrayList<Change> result = new ArrayList<>(mTarget.getChanges().size());
        for (android.window.TransitionInfo.Change change : mTarget.getChanges()) {
            result.add(new Change(change));
        }
        return result;
    }

    public int getFlags() {
        return mTarget.getFlags();
    }

    public Change getChange(WindowContainerToken token) {
        if (token == null || token.get() == null) {
            return null;
        }
        android.window.TransitionInfo.Change change = mTarget.getChange(token.get());
        return change == null ? null : new Change(change);
    }

    public int getType() {
        return mTarget.getType();
    }

    public static boolean isIndependent(Change change, TransitionInfo info) {
        return android.window.TransitionInfo.isIndependent(change.mTarget, info.mTarget);
    }

    public Root getRoot(int index) {
        return new Root(mTarget.getRoot(index));
    }

    public SurfaceControl getRootLeash() {
        return mTarget.getRootLeash();
    }

    public int findRootIndex(int displayId) {
        return mTarget.findRootIndex(displayId);
    }

    public void releaseAllSurfaces() {
        mTarget.releaseAllSurfaces();
    }

    public void releaseAnimSurfaces() {
        mTarget.releaseAnimSurfaces();
    }

    public Parcelable getParcelable() {
        return mTarget;
    }

    public static final class Change {
        private final android.window.TransitionInfo.Change mTarget;

        Change(android.window.TransitionInfo.Change target) {
            mTarget = target;
        }

        public android.window.TransitionInfo.Change get() {
            return mTarget;
        }

        public boolean getAllowEnterPip() {
            return mTarget.isAllowEnterPip();
        }

        public WindowContainerToken getContainer() {
            return wrapToken(mTarget.getContainer());
        }

        public Rect getEndAbsBounds() {
            return mTarget.getEndAbsBounds();
        }

        public Point getEndRelOffset() {
            return mTarget.getEndRelOffset();
        }

        public int getEndRotation() {
            return mTarget.getEndRotation();
        }

        public int getFlags() {
            return mTarget.getFlags();
        }

        public SurfaceControl getLeash() {
            return mTarget.getLeash();
        }

        public int getMode() {
            return mTarget.getMode();
        }

        public WindowContainerToken getParent() {
            return wrapToken(mTarget.getParent());
        }

        public Rect getStartAbsBounds() {
            return mTarget.getStartAbsBounds();
        }

        public int getStartRotation() {
            return mTarget.getStartRotation();
        }

        public ActivityManager.RunningTaskInfo getTaskInfo() {
            return mTarget.getTaskInfo();
        }

        public int getEndDisplayId() {
            return mTarget.getEndDisplayId();
        }

        public int getStartDisplayId() {
            return mTarget.getStartDisplayId();
        }

        public int getBackgroundColor() {
            return mTarget.getBackgroundColor();
        }

        public void setBackgroundColor(int color) {
            mTarget.setBackgroundColor(color);
        }

        public boolean hasFlags(int flags) {
            return mTarget.hasFlags(flags);
        }

        public ComponentName getActivityComponent() {
            return mTarget.getActivityComponent();
        }

        public WindowContainerToken getLastParent() {
            return wrapToken(mTarget.getLastParent());
        }

        private static WindowContainerToken wrapToken(
                android.window.WindowContainerToken token) {
            return token == null ? null : new WindowContainerToken(token);
        }
    }

    public static final class Root {
        private final android.window.TransitionInfo.Root mTarget;

        Root(android.window.TransitionInfo.Root target) {
            mTarget = target;
        }

        public SurfaceControl getLeash() {
            return mTarget.getLeash();
        }

        public Point getOffset() {
            return mTarget.getOffset();
        }
    }
}
