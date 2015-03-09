package com.reelyactive.blesdk.support.ble;/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.reelyactive.blesdk.support.ble.util.Logger;

/**
 * com.reelyactive.blesdk.support.ble.ScanWakefulBroadcastReceiver initiates the Bluetooth LE scan by calling
 * {@link ScanWakefulService} after acquiring a WakeLock. On completion {@link ScanWakefulService}
 * releases the WakeLock.
 * <p/>
 * This WakefulBroadcastReceiver is invoked by a pending intent through the alarm manager.
 */
public class ScanWakefulBroadcastReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.logDebug("Alarm triggered");
        // This is the Intent to deliver to our scan service.
        Intent intentService = new Intent(context, ScanWakefulService.class)
                .putExtra(
                        ScanWakefulService.EXTRA_USE_LOLLIPOP_API,
                        intent.getBooleanExtra(ScanWakefulService.EXTRA_USE_LOLLIPOP_API, true)
                );

        // Start the scan service, keeping the device awake while it is launching. This method will
        // explicitly grab a wakelock until the service tells the broadcast receiver to release it.
        startWakefulService(context, intentService);
    }
}
