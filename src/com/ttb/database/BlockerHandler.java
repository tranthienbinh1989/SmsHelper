package com.ttb.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class BlockerHandler extends SQLiteOpenHelper {

	// database version
	public static final int DATABASE_VERSION = 1;
	// database name
	public static final String DATABASE_NAME = "smshelper";
	// contact table name
	public static final String BLOCK_TABLE = "block_list";
	// contact columns name
	public static final String KEY_ID = BaseColumns._ID;
	public static final String KEY_VALUE = "value";
	public static final String KEY_TYPE = "type";

	public BlockerHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		String createString = "CREATE TABLE " + BLOCK_TABLE + "( " + KEY_ID
				+ " INTEGER PRIMARY KEY, " + KEY_VALUE + " TEXT," + KEY_TYPE
				+ " TEXT" + ");";
		Log.d("BLOCK TABLE CREATE :", createString);
		db.execSQL(createString);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//drop table if exist
		String dropSql = "DROP TABLE IF EXIST " + BLOCK_TABLE;
		db.execSQL(dropSql);
		onCreate(db);
	}
}
