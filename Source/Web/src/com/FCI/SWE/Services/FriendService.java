package com.FCI.SWE.Services;

import java.util.*;

import javax.ws.rs.*;

import com.FCI.SWE.Models.*;

import org.json.simple.*;
import org.json.simple.parser.ParseException;

@Path("/")
@Produces("text/html")
public class FriendService {
	
	static UserEntity user;
	static JSONObject object;
	static JSONArray array;
	static ArrayList<UserEntity> users;

	/**
	 * Request Rest service, this service will be called to make friend request.
	 * This function will store request in data store
	 * 
	 * @param username
	 *            provided user name
	 * @param friendid
	 *            provided friend id
	 * @param userid
	 *            provided userid
	 * @param status
	 *            provided status
	 * @return Status json
	 */
	@POST
	@Path("/RequestService")
	public String requestService(@FormParam("friend_name") String friendname,
			@FormParam("user_name") String username,
			@FormParam("friend_id") String friendid,
			@FormParam("user_id") String userid,
			@FormParam("status") String status) {
		user = new UserEntity();
		object = new JSONObject();

		if (user.saveRequset(friendname, username, friendid, userid, status) == 0) {
			object.put("Status", "OK");
		} else if (user.saveRequset(friendname, username, friendid, userid,
				status) == 1) {
			object.put("Status", "Failed");
			object.put("note", "You have already send to him");
		} else {
			object.put("Status", "Failed");
			object.put("note", "He have already send request to you");
		}
		return object.toString();
	}

	/**
	 * View Request Rest service, this service will be called to view friend
	 * request. This function will view request from data store
	 * 
	 * @param userid
	 *            provided userid
	 * @return Status json
	 */
	@POST
	@Path("/viewrequestService")
	public String viewrequestService(@FormParam("user_id") String user_id) {
		object = new JSONObject();
		array = new JSONArray();
		users = UserEntity.viewRequset(user_id);

		if (users.size() == 0) {
			object.put("Status", "Failed");
			array.add(object);
		}

		for (int i = 0; i < users.size(); i++) {

			object = new JSONObject();
			object.put("Status", "OK");
			object.put("friend_name", users.get(i).get_fname());
			object.put("friend_id", users.get(i).getId());
			object.put("user_id", users.get(i).get_userid());
			array.add(object);
		}

		return array.toString();
	}

	/**
	 * Accept Request Rest service, this service will be called to accept friend
	 * request
	 * 
	 * @param friendid
	 *            provided friend id
	 * @param userid
	 *            provided userid
	 * 
	 * @return Status json
	 */
	@POST
	@Path("/acceptrequestService")
	public String acceptrequestService(@FormParam("user_id") String user_id,
			@FormParam("friend_id") String friend_id) {
		user = new UserEntity();
		object = new JSONObject();

		if (user.acceptRequset(user_id, friend_id) == "accept") {
			object.put("Status", "OK");
		} else {
			object.put("Status", "Failed");
		}

		return object.toString();
	}
	/**
	 * Friend List Rest service, this service will be called to list friends
	 * This function will view friends from data store
	 * 
	 * @param userid
	 *            provided user id
	 * @return Status json
	 */
	@POST
	@Path("/FriendList")
	public String FriendList(@FormParam("userid") String user_id) {
		object = new JSONObject();
		array = new JSONArray();
		ArrayList<Friends> fri = Friends.Friendlist(user_id);

		if (fri.size() == 0) {
			object.put("Status", "Failed");
			array.add(object);
		} else {

			for (int i = 0; i < fri.size(); i++) {
				JSONObject object = new JSONObject();
				object.put("Status", "OK");
				object.put("friend_name", fri.get(i).getName());
				object.put("friend_id", fri.get(i).getId());

				array.add(object);

			}
		}
		return array.toJSONString();

	}
	

}