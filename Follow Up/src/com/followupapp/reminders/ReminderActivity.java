package com.followupapp.reminders;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
        
        final String smsSourceNumber = getIntent().getStringExtra(SmsIncomingReceiver.SMS_SOURCE_NUMBER);
        final String smsSourceName = getIntent().getStringExtra(SmsIncomingReceiver.SMS_SOURCE_NAME);
        
        TextView replyDescription = (TextView)findViewById(R.id.replyDescription);
        replyDescription.setText("It looks like you haven't replied to the text message recently received from " + smsSourceName + ".");

        Button replyButton = (Button)findViewById(R.id.replyButton);
        replyButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + Uri.encode(smsSourceNumber)));
				ReminderActivity.this.startActivity(intent);
				finish();
			}
        });

        TextView ignoreDescription = (TextView)findViewById(R.id.ignoreDescription);
        ignoreDescription.setText("If you don't want to get any more reminders for text messages received from " + smsSourceName + ", just ignore it.");

        Button ignoreButton = (Button)findViewById(R.id.ignoreButton);
        ignoreButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
        });
    }
}