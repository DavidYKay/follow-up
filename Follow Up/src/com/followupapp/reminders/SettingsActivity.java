package com.followupapp.reminders;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Follow up settings");
        addPreferencesFromResource(R.xml.settings);
	}
}
