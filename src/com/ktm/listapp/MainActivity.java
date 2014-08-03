package com.ktm.listapp;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ktm.listapp.database.ListDatabaseHelper;
import com.ktm.listapp.objects.ListItem;

/*
 *	The main activity which displays the list of items
 */
public class MainActivity extends ListActivity {

	Integer id = -1;
	ListDatabaseHelper loh = new ListDatabaseHelper(this);
	String [] list = {"DB not Working"};
	Integer[] idList = {-1};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        //loh.deleteAll();
        try{
        	id = getIntent().getExtras().getInt("parent_id");
        }catch(NullPointerException e){
        	Log.d("Exception", "The intent throws null");
        }
        list = getListTitles(id);
        
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_list_item_1, list);
        
        setListAdapter (adapter);
        
    }



    
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		drillListActivity(idList[position]);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()){
    	case R.id.new_list:
    		addNewList();
    		return true;
    	case R.id.menu_settings :
    		return true;
    	default:
    		return false;
    	}
    }
    
    
    //other than the android stuff
    //returns the array of titles
    private String[] getListTitles(Integer parent_id){
    	String [] titles = null;
    	Integer[] ids = null;
    	
    	List<String> titleList = new ArrayList<String>();
    	List<Integer> idList = new ArrayList<Integer>();
    	
    	for(ListItem item : loh.getList(parent_id)){
    		titleList.add(item.getTitle());
    		idList.add(item.getId());
    	}
    	
    	titles = new String[titleList.size()];//titleList.toArray(titles);
    	ids = new Integer[idList.size()];
    	
    	for(int i=0;i<titleList.size(); i++){
    		titles[i] = titleList.get(i);
    		ids[i] = idList.get(i);
    	}
    	
    	this.idList = ids;
    	return titles;
    }
    
    public void drillListActivity(Integer parent_id){
    	Intent i = new Intent(getBaseContext(), MainActivity.class);
    	i.putExtra("parent_id", parent_id);
    	startActivity(i);
    }
    
    public void addNewList(){
    	Intent i = new Intent(getBaseContext(), ListAddActivity.class);
    	i.putExtra("parent_id", this.id);
    	startActivity(i);
    }

}
