package com.oplus.wrapper.view;

import android.graphics.Matrix;
import android.graphics.Rect;

/** Wrapper used by the stock activity-launch animation. */
public final class SyncRtSurfaceTransactionApplier {
    private final android.view.SyncRtSurfaceTransactionApplier mTarget;

    public SyncRtSurfaceTransactionApplier(android.view.View view) {
        mTarget = new android.view.SyncRtSurfaceTransactionApplier(view);
    }

    public void scheduleApply(SurfaceParams... params) {
        if (params == null || params.length == 0) {
            return;
        }
        android.view.SyncRtSurfaceTransactionApplier.SurfaceParams[] converted =
                new android.view.SyncRtSurfaceTransactionApplier.SurfaceParams[params.length];
        for (int i = 0; i < params.length; i++) {
            converted[i] = params[i].mTarget;
        }
        mTarget.scheduleApply(converted);
    }

    public static final class SurfaceParams {
        private final android.view.SyncRtSurfaceTransactionApplier.SurfaceParams mTarget;

        private SurfaceParams(
                android.view.SyncRtSurfaceTransactionApplier.SurfaceParams target) {
            mTarget = target;
        }

        public static final class Builder {
            private final android.view.SyncRtSurfaceTransactionApplier.SurfaceParams.Builder mTarget;

            public Builder(android.view.SurfaceControl surface) {
                mTarget =
                        new android.view.SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(
                                surface);
            }

            public Builder withAlpha(float alpha) {
                mTarget.withAlpha(alpha);
                return this;
            }

            public Builder withMatrix(Matrix matrix) {
                mTarget.withMatrix(matrix);
                return this;
            }

            public Builder withWindowCrop(Rect crop) {
                mTarget.withWindowCrop(crop);
                return this;
            }

            public Builder withLayer(int layer) {
                mTarget.withLayer(layer);
                return this;
            }

            public Builder withCornerRadius(float radius) {
                mTarget.withCornerRadius(radius);
                return this;
            }

            public Builder withBackgroundBlur(int radius) {
                // This ColorOS extension is optional; OplusBlurManager applies the blur directly.
                return this;
            }

            public Builder withVisibility(boolean visible) {
                mTarget.withVisibility(visible);
                return this;
            }

            public Builder withMergeTransaction(SurfaceControl.Transaction transaction) {
                if (transaction != null) {
                    mTarget.withMergeTransaction(transaction.getTransaction());
                }
                return this;
            }

            public SurfaceParams build() {
                return new SurfaceParams(mTarget.build());
            }
        }
    }
}
