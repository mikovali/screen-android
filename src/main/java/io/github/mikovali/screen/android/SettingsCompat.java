package io.github.mikovali.screen.android;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

public class SettingsCompat {

    public static boolean canDrawOverlays(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context);
        } else {
            return true;
        }
    }
}
