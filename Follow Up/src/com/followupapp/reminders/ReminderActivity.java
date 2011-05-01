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
        replyDescription.setText("You have not replied to the text message recently received from " + smsSourceName + ".");

        Button replyButton = (Button)findViewById(R.id.replyButton);
        replyButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + Uri.encode(smsSourceNumber)));
				ReminderActivity.this.startActivity(intent);
				//TODO: Remove subsequent alarms
				finish();
			}
        });

        TextView ignoreFutureOnesDescription = (TextView)findViewById(R.id.ignoreFutureOnesDescription);
        ignoreFutureOnesDescription.setText("If you don't want to get any more reminders for text messages from " + smsSourceName + ", just ignore them.");

        Button ignoreFutureOnesButton = (Button)findViewById(R.id.ignoreFutureOnesButton);
        ignoreFutureOnesButton.setText("Ignore " + smsSourceName);
        ignoreFutureOnesButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO: Implement
			}
        });
    }
}