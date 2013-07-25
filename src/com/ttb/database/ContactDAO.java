package com.ttb.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class ContactDAO {
	private SQLiteDatabase database;
	private ContactHandler contacthandler;
	private String[] allColums = {ContactHandler.KEY_ID,ContactHandler.KEY_NAME,
			ContactHandler.KEY_PH_NO};
	public ContactDAO(Context context)
	{
		contacthandler = new ContactHandler(context);
	}
	public void open()
	{
		database = contacthandler.getWritableDatabase();
	}
	public void close()
	{
		contacthandler.close();
	}
	//add contact
	public void addContact(Contact contact)
	{
		this.open();
		ContentValues values = new ContentValues();
		values.put(ContactHandler.KEY_NAME, contact.getName());
		values.put(ContactHandler.KEY_PH_NO, contact.getPhone_number());
		//insert row
		database.insert(ContactHandler.CONTACT_TABLE, null, values);
		this.close();
	}
	//get single contact
	public Contact  getContact(int id) {
		Cursor cursor = database.query(ContactHandler.CONTACT_TABLE, 
				allColums, 
				ContactHandler.KEY_ID + "=?", new String[] {String.valueOf(id)}, 
				null, null, null);
		if(cursor != null)
		{
			cursor.moveToFirst();
			
		}
		Contact contact = new Contact();
		contact.setId(Integer.parseInt(cursor.getString(0)));
		contact.setName(cursor.getString(1));
		contact.setPhone_number(cursor.getString(2));
		//return contact
		return contact;
	}
	//Get all contacts
	public List<Contact> getAllContacts() {
		List<Contact> contactList = new ArrayList<Contact>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + ContactHandler.CONTACT_TABLE;
	 
	    this.open();
	    Cursor cursor = database.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	            Contact contact = new Contact();
	            contact.setId(Integer.parseInt(cursor.getString(0)));
	            contact.setName(cursor.getString(1));
	            contact.setPhone_number(cursor.getString(2));
	            // Adding contact to list
	            contactList.add(contact);
	        } while (cursor.moveToNext());
	    }
	    this.close();
	    // return contact list
	    return contactList;
	}
	//delete single contact
	public void deleteContact(int id)
	{
		this.open();
		database.delete(ContactHandler.CONTACT_TABLE, ContactHandler.KEY_ID + "=?" , new String[]{String.valueOf(id)});
		this.close();
	}
	//update single contact
	public void updateContact(Contact contact)
	{
		this.open();
		ContentValues values = new ContentValues();
		values.put(ContactHandler.KEY_NAME, contact.getName());
		values.put(ContactHandler.KEY_PH_NO, contact.getPhone_number());
		//update
		database.update(ContactHandler.CONTACT_TABLE, values,ContactHandler.KEY_ID + "=?",
				new String[]{String.valueOf(contact.getId())});
		this.close();
	}
	// Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + ContactHandler.CONTACT_TABLE;
        this.open();
        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
}
