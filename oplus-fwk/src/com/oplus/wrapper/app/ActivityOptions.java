package com.oplus.wrapper.app;

import com.oplus.wrapper.view.RemoteAnimationAdapter;
import com.oplus.wrapper.window.RemoteTransition;

/** ColorOS-compatible access to hidden ActivityOptions animation factories. */
public final class ActivityOptions {
    private ActivityOptions() {}

    public static android.app.ActivityOptions makeRemoteAnimation(
            RemoteAnimationAdapter adapter) {
        return android.app.ActivityOptions.makeRemoteAnimation(
                adapter.getRemoteAnimationAdapter());
    }

    public static android.app.ActivityOptions makeRemoteAnimation(
            RemoteAnimationAdapter adapter, RemoteTransition transition) {
        return android.app.ActivityOptions.makeRemoteAnimation(
                adapter.getRemoteAnimationAdapter(), transition.getRemoteTransition());
    }

    public static android.app.ActivityOptions makeRemoteTransition(
            RemoteTransition transition) {
        return android.app.ActivityOptions.makeRemoteTransition(
                transition.getRemoteTransition());
    }
}
