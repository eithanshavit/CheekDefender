package com.oogiyot.cheekdefender;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;


public class listener extends BroadcastReceiver {

	public static final String TAG = "CheekDefender";
	@Override
	public void onReceive(Context context, Intent intent) {
	
		Bundle extras = intent.getExtras();
		if (extras != null) {
			String state = extras.getString(TelephonyManager.EXTRA_STATE);
			Log.i(TAG,"listener-onReceive");
			if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
				Log.i(TAG,"listener-offhook");
				
				Log.i(TAG,"listener-launching defender");
				Intent i = new Intent(context, defender.class);
				i.addFlags(Intent.FLAG_FROM_BACKGROUND); 
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);   // Added 
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				context.startActivity(i);		
			
			}
			if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
				Log.i(TAG,"listener-idle");
				// kill notification
			    ((NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(1);
			   
			}
		}
	}
}