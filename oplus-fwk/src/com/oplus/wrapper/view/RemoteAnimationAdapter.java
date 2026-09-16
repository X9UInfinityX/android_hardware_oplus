package com.oplus.wrapper.view;

import android.os.Parcelable;
import com.oplus.wrapper.app.IApplicationThread;

/** ColorOS wrapper around the platform remote-animation adapter. */
public final class RemoteAnimationAdapter {
    private final android.view.RemoteAnimationAdapter mTarget;
    private final IRemoteAnimationRunner mRunner;

    public RemoteAnimationAdapter(IRemoteAnimationRunner runner, long duration,
            long statusBarTransitionDelay) {
        mRunner = runner;
        mTarget = new android.view.RemoteAnimationAdapter(
                IRemoteAnimationRunner.Stub.unwrap(runner), duration,
                statusBarTransitionDelay);
    }

    public RemoteAnimationAdapter(IRemoteAnimationRunner runner, long duration,
            long statusBarTransitionDelay, IApplicationThread callingApplication) {
        mRunner = runner;
        mTarget = new android.view.RemoteAnimationAdapter(
                IRemoteAnimationRunner.Stub.unwrap(runner), duration,
                statusBarTransitionDelay, IApplicationThread.Stub.unwrap(callingApplication));
    }

    public RemoteAnimationAdapter(android.view.RemoteAnimationAdapter target) {
        mTarget = target;
        mRunner = target == null || target.getRunner() == null
                ? null : IRemoteAnimationRunner.Stub.asInterface(target.getRunner().asBinder());
    }

    public android.view.RemoteAnimationAdapter getRemoteAnimationAdapter() {
        return mTarget;
    }

    public IRemoteAnimationRunner getRunner() {
        return mRunner;
    }

    public Parcelable getParcelable() {
        return mTarget;
    }

    public IApplicationThread getCallingApplication() {
        android.app.IApplicationThread thread = mTarget.getCallingApplication();
        return thread == null ? null : IApplicationThread.Stub.asInterface(thread.asBinder());
    }
}
