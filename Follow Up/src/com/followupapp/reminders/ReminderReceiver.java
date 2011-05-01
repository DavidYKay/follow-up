package com.followupapp.reminders;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ReminderReceiver extends BroadcastReceiver {
	private static final int P_I_NOTIFICATION_CODE = 241;

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("sms reply", "Got reminder. Timestamp: " + intent.getLongExtra(SmsIncomingReceiver.SMS_RECEIVED_TIMESTAMP, -1L));
		String smsSource = intent.getStringExtra(SmsIncomingReceiver.SMS_SOURCE);
		showReplyReminderNotification(context, smsSource);
	}
	
	private static void showReplyReminderNotification(Context context, String smsSource) {
		Intent positiveIntent = new Intent(context, ReminderActivity.class);
		positiveIntent.putExtra(SmsIncomingReceiver.SMS_SOURCE, smsSource);
		PendingIntent positivePendingIntent = PendingIntent.getActivity(context, P_I_NOTIFICATION_CODE, positiveIntent, 0);

		Intent negativeIntent = new Intent(context, ReminderActivity.class);
		negativeIntent.putExtra(SmsIncomingReceiver.SMS_SOURCE, smsSource);
//		PendingIntent negativePendingIntent = PendingIntent.getActivity(context, P_I_NOTIFICATION_CODE, negativeIntent, 0);
		PendingIntent negativePendingIntent = null;

		String tickerText = "Reminder: reply to " + smsSource;
		String from = "Follow up";
		String message = "Reminder: reply to " + smsSource;
		showNotification(context, positivePendingIntent, negativePendingIntent, tickerText, from, message, smsSource);
	}

    protected static void showNotification(Context context, PendingIntent positiveIntent, PendingIntent negativeIntent, 
    										String tickerText, CharSequence from, CharSequence message, String smsSource) {
        NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.notification_icon, tickerText, System.currentTimeMillis());
        notification.setLatestEventInfo(context, from, message, positiveIntent);
    	notification.defaults = Notification.DEFAULT_ALL;
    	if (negativeIntent != null) {
        	notification.deleteIntent = negativeIntent;
    	}
    	notification.flags = notification.flags | Notification.FLAG_AUTO_CANCEL;
	    int uniqueId = smsSource.hashCode();
        nm.notify(uniqueId, notification); // unique ids allow for concurrent notifications
    }
}
