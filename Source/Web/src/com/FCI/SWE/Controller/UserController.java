package com.FCI.SWE.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
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
public class UserController {
	/**
	 * Action function to render Signup page, this function will be executed
	 * using url like this /rest/signup
	 * 
	 * @return sign up page
	 */
	public String service_url="http://fci-sn-hhk.appspot.com/rest/";
//	public String service_url="http://localhost:8888/rest/";
	
	@GET
	@Path("/signup")
	@Produces("text/html")
	public Response signUp() {
		return Response.ok(new Viewable("/jsp/register")).build();
	}
	

	/**
	 * Action function to render home page of application, home page contains
	 * only signup and login buttons
	 * 
	 * @return entry point page (Home page of this application)
	 */
	@GET
	@Path("/")
	@Produces("text/html")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}

	/**
	 * Action function to render login page this function will be executed using
	 * url like this /rest/login
	 * sign out 
	 * @return login page 
	 */
	@GET
	@Path("/login")
	@Produces("text/html")
	public Response login() {
		return Response.ok(new Viewable("/jsp/login")).build();
	}
	/**
	 * Action function to  render login page this function will be executed using
	 * url like this /rest/login
	 * 
	 * @return login page to give option to sign in again
	 */
	@POST
	@Path("/login")
	@Produces("text/html")
	public Response signout() {
		return Response.ok(new Viewable("/jsp/login")).build();
	}
	/**
	 * Action function to show requests 
	 * url like this /rest/viewrequest	 
	 * show all requests to be friend with someone who added you
	 * @return request page
	 */
	@POST
	@Path("/viewrequest")
	@Produces("text/html")
	public Response viewrequest(@FormParam("activeid") String uid) {
		

			String serviceUrl = service_url+"viewrequestService";
		
			
			try {
				URL url = new URL(serviceUrl);
				String urlParameters = "user_id=" + uid ;
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setInstanceFollowRedirects(false);
				connection.setRequestMethod("POST");
				connection.setConnectTimeout(60000);  //60 Seconds
				connection.setReadTimeout(60000);  //60 Seconds
				
				connection.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded;charset=UTF-8");
				OutputStreamWriter writer = new OutputStreamWriter(
						connection.getOutputStream());
				writer.write(urlParameters);
				writer.flush();
				String line, retJson = "";
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));

				while ((line = reader.readLine()) != null) {
					retJson += line;
				}
				writer.close();
				reader.close();
				JSONParser parser = new JSONParser();
				Object obj = parser.parse(retJson);
				JSONObject result= (JSONObject) obj;
				
				
				if (result.get("Status").equals("Failed")){
				System.out.println("not looool");
					return Response.ok(new Viewable("/jsp/request")).build();
				}
				
				Map<String,String> map= new HashMap<String,String>();
				System.out.println(result.get("friend_name"));
				map.put("fname",(String) result.get("friend_name"));
				map.put("fid",(String) result.get("friend_id"));
				map.put("uid",(String) result.get("user_id"));
				
				
				
				return Response.ok(new Viewable("/jsp/request",map)).build();
				
				
	}
	 catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	return null;	
	}
	
	/**
	 * Action function to render search page this function will be executed using
	 * url like this /rest/search
	 *  search for friends to add them 
	 * @return search page
	 */

	
	@POST
	@Path("/search")
	@Produces("text/html")
	public Response search(@FormParam("searchname") String sname ,
			@FormParam("activeid") String activeid,@FormParam("activename") String activename) {
	
		String serviceUrl = service_url+"SearchService";
	
		
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "searchname=" + sname ;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			
			JSONArray object = (JSONArray) obj;
			JSONObject result= (JSONObject) object.get(0);
			if (result.get("Status").equals("Failed")){
				System.out.println("not looool");
				return  Response.ok(new Viewable("/jsp/search")).build();
			}
			else{
				System.out.println("loool");
				System.out.print(object.size());
				for (int i=0;i<object.size();i++){
					System.out.println("i "+i);	
				 result= (JSONObject) object.get(i);	
				System.out.print("ctr "+ result.get("name"));
				}
			Map<String,String> searchresult= new HashMap<String,String>();
			UserEntity user = UserEntity.getUser(result.toString());
			searchresult.put("fname",user.getName());
			searchresult.put("uname",activename);
			searchresult.put("fid",user.getId());
			searchresult.put("uid",activeid);
			
			
			return Response.ok(new Viewable("/jsp/search",searchresult)).build();
			}
			
			
		}
		 catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			 * UserEntity user = new UserEntity(uname, email, pass);
			 * user.saveUser(); return uname;
			 */
		return null;
		
		
		
	}
	
	/**
	 * Action function to render request page this function will be executed using
	 * url like this /rest/request
	 * add friend
	 * @return request page
	 */

	
	@POST
	@Path("/request")
	@Produces("text/html")
	public String sendreqest(@FormParam("friend_name") String fname ,@FormParam("user_name") String uname ,
			@FormParam("user_id") String uid,@FormParam("friend_id") String fid ) {
	
		String serviceUrl = service_url+"RequestService";
	
		
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "friend_name=" + fname +"&user_name="+uname+"&friend_id=" + fid +"&user_id="+uid
					+"&status=send";
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return "Request send Successfully";
			
			}
			
			
		
		 catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			 * UserEntity user = new UserEntity(uname, email, pass);
			 * user.saveUser(); return uname;
			 */
		return null;
		
		
		
	}
	/**
	 * Action function to accept friend request
	 * url like this /rest/acceptrequest
	 * accept friend request
	 */

	@POST
	@Path("/acceptrequest")
	@Produces("text/html")
	public String acceptrequest(@FormParam("user_id") String uid,@FormParam("friend_id") String fid ) {

		String serviceUrl = service_url+"acceptrequestService";
	
		
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "friend_id=" + fid +"&user_id="+uid;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return "done :D";
			
			}
			
			
		
		 catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			 * UserEntity user = new UserEntity(uname, email, pass);
			 * user.saveUser(); return uname;
			 */
		return null;
		
		
		
	}
	
	
	/**
	 * Action function to response to signup request, This function will act as
	 * a controller part and it will calls RegistrationService to make
	 * registration
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided user password
	 * @return Status string
	 */
	@POST
	@Path("/response")
	@Produces(MediaType.TEXT_PLAIN)
	public String response(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		
		String serviceUrl =service_url+"RegistrationService";
	
		
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "uname=" + uname + "&email=" + email
					+ "&password=" + pass;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return "Registered Successfully";
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return "Failed";
	}

	/**
	 * Action function to response to login request. This function will act as a
	 * controller part, it will calls login service to check user data and get
	 * user from datastore
	 * 
	 * @param uname
	 *            provided user name
	 * @param pass
	 *            provided user password
	 * @return Home page view
	 */
	@POST
	@Path("/home")
	@Produces("text/html")
	public Response home(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		
		String serviceUrl = service_url+"LoginService";
	
		
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "uname=" + uname + "&password=" + pass;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			UserEntity user = UserEntity.getUser(object.toJSONString());
			map.put("name", user.getName());
			map.put("email", user.getEmail());
			map.put("ID", user.getId());
			 
			return Response.ok(new Viewable("/jsp/home", map)).build();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return null;

	}


}