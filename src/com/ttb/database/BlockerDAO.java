package com.ttb.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BlockerDAO {
	private SQLiteDatabase database;
	private BlockerHandler blockerhandler;
	private String[] allColums = {BlockerHandler.KEY_ID,BlockerHandler.KEY_VALUE,
			BlockerHandler.KEY_TYPE};
	private static final String BLOCK_TABLE = BlockerHandler.BLOCK_TABLE;
	private static final String ID = BlockerHandler.KEY_ID;
	private static final String VALUE =  BlockerHandler.KEY_VALUE;
	private static final String TYPE =  BlockerHandler.KEY_TYPE;
	
	public BlockerDAO(Context context)
	{
		blockerhandler = new BlockerHandler(context);
	}
	public void open()
	{
		database = blockerhandler.getWritableDatabase();
	}
	public void close()
	{
		blockerhandler.close();
	}
	//add blocker
	public void addBlocker(Blocker blocker)
	{
		this.open();
		ContentValues values = new ContentValues();
		values.put(VALUE, blocker.getValue());
		values.put(TYPE, blocker.getType());
		//insert row
		database.insert(BLOCK_TABLE, null, values);
		this.close();
	}
	//get single blocker
	public Blocker  getBlocker(int id) {
		this.open();
		Cursor cursor = database.query(BLOCK_TABLE, 
				allColums, 
				ID + "=?", new String[] {String.valueOf(id)}, 
				null, null, null);
		if(cursor != null)
		{
			cursor.moveToFirst();
			
		}
		Blocker blocker = new Blocker();
		blocker.setId(Integer.parseInt(cursor.getString(0)));
		blocker.setValue(cursor.getString(1));
		blocker.setType(cursor.getString(2));
		this.open();
		//return blocker
		return blocker;
	}
	//Get all blockers
	public List<Blocker> getAllContacts() {
		List<Blocker> blockerList = new ArrayList<Blocker>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + BLOCK_TABLE;
	 
	    this.open();
	    Cursor cursor = database.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	            Blocker blocker = new Blocker();
	            blocker.setId(Integer.parseInt(cursor.getString(0)));
	            blocker.setValue(cursor.getString(1));
	            blocker.setType(cursor.getString(2));
	            // Adding contact to list
	            blockerList.add(blocker);
	        } while (cursor.moveToNext());
	    }
	    this.close();
	    // return blocker list
	    return blockerList;
	}
	//delete single blocker
	public void deleteBlocker(int id)
	{
		this.open();
		database.delete(BLOCK_TABLE, ID + "=?" , new String[]{String.valueOf(id)});
		this.close();
	}
	//update single blocker
	public void updateContact(Blocker blocker)
	{
		this.open();
		ContentValues values = new ContentValues();
		values.put(VALUE, blocker.getValue());
		values.put(TYPE, blocker.getType());
		//update
		database.update(BLOCK_TABLE, values,ID + "=?",
				new String[]{String.valueOf(blocker.getId())});
		this.close();
	}
	// Getting blockers Count
    public int getBlockerCount() {
    	
        String countQuery = "SELECT  * FROM " + BLOCK_TABLE;
        this.open();
        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.close();
        this.close();
        // return count
        return cursor.getCount();
    }
    public Blocker  getBlockerFromValueType(String value, String type) {
		this.open();
    	Cursor cursor = database.query(BLOCK_TABLE, 
				allColums, 
				VALUE + "=?" + "AND TYPE =?", new String[] {value,type}, 
				null, null, null);
    	Blocker blocker = new Blocker();
		if(cursor != null && cursor.getCount() > 0)
		{
			cursor.moveToFirst();
			blocker.setId(Integer.parseInt(cursor.getString(0)));
			blocker.setValue(cursor.getString(1));
			blocker.setType(cursor.getString(2));
		}
		
		this.close();
		//return blocker
		return blocker;
	}
}
