package com.rohan.android.assignments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity {

    Boolean checkBoxBool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_prefs);

        final CheckBoxPreference checkboxPref = (CheckBoxPreference) getPreferenceManager().findPreference("checkBoxPrefs");
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        checkboxPref.
                setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

                    Intent intent_sem1 = new Intent(SettingsActivity.this, SemesterActivity.class);

                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        if (newValue instanceof Boolean) {
                            checkBoxBool = (Boolean) newValue;
                        }
                        editor.putBoolean("boolValue", checkBoxBool);
                        editor.apply();
                        return true;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
