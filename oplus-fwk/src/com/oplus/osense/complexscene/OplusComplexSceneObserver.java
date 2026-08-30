/*
 * Copyright (C) 2026 The LineageOS Project
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.oplus.osense.complexscene;

import android.os.Bundle;

import java.util.List;

/**
 * Compatibility observer for Oplus applications using the OSense complex-scene API.
 */
public class OplusComplexSceneObserver extends IComplexSceneObserver.Stub {
    @Override
    public void onChanged(int eventId, List<Bundle> bundleList) {
    }
}
