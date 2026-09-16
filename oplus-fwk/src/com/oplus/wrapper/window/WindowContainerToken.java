package com.oplus.wrapper.window;

/* loaded from: classes2.dex */
public class WindowContainerToken {
    private final android.window.WindowContainerToken mWindowContainerToken;

    public WindowContainerToken(android.window.WindowContainerToken windowContainerToken) {
        this.mWindowContainerToken = windowContainerToken;
    }

    public boolean equals(Object obj) {
        if (this.mWindowContainerToken == null) {
            return obj == null;
        }
        if (obj instanceof WindowContainerToken) {
            return this.mWindowContainerToken.equals(((WindowContainerToken) obj).get());
        }
        return this.mWindowContainerToken.equals(obj);
    }

    public int hashCode() {
        if (this.mWindowContainerToken == null) {
            return 0;
        }
        return this.mWindowContainerToken.hashCode();
    }

    public android.window.WindowContainerToken get() {
        return this.mWindowContainerToken;
    }
}
