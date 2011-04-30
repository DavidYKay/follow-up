package com.followupapp.reminders;

import android.app.Activity;
import android.os.Bundle;

/**
 * Main activity of the application. Presents a list of people that the user has not yet followed up with.   
 * 
 * @author dk
 *
 */
public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}