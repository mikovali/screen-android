package io.github.mikovali.screen.android;

import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;

public class Fragment extends PreferenceFragment {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings);
    }

    public static Fragment newInstance() {
        return new Fragment();
    }
}
