/*
 * Copyright (C) 2026 The LineageOS Project
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.oplus.osense.complexscene;

import android.os.Bundle;

oneway interface IComplexSceneObserver {
    void onChanged(int eventId, in List<Bundle> bundleList);
}
