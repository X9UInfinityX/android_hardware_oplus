package com.oplus.wrapper.view;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

/** Wrapper ABI for android.view.IRemoteAnimationFinishedCallback. */
public interface IRemoteAnimationFinishedCallback {
    void onAnimationFinished() throws RemoteException;

    abstract class Stub implements IInterface, IRemoteAnimationFinishedCallback {
        private final android.view.IRemoteAnimationFinishedCallback mTarget =
                new android.view.IRemoteAnimationFinishedCallback.Stub() {
                    @Override
                    public void onAnimationFinished() throws RemoteException {
                        IRemoteAnimationFinishedCallback.Stub.this.onAnimationFinished();
                    }
                };

        @Override
        public IBinder asBinder() {
            return mTarget.asBinder();
        }

        public static IRemoteAnimationFinishedCallback asInterface(IBinder binder) {
            final android.view.IRemoteAnimationFinishedCallback target =
                    android.view.IRemoteAnimationFinishedCallback.Stub.asInterface(binder);
            if (target == null) {
                return null;
            }
            return target::onAnimationFinished;
        }
    }
}
