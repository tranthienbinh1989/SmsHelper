package com.ttb.smshelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	private static final String TAG = DbHelper.class.getSimpleName();
	//DB constants
	private static final String DB_NAME = "smshelper.db";
	private static final int DB_VERSION = 2;
	//Table sms constants
	private static final String TABLE_SMS = "sms";
	private static final String _ID = BaseColumns._ID + " INTEGER PRIMARY KEY"; // Special for id
	private static final String THREAD_ID = "thread_id INTEGER";
	private static final String ADDRESS = "address TEXT";
	private static final String PERSON = "person INTEGER";
	private static final String DATE = "date INTEGER";
	private static final String PROTOCOL = "protocol INTEGER";
	private static final String READ = "read INTEGER DEFAULT 0";
	private static final String STATUS = "status INTEGER DEFAULT -1";
	private static final String TYPE = "type INTEGER";
	private static final String SUBJECT = "subject TEXT";
	private static final String BODY = "body TEXT";
	private static final String SERVICE_CENTER = "service_center TEXT";
	private static final String LOCKED = "locked INTEGER DEFAULT 0";
	private static final String ERROR_CODE = "error_code INTEGER DEFAULT 0";
	private static final String SEEN = "seen INTEGER DEFAULT 0";
	//Table BlockSms constants
	private static final String TABLE_BLOCK = "blocksms";
	private static final String BLOCK_ID = BaseColumns._ID + " INTEGER PRIMARY KEY";
	private static final String BLOCK_ADDRESS = "address TEXT";

	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		//create table sms
		String sql_sms = String.format("CREATE TABLE %s (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)", 
				TABLE_SMS,
				_ID,
				THREAD_ID,
				ADDRESS,
				PERSON,
				DATE,
				PROTOCOL,
				READ,
				STATUS,
				TYPE,
				SUBJECT,
				BODY,
				SERVICE_CENTER,
				LOCKED,
				ERROR_CODE,
				SEEN
				);
		Log.d(TAG, "onCreate table sms: "+ sql_sms);
		database.execSQL(sql_sms);
		//create table blocksms
		String sql_block_sms = String.format("CREATE TABLE %s (%s,%s)", TABLE_BLOCK,BLOCK_ID,BLOCK_ADDRESS);
		Log.d(TAG, "onCreate table block sms: "+ sql_block_sms);
		database.execSQL(sql_block_sms);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Typically you do ALTER TABLE... here
	    db.execSQL("drop table if exists " + TABLE_SMS);
	    Log.d(TAG, "onUpdate dropped table "+TABLE_SMS);
	    this.onCreate(db);
	}

}
