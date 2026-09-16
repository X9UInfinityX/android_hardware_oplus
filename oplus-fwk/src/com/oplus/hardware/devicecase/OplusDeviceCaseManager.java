/*
 * Copyright (C) 2026 The Infinity-X Project
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.oplus.hardware.devicecase;

import java.util.concurrent.Executor;

/**
 * Compatibility surface for the ColorOS device-case API.
 *
 * The Find X9 Ultra GestureUI uses this only for the optional glove/case feature. The custom
 * ROM does not expose the corresponding ColorOS service, so report it unsupported while keeping
 * the stock application alive for Spruce-button handling.
 */
public final class OplusDeviceCaseManager {
    private static final OplusDeviceCaseManager INSTANCE = new OplusDeviceCaseManager();

    private OplusDeviceCaseManager() {
    }

    public static OplusDeviceCaseManager getInstance() {
        return INSTANCE;
    }

    public boolean isSupported() {
        return false;
    }

    public boolean isEnabled() {
        return false;
    }

    public void registerCallback(Executor executor, OplusDeviceCaseStateCallback callback) {
        // The optional device-case service is not present on AOSP-based builds.
    }

    public void unregisterCallback(OplusDeviceCaseStateCallback callback) {
        // The optional device-case service is not present on AOSP-based builds.
    }
}
