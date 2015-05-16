package com.FCI.SWE.Models;

import java.util.*;
import org.json.simple.parser.*;
import org.json.simple.JSONArray;
import com.google.appengine.api.datastore.*;

public class Chat {
	private String chat_id; // record id
	private JSONArray senders;
	private String sender;
	private String recivers;
	private String group_chat;
	private String Chat_name;
	private String msg;

	static DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	static Query gaeQuery = new Query();
	static PreparedQuery pq;
	static Entity Record;
	static List<Entity> lists;

	/* Constructor to initiolaize group chat */
	Chat(String name, String names, String id) throws ParseException {
		this.Chat_name = name;
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(names);
		JSONArray array = (JSONArray) obj;
		this.senders = array;
		this.chat_id = id;
	}

	public Chat() {
		// TODO Auto-generated constructor stub
	}

	/* Constructor to create normal chat */
	Chat(String _sender, String content, String name, boolean flage) {
		this.sender = _sender;
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

	/**
	 * create group chat
	 * 
	 * @param name
	 *            provided chat group chat name
	 * @param owner
	 *            provided chat group chat admin
	 * @param senders
	 *            provided people name in group chat
	 * @param ides
	 *            provided ides of members in chat
	 * @return Status boolean
	 */
	public boolean CreateChatGroup(String name, String owner, String senders,
			String ides) {

		// add group chat to DB
		gaeQuery = new Query("Chats");
		pq = datastore.prepare(gaeQuery);
		lists = pq.asList(FetchOptions.Builder.withDefaults());

		Record = new Entity("Chats", lists.size() + 2);

		Record.setProperty("GroupName", name);
		Record.setProperty("Sender", senders);
		Record.setProperty("Ides", ides);
		datastore.put(Record);

		// create notification about new group chat
		gaeQuery = new Query("Group_Notifications");
		pq = datastore.prepare(gaeQuery);
		lists = pq.asList(FetchOptions.Builder.withDefaults());

		Record = new Entity("Group_Notifications", lists.size() + 2);
		Record.setProperty("GroupName", name);
		Record.setProperty("owner", owner);
		Record.setProperty("Type", "CreateGroupChat");
		Record.setProperty("note", "Create");
		datastore.put(Record);

		return true;
	}

	/**
	 * send massage in group chat
	 * 
	 * @param id
	 *            provided group chat id
	 * @param sender
	 *            provided sender of the massage
	 * @param content
	 *            provided content of massage
	 * @return provide true if found group chat else return false
	 */
	public boolean MsgChatGroup(String id, String Sender, String content) {

		gaeQuery = new Query("Chats");
		pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {

			// find group chat in DB if found
			if (Long.toString(entity.getKey().getId()).equals(id)) {

				Query gaeQuery1 = new Query("Chat_Group");
				PreparedQuery pq1 = datastore.prepare(gaeQuery1);
				lists = pq1.asList(FetchOptions.Builder.withDefaults());

				// save message in DB table
				Record = new Entity("Chat_Group", lists.size() + 2);
				Record.setProperty("GroupName", entity.getProperty("GroupName"));
				Record.setProperty("GroupId",
						Long.toString(entity.getKey().getId()));
				Record.setProperty("Sender", Sender);
				Record.setProperty("SeenBy", "");
				Record.setProperty("Content", content);
				datastore.put(Record);

				// notify this update to notification DB table
				gaeQuery1 = new Query("Group_Notifications");
				pq1 = datastore.prepare(gaeQuery1);
				lists = pq1.asList(FetchOptions.Builder.withDefaults());

				Record = new Entity("Group_Notifications", lists.size() + 2);
				Record.setProperty("GroupName", entity.getProperty("GroupName"));
				Record.setProperty("owner", Sender);
				Record.setProperty("Type", "MsgGroupChat");
				Record.setProperty("note", entity.getProperty("Ides"));
				datastore.put(Record);

				return true;

			}
		}

		return false;
	}

	/**
	 * view massage in chat group
	 * 
	 * @param id
	 *            provided group chat id
	 * @return Status ArrayList<Chat> provided copy of msg in this group chat
	 */
	public ArrayList<Chat> ViewMsgChatGroup(String id) throws ParseException {

		ArrayList<Chat> MSG = new ArrayList<Chat>();

		gaeQuery = new Query("Chat_Group");
		pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("GroupId").equals(id)) {
				MSG.add(new Chat(entity.getProperty("Sender").toString(),
						entity.getProperty("Content").toString(), entity
								.getProperty("GroupName").toString(), true));

			}

		}

		// return arrayList of msg
		return MSG;
	}

	/**
	 * View Chat Group of this user
	 * 
	 * @param id
	 *            provided user id
	 * @return Status ArrayList <Chat>
	 */
	public ArrayList<Chat> ViewChatGroup(String userid) throws ParseException {
		JSONParser parser = new JSONParser();
		Object obj = null;

		ArrayList<Chat> group = new ArrayList<Chat>();

		gaeQuery = new Query("Chats");
		pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {

			String ides = entity.getProperty("Ides").toString();

			obj = parser.parse(ides);
			// get ides of this record then check if user id found then save
			// this record
			// in arrayList<chats>
			JSONArray array = (JSONArray) obj;
			for (int i = 0; i < array.size(); i++) {
				if (array.get(i).equals(userid)) {
					group.add(new Chat(entity.getProperty("GroupName")
							.toString(), entity.getProperty("Sender")
							.toString(), Long.toString(entity.getKey().getId())));
				}

			}
		}

		return group;
	}

}