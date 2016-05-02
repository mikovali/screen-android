package io.github.mikovali.screen.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v14.preference.PreferenceFragment;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.TwoStatePreference;
import android.text.TextUtils;
import android.view.View;

public class Fragment extends PreferenceFragment implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    private Snackbar overlayPermissionSnackbar;

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

    @Override
    public void onResume() {
        super.onResume();
        final boolean canDrawOverlays = SettingsCompat.canDrawOverlays(getActivity());
        setKeepScreenOnPreferenceEnabled(canDrawOverlays);
        setShowOverlayPermissionSnackbar(!canDrawOverlays);
    }

    @Override
    public void onPause() {
        setShowOverlayPermissionSnackbar(false);
        super.onPause();
    }

    private void setKeepScreenOnPreferenceEnabled(boolean enabled) {
        findPreference(getString(R.string.settings_key_mode)).setEnabled(enabled);
    }

    private void setShowOverlayPermissionSnackbar(boolean show) {
        final View view = getView();
        if (view == null) {
            return;
        }
        if (show) {
            overlayPermissionSnackbar = Snackbar.make(view, R.string.overlay_permission_text,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.overlay_permission_action, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                final Intent intent = new Intent(
                                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                        Uri.fromParts("package", BuildConfig.APPLICATION_ID, null));
                                startActivity(intent);
                            }
                        }
                    });
            overlayPermissionSnackbar.show();
        } else if (overlayPermissionSnackbar != null && overlayPermissionSnackbar.isShown()) {
            overlayPermissionSnackbar.dismiss();
            overlayPermissionSnackbar = null;
        }
    }

    public static Fragment newInstance() {
        return new Fragment();
    }
}
