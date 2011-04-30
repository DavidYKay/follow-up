package com.followupapp.reminders;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ReminderActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder);
        
        String smsSource = getIntent().getStringExtra(SmsIncomingReceiver.SMS_SOURCE);
        
        TextView replyDescription = (TextView)findViewById(R.id.replyDescription);
        replyDescription.setText("It looks like you haven't replied to the text recently received from " + smsSource + ".");

        TextView ignoreDescription = (TextView)findViewById(R.id.ignoreDescription);
        ignoreDescription.setText("If you don't want to get any more reminders for text messages received from " + smsSource + ", just ignore it.");

        Button ignoreButton = (Button)findViewById(R.id.ignoreButton);
        ignoreButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
        });
    }
}