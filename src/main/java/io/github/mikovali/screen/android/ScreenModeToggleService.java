package io.github.mikovali.screen.android;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceManager;

public class ScreenModeToggleService extends Service {

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String key = getString(R.string.settings_key_mode);
        final boolean mode = sharedPreferences.getBoolean(key, false);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, !mode);
        editor.apply();

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static PendingIntent createPendingIntent(Context context) {
        return PendingIntent.getService(context, 0,
                new Intent(context, ScreenModeToggleService.class), 0);
    }
}
