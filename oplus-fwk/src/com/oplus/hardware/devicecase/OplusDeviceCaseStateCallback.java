/*
 * Copyright (C) 2026 The Infinity-X Project
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.oplus.hardware.devicecase;

/** Callback used by OPlus clients to observe an optional device-case state. */
public interface OplusDeviceCaseStateCallback {
    void onStateChanged(int state);
}
