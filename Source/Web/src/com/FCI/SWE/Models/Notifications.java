package com.FCI.SWE.Models;

import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Notifications {

	private String friend_name;
	private String user_id;
	private String type;
	private String not_id;
	
	public String getN_id() {
		return not_id;
	}

	public void setN_id(String n_id) {
		this.not_id = n_id;
	}

	public Notifications(String name,String u_id,String t,String n_id){
		this.friend_name=name;
		this.user_id=u_id;
		this.type=t;
		this.not_id=n_id;
	}
	
	public String getFriend_name() {
		return friend_name;
	}
	public void setFriend_name(String friend_name) {
		this.friend_name = friend_name;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	public static ArrayList<Notifications> Notifiy(String user_id) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		ArrayList<Notifications> not = new ArrayList<Notifications>();
		Query gaeQuery = new Query("Notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			// System.out.println(entity.getProperty("name").toString());
			if (entity.getProperty("name").toString().equals(user_id)) {
				not.add(new Notifications(entity.getProperty("name")
						.toString(), entity.getProperty("email").toString(),
						entity.getProperty("password").toString(), Long
								.toString(entity.getKey().getId())));

			}
		}

		return not;
	}

	
}
