package com.oplus.wrapper.view;

import android.graphics.Matrix;
import android.os.IBinder;

/** Small ABI wrapper around the SurfaceControl APIs used by ColorOS transitions. */
public final class SurfaceControl {
    private final android.view.SurfaceControl mTarget;

    public SurfaceControl(android.view.SurfaceControl target) {
        mTarget = target;
    }

    public android.view.SurfaceControl getSurfaceControl() {
        return mTarget;
    }

    public static final class Transaction {
        private final android.view.SurfaceControl.Transaction mTarget;

        public Transaction(android.view.SurfaceControl.Transaction target) {
            mTarget = target;
        }

        public Transaction show(android.view.SurfaceControl surface) {
            mTarget.show(surface);
            return this;
        }

        public Transaction hide(android.view.SurfaceControl surface) {
            mTarget.hide(surface);
            return this;
        }

        public Transaction setCornerRadius(android.view.SurfaceControl surface, float radius) {
            mTarget.setCornerRadius(surface, radius);
            return this;
        }

        public Transaction setMatrix(android.view.SurfaceControl surface, Matrix matrix,
                float[] values) {
            mTarget.setMatrix(surface, matrix, values);
            return this;
        }

        public android.view.SurfaceControl.Transaction getTransaction() {
            return mTarget;
        }

        public static void setDefaultApplyToken(IBinder token) {
            android.view.SurfaceControl.Transaction.setDefaultApplyToken(token);
        }

        public static IBinder getDefaultApplyToken() {
            return android.view.SurfaceControl.Transaction.getDefaultApplyToken();
        }
    }

    public static final class Builder {
        private final android.view.SurfaceControl.Builder mTarget;

        public Builder() {
            mTarget = new android.view.SurfaceControl.Builder();
        }

        public Builder setContainerLayer() {
            mTarget.setContainerLayer();
            return this;
        }

        public Builder setEffectLayer() {
            mTarget.setEffectLayer();
            return this;
        }

        public Builder setCallsite(String callsite) {
            mTarget.setCallsite(callsite);
            return this;
        }

        public android.view.SurfaceControl.Builder getBuilder() {
            return mTarget;
        }
    }
}
