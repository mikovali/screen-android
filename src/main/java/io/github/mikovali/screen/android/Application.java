package io.github.mikovali.screen.android;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.text.TextUtils;

public class Application extends android.app.Application
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (TextUtils.equals(key, getString(R.string.settings_key_mode))) {
            // notify widgets
            final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            final int[] ids = appWidgetManager.getAppWidgetIds(
                    new ComponentName(this, WidgetProvider.class));
            final Intent widgetIntent = new Intent(WidgetProvider.ACTION_APPWIDGET_UPDATE);
            widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            sendBroadcast(widgetIntent);

            // notify service
            startService(ScreenModeService.createIntent(this));
        }
    }
}
