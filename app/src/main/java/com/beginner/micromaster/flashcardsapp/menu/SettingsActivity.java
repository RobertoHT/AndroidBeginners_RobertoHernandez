package com.beginner.micromaster.flashcardsapp.menu;

import android.app.FragmentTransaction;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.beginner.micromaster.flashcardsapp.R;
import com.beginner.micromaster.flashcardsapp.service.ReminderService;

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
        private int HOUR = 3600000;
        private int JOB_ID = 1;

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
            if(key.equals("activate")){
                enableReminder(sharedPreferences, key);
            }
            else if (key.equals("frequency")){
                changeSummaryFrequency(key, sharedPreferences.getInt(key, 10));
            }
        }

        private void enableReminder(SharedPreferences sharedPreferences, String key){
            JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            ComponentName jobService = new ComponentName(getPackageName(), ReminderService.class.getName());

            boolean activate = sharedPreferences.getBoolean(key, false);
            if(activate){
                jobScheduler.cancelAll();

                int refresh = HOUR * sharedPreferences.getInt("frequency", 10);
                JobInfo jobInfo = new JobInfo.Builder(JOB_ID, jobService).setPeriodic(refresh).build();
                jobScheduler.schedule(jobInfo);
            } else {
                jobScheduler.cancel(JOB_ID);
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
