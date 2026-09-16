package com.oplus.wrapper.window;

import com.oplus.wrapper.app.IApplicationThread;

/** ColorOS wrapper around android.window.RemoteTransition. */
public final class RemoteTransition {
    private final android.window.RemoteTransition mTarget;

    public RemoteTransition(IRemoteTransition transition,
            IApplicationThread applicationThread, String debugName) {
        mTarget = new android.window.RemoteTransition(
                IRemoteTransition.Stub.unwrap(transition),
                IApplicationThread.Stub.unwrap(applicationThread), debugName);
    }

    public android.window.RemoteTransition getRemoteTransition() {
        return mTarget;
    }
}
