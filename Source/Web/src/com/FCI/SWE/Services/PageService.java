package com.FCI.SWE.Services;

import java.util.*;
import javax.ws.rs.*;
import com.FCI.SWE.Models.*;
import org.json.simple.*;
import org.json.simple.parser.ParseException;

@Path("/")
@Produces("text/html")
public class PageService {
	static JSONObject object;
	static JSONArray array;
	static Page page;
	
	
	/**
	 * Create New page Rest service, this service will be called to create new
	 * page This function will store page in data store
	 * 
	 * @param owner
	 *            provided page admin
	 * @param name
	 *            provided name
	 * @param category
	 *            provided page category
	 * @return Status json
	 */
	@POST
	@Path("/CreatenewpageService")
	public String CreatenewPageService(@FormParam("owner") String owner,
			@FormParam("name") String name,
			@FormParam("cateagory") String cateagory) {
		object = new JSONObject();

		page = new Page();
		if (page.newpage(owner, name, cateagory) != "done") {
			object.put("Status", "Failed");
		} else {
			object.put("Status", "OK");
		}
		return object.toString();

	}

	
	/**
	 * Create PostPage Rest service, this service will be called to create new
	 * post in a page This function will store post in data store
	 * 
	 * @param PID
	 *            provided post id
	 * @param UID
	 *            provided user id
	 * @param feeling
	 *            provided user feeling
	 * @param content
	 *            provided post content
	 * @return Status json
	 */
	@POST
	@Path("/CreatePostPageService")
	public String CreatePostPageService(@FormParam("PID") String ID,
			@FormParam("UID") String uid, @FormParam("UNAME") String name,
			@FormParam("feeling") String feeling,
			@FormParam("content") String content) {

		object = new JSONObject();

		page = new Page();
		if (page.newpagepost(ID, uid, name, feeling, content) != "page") {
			object.put("Status", "Failed");
		} else {
			object.put("Status", "OK");
		}
		return object.toString();

	}

	/**
	 * View Post Page Rest service, this service will be called to viw post in
	 * apage This function will store page in data store
	 * 
	 * @param Page_id
	 *            provided Page id
	 * @return Status json
	 */

	@POST
	@Path("/ViewpostpageService")
	public String ViewPagePostService(@FormParam("page_id") String ID) {

		array = new JSONArray();
		page = new Page();

		ArrayList<Page> PagePost = page.ViewPosts(ID);

		if (PagePost.size() == 0) {
			object = new JSONObject();
			object.put("Status", "Failed");
			return array.toJSONString();
		}

		for (int i = 0; i < PagePost.size(); i++) {
			object = new JSONObject();
			object.put("page_name", PagePost.get(i).getUser_name());
			object.put("page_id", PagePost.get(i).getUser_ID());
			object.put("content", PagePost.get(i).getContent());
			object.put("type", PagePost.get(i).getType());
			object.put("feeling", PagePost.get(i).getFeelings());
			array.add(object);
		}

		return array.toJSONString();
	}

	/**
	 * Search Page Rest service, this service will be called to Search for a
	 * specific page This function will store page in data store
	 * 
	 * @param name
	 *            provided user name
	 * @param type
	 *            provided type
	 * @return Status json
	 */
	@POST
	@Path("/SearchPageService")
	public String SearchPageService(@FormParam("name") String name,
			@FormParam("type") String type) {

		array = new JSONArray();
		page = new Page();

		ArrayList<Page> pages = page.PageSearch(name, type);

		for (int i = 0; i < pages.size(); i++) {
			object = new JSONObject();
			object.put("Status", "OK");
			object.put("page_name", pages.get(i).getPage_name());
			object.put("page_owner", pages.get(i).getPage_owner());
			object.put("cateagory", pages.get(i).getCategory());
			object.put("numoflikes", pages.get(i).getNumoflikes());

			array.add(object);
		}

		return array.toJSONString();
	}


	/**
	 * Like Post Pge Rest service, this service will be called to Like post in a
	 * page This function will store page in data store
	 * 
	 * @param post_id
	 *            provided Post Id
	 * @param User_id
	 *            provided user id
	 * @return Status json
	 */

	@POST
	@Path("/LikePostPageService")
	public String LikePostPageService(@FormParam("postid") String post_id,
			@FormParam("userid") String user_id) throws ParseException {

		object = new JSONObject();

		page = new Page();
		if (!page.LikePost(post_id, user_id)) {
			object.put("Status", "Failed");
		} else {
			object.put("Status", "OK");
		}
		return object.toString();

	}

}