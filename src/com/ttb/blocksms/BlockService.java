package com.ttb.blocksms;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ttb.database.Blocker;
import com.ttb.database.BlockerDAO;
import com.ttb.schedule.ScheduleHandler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class BlockService extends BroadcastReceiver {
	private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
	private static final String SCHEDULE_SMS = "com.ttb.smshelper.SCHEDULE_SMS";
	private Boolean block = false;
	public static final String ONE_TIME = "onetime";

	@Override
	public void onReceive(Context context, Intent intent) {
		// ---get the SMS message passed in---
		if (intent.getAction().equals(SMS_RECEIVED)) {

			Bundle bundle = intent.getExtras();
			SmsMessage[] msgs = null;
			String str = "";
			if (bundle != null) {
				// ---retrieve the SMS message received---
				Object[] pdus = (Object[]) bundle.get("pdus");
				msgs = new SmsMessage[pdus.length];
				for (int i = 0; i < msgs.length; i++) {
					msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
					BlockerDAO blockerDAO = new BlockerDAO(context);
					String address = msgs[i].getOriginatingAddress();
					Blocker blocker = blockerDAO.getBlockerFromValueType(
							address, "NUMBER_PHONE");
					if (blocker.getValue() != null)
						;
					{
						block = true;
					}
					str += "SMS from " + msgs[i].getOriginatingAddress();
					str += " :";
					str += msgs[i].getMessageBody().toString();
					str += "\n";
				}
				// ---display the new SMS message---
				Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
			}
			if (block) {
				abortBroadcast();
			}
		}
		if (intent.getAction().equals(SCHEDULE_SMS)) {
			PowerManager pm = (PowerManager) context
					.getSystemService(Context.POWER_SERVICE);
			PowerManager.WakeLock wl = pm.newWakeLock(
					PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
			// Acquire the lock
			wl.acquire();

			// You can do the processing here update the widget/remote views.
			Bundle extras = intent.getExtras();
			StringBuilder msgStr = new StringBuilder();

			if (extras != null && extras.getBoolean(ONE_TIME, Boolean.FALSE)) {
				msgStr.append("One time Timer : ");
			}
			Format formatter = new SimpleDateFormat("hh:mm:ss a");
			msgStr.append(formatter.format(new Date()));

			Toast.makeText(context, msgStr, Toast.LENGTH_LONG).show();
			Log.d("RECIVED :", msgStr.toString());
			// Release the lock
			wl.release();
		}

	}

	public void SetAlarm(Context context) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, BlockService.class);
		intent.putExtra(ONE_TIME, Boolean.FALSE);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		// After after 30 seconds
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
				1000 * 5, pi);
	}

	public void CancelAlarm(Context context) {
		Intent intent = new Intent(context, BlockService.class);
		PendingIntent sender = PendingIntent
				.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}
}
