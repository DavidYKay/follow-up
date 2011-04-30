package com.followupapp.reminders;

import android.database.ContentObserver;
import android.os.Handler;

public class SmsOutgoingObserver extends ContentObserver {
	public SmsOutgoingObserver(Handler handler) {
		super(handler);
	}
}
