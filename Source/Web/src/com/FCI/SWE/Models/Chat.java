package com.FCI.SWE.Models;

import java.util.ArrayList;
import java.util.List;

import com.FCI.SWE.Notification.UserNotification;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Chat {
	private String chat_id;
	private String senders;
	private String recivers;
	private String group_chat;
	private String Chat_name;

	public String getChat_name() {
		return Chat_name;
	}

	public void setChat_name(String chat_name) {
		Chat_name = chat_name;
	}

	public String getChat_id() {
		return chat_id;
	}

	public void setChat_id(String chat_id) {
		this.chat_id = chat_id;
	}

	public String getSenders() {
		return senders;
	}

	public void setSenders(String senders) {
		this.senders = senders;
	}

	public String getRecivers() {
		return recivers;
	}

	public void setRecivers(String recivers) {
		this.recivers = recivers;
	}

	public String getGroup_chat() {
		return group_chat;
	}

	public void setGroup_chat(String group_chat) {
		this.group_chat = group_chat;
	}

	public boolean CreateChatGroup(String name,String owner, String senders, String ides) {
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Chats");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity employee = new Entity("Chats", list.size() + 2);

		employee.setProperty("GroupName", name);
		employee.setProperty("Sender", senders);
		employee.setProperty("Ides", ides);
		datastore.put(employee);

	
		Query Q= new Query("Group_Notifications");
		PreparedQuery p = datastore.prepare(Q);
		List<Entity> lists = p.asList(FetchOptions.Builder.withDefaults());

		Entity eme = new Entity("Group_Notifications", list.size() + 2);
		eme.setProperty("GroupName", name);
		eme.setProperty("owner", owner);
		eme.setProperty("Type", "CreateGroupChat");
		eme.setProperty("note", "Create");
		datastore.put(eme);
		
		return true;
	}

	public boolean MsgChatGroup(String name, String Sender , String content) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Chats");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {

			if (entity.getProperty("GroupName").toString().equals(name)) {

				Query gaeQuery1 = new Query("Chat_Group");
				PreparedQuery pq1 = datastore.prepare(gaeQuery1);
				List<Entity> list = pq1.asList(FetchOptions.Builder.withDefaults());

				Entity employee = new Entity("Chat_Group", list.size() + 2);

				employee.setProperty("GroupName", name);
				employee.setProperty("Sender", Sender);
				employee.setProperty("SeenBy", "");
				employee.setProperty("Content",content);
				datastore.put(employee);


				Query Q= new Query("Group_Notifications");
				PreparedQuery p = datastore.prepare(Q);
				List<Entity> lists = p.asList(FetchOptions.Builder.withDefaults());

				Entity eme = new Entity("Group_Notifications", list.size() + 2);
				eme.setProperty("GroupName", name);
				eme.setProperty("owner", Sender);
				eme.setProperty("Type", "MsgGroupChat");
				eme.setProperty("note", entity.getProperty("Ides"));
				datastore.put(eme);
				
				
				return true ;
				
			}
		}

		return false;
	}
}