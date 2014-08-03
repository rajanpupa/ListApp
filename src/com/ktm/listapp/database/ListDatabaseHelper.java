package com.ktm.listapp.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ktm.listapp.objects.ListItem;

public class ListDatabaseHelper extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "LISTAPPDB";
	public static final String LIST_TABLE_NAME = "LISTAPP";
	// queries
	public static final String TABLE_CREATE_QUERY = 
			"CREATE TABLE "	+ LIST_TABLE_NAME + "(" +
				" list_id integer primary key autoincrement default 0, "
			+ 	 "title text, "
			+ 	 "content text, " 
			+ 	 "parent_id integer " 
			+ ")";
	
	public ListDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null , DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			//db.execSQL("Drop table " + LIST_TABLE_NAME);
			db.execSQL(TABLE_CREATE_QUERY);
		} catch (Exception e) {
			Log.i(LIST_TABLE_NAME, "tables already exist");
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + LIST_TABLE_NAME);
 
        // Create tables again
        onCreate(db);
	}

	//CRUD operations
	public void deleteAll(){
		
		SQLiteDatabase db = getWritableDatabase();
		db.delete(LIST_TABLE_NAME, null, null);
		
	}
	//insert single item
	public boolean insertItem(ListItem listItem) {
		boolean status = true;
		
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put("title", listItem.getTitle());
		values.put("content", listItem.getContent());
		values.put("parent_id", listItem.getParent_id());

		status = (db.insert(LIST_TABLE_NAME, null, values) > 0);
		db.close();
		return status;
	}

	//Get all list items in a category
	public List<ListItem> getList(Integer parent_id){
		List<ListItem> listItem = new ArrayList<ListItem> ();
		
		String query = "SELECT * FROM " + LIST_TABLE_NAME + " WHERE parent_id = " + parent_id;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		if(cursor.moveToFirst()){
			do{
				listItem.add(new ListItem(
						Integer.parseInt(cursor.getString(0)),
						cursor.getString(1),
						cursor.getString(2),
						Integer.parseInt(cursor.getString(3))
						));
			} while(cursor.moveToNext());
		}
		return listItem;
	}
	
	//Get single list
	public ListItem getListItem(Integer id){
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(LIST_TABLE_NAME, new String[] {"list_id", "title", "content", "parent_id"}, 
				"parent_id = ?", new String[] {String.valueOf(id)}, null, null, null );
		
		if(cursor != null){
			cursor.moveToFirst();
		}
		
		ListItem listItem = new ListItem(
				Integer.parseInt(cursor.getString(0)), 
				cursor.getString(1),
				cursor.getString(2),
				Integer.parseInt(cursor.getString(3))
				);
		
		return listItem;
	}
	
	// Get count of lists inside a category
	public Integer getCount(Integer parent_id){
		return 0;
	}
	
	//Updating single contact
	public int update(ListItem listItem){
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("title", listItem.getTitle());
		values.put("content", listItem.getContent());
		values.put("parent_id", listItem.getParent_id());

		// updating row
		return db.update(LIST_TABLE_NAME, values, "list_id = ?",
				new String[] { String.valueOf(listItem.getId()) });
	}
	
	//delete single contact
	public void deleteList(ListItem listItem){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(LIST_TABLE_NAME, "list_id = ?",
				new String[] { String.valueOf(listItem.getId()) });
		db.close();
	}
}
