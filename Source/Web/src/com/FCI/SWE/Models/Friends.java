package com.FCI.SWE.Models;

import java.util.*;
import com.google.appengine.api.datastore.*;

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

	/**
	 * Constructor to accept data
	 */
	public Friends() {

	}

	/**
	 * constructor
	 * 
	 * @param name
	 *            provided name
	 * @param id
	 *            provided id
	 */
	public Friends(String name, String id) {

		this.name = name;
		this.id = id;

	}

	/**
	 * function is used to list names of friends
	 * 
	 * @param user_id
	 *            provided user id
	 * @return Status ArrayList<Friends>
	 */
	public static ArrayList<Friends> Friendlist(String user_id) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		ArrayList<Friends> list = new ArrayList<Friends>();
		Query gaeQuery = new Query("request");
		PreparedQuery pq = datastore.prepare(gaeQuery);

		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("friend_id").toString().equals(user_id)
					&& entity.getProperty("status").equals("accept")) {
				list.add(new Friends(
						entity.getProperty("user_name").toString(), entity
								.getProperty("user_id").toString()));

			} else if (entity.getProperty("user_id").toString().equals(user_id)
					&& entity.getProperty("status").equals("accept")) {
				list.add(new Friends(entity.getProperty("friend_name")
						.toString(), entity.getProperty("friend_id").toString()));

			}
		}

		return list;
	}
}