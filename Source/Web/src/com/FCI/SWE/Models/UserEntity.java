package com.FCI.SWE.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/**
 * <h1>User Entity class</h1>
 * <p>
 * This class will act as a model for user, it will holds user data
 * </p>
 *
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 */
public class UserEntity {
	private String name;
	private String email;
	private String password;
	private String id;
	private String friend_name;
	private String user_id;

	/**
	 * Constructor accepts user data
	 * 
	 * @param name
	 *            user name
	 * @param email
	 *            user email
	 * @param password
	 *            user provided password
	 */
	public UserEntity() {

	}

	public UserEntity(String name, String email, String password, String id) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.id = id;

	}

	public UserEntity(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public UserEntity(String friend_name, String friend_id, String user_id,
			int i) {
		this.friend_name = friend_name;
		this.id = friend_id;
		this.user_id = user_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFriend_name() {
		return friend_name;
	}

	public void setFriend_name(String friend_name) {
		this.friend_name = friend_name;
	}

	public String getuser_id() {
		return user_id;
	}

	public void setuser_id(String user_id) {
		this.user_id = user_id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPass() {
		return password;
	}

	public String get_fname() {
		return friend_name;
	}

	public String get_userid() {
		return user_id;
	}

	/**
	 * 
	 * This static method will form UserEntity class using json format contains
	 * user data
	 * 
	 * @param json
	 *            String in json format contains user data
	 * @return Constructed user entity
	 */
	public static UserEntity getUser(String json) {

		JSONParser parser = new JSONParser();
		try {
			JSONObject object = (JSONObject) parser.parse(json);
			return new UserEntity(object.get("name").toString(), object.get(
					"email").toString(), object.get("password").toString(),
					object.get("ID").toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 
	 * This static method will form UserEntity class using user name and
	 * password This method will serach for user in datastore
	 * 
	 * @param name
	 *            user name
	 * @param pass
	 *            user password
	 * @return Constructed user entity
	 */

	public static UserEntity getUser(String name, String pass) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			// System.out.println(entity.getProperty("name").toString());
			if (entity.getProperty("name").toString().equals(name)
					&& entity.getProperty("password").toString().equals(pass)) {
				UserEntity returnedUser = new UserEntity(entity.getProperty(
						"name").toString(), entity.getProperty("email")
						.toString(), entity.getProperty("password").toString(),
						Long.toString(entity.getKey().getId()));
				return returnedUser;
			}
		}

		return null;
	}

	public static ArrayList<UserEntity> searchforuser(String name) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		ArrayList<UserEntity> returnedUser = new ArrayList<UserEntity>();
		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			// System.out.println(entity.getProperty("name").toString());
			if (entity.getProperty("name").toString().equals(name)) {
				returnedUser.add(new UserEntity(entity.getProperty("name")
						.toString(), entity.getProperty("email").toString(),
						entity.getProperty("password").toString(), Long
								.toString(entity.getKey().getId())));

			}
		}

		return returnedUser;
	}
	
	
	
	/**
	 * This method will be used to save user object in datastore
	 * 
	 * @return boolean if user is saved correctly or not
	 */
	public Boolean saveUser() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity employee = new Entity("users", list.size() + 2);

		employee.setProperty("name", this.name);
		employee.setProperty("email", this.email);
		employee.setProperty("password", this.password);
		datastore.put(employee);
		

		return true;

	}

	public Boolean saveRequset(String fre_name, String user_name,
			String fre_id, String user_id, String status) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("request");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity employee = new Entity("request", list.size() + 2);
		employee.setProperty("friend_name", fre_name);
		employee.setProperty("user_name", user_name);
		employee.setProperty("friend_id", fre_id);
		employee.setProperty("user_id", user_id);
		employee.setProperty("status", status);
		datastore.put(employee);
		
		Query Q= new Query("Notifications");
		PreparedQuery p = datastore.prepare(Q);
		List<Entity> lists = p.asList(FetchOptions.Builder.withDefaults());

		Entity eme = new Entity("Notifications", list.size() + 2);
		eme.setProperty("friend_name", fre_name);
		eme.setProperty("friend_id", fre_id);
		eme.setProperty("user_name", user_name);
		eme.setProperty("user_id", user_id);
		eme.setProperty("type", "Notifiy_Request");
		eme.setProperty("note", "request");
		datastore.put(eme);
		return true;

	}

	public static ArrayList<UserEntity> viewRequset(String user_id) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("request");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		ArrayList<UserEntity> returnedUser = new ArrayList<UserEntity>();

		System.out.println(user_id);
		for (Entity entity : pq.asIterable()) {
			// System.out.println(entity.getProperty("user_id").toString());
			// System.out.println(entity.getProperty("friend_accept").toString());
			if (entity.getProperty("friend_id").toString().equals(user_id)
					&& entity.getProperty("status").toString().equals("send")) {

				UserEntity User = new UserEntity();
				User.setFriend_name(entity.getProperty("user_name")
						.toString());
				User.setId(entity.getProperty("user_id").toString());
				User.setuser_id(entity.getProperty("friend_id")
						.toString());
				returnedUser.add(User);
				
				
			}

		}
		return returnedUser;

	}

	public String acceptRequset(String user_id, String friend_id) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("request");
		PreparedQuery pq = datastore.prepare(gaeQuery);

		System.out.println("find1 " + user_id + " " + friend_id);
		for (Entity entity : pq.asIterable()) {
			// System.out.println(entity.getProperty("name").toString());
			if (entity.getProperty("user_id").toString().equals(user_id)
					&& entity.getProperty("friend_id").toString()
							.equals(friend_id)
					&& entity.getProperty("status").toString().equals("send")) {
				entity.setProperty("status", "accept");
				datastore.put(entity);
				System.out.println("find");
				
				Query Q= new Query("Notifications");
				PreparedQuery p = datastore.prepare(Q);
				List<Entity> lists = p.asList(FetchOptions.Builder.withDefaults());

				Entity eme = new Entity("Notifications", lists.size() + 2);
				eme.setProperty("friend_name", entity.getProperty("friend_name"));
				eme.setProperty("friend_id", friend_id);
				eme.setProperty("user_name", entity.getProperty("user_name"));
				eme.setProperty("note", "accept");
				eme.setProperty("user_id", user_id);
				eme.setProperty("type", "Notifiy_Request");
				datastore.put(eme);
				
				return "accept";
			} else if (entity.getProperty("friend_id").toString()
					.equals(user_id)
					&& entity.getProperty("user_id").toString()
							.equals(friend_id)
					&& entity.getProperty("status").toString().equals("send")) {
				entity.setProperty("status", "accept");
				datastore.put(entity);
				System.out.println("find");
				
				Query Q= new Query("Notifications");
				PreparedQuery p = datastore.prepare(Q);
				List<Entity> lists = p.asList(FetchOptions.Builder.withDefaults());

				Entity eme = new Entity("Notifications", lists.size() + 2);
				eme.setProperty("friend_id", user_id);
				eme.setProperty("friend_name", entity.getProperty("user_name").toString());
				eme.setProperty("user_id", friend_id);
				eme.setProperty("user_name", entity.getProperty("friend_name"));
				eme.setProperty("type", "Notifiy_Request");
				eme.setProperty("note", "accept");

				datastore.put(eme);
				return "accept";
			}
		}

		return null;
	}

}
