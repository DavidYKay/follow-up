package com.followupapp.reminders;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Listens for all received SMS. Hopefully will handle the sent SMSs too.
 * May need to use a ContentObserver for the outbound. 
 * http://developer.android.com/reference/android/database/ContentObserver.html
 * 
 * @author dk
 *
 */
public class SMSBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub

	}

}
