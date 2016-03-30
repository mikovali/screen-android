package io.github.mikovali.screen.android;

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
            final Intent intent = new Intent(this, Service.class);
            startService(intent);
        }
    }
}
