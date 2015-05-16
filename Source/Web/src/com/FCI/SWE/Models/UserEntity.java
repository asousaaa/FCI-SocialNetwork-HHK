package com.FCI.SWE.Models;

import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import com.google.appengine.api.datastore.*;

/**
 * <h1>User Entity class</h1>
 * <p>
 * This class will act as a model for user, it will holds user data
 * </p>
 */

public class UserEntity {
	static DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	static Query gaeQuery = new Query();
	static Query Q = new Query();
	static PreparedQuery pq;
	static PreparedQuery p;
	static ArrayList<UserEntity> returnedUser = new ArrayList<UserEntity>();
	static List<Entity> list;
	static List<Entity> list2;
	static Entity Record;
	static Entity Record2;
	static UserEntity User = new UserEntity();
	private String name;
	private String email;
	private String password;
	private String id; // record id to be saves in DB
	private String friend_name;
	private String user_id;

	/**
	 * Constructor accepts user data
	 */

	public UserEntity() {

	}

	/**
	 * 
	 * Constructor used to save user
	 * 
	 * @param name
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param password
	 *            provided password
	 * @param id
	 *            provided user_id
	 * 
	 */
	public UserEntity(String name, String email, String password, String id) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.id = id;
	}

	/**
	 * 
	 * Constructor used to login
	 * 
	 * @param name
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param password
	 *            provided password
	 * 
	 */
	public UserEntity(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
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
	 * user data and divide it to user entity attributes
	 * 
	 * @param json
	 *            String in json format contains user data
	 * @return Constructed user entity
	 * 
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
	 * function to login check first if it already exist in database
	 * 
	 * @param name
	 *            user name
	 * @param pass
	 *            user password
	 * @return Constructed user entity
	 */
	public static UserEntity Login(String email, String pass) {

		gaeQuery = new Query("users");
		pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("email").toString().equals(email)
					&& entity.getProperty("password").toString().equals(pass)) {
				UserEntity returnedUser = new UserEntity(entity.getProperty(
						"name").toString(), entity.getProperty("email")
						.toString(), entity.getProperty("password").toString(),
						Long.toString(entity.getKey().getId()));
				return returnedUser; // return if record exist
			}
		}

		return null;
	}

	/**
	 * 
	 * This static method will form UserEntity class using user name This method
	 * will search for user in Data store
	 * 
	 * @param name
	 * 
	 * @return ArrayList<UserEntity>
	 */
	public static ArrayList<UserEntity> searchforuser(String name) {

		gaeQuery = new Query("users");
		pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
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
		gaeQuery = new Query("users");
		pq = datastore.prepare(gaeQuery);
		list = pq.asList(FetchOptions.Builder.withDefaults());

		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("email").equals(this.email)) {
				return false;
			}
		}

		Record = new Entity("users", list.size() + 2);

		Record.setProperty("name", this.name);
		Record.setProperty("email", this.email);
		Record.setProperty("password", this.password);
		datastore.put(Record);

		return true;

	}

	/**
	 * 
	 * This static method will form UserEntity class using user name and friend
	 * name and user id and status password This method will save request in
	 * datastore
	 * 
	 * @param user_name
	 *            provided user name
	 * @param fre_id
	 *            provided friend id
	 * @param user_id
	 *            provided user id
	 * @param fre_name
	 *            provided friend name
	 * @param status
	 *            provided status
	 * @return Integer zero if saved one if it already exist two if user2
	 *         already send request to user1
	 */
	public int saveRequset(String fre_name, String user_name, String fre_id,
			String user_id, String status) {
		gaeQuery = new Query("request");
		pq = datastore.prepare(gaeQuery);
		list = pq.asList(FetchOptions.Builder.withDefaults());

		for (Entity entity : pq.asIterable()) {

			if (entity.getProperty("user_id").equals(user_id)
					&& entity.getProperty("friend_id").equals(fre_id)) {
				return 1;
			}
			if (entity.getProperty("friend_id").equals(user_id)
					&& entity.getProperty("user_id").equals(fre_id)) {
				return 2;
			}
		}

		Record = new Entity("request", list.size() + 2);
		Record.setProperty("friend_name", fre_name);
		Record.setProperty("user_name", user_name);
		Record.setProperty("friend_id", fre_id);
		Record.setProperty("user_id", user_id);
		Record.setProperty("status", status);
		datastore.put(Record);

		Q = new Query("Notifications");
		p = datastore.prepare(Q);
		list2 = p.asList(FetchOptions.Builder.withDefaults());

		Record2 = new Entity("Notifications", list.size() + 2);
		Record2.setProperty("friend_name", fre_name);
		Record2.setProperty("friend_id", fre_id);
		Record2.setProperty("user_name", user_name);
		Record2.setProperty("user_id", user_id);
		Record2.setProperty("type", "Notifiy_Request");
		Record2.setProperty("note", "request");
		datastore.put(Record2);

		return 0;

	}

	/**
	 * View Friend Request This function will view request from data store
	 * 
	 * @param userid
	 *            provided userid
	 * @return ArrayList<UserEntity>
	 */
	public static ArrayList<UserEntity> viewRequset(String user_id) {

		gaeQuery = new Query("request");
		pq = datastore.prepare(gaeQuery);

		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("friend_id").toString().equals(user_id)
					&& entity.getProperty("status").toString().equals("send")) {

				User = new UserEntity();
				User.setFriend_name(entity.getProperty("user_name").toString());
				User.setId(entity.getProperty("user_id").toString());
				User.setuser_id(entity.getProperty("friend_id").toString());
				returnedUser.add(User);

			}

		}
		return returnedUser;

	}

	/**
	 * Accept Request Rest service, this service will be called to accept friend
	 * request
	 * 
	 * @param friend_id
	 *            provided friend_id
	 * @param user_id
	 *            provided user_id
	 * 
	 * @return String
	 */
	public String acceptRequset(String user_id, String friend_id) {
		gaeQuery = new Query("request");
		pq = datastore.prepare(gaeQuery);

		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("user_id").toString().equals(user_id)
					&& entity.getProperty("friend_id").toString()
							.equals(friend_id)
					&& entity.getProperty("status").toString().equals("send")) {
				entity.setProperty("status", "accept");
				datastore.put(entity);

				Q = new Query("Notifications");
				p = datastore.prepare(Q);
				list2 = p.asList(FetchOptions.Builder.withDefaults());

				Record2 = new Entity("Notifications", list2.size() + 2);
				Record2.setProperty("friend_name",
						entity.getProperty("friend_name"));
				Record2.setProperty("friend_id", friend_id);
				Record2.setProperty("user_name",
						entity.getProperty("user_name"));
				Record2.setProperty("note", "accept");
				Record2.setProperty("user_id", user_id);
				Record2.setProperty("type", "Notifiy_Request");
				datastore.put(Record2);

				return "accept";
			} else if (entity.getProperty("friend_id").toString()
					.equals(user_id)
					&& entity.getProperty("user_id").toString()
							.equals(friend_id)
					&& entity.getProperty("status").toString().equals("send")) {
				entity.setProperty("status", "accept");
				datastore.put(entity);

				Q = new Query("Notifications");
				p = datastore.prepare(Q);
				list2 = p.asList(FetchOptions.Builder.withDefaults());

				Record2 = new Entity("Notifications", list2.size() + 2);
				Record2.setProperty("friend_id", user_id);
				Record2.setProperty("friend_name",
						entity.getProperty("user_name").toString());
				Record2.setProperty("user_id", friend_id);
				Record2.setProperty("user_name",
						entity.getProperty("friend_name"));
				Record2.setProperty("type", "Notifiy_Request");
				Record2.setProperty("note", "accept");

				datastore.put(Record2);
				return "accept";
			}
		}

		return null;
	}
}