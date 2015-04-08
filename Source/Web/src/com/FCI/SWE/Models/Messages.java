package com.FCI.SWE.Models;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Messages {
	public String sender_id;
	public String sender_name;
	public String receiver_id;
 	public String receiver_name;
	public String msg_id;
	public String getSender_id() {
		return sender_id;
	}
	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}
	public String getSender_name() {
		return sender_name;
	}
	public void setSender_name(String sender_name) {
		this.sender_name = sender_name;
	}
	public String getReceiver_id() {
		return receiver_id;
	}
	public void setReceiver_id(String receiver_id) {
		this.receiver_id = receiver_id;
	}
	public String getReceiver_name() {
		return receiver_name;
	}
	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}
	public String getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}
	
	public String sendmsg(String user_id,String  fre_id,String user_name,
			String fre_name , String content){
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Messages");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		
		Entity employee = new Entity("Messages", list.size() + 2);
		employee.setProperty("friend_name", fre_name);
		employee.setProperty("user_name", user_name);
		employee.setProperty("friend_id", fre_id);
		employee.setProperty("user_id", user_id);
		employee.setProperty("content", content);
		datastore.put(employee);
		
		Query Q= new Query("Notifications");
		PreparedQuery p = datastore.prepare(Q);
		List<Entity> lists = p.asList(FetchOptions.Builder.withDefaults());

		Entity eme = new Entity("Notifications", lists.size() + 2);
		eme.setProperty("friend_name", fre_name);
		eme.setProperty("friend_id", fre_id);
		eme.setProperty("user_name", user_name);
		eme.setProperty("note", "accept");
		eme.setProperty("user_id", user_id);
		eme.setProperty("type", "Notifiy_Message");
		eme.setProperty("note", "empty");
		
		datastore.put(eme);
		
		return "accept";
	}
	
}
