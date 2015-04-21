package com.FCI.SWE.Models;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class User_timeLine {
	public String user_ID;
	public String user_name;
	public String page_ID;
 	public String post_ID;
	
	
	public String getUser_id() {
		return user_ID;
	}
	public void setUser_id(String user_id) {
		this.user_ID = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPage_ID() {
		return page_ID;
	}
	public void setPage_ID(String page_id) {
		this.page_ID = page_id;
	}
	public String getPost_ID() {
		return post_ID;
	}
	public void setPost_ID(String post_ID) {
		this.post_ID = post_ID;
	}
	
	
}
