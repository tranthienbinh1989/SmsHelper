package com.ttb.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class ContactHandler extends SQLiteOpenHelper{

	//database version
	public static final int DATABASE_VERSION = 1;
	//database name
	public static final String DATABASE_NAME = "smshelper";
	//contact table name
	public static final String CONTACT_TABLE = "contacts";
	//contact columns name
	public static final String KEY_ID = BaseColumns._ID;
	public static final String KEY_NAME = "name";
	public static final String KEY_PH_NO = "phone_number";
	
	
	public ContactHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	//create contact table
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + CONTACT_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY ," 
				+ KEY_NAME + " TEXT, "
				+ KEY_PH_NO + " TEXT" + ");";
		Log.d("Create contact table", CREATE_CONTACTS_TABLE);
		db.execSQL(CREATE_CONTACTS_TABLE);
	}
	//upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Drop old table if exist
		db.execSQL("DROP TABLE IF EXIST " + CONTACT_TABLE);
		onCreate(db);
	}

}
