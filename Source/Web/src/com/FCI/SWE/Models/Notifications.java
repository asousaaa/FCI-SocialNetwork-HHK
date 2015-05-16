package com.FCI.SWE.Models;

import java.util.*;
import com.FCI.SWE.Notification.*;
import com.google.appengine.api.datastore.*;

public class Notifications {

	private String friend_name;
	private String user_id;
	private String friend_id;
	private String user_name;
	private String type;
	private String note;
	private String not_id;

	static DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	static Query gaeQuery = new Query();
	static PreparedQuery pq;

	public String getFriend_id() {
		return friend_id;
	}

	public void setFriend_id(String friend_id) {
		this.friend_id = friend_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getNot_id() {
		return not_id;
	}

	public void setNot_id(String not_id) {
		this.not_id = not_id;
	}

	/* Constructor to access data */
	public Notifications(String id, String name, String u_id, String u_name,
			String not, String n_id) {
		this.friend_id = id;
		this.friend_name = name;
		this.user_id = u_id;
		this.user_name = u_name;
		this.note = not;
		this.not_id = n_id;
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

	/**
	 * view Notification
	 * 
	 * @param userid
	 *            provided user id
	 * @return Status ArrayList<Notifications>
	 */

	public static ArrayList<Notifications> Notifiy(String user_id)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		ArrayList<Notifications> notifiy = new ArrayList<Notifications>();
		gaeQuery = new Query("Notifications");
		pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			// chech if frined id (sameone send to use so you are his friend)
			// equal user id
			if (entity.getProperty("friend_id").toString().equals(user_id)) {

				Notifications N = new Notifications(entity.getProperty(
						"user_id").toString(), entity.getProperty("user_name")
						.toString(),
						entity.getProperty("friend_id").toString(), entity
								.getProperty("friend_name").toString(), entity
								.getProperty("note").toString(),
						Long.toString(entity.getKey().getId()));

				// create object of notification and add to arrayList
				String type = "com.FCI.SWE.Notification."
						+ entity.getProperty("type").toString();
				UserNotification usernot = (UserNotification) Class.forName(
						type).newInstance();
				N.setType(usernot.extract(N.friend_name, N.note));
				notifiy.add(N);
			}
		}

		return notifiy;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}