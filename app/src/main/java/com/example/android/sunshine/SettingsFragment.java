package com.example.android.sunshine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

/**
 * Created by eduardo_thp on 1/28/2018.
 */

// COMPLETED (4) Create SettingsFragment and extend PreferenceFragmentCompat
// COMPLETED (10) Implement OnSharedPreferenceChangeListener from SettingsFragment
public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    // Do steps 5 - 11 within SettingsFragment

    // COMPLETED (5) Override onCreatePreferences and add the preference xml file using addPreferencesFromResource
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Do step 9 within onCreatePreference
        // COMPLETED (9) Set the preference summary on each preference that isn't a CheckBoxPreference
        addPreferencesFromResource(R.xml.pref_general);

        PreferenceScreen preferenceScreen = getPreferenceScreen();
        SharedPreferences sharedPreferences = preferenceScreen.getSharedPreferences();
        final int count = preferenceScreen.getPreferenceCount();
        for(int i = 0; i < count; i++) {
            Preference preference = preferenceScreen.getPreference(i);
            if (!(preference instanceof CheckBoxPreference))
            {
                setPreferenceSummary(preference, sharedPreferences.getString(preference.getKey(), ""));
            }
        }

    }

    // COMPLETED (8) Create a method called setPreferenceSummary that accepts a Preference and an Object and sets the summary of the preference
    void setPreferenceSummary(Preference preference, Object value) {
        String valueStr = value.toString();
        if (preference instanceof ListPreference)
        {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(valueStr);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }
        else
        {
            preference.setSummary(valueStr);
        }
    }

    // COMPLETED (11) Override onSharedPreferenceChanged to update non CheckBoxPreferences when they are changed
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (!(preference instanceof CheckBoxPreference)) {
            String value = sharedPreferences.getString(key, "");
            setPreferenceSummary(preference, value);
        }
    }

    // COMPLETED (12) Register SettingsFragment (this) as a SharedPreferenceChangedListener in onStart

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    // COMPLETED (13) Unregister SettingsFragment (this) as a SharedPreferenceChangedListener in onStop
    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
