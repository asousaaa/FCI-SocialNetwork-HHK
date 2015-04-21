package com.FCI.SWE.Models;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Page_timeLine {
	public String Page_ID;
	public String Page_name;
	public String ID;
 	public String post_ID;
	
	
	public String getPage_id() {
		return Page_ID;
	}
	public void setPage_id(String page_id) {
		this.Page_ID = page_id;
	}
	public String getPage_name() {
		return Page_name;
	}
	public void setPage_name(String Page_name) {
		this.Page_name = Page_name;
	}
	public String get_ID() {
		return ID;
	}
	public void set_ID(String page_id) {
		this.ID = page_id;
	}
	public String getPost_ID() {
		return post_ID;
	}
	public void setPost_ID(String post_ID) {
		this.post_ID = post_ID;
	}
	
	
}
