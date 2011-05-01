package com.followupapp.reminders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceClickListener;

public class SettingsActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Follow up settings");
        addPreferencesFromResource(R.xml.settings);

        final Context context = this;
        PreferenceScreen rootPS;
        PreferenceCategory pc;
        Preference p;

        rootPS = getPreferenceScreen();

        p = rootPS.findPreference("sample_reminder");
        p.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
        		Intent intent = new Intent(context, ReminderActivity.class);
        		intent.putExtra(SmsIncomingReceiver.SMS_SOURCE_NUMBER, "+10000000000");
        		intent.putExtra(SmsIncomingReceiver.SMS_SOURCE_NAME, "John Example");
        		context.startActivity(intent);
                return true;
            }
        });
        
        pc = (PreferenceCategory)rootPS.findPreference("messages_awaiting_reply");

        SharedPreferences prefsRead = PreferenceManager.getDefaultSharedPreferences(context);
        String from;
        String message;
        int writtenMessagesCount = prefsRead.getInt(SmsIncomingReceiver.WRITTEN_MESSAGES_COUNT, 0);
        for (int i = 0; i < writtenMessagesCount; i++) {
        	from = prefsRead.getString(SmsIncomingReceiver.SMS_SOURCE_NAME + i, "");
        	message = prefsRead.getString(SmsIncomingReceiver.SMS_MESSAGE + i, "");
            p = new Preference(this);
            p.setTitle(from);
            p.setSummary(message);
            pc.addPreference(p);
        }
	}
}
