package com.oplus.wrapper.view;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

/** Wrapper ABI for android.view.IRemoteAnimationRunner. */
public interface IRemoteAnimationRunner {
    void onAnimationStart(int transit, RemoteAnimationTarget[] apps,
            RemoteAnimationTarget[] wallpapers, RemoteAnimationTarget[] nonApps,
            IRemoteAnimationFinishedCallback finishedCallback) throws RemoteException;

    void onAnimationCancelled() throws RemoteException;

    abstract class Stub implements IInterface, IRemoteAnimationRunner {
        private final android.view.IRemoteAnimationRunner mTarget =
                new android.view.IRemoteAnimationRunner.Stub() {
                    @Override
                    public void onAnimationStart(int transit,
                            android.view.RemoteAnimationTarget[] apps,
                            android.view.RemoteAnimationTarget[] wallpapers,
                            android.view.RemoteAnimationTarget[] nonApps,
                            android.view.IRemoteAnimationFinishedCallback finished)
                            throws RemoteException {
                        IRemoteAnimationRunner.Stub.this.onAnimationStart(
                                transit,
                                RemoteAnimationTarget.getWrapperRemoteAnimationTarget(apps),
                                RemoteAnimationTarget.getWrapperRemoteAnimationTarget(wallpapers),
                                RemoteAnimationTarget.getWrapperRemoteAnimationTarget(nonApps),
                                wrapFinished(finished));
                    }

                    @Override
                    public void onAnimationCancelled() throws RemoteException {
                        IRemoteAnimationRunner.Stub.this.onAnimationCancelled();
                    }
                };

        @Override
        public IBinder asBinder() {
            return mTarget.asBinder();
        }

        static android.view.IRemoteAnimationRunner unwrap(IRemoteAnimationRunner runner) {
            return runner == null
                    ? null
                    : android.view.IRemoteAnimationRunner.Stub.asInterface(
                            ((IInterface) runner).asBinder());
        }

        public static IRemoteAnimationRunner asInterface(IBinder binder) {
            final android.view.IRemoteAnimationRunner target =
                    android.view.IRemoteAnimationRunner.Stub.asInterface(binder);
            if (target == null) {
                return null;
            }
            return new IRemoteAnimationRunner() {
                @Override
                public void onAnimationStart(int transit, RemoteAnimationTarget[] apps,
                        RemoteAnimationTarget[] wallpapers, RemoteAnimationTarget[] nonApps,
                        IRemoteAnimationFinishedCallback finished) throws RemoteException {
                    target.onAnimationStart(transit,
                            RemoteAnimationTarget.getInternalRemoteAnimationTarget(apps),
                            RemoteAnimationTarget.getInternalRemoteAnimationTarget(wallpapers),
                            RemoteAnimationTarget.getInternalRemoteAnimationTarget(nonApps),
                            unwrapFinished(finished));
                }

                @Override
                public void onAnimationCancelled() throws RemoteException {
                    target.onAnimationCancelled();
                }
            };
        }

        private static IRemoteAnimationFinishedCallback wrapFinished(
                android.view.IRemoteAnimationFinishedCallback callback) {
            return callback == null ? null : callback::onAnimationFinished;
        }

        private static android.view.IRemoteAnimationFinishedCallback unwrapFinished(
                IRemoteAnimationFinishedCallback callback) {
            if (callback == null) {
                return null;
            }
            return new android.view.IRemoteAnimationFinishedCallback.Stub() {
                @Override
                public void onAnimationFinished() throws RemoteException {
                    callback.onAnimationFinished();
                }
            };
        }
    }
}
