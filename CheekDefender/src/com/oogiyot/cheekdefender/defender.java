package com.oogiyot.cheekdefender;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;

public class defender extends Activity {
	
	
	public static final String TAG = "CheekDefender";

		
	IntentFilter filter;
	
	private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle extras = intent.getExtras();
			if (extras != null) {
				String state = extras.getString(TelephonyManager.EXTRA_STATE);
				Log.i(TAG,"defender-onReceive");
				if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
					Log.i(TAG,"defender-idle");
					// kill notification
				    ((NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(1);
					finish();
				}
			}
		}
	};
	

	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout);
        filter = new IntentFilter();
        filter.addAction("android.intent.action.PHONE_STATE");
		setNotification();
		
		
		Log.i(TAG,"defender-onCreate");

    }
	
	@Override
	public void onBackPressed() {
	   Log.i(TAG, "defender-onBackPressed Called");
	   Intent intent = new Intent(Intent.ACTION_DIAL);
		startActivity(intent);
	   return;
	}
	
	
    
    public void setNotification()
    {
    	Log.i(TAG,"defender-setNotification");
    	
    	String ns = Context.NOTIFICATION_SERVICE;
    	NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
    	
    	int icon = R.drawable.notify;        // icon from resources
    	CharSequence tickerText = "Cheek Defender";   // ticker-text
    	long when = System.currentTimeMillis();      // notification time
    	Context context = getApplicationContext();    // application Context
    	CharSequence contentText = "Click to lock";  // expanded message title
    	CharSequence contentTitle = tickerText;      // expanded message text
   
    	Intent notificationIntent = new Intent(this, defender.class);
    	PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

    	// the next two lines initialize the Notification, using the configurations above
    	Notification notification = new Notification(icon, tickerText, when);
    	notification.flags |= Notification.FLAG_ONGOING_EVENT;
    	notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
    	   	
    	mNotificationManager.notify(1, notification);
    }
    
    @Override
    protected void onResume() {
    	registerReceiver(mIntentReceiver, filter);
    	super.onResume();
    }

    @Override
    protected void onPause() {
    	unregisterReceiver(mIntentReceiver);
    	super.onPause();
    }
  

}