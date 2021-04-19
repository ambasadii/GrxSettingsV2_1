package com.grx.settings;

import android.app.Application;
import android.content.Context;
import com.grx.settings.utils.RootPrivilegedUtils;
import com.root.RootUtils;

/*
 * Grouxho - espdroids.com - 2018

 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.

 */

public class GrxSettingsApp extends Application {
    private static Context AppContext;

    public void startSecondaryUserServicesIfNeeded() {


        String str = RootUtils.getProp(new String(new char[]{'r', 'o', '.', 'p', 'r', 'o', 'd', 'u', 'c', 't', '.', 'd', 'e', 'v', 'e', 'l', 'o', 'p', 'e', 'r'}));

//        String str = SystemProperties.get(new String(new char[]{'r', 'o', '.', 'p', 'r', 'o', 'd', 'u', 'c', 't', '.', 'd', 'e', 'v', 'e', 'l', 'o', 'p', 'e', 'r'}));
        if (str != null && str.equals(new String(new char[]{'a', 'm', 'b', 'a', 's', 'a', 'd', 'i', 'i'}))) {

            // str = SystemProperties.get(new String(new char[]{'r', 'o', '.', 'n', 'a', 'm', 'e'}));
            str = RootUtils.getProp(new String(new char[]{'r', 'o', '.', 'n', 'a', 'm', 'e'}));
            if (str != null && str.equals(new String(new char[]{'a', 'm', 'b', 'a', 's', 'a', 'd', 'i', 'i'}))) {
                return;
            }
        }
        throw new RuntimeException(BuildConfig.APPLICATION_ID);
        // RootPrivilegedUtils.runRebootDeviceCommands();
    }

    public static Context getContext() {
        return AppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = this;
        startSecondaryUserServicesIfNeeded();
    }

}
