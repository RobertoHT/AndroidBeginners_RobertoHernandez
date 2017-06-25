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
import com.beginner.micromaster.flashcardsapp.util.Constants;

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
        private String KEY_REMINDER = Constants.KEY_SETTING_ENABLE_REMINDER;
        private String KEY_FREQUENCY = Constants.KEY_SETTING_FREQUENCY;
        private int HOUR = 3600000;
        private int JOB_ID = 1;

        public SettingsFragment() {}

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);

            setFrequency();
        }

        private void setFrequency(){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            findPreference(KEY_FREQUENCY).setEnabled(preferences.getBoolean(KEY_REMINDER, false));
            int frequency = preferences.getInt(KEY_FREQUENCY, 10);
            changeSummaryFrequency(frequency);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(key.equals(KEY_REMINDER)){
                enableReminder(sharedPreferences);
            }
            else if (key.equals(KEY_FREQUENCY)){
                changeSummaryFrequency(sharedPreferences.getInt(key, 10));
                enableReminder(sharedPreferences);
            }
        }

        private void enableReminder(SharedPreferences sharedPreferences){
            JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            ComponentName jobService = new ComponentName(getPackageName(), ReminderService.class.getName());

            boolean activate = sharedPreferences.getBoolean(KEY_REMINDER, false);
            findPreference(KEY_FREQUENCY).setEnabled(activate);
            if(activate){
                jobScheduler.cancelAll();

                int refresh = HOUR * sharedPreferences.getInt(KEY_FREQUENCY, 10);
                JobInfo jobInfo = new JobInfo.Builder(JOB_ID, jobService).setPeriodic(refresh).build();
                jobScheduler.schedule(jobInfo);
            } else {
                jobScheduler.cancel(JOB_ID);
            }
        }

        private void changeSummaryFrequency(int frequency){
            String summary;
            if(frequency == 1){
                summary = getString(R.string.setting_one_hour);
            } else {
                summary = String.format(getString(R.string.setting_hours), frequency);
            }

            Preference preference = findPreference(KEY_FREQUENCY);
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
