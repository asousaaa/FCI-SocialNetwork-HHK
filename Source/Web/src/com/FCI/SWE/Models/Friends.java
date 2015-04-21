package com.FCI.SWE.Models;

import java.util.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Friends {
	public String name;
	public String id;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	Friends(){}


public Friends(String name, String id) {
	
		this.name = name;
		this.id = id;
	}
public static ArrayList<Friends> Friendlist(String user_id){
	
	DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	
	ArrayList<Friends> list = new ArrayList<Friends>();
	
	Query gaeQuery = new Query("request");
	PreparedQuery pq = datastore.prepare(gaeQuery);
	for (Entity entity : pq.asIterable()) {
		// System.out.println(entity.getProperty("name").toString());
		if (entity.getProperty("friend_id").toString().equals(user_id)
				&& entity.getProperty("status").equals("accept")) {
			list.add(new Friends(entity.getProperty("user_name")
					.toString(), entity.getProperty("user_id").toString()));

		}
		else if (entity.getProperty("user_id").toString().equals(user_id)
				&& entity.getProperty("status").equals("accept")) {
			list.add(new Friends(entity.getProperty("friend_name")
					.toString(), entity.getProperty("friend_id").toString()));

		}
	}
	

	return list;
}

}
