package com.FCI.SWE.Services;

import java.util.*;
import javax.ws.rs.*;
import com.FCI.SWE.Models.*;
import org.json.simple.*;
import org.json.simple.parser.ParseException;

@Path("/")
@Produces("text/html")
public class GeneralService {
	static UserEntity user;
	static JSONObject object;
	static JSONArray array;
	static ArrayList<UserEntity> users;

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
		user = new UserEntity(uname, email, pass);
		object = new JSONObject();

		if (user.saveUser()) {
			object.put("Status", "OK");
		} else {
			object.put("Status", "Failed");
		}
		return object.toString();
	}

	

	/**
	 * Login Rest service, this service will be called to Login into app
	 * 
	 * @param uname
	 *            provided user name
	 * @param password
	 *            provided password
	 * @return Status json
	 */
	@POST
	@Path("/LoginService")
	public String loginService(@FormParam("uemail") String uemail,
			@FormParam("password") String pass) {
		object = new JSONObject();
		user = UserEntity.Login(uemail, pass);
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

	
}