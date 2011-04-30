package com.followupapp.reminders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsIncomingReceiver extends BroadcastReceiver {
	private static final int P_I_REQUEST_CODE = 240;
	public static final String SMS_RECEIVED_TIMESTAMP = "SMS_RECEIVED_TIMESTAMP";

	@Override
	public void onReceive(Context context, Intent intent) {
		boolean reminderNeeded = true; // come up with the correct criteria
		if (reminderNeeded) {
			// Get SMS details
			SmsMessage message;
			String smsSource;
			long alarmTime;
			long smsTS = System.currentTimeMillis();
	        Bundle extras = intent.getExtras();
	        if (extras == null) {
	            return;
	        }
	        
	        Object[] pdus = (Object[]) extras.get("pdus");

	        for (int i = 0; i < pdus.length; i++) {
	        	// Loop through each SMS
	            message = SmsMessage.createFromPdu((byte[]) pdus[i]);
	            smsSource = message.getOriginatingAddress();
	            alarmTime = smsTS + 30 * 1000;

	            // Set reminder alarm
				AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
				PendingIntent pendingIntent = createPendingIntent(context, smsSource, smsTS);
		        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
		        System.out.println("Set sms reply reminder. SMS source: " + smsSource + ". SMS reminder timestamp: " + smsTS);

				// Add a SmsOutgoingObserver
				
	        }
		}
	}
	
	private PendingIntent createPendingIntent(Context context, String smsSource, long smsTS) {
		Intent alarmIntent = createAlarmIntent(context, smsSource, smsTS);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, P_I_REQUEST_CODE, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
		return pendingIntent;
	}
	
	private Intent createAlarmIntent(Context context, String smsSource, long smsTS) {
        Intent alarmIntent = new Intent(context, ReminderReceiver.class);
        alarmIntent.setData(Uri.parse("http://reminders.followupapp.com/" + Uri.encode(smsSource)));
        alarmIntent.putExtra(SMS_RECEIVED_TIMESTAMP, smsTS);
        return alarmIntent;
	}
}
