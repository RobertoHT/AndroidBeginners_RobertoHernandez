package com.beginner.micromaster.flashcardsapp.menu;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.beginner.micromaster.flashcardsapp.R;

/**
 * Created by praxis on 11/05/17.
 */

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentTransaction fragment = getFragmentManager().beginTransaction();
        fragment.add(android.R.id.content, new SettingsFragment());
        fragment.commit();
    }

    public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{
        public SettingsFragment() {}

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            int frequency = preferences.getInt("frequency", 10);
            changeSummaryFrequency("frequency", frequency);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(key.equals("frequency")){
                changeSummaryFrequency(key, sharedPreferences.getInt(key, 10));
            }
        }

        private void changeSummaryFrequency(String key, int frequency){
            String summary;
            if(frequency == 1){
                summary = "1 hour";
            } else {
                summary = frequency + " hours";
            }

            Preference preference = findPreference(key);
            preference.setSummary(summary);
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }
    }
}
