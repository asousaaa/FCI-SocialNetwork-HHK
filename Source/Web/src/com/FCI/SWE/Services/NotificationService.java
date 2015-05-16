package com.FCI.SWE.Services;

import java.util.*;
import javax.ws.rs.*;
import com.FCI.SWE.Models.*;
import org.json.simple.*;
import org.json.simple.parser.ParseException;

@Path("/")
@Produces("text/html")
public class NotificationService {
	static JSONObject object;
	static JSONArray array;

	

	/**
	 * Notification Rest service, this service will be called to view
	 * Notification This function will view notification from data store
	 * 
	 * @param userid
	 *            provided user id
	 * @return Status json
	 */
	@POST
	@Path("/NotificationsService")
	public String NotificationsService(@FormParam("userid") String user_id)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		object = new JSONObject();
		array = new JSONArray();
		ArrayList<Notifications> not = Notifications.Notifiy(user_id);

		if (not.size() == 0) {
			object.put("Status", "Failed");
			array.add(object);
		} else {

			for (int i = 0; i < not.size(); i++) {
				object = new JSONObject();
				object.put("Status", "OK");
				object.put("user_name", not.get(i).getUser_name());
				object.put("user_id", not.get(i).getUser_id());
				object.put("friend_name", not.get(i).getFriend_name());
				object.put("not_id", not.get(i).getNot_id());
				object.put("type", not.get(i).getType());
				object.put("note", not.get(i).getNote());
				array.add(object);

			}
		}
		return array.toJSONString();

	}

	
	
}