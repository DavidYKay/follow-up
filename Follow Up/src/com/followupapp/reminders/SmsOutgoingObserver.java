package com.followupapp.reminders;

import android.database.ContentObserver;
import android.os.Handler;
import android.util.Log;

public class SmsOutgoingObserver extends ContentObserver {
	public SmsOutgoingObserver(Handler handler) {
		super(handler);
	}

	@Override
	public boolean deliverSelfNotifications() {
		Log.d("sms reply", "deliverSelfNotifications()");
		return super.deliverSelfNotifications();
	}

	@Override
	public void onChange(boolean selfChange) {
		Log.d("sms reply", "onChange(): "+ selfChange);
		super.onChange(selfChange);
	}
}
