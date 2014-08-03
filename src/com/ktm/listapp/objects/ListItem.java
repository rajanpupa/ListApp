package com.ktm.listapp.objects;

public class ListItem {
	Integer id = 0;
	String  title = "TITLE";
	String  content = "CONTENT";
	Integer parent_id = null;
	
	public ListItem(){};
	public ListItem(Integer id, String title, String content, Integer parent_id){
		this.id = id;
		this.title = title;
		this.content = content;
		this.parent_id = parent_id;
	}
	
	public ListItem (String title, String content, Integer parent_id){
		this.title = title;
		this.content = content;
		this.parent_id = parent_id;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	
	
}
