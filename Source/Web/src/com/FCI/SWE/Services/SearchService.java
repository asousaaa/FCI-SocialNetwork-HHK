package com.FCI.SWE.Services;

import java.util.*;
import javax.ws.rs.*;
import com.FCI.SWE.Models.*;
import org.json.simple.*;
import org.json.simple.parser.ParseException;

@Path("/")
@Produces("text/html")
public class SearchService {
	
	static UserEntity user;
	static JSONObject object;
	static JSONArray array;
	static ArrayList<UserEntity> users;

	
	/**
	 * Search Rest Service, this service will be called to search for a name
	 * also will check user data and returns new user from datastore
	 * 
	 * @param uname
	 *            provided search name
	 * @return user in json format
	 */
	@POST
	@Path("/SearchService")
	public String searchService(@FormParam("searchname") String sname) {
		object = new JSONObject();
		array = new JSONArray();
		users = UserEntity.searchforuser(sname);

		if (users.size() == 0) {
			// System.out.println("null");
			object.put("Status", "Failed");
			array.add(object);
		} else {

			for (int i = 0; i < users.size(); i++) {
				JSONObject object = new JSONObject();
				object.put("Status", "OK");
				object.put("name", users.get(i).getName());
				object.put("email", users.get(i).getEmail());
				object.put("password", users.get(i).getPass());
				object.put("ID", users.get(i).getId());
				array.add(object);
			}

		}
		return array.toJSONString();
	}



}