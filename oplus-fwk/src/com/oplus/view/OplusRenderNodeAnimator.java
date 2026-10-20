package com.oplus.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;

public class OplusRenderNodeAnimator {
    private static final class RtAnimator extends ValueAnimator implements IRtAnimationTarget {
        private final IRtAnimationTarget mTarget;

        RtAnimator(IRtAnimationTarget target) {
            mTarget = target;
        }

        @Override
        public void animateToFinalPosition(float finalPosition) {
            mTarget.animateToFinalPosition(finalPosition);
        }

        @Override
        public void cancel() {
            mTarget.cancel();
        }

        @Override
        public boolean doFrame(long frameTime) {
            return mTarget.doFrame(frameTime);
        }

        @Override
        public void end() {
            mTarget.end();
        }

        @Override
        public boolean isRunning() {
            return mTarget.isRunning();
        }

        @Override
        public void setAnimationHandler() {
            mTarget.setAnimationHandler();
        }

        @Override
        public void skipToEnd() {
            mTarget.skipToEnd();
        }

        @Override
        public void start() {
            mTarget.start();
        }
    }

    public static void animateToFinalPosition(Animator animator, float finalPosition) {
        if (animator instanceof IRtAnimationTarget) {
            ((IRtAnimationTarget) animator).animateToFinalPosition(finalPosition);
        }
    }

    public static Animator createRenderValueAnimator(Animator animator, View view) {
        return animator;
    }

    public static Animator createRtAnimator(IRtAnimationTarget target, View view) {
        if (target == null || view == null) {
            return null;
        }

        // ColorOS uses OplusRenderValueAnimator to drive this target from the render thread.
        // AOSP does not provide that class, so keep the target on its own Choreographer-backed
        // animation handler and provide the same Animator/IRtAnimationTarget forwarding contract.
        return new RtAnimator(target);
    }

    public static long getFrameNumber(Animator animator) {
        return 0L;
    }
}
