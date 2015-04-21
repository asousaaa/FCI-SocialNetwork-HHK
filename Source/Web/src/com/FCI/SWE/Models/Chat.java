package com.FCI.SWE.Models;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

import com.FCI.SWE.Notification.UserNotification;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;


public class Chat {
	private String chat_id;
	private JSONArray senders;
	private String sender;
	private String recivers;
	private String group_chat;
	private String Chat_name;
	private String msg;
	
	Chat(String name,String names , String id) throws ParseException{
		this.Chat_name=name;
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(names);
		JSONArray array = (JSONArray) obj;
		this.senders=array;
		this.chat_id=id;
	}

	public Chat() {
		// TODO Auto-generated constructor stub
	}
	
	Chat(String _sender,String content,String name,boolean flage) {
		this.sender =_sender;
		this.msg = content;
		this.Chat_name = name;
	}

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

	public JSONArray getSenders() {
		return senders;
	}

	public void setSenders(JSONArray senders) {
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

	public boolean CreateChatGroup(String name, String owner, String senders,
			String ides) {

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

		Query Q = new Query("Group_Notifications");
		PreparedQuery p = datastore.prepare(Q);
		List<Entity> lists = p.asList(FetchOptions.Builder.withDefaults());

		Entity eme = new Entity("Group_Notifications", lists.size() + 2);
		eme.setProperty("GroupName", name);
		eme.setProperty("owner", owner);
		eme.setProperty("Type", "CreateGroupChat");
		eme.setProperty("note", "Create");
		datastore.put(eme);

		return true;
	}

	public boolean MsgChatGroup(String id, String Sender, String content) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Chats");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {

			if (Long.toString(entity.getKey().getId()).equals(id)) {

				Query gaeQuery1 = new Query("Chat_Group");
				PreparedQuery pq1 = datastore.prepare(gaeQuery1);
				List<Entity> list = pq1.asList(FetchOptions.Builder
						.withDefaults());

				Entity employee = new Entity("Chat_Group", list.size() + 2);

				employee.setProperty("GroupName", entity.getProperty("GroupName"));
				employee.setProperty("GroupId", Long.toString(entity.getKey().getId()));
				employee.setProperty("Sender", Sender);
				employee.setProperty("SeenBy", "");
				employee.setProperty("Content", content);
				datastore.put(employee);

				Query Q = new Query("Group_Notifications");
				PreparedQuery p = datastore.prepare(Q);
				List<Entity> lists = p.asList(FetchOptions.Builder
						.withDefaults());

				Entity eme = new Entity("Group_Notifications", lists.size() + 2);
				eme.setProperty("GroupName", entity.getProperty("GroupName"));
				eme.setProperty("owner", Sender);
				eme.setProperty("Type", "MsgGroupChat");
				eme.setProperty("note", entity.getProperty("Ides"));
				datastore.put(eme);

				return true;

			}
		}

		return false;
	}

	public ArrayList<Chat> ViewMsgChatGroup(String id) throws ParseException {
		JSONParser parser = new JSONParser();
		Object obj = null;
		
		ArrayList<Chat> MSG = new ArrayList<Chat>();
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Chat_Group");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if(entity.getProperty("GroupId").equals(id)){
				MSG.add(new Chat(entity.getProperty("Sender").toString(),
						entity.getProperty("Content").toString(),
						entity.getProperty("GroupName").toString(),true));
				
			}
			
		}
		
		return MSG;
	}
	
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ArrayList<Chat> ViewChatGroup(String userid) throws ParseException {
		JSONParser parser = new JSONParser();
		Object obj = null;
		
		ArrayList<Chat> group = new ArrayList<Chat>();
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Chats");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {

			String ides = entity.getProperty("Ides").toString();

			obj = parser.parse(ides);
			
			JSONArray array = (JSONArray) obj;
			for(int i = 0 ;i<array.size();i++){
				if(array.get(i).equals(userid)){
					group.add(new Chat(entity.getProperty("GroupName").toString(),
							entity.getProperty("Sender").toString(),
							 Long.toString(entity.getKey().getId())));
				}
				
			}
		}
		
		return group;
	}
	
	
	
}






