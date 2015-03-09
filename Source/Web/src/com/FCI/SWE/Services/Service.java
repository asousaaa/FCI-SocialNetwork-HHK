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

import com.FCI.SWE.Models.UserEntity;


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
	
	
	/*@GET
	@Path("/index")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}*/


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
	public String requestService(@FormParam("friend_name")String friendname,
			@FormParam("user_name")String username,
			@FormParam("friend_id")String friendid,
			@FormParam("user_id") String userid,
			@FormParam("status") String status){
		UserEntity user = new UserEntity();
		user.saveRequset(friendname,username,friendid,userid,status);
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}
	
	@POST
	@Path("/viewrequestService")
	public String viewrequestService(@FormParam("user_id")String user_id){
		UserEntity user = new UserEntity();
		user = user.viewRequset(user_id);
		
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		object.put("friend_name",user.get_fname());
		object.put("friend_id",user.getId());
		object.put("user_id",user.get_userid());
		return object.toString();
	}
	
	@POST
	@Path("/acceptrequestService")
	public String acceptrequestService(@FormParam("user_id")String user_id,@FormParam("friend_id")String friend_id){
		UserEntity user = new UserEntity();
		
		JSONObject object = new JSONObject();
		
		if(user.acceptRequset(user_id,friend_id)=="accept"){
			object.put("Status", "OK");
		 }
		else {
			object.put("Status", "Failed");
		}
		
		return object.toString();
	}
	
	
	
	
	/**
	 * Login Rest Service, this service will be called to make login process
	 * also will check user data and returns new user from datastore
	 * @param uname provided user name
	 * @param pass provided user password
	 * @return user in json format
	 */
	@POST
	@Path("/SearchService")
	public String searchService(@FormParam("searchname") String sname) {
		JSONObject object = new JSONObject();
		JSONArray array = new JSONArray();
		ArrayList<UserEntity> user = UserEntity.searchforuser(sname);
		if (user.size()==0) {
			System.out.println("null");
			object.put("Status", "Failed");
			 array.add(object);
		} else {
			
			for(int i=0;i<user.size();i++){
			//object.put("name", user.get(i));
				JSONObject user1 = new JSONObject();
				user1.put("Status", "OK");
				user1.put("name", user.get(i).getName());
				user1.put("email", user.get(i).getEmail());
				user1.put("password", user.get(i).getPass());
				user1.put("ID",user.get(i).getId());
			    array.add(user1);
			    System.out.println("ser "+ user.get(i).getName());
			}
			
			
		}
System.out.println("size "+array.size());
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
			object.put("ID",user.getId());
		}

		return object.toString();

	}

}