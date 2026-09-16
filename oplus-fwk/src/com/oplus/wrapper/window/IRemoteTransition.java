package com.oplus.wrapper.window;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.view.SurfaceControl;
import android.window.WindowAnimationState;

/** Wrapper ABI for android.window.IRemoteTransition. */
public interface IRemoteTransition {
    void mergeAnimation(IBinder transition, TransitionInfo info,
            SurfaceControl.Transaction transaction, IBinder mergeTarget,
            IRemoteTransitionFinishedCallback finishCallback) throws RemoteException;

    void startAnimation(IBinder token, TransitionInfo info,
            SurfaceControl.Transaction transaction,
            IRemoteTransitionFinishedCallback finishCallback) throws RemoteException;

    default void onTransitionConsumed(IBinder transition, boolean aborted)
            throws RemoteException {}

    abstract class Stub implements IInterface, IRemoteTransition {
        private final android.window.IRemoteTransition mTarget =
                new android.window.IRemoteTransition.Stub() {
                    @Override
                    public void startAnimation(IBinder token, android.window.TransitionInfo info,
                            SurfaceControl.Transaction transaction,
                            android.window.IRemoteTransitionFinishedCallback finish)
                            throws RemoteException {
                        IRemoteTransition.Stub.this.startAnimation(
                                token, new TransitionInfo(info), transaction,
                                wrapFinished(finish));
                    }

                    @Override
                    public void mergeAnimation(IBinder transition,
                            android.window.TransitionInfo info,
                            SurfaceControl.Transaction transaction, IBinder mergeTarget,
                            android.window.IRemoteTransitionFinishedCallback finish)
                            throws RemoteException {
                        IRemoteTransition.Stub.this.mergeAnimation(
                                transition, new TransitionInfo(info), transaction,
                                mergeTarget, wrapFinished(finish));
                    }

                    @Override
                    public void takeOverAnimation(IBinder transition,
                            android.window.TransitionInfo info,
                            SurfaceControl.Transaction transaction,
                            android.window.IRemoteTransitionFinishedCallback finish,
                            WindowAnimationState[] states) {}

                    @Override
                    public void onTransitionConsumed(IBinder transition, boolean aborted)
                            throws RemoteException {
                        IRemoteTransition.Stub.this.onTransitionConsumed(transition, aborted);
                    }
                };

        @Override
        public IBinder asBinder() {
            return mTarget.asBinder();
        }

        public static IRemoteTransition asInterface(IBinder binder) {
            final android.window.IRemoteTransition target =
                    android.window.IRemoteTransition.Stub.asInterface(binder);
            if (target == null) {
                return null;
            }
            return new Proxy(target);
        }

        static android.window.IRemoteTransition unwrap(IRemoteTransition transition) {
            return transition == null ? null
                    : android.window.IRemoteTransition.Stub.asInterface(
                            ((IInterface) transition).asBinder());
        }

        private static IRemoteTransitionFinishedCallback wrapFinished(
                android.window.IRemoteTransitionFinishedCallback finish) {
            return finish == null ? null
                    : IRemoteTransitionFinishedCallback.Stub.asInterface(finish.asBinder());
        }

        private static android.window.IRemoteTransitionFinishedCallback unwrapFinished(
                IRemoteTransitionFinishedCallback finish) {
            if (finish == null) {
                return null;
            }
            return new android.window.IRemoteTransitionFinishedCallback.Stub() {
                @Override
                public void onTransitionFinished(
                        android.window.WindowContainerTransaction transaction,
                        SurfaceControl.Transaction surfaceTransaction)
                        throws RemoteException {
                    finish.onTransitionFinished(
                            transaction == null
                                    ? null : new WindowContainerTransaction(transaction),
                            surfaceTransaction);
                }
            };
        }

        private static final class Proxy implements IRemoteTransition {
            private final android.window.IRemoteTransition mTarget;

            Proxy(android.window.IRemoteTransition target) {
                mTarget = target;
            }

            @Override
            public void startAnimation(IBinder token, TransitionInfo info,
                    SurfaceControl.Transaction transaction,
                    IRemoteTransitionFinishedCallback finish) throws RemoteException {
                mTarget.startAnimation(token, info.get(), transaction, unwrapFinished(finish));
            }

            @Override
            public void mergeAnimation(IBinder transition, TransitionInfo info,
                    SurfaceControl.Transaction transaction, IBinder mergeTarget,
                    IRemoteTransitionFinishedCallback finish) throws RemoteException {
                mTarget.mergeAnimation(transition, info.get(), transaction, mergeTarget,
                        unwrapFinished(finish));
            }

            @Override
            public void onTransitionConsumed(IBinder transition, boolean aborted)
                    throws RemoteException {
                mTarget.onTransitionConsumed(transition, aborted);
            }
        }
    }
}
