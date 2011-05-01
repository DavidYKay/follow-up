package com.followupapp.reminders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.Log;

public class SettingsActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Follow up settings");
        addPreferencesFromResource(R.xml.settings);

        final Context context = this;
        PreferenceScreen rootPS;
        Preference p;
        rootPS = getPreferenceScreen();
        p = rootPS.findPreference("sample_reminder");
        p.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
        		Intent intent = new Intent(context, ReminderActivity.class);
        		intent.putExtra(SmsIncomingReceiver.SMS_SOURCE_NUMBER, "+10000000000");
        		intent.putExtra(SmsIncomingReceiver.SMS_SOURCE_NAME, "John Tester");
        		context.startActivity(intent);
                return true;
            }
        });
	}
}
