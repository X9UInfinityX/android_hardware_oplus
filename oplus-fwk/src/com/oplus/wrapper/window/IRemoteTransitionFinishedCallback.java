package com.oplus.wrapper.window;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.view.SurfaceControl;

/** Wrapper ABI for the shell transition completion callback. */
public interface IRemoteTransitionFinishedCallback {
    void onTransitionFinished(WindowContainerTransaction transaction,
            SurfaceControl.Transaction surfaceTransaction) throws RemoteException;

    abstract class Stub implements IInterface, IRemoteTransitionFinishedCallback {
        private final android.window.IRemoteTransitionFinishedCallback mTarget =
                new android.window.IRemoteTransitionFinishedCallback.Stub() {
                    @Override
                    public void onTransitionFinished(
                            android.window.WindowContainerTransaction transaction,
                            SurfaceControl.Transaction surfaceTransaction)
                            throws RemoteException {
                        IRemoteTransitionFinishedCallback.Stub.this.onTransitionFinished(
                                transaction == null
                                        ? null : new WindowContainerTransaction(transaction),
                                surfaceTransaction);
                    }
                };

        @Override
        public IBinder asBinder() {
            return mTarget.asBinder();
        }

        public static IRemoteTransitionFinishedCallback asInterface(IBinder binder) {
            final android.window.IRemoteTransitionFinishedCallback target =
                    android.window.IRemoteTransitionFinishedCallback.Stub.asInterface(binder);
            if (target == null) {
                return null;
            }
            return (transaction, surfaceTransaction) ->
                    target.onTransitionFinished(
                            transaction == null ? null : transaction.get(),
                            surfaceTransaction);
        }
    }
}
