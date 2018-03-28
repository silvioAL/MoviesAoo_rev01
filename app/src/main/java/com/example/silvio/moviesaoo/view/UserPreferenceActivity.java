package com.example.silvio.moviesaoo.view;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.silvio.moviesaoo.R;
import com.example.silvio.moviesaoo.util.PreferenceStorageUtil;

public class UserPreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preference);

        getFragmentManager().beginTransaction().replace(R.id.activityPrefs, new Settings()).commit();
    }

    public static class Settings extends PreferenceFragment {

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            PreferenceStorageUtil preferenceStorageUtil = new PreferenceStorageUtil(getActivity().getApplicationContext());
            CheckBoxPreference listFilterPreference = (CheckBoxPreference) findPreference(getActivity().getApplicationContext().getString(R.string.favorites));
            listFilterPreference.setOnPreferenceClickListener(preference -> {
                preferenceStorageUtil.saveFilteringMode(listFilterPreference.isChecked());
                return true;
            });

        }
    }
}


