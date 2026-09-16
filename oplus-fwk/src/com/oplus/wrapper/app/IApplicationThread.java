package com.oplus.wrapper.app;

import android.os.IBinder;
import android.os.IInterface;

/**
 * ABI-compatible wrapper around the hidden platform application-thread binder.
 *
 * <p>Consumers pass this wrapper to animation APIs so the process hosting the animation can be
 * boosted; calls remain on the original platform binder.
 */
public interface IApplicationThread extends IInterface {
    abstract class Stub {
        private Stub() {}

        public static IApplicationThread asInterface(IBinder binder) {
            final android.app.IApplicationThread target =
                    android.app.IApplicationThread.Stub.asInterface(binder);
            if (target == null) {
                return null;
            }
            return new IApplicationThread() {
                @Override
                public IBinder asBinder() {
                    return target.asBinder();
                }
            };
        }

        public static android.app.IApplicationThread unwrap(IApplicationThread wrapper) {
            return wrapper == null
                    ? null
                    : android.app.IApplicationThread.Stub.asInterface(wrapper.asBinder());
        }
    }
}
