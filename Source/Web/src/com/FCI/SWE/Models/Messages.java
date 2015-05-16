package com.FCI.SWE.Models;

import java.util.*;
import com.google.appengine.api.datastore.*;


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

	/**
	 * function used to send massage
	 * put on datastore
	 * @param UserName
	 *            provided user name
	 * @param FriendID
	 *            provided friend id
	 * @param UserID
	 *            provided userid
	 * @param FriendName
	 *            provided Friend name
	 * @param Content
	 *            provided massege content           
	 *            
	 * @return Status String
	 */
	public String sendmsg(String UserID,String FriendID,String UserName,
			String FriendName , String Content){
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Messages");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		
		Entity Record = new Entity("Messages", list.size() + 2);
		Record.setProperty("friend_name", FriendName);
		Record.setProperty("user_name", UserName);
		Record.setProperty("friend_id", FriendID);
		Record.setProperty("user_id", UserID);
		Record.setProperty("content", Content);
		datastore.put(Record);
		
		Query Q= new Query("Notifications");
		PreparedQuery p = datastore.prepare(Q);
		List<Entity> list2 = p.asList(FetchOptions.Builder.withDefaults());

		Entity Record2 = new Entity("Notifications", list2.size() + 2);
		Record2.setProperty("friend_name", FriendName);
		Record2.setProperty("friend_id", FriendID);
		Record2.setProperty("user_name", UserName);
		Record2.setProperty("note", "accept");
		Record2.setProperty("user_id", UserID);
		Record2.setProperty("type", "Notifiy_Message");
		Record2.setProperty("note", "empty");
		
		datastore.put(Record2);
		
		return "accept";
	}	
}