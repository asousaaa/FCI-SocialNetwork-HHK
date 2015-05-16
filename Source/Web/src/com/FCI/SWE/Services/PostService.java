package com.FCI.SWE.Services;

import java.util.*;
import javax.ws.rs.*;
import com.FCI.SWE.Models.*;
import org.json.simple.*;
import org.json.simple.parser.ParseException;

@Path("/")
@Produces("text/html")
public class PostService {
	static JSONObject object;
	static JSONArray array;
	static Post post;

	/**
	 * Create Post Rest service, this service will be called to create new post
	 * This function will store page in data store
	 * 
	 * @param UID
	 *            provided user id
	 * @param user_name
	 *            provided username
	 * @param feeling
	 *            provided user feeling
	 * @param type
	 *            provided type
	 * @return Status json
	 */
	@POST
	@Path("/CreatePostService")
	public String CreatePostService(@FormParam("user") String user_name,
			@FormParam("UID") String user_ID,
			@FormParam("feeling") String feeling,
			@FormParam("content") String content, @FormParam("type") String type) {

		object = new JSONObject();

		post = new Post();
		if (post.newpost(user_ID, user_name, feeling, content, type) != "post") {
			object.put("Status", "Failed");
		} else {
			object.put("Status", "OK");
		}
		return object.toString();

	}

	/**
	 * View User Post Rest service, this service will be called to viw post This
	 * function will store page in data store
	 * 
	 * @param user
	 *            id provided user id
	 * @return Status json
	 */
	@POST
	@Path("/ViewUserPostService")
	public String ViewUserPostService(@FormParam("user_id") String ID) {

		array = new JSONArray();
		post = new Post();

		ArrayList<Post> posts = post.ViewPosts(ID);

		if (posts.size() == 0) {
			object = new JSONObject();
			object.put("Status", "Failed");
			return array.toJSONString();
		}

		for (int i = 0; i < posts.size(); i++) {
			object = new JSONObject();
			object.put("Status", "OK");
			object.put("user_name", posts.get(i).getUser_name());
			object.put("user_id", posts.get(i).getUser_ID());
			object.put("content", posts.get(i).getContent());
			object.put("type", posts.get(i).getType());
			object.put("feeling", posts.get(i).getFeelings());
			array.add(object);
		}

		return array.toJSONString();
	}

	/**
	 * Like Post Rest service, this service will be called to Like post This
	 * function will store page in data store
	 * 
	 * @param post_id
	 *            provided Post Id
	 * @param User_id
	 *            provided user id
	 * @return Status json
	 */
	@POST
	@Path("/LikePostService")
	public String LikePostService(@FormParam("postid") String post_id,
			@FormParam("userid") String user_id) throws ParseException {

		object = new JSONObject();

		Post P = new Post();
		if (!P.LikePost(post_id, user_id)) {
			object.put("Status", "Failed");
		} else {
			object.put("Status", "OK");
		}
		return object.toString();

	}

}