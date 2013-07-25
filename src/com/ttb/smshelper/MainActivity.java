package com.ttb.smshelper;



import java.util.List;

import com.ttb.smshelper.MainActivity;
import com.ttb.smshelper.chanmessage;
import com.ttb.smshelper.lichmessage;
import com.ttb.smshelper.xoamessage;
import com.ttb.schedule.ScheduleHandler;
import com.ttb.smshelper.R;
import com.ttb.blocksms.BlockService;
import com.ttb.database.Blocker;
import com.ttb.database.BlockerDAO;
import com.ttb.database.Contact;
import com.ttb.database.ContactDAO;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static final String TYPE_NO_PHONE = "NUMBER_PHONE";
	private ScheduleHandler schedule;
	private Context context;
    private ImageButton xoa;
	private ImageButton chan;
	private ImageButton lich;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this.getApplicationContext();
		BlockerDAO blockerDAO = new BlockerDAO(this.getApplicationContext());
		
		blockerDAO.addBlocker(new Blocker("5556",TYPE_NO_PHONE));
//		Intent intent = new Intent();
//		intent.setAction("com.ttb.smshelper.SCHEDULE_SMS");
//		sendBroadcast(intent); 
		
//		schedule = new ScheduleHandler();
//		if(schedule != null){
//			schedule.SetAlarm(this.getApplicationContext());
//    	}else{
//    		Toast.makeText(this.getApplicationContext(), "Alarm is null", Toast.LENGTH_SHORT).show();
//    	}
		xoa=(ImageButton)findViewById(R.id.btnxoa);
        chan=(ImageButton)findViewById(R.id.btnchan);
        lich=(ImageButton)findViewById(R.id.btnlich);
        xoa.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(MainActivity.this,xoamessage.class);
				startActivity(intent);
					
			}
		});
        chan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				 Intent intent=new Intent(MainActivity.this,chanmessage.class);
				 startActivity(intent);
			}
		});
        lich.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				 Intent intent=new Intent(MainActivity.this,lichmessage.class);
				 startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onPause() {
		if(schedule != null)
		{
			schedule.CancelAlarm(context);
		}
		super.onPause();
		
	}

	@Override
	protected void onDestroy() {
		schedule.CancelAlarm(context);
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

}
