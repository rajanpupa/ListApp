package com.ktm.listapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ktm.listapp.database.ListDatabaseHelper;
import com.ktm.listapp.objects.ListItem;

/**
 *	This activity will display the add_list UI 
 */
public class ListAddActivity extends Activity {

	Integer parent_id;
	
	private EditText title;
	private EditText content;

	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_add);
		
		parent_id = getIntent().getExtras().getInt("parent_id");
		title = (EditText) findViewById(R.id.title);
		content = (EditText) findViewById(R.id.content);
	}
	
	public void onSave(View view){
		String titleText = title.getText().toString();
		String contentText = content.getText().toString();
		ListDatabaseHelper loh = new ListDatabaseHelper(this);
		
		loh.insertItem(new ListItem(titleText, contentText, parent_id));
		finish();
	}
	
	public void onCancel(View view){
		finish();
	}
	
}
