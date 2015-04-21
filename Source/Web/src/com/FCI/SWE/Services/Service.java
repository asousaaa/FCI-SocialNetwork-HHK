package com.FCI.SWE.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.*;

/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces("text/html")
public class Service {

	/*
	 * @GET
	 * 
	 * @Path("/index") public Response index() { return Response.ok(new
	 * Viewable("/jsp/entryPoint")).build(); }
	 */

	/**
	 * Registration Rest service, this service will be called to make
	 * registration. This function will store user data in data store
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided password
	 * @return Status json
	 */
	@POST
	@Path("/RegistrationService")
	public String registrationService(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		UserEntity user = new UserEntity(uname, email, pass);
		user.saveUser();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}

	@POST
	@Path("/RequestService")
	public String requestService(@FormParam("friend_name") String friendname,
			@FormParam("user_name") String username,
			@FormParam("friend_id") String friendid,
			@FormParam("user_id") String userid,
			@FormParam("status") String status) {
		UserEntity user = new UserEntity();
		user.saveRequset(friendname, username, friendid, userid, status);
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}

	@POST
	@Path("/viewrequestService")
	public String viewrequestService(@FormParam("user_id") String user_id) {
		JSONObject ob = new JSONObject();
		JSONArray array = new JSONArray();
		ArrayList<UserEntity> user = UserEntity.viewRequset(user_id);

		if (user.size() == 0) {
			ob.put("Status", "Failed");
			array.add(ob);
		}

		for (int i = 0; i < user.size(); i++) {

			JSONObject object = new JSONObject();
			object.put("Status", "OK");
			object.put("friend_name", user.get(i).get_fname());
			object.put("friend_id", user.get(i).getId());
			object.put("user_id", user.get(i).get_userid());
			array.add(object);
		}

		return array.toString();
	}

	@POST
	@Path("/acceptrequestService")
	public String acceptrequestService(@FormParam("user_id") String user_id,
			@FormParam("friend_id") String friend_id) {
		UserEntity user = new UserEntity();

		JSONObject object = new JSONObject();

		if (user.acceptRequset(user_id, friend_id) == "accept") {
			object.put("Status", "OK");
		} else {
			object.put("Status", "Failed");
		}

		return object.toString();
	}

	@POST
	@Path("/SendMessageService")
	public String SendMessageService(@FormParam("user_id") String user_id,
			@FormParam("friend_id") String friend_id,
			@FormParam("user_name") String user_name,
			@FormParam("friend_name") String friend_name,
			@FormParam("content") String content) {
		Messages msg = new Messages();

		JSONObject object = new JSONObject();

		if (msg.sendmsg(user_id, friend_id, user_name, friend_name, content) == "accept") {
			object.put("Status", "OK");
		} else {
			object.put("Status", "Failed");
		}

		return object.toString();
	}

	/**
	 * Login Rest Service, this service will be called to make login process
	 * also will check user data and returns new user from datastore
	 * 
	 * @param uname
	 *            provided user name
	 * @param pass
	 *            provided user password
	 * @return user in json format
	 */
	@POST
	@Path("/SearchService")
	public String searchService(@FormParam("searchname") String sname) {
		JSONObject object = new JSONObject();
		JSONArray array = new JSONArray();
		ArrayList<UserEntity> user = UserEntity.searchforuser(sname);

		if (user.size() == 0) {
			System.out.println("null");
			object.put("Status", "Failed");
			array.add(object);
		} else {

			for (int i = 0; i < user.size(); i++) {
				// object.put("name", user.get(i));
				JSONObject user1 = new JSONObject();
				user1.put("Status", "OK");
				user1.put("name", user.get(i).getName());
				user1.put("email", user.get(i).getEmail());
				user1.put("password", user.get(i).getPass());
				user1.put("ID", user.get(i).getId());
				array.add(user1);
				System.out.println("ser " + user.get(i).getName());
			}

		}
		System.out.println("size " + array.size());
		return array.toJSONString();

	}

	@POST
	@Path("/CreateGroupChatService")
	public String CreateGroupChatService(@FormParam("gname") String Gname,
			@FormParam("owner") String owner, @FormParam("names") String names,
			@FormParam("ides") String ides) {

		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		Chat c = new Chat();
		if (!c.CreateChatGroup(Gname, owner, names, ides)) {
			object.put("Status", "Failed");
		} else {
			object.put("Status", "OK");
		}
		return object.toString();

	}

	@POST
	@Path("/MsgChatGroupService")
	public String MsgChatGroup(@FormParam("gid") String Gid,
			@FormParam("sender") String sender, @FormParam("msg") String content) {

		Chat c = new Chat();
		JSONObject object = new JSONObject();
		if (!c.MsgChatGroup(Gid, sender, content)) {
			object.put("Status", "Failed");
		} else {
			object.put("Status", "OK");
		}
		return object.toString();

	}

	@POST
	@Path("/ViewChatGroupService")
	public String viewChatGroup(@FormParam("user_id") String userid)
			throws ParseException {

		Chat c = new Chat();
		ArrayList<Chat> group = c.ViewChatGroup(userid);

		JSONArray array = new JSONArray();
		JSONObject object = new JSONObject();
		if (group == null) {
			object.put("Status", "Failed");
			array.add(object);
			return array.toJSONString();
		}
		for (int i = 0; i < group.size(); i++) {
			object = new JSONObject();
			object.put("Status", "Ok");
			object.put("chatid", group.get(i).getChat_id());
			object.put("groupname", group.get(i).getChat_name());
			object.put("member", group.get(i).getSenders());
			array.add(object);
		}

		return array.toJSONString();
	}

	@POST
	@Path("/ViewMsgChatGroupService")
	public String viewMsgChatGroup(@FormParam("GroupId") String id)
			throws ParseException {

		Chat c = new Chat();
		ArrayList<Chat> group = c.ViewMsgChatGroup(id);

		JSONArray array = new JSONArray();
		JSONObject object = new JSONObject();
		if (group.size() == 0) {
			object.put("Status", "Failed");
			object.put("chatid", id);
			array.add(object);

			return array.toJSONString();

		}
		for (int i = 0; i < group.size(); i++) {
			object = new JSONObject();
			object.put("Status", "Ok");
			object.put("chatname", group.get(i).getChat_name());
			object.put("chatid", id);
			object.put("content", group.get(i).getMsg());
			object.put("sender", group.get(i).getSender());
			array.add(object);

		}

		return array.toJSONString();
	}

	@POST
	@Path("/NotificationsService")
	public String NotificationsService(@FormParam("userid") String user_id)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		JSONObject object = new JSONObject();
		JSONArray array = new JSONArray();
		ArrayList<Notifications> not = Notifications.Notifiy(user_id);

		if (not.size() == 0) {
			System.out.println("null");
			object.put("Status", "Failed");
			array.add(object);
		} else {

			for (int i = 0; i < not.size(); i++) {
				// object.put("name", user.get(i));
				JSONObject not1 = new JSONObject();
				not1.put("Status", "OK");
				not1.put("user_name", not.get(i).getUser_name());
				not1.put("user_id", not.get(i).getUser_id());
				not1.put("friend_name", not.get(i).getFriend_name());
				not1.put("not_id", not.get(i).getNot_id());
				not1.put("type", not.get(i).getType());
				not1.put("note", not.get(i).getNote());
				array.add(not1);

			}

		}
		System.out.println("size " + array.size());
		return array.toJSONString();

	}

	@POST
	@Path("/FriendList")
	public String FriendList(@FormParam("userid") String user_id) {
		JSONObject object = new JSONObject();
		JSONArray array = new JSONArray();
		ArrayList<Friends> fri = Friends.Friendlist(user_id);

		if (fri.size() == 0) {
			System.out.println("null");
			object.put("Status", "Failed");
			array.add(object);
		} else {

			for (int i = 0; i < fri.size(); i++) {
				// object.put("name", user.get(i));
				JSONObject frined = new JSONObject();
				frined.put("Status", "OK");
				frined.put("friend_name", fri.get(i).getName());
				frined.put("friend_id", fri.get(i).getId());

				array.add(frined);

			}

		}
		System.out.println("size " + array.size());
		return array.toJSONString();

	}

	@POST
	@Path("/LoginService")
	public String loginService(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser(uname, pass);
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			object.put("password", user.getPass());
			object.put("ID", user.getId());
		}

		return object.toString();

	}

	@POST
	@Path("/CreatenewpageService")
	public String CreatenewPageService(@FormParam("owner") String owner,
			@FormParam("name") String name,
			@FormParam("cateagory") String cateagory)
			{
		JSONObject object = new JSONObject();

		page P = new page();
		if (P.newpage(owner,name, cateagory) != "done") {
			object.put("Status", "Failed");
		} else {
			object.put("Status", "OK");
		}
		return object.toString();

	}

	
	@POST
	@Path("/CreatePostService")
	public String CreatePostService(@FormParam("user") String user_name,
			@FormParam("UID") String user_ID,
			@FormParam("feeling") String feeling,
			@FormParam("content") String content, @FormParam("type") String type) {

		System.out.print(user_name);
		JSONObject object = new JSONObject();

		Post P = new Post();
		if (P.newpost(user_ID, user_name, feeling, content, type) != "post") {
			object.put("Status", "Failed");
		} else {
			object.put("Status", "OKjjjjjj");
		}
		return object.toString();

	}

	@POST
	@Path("/CreatePostPageService")
	public String CreatePostPageService(@FormParam("PID") String ID,
			@FormParam("UID") String uid,
			@FormParam("UNAME") String name,
			@FormParam("feeling") String feeling,
			@FormParam("content") String content
			) {

		JSONObject object = new JSONObject();

		page P = new page();
		if (P.newpagepost(ID,uid, name, feeling, content) != "page") {
			object.put("Status", "Failed");
		} else {
			object.put("Status", "OK");
		}
		return object.toString();

	}
	
	@POST
	@Path("/ViewUserPostService")
	public String ViewUserPostService(@FormParam("user_id") String ID) {

		JSONArray array = new JSONArray();
		JSONObject object;
		Post p = new Post();

		ArrayList<Post> post = p.ViewPosts(ID);

		if(post.size() == 0){
			object = new JSONObject();
			object.put("Status", "Failed");
			return array.toJSONString();
		}
		
		for (int i = 0; i < post.size(); i++) {
			object = new JSONObject();
			object.put("Status", "OK");
			object.put("user_name", post.get(i).getUser_name());
			object.put("user_id", post.get(i).getUser_ID());
			object.put("content", post.get(i).getContent());
			object.put("type", post.get(i).getType());
			object.put("feeling", post.get(i).getFeelings());
			array.add(object);
		}

		return array.toJSONString();
	}

	@POST
	@Path("/ViewpostpageService")
	public String ViewPagePostService(@FormParam("page_id") String ID) {

		JSONArray array = new JSONArray();
		JSONObject object;
		page p = new page();

		ArrayList<page> post = p.ViewPosts(ID);
		
		if(post.size() == 0){
			object = new JSONObject();
			object.put("Status", "Failed");
			return array.toJSONString();
		}

		for (int i = 0; i < post.size(); i++) {
			object = new JSONObject();
			object.put("page_name", post.get(i).getUser_name());
			object.put("page_id", post.get(i).getUser_ID());
			object.put("content", post.get(i).getContent());
			object.put("type", post.get(i).getType());
			object.put("feeling", post.get(i).getFeelings());
			array.add(object);
		}

		return array.toJSONString();
	}

	@POST
	@Path("/SearchPageService")
	public String SearchPageService(@FormParam("name") String name,@FormParam("type") String type) {

		JSONArray array = new JSONArray();
		JSONObject object;
		page p = new page();

		ArrayList<page> page = p.PageSearch(name, type);

		for (int i = 0; i < page.size(); i++) {
			object = new JSONObject();
			object.put("Status", "OK");
			object.put("page_name", page.get(i).getPage_name());
			object.put("page_owner", page.get(i).getPage_owner());
			object.put("cateagory", page.get(i).getCategory());
			object.put("numoflikes", page.get(i).getNumoflikes());
			
			array.add(object);
		}

		return array.toJSONString();
	}

	
}

