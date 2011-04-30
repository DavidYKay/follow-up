package com.followupapp.reminders;

import android.database.ContentObserver;
import android.os.Handler;

public class SmsOutgoingObserver extends ContentObserver {
	public SmsOutgoingObserver(Handler handler) {
		super(handler);
	}

	@Override
	public boolean deliverSelfNotifications() {
		return super.deliverSelfNotifications();
	}

	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
	}
}
