package io.github.mikovali.screen.android;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.TwoStatePreference;
import android.text.TextUtils;
import android.view.View;

public class Fragment extends PreferenceFragment implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (TextUtils.equals(key, getString(R.string.settings_key_mode))) {
            final boolean mode = sharedPreferences.getBoolean(key, false);
            final TwoStatePreference modePreference = (TwoStatePreference) findPreference(key);
            if (mode != modePreference.isChecked()) {
                modePreference.setChecked(mode);
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroyView() {
        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroyView();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings);
    }

    public static Fragment newInstance() {
        return new Fragment();
    }
}
