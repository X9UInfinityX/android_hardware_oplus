package com.oplus.wrapper.app;

/** Thin wrapper matching the ColorOS TaskInfo ABI used by GestureUI. */
public final class TaskInfo {
    private final android.app.TaskInfo mTarget;

    public TaskInfo(android.app.TaskInfo target) {
        mTarget = target;
    }

    public int getParentTaskId() {
        return mTarget.getParentTaskId();
    }
}
