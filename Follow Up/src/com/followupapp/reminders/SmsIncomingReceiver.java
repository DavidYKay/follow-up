package com.followupapp.reminders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsIncomingReceiver extends BroadcastReceiver {
	private static final int P_I_REQUEST_CODE = 240;
	public static final String SMS_RECEIVED_TIMESTAMP = "SMS_RECEIVED_TIMESTAMP";
	public static final String SMS_SOURCE_NUMBER = "SMS_SOURCE_NUMBER";
	public static final String SMS_SOURCE_NAME = "SMS_SOURCE_NAME";
	private static final String IGNORE_UNKNOWNS = "contacts_only";

	@Override
	public void onReceive(Context context, Intent intent) {
		boolean reminderNeeded = true; // TODO: come up with the correct criteria
		if (reminderNeeded) {
			// Get SMS details
	        Bundle extras = intent.getExtras();
	        if (extras == null) {
	            return;
	        }
	        
			SmsMessage message;
			String smsSourceNumber;
			String smsSourceName = null;
			long alarmTime;
			long smsTS;
            Uri uri;
            String[] projection;

            Object[] pdus = (Object[]) extras.get("pdus");

	        for (int i = 0; i < pdus.length; i++) {
	        	// Loop through each SMS
	            message = SmsMessage.createFromPdu((byte[]) pdus[i]);
	            smsTS = System.currentTimeMillis();
	            smsSourceNumber = message.getOriginatingAddress();
	            uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(smsSourceNumber));
	            projection = new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME };
	            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
	            boolean ignoreUnknowns = false;
	            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	            ignoreUnknowns = prefs.getBoolean(IGNORE_UNKNOWNS, ignoreUnknowns);
	            if (cursor != null) {
	                if (cursor.moveToFirst()) {
	                	smsSourceName = cursor.getString(0);
	                }
		            else {
		            	if (ignoreUnknowns) {
		            		continue;
		            	}
		            	smsSourceName = smsSourceNumber;
		            }
	                cursor.close();
	            }
	            else {
	            	if (ignoreUnknowns) {
	            		continue;
	            	}
	            	smsSourceName = smsSourceNumber;
	            }
	            alarmTime = smsTS + 15 * 1000;

	            // Set reminder alarm. Requirements:
	            //     One alarm per phone number, offset from the last message received from that number.
	            //     The intent delivered through the alarm must contain the latest data. Ensure no improper reuse.
				AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
				PendingIntent pendingIntent = createPendingIntent(context, smsSourceNumber, smsSourceName, smsTS);
		        alarmManager.cancel(pendingIntent);
				pendingIntent = createPendingIntent(context, smsSourceNumber, smsSourceName, smsTS);
		        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
		        Log.d("sms reply", "Set reminder. SMS source: " + smsSourceNumber + ". SMS reminder timestamp: " + smsTS);

				// Add a SmsOutgoingObserver
				SmsOutgoingObserver smsOutgoingObserver = new SmsOutgoingObserver(new Handler());
//				context.getContentResolver().registerContentObserver(Uri.parse("content://sms"), true, smsOutgoingObserver);
//				context.getContentResolver().registerContentObserver(Uri.parse("content://sms/sent"), true, smsOutgoingObserver);
//				context.getContentResolver().registerContentObserver(Uri.parse("content://sms/inbox"), true, smsOutgoingObserver);
//				context.getContentResolver().registerContentObserver(Uri.parse("content://sms/outbox"), true, smsOutgoingObserver);
				context.getContentResolver().registerContentObserver(Uri.parse("content://sms/status"), true, smsOutgoingObserver);
	        }
		}
	}
	
	private PendingIntent createPendingIntent(Context context, String smsSourceNumber, String smsSourceName, long smsTS) {
		Intent alarmIntent = createAlarmIntent(context, smsSourceNumber, smsSourceName, smsTS);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, P_I_REQUEST_CODE, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		return pendingIntent;
	}
	
	private Intent createAlarmIntent(Context context, String smsSourceNumber, String smsSourceName, long smsTS) {
        Intent alarmIntent = new Intent(context, ReminderReceiver.class);
        alarmIntent.setData(Uri.parse("http://reminders.followupapp.com/" + Uri.encode(smsSourceNumber)));
        alarmIntent.putExtra(SMS_SOURCE_NUMBER, smsSourceNumber);
        alarmIntent.putExtra(SMS_SOURCE_NAME, smsSourceName);
        alarmIntent.putExtra(SMS_RECEIVED_TIMESTAMP, smsTS);
        return alarmIntent;
	}
}
