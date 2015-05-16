package com.FCI.SWE.Models;

import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.parser.*;

import com.google.appengine.api.datastore.*;

public class Post {

	public String ID; // post id
	public String user_ID;
	public String user_name;
	public String post_ID;
	public String feelings;
	public String content;
	public String type;
	public String security;
	public String notes;
	public JSONArray Likes = new JSONArray();
	static DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	static Query q = new Query();
	static Query gaeQuery = new Query();
	static PreparedQuery pq;
	static List<Entity> list;
	static Entity Record;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getUser_ID() {
		return user_ID;
	}

	public void setUser_ID(String user_ID) {
		this.user_ID = user_ID;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPost_ID() {
		return post_ID;
	}

	public void setPost_ID(String post_ID) {
		this.post_ID = post_ID;
	}

	public String getFeelings() {
		return feelings;
	}

	public void setFeelings(String feelings) {
		this.feelings = feelings;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * create new post This function will store page in data store
	 * 
	 * @param UserID
	 *            provided user id
	 * @param UserName
	 *            provided user name
	 * @param Feeling
	 *            provided user feeling
	 * @param Type
	 *            provided type
	 * @param Content
	 *            provided content
	 * @return Status String
	 */
	public String newpost(String UserID, String UserName, String Feeling,
			String Content, String Type) {
		gaeQuery = new Query("post");
		pq = datastore.prepare(gaeQuery);
		list = pq.asList(FetchOptions.Builder.withDefaults());

		Record = new Entity("post", list.size() + 2);
		Record.setProperty("feeling", Feeling);
		Record.setProperty("user_name", UserName);
		Record.setProperty("user_id", UserID);
		Record.setProperty("content", Content);
		Record.setProperty("type", Type);
		Record.setProperty("Likes", Likes.toJSONString());
		datastore.put(Record);

		Hashtag H = new Hashtag();
		List<String> hash = gethashes(Content);

		for (int i = 0; i < hash.size(); i++) {
			H.AddHashtag(hash.get(i).toString(),
					String.valueOf(list.size() + 1));
		}

		return "post";
	}

	/**
	 * put hashtags in a list spliting hashtag from post
	 * 
	 * @param hashtag
	 *            provided post to get as hashtag
	 * @return Status List
	 */

	public List<String> gethashes(String hashtag) {

		List<String> hashtags = new ArrayList<String>();
		StringTokenizer split = new StringTokenizer(hashtag);

		while (split.hasMoreTokens()) {
			hashtag = split.nextToken();
			if (hashtag.startsWith("@")) {
				if (!hashtags.contains(hashtag))
					hashtags.add(hashtag);

			}
		}

		return hashtags;

	}

	/**
	 * View posts This function will get posts from data store
	 * 
	 * @param user_id
	 *            provided user id
	 * @return Status ArrayList
	 */
	public ArrayList<Post> ViewPosts(String userid) {

		ArrayList<Post> posts = new ArrayList<Post>();
		ArrayList<Friends> friend = Friends.Friendlist(userid);
		ArrayList<String> data = new ArrayList<String>();

		for (int i = 0; i < friend.size(); i++) {
			data.add(friend.get(i).getId());
		}

		data.add(userid);

		q = new Query("post");
		pq = datastore.prepare(q);
		for (Entity entity : pq.asIterable()) {
			for (int i = 0; i < data.size(); i++) {

				if (data.get(i).toString()
						.equals(entity.getProperty("user_id").toString())) {
					Post p = new Post();
					p.setID(Long.toString(entity.getKey().getId()));
					p.setUser_name(entity.getProperty("user_name").toString());
					p.setUser_ID(entity.getProperty("user_id").toString());
					p.setContent(entity.getProperty("content").toString());
					p.setType(entity.getProperty("type").toString());
					p.setFeelings(entity.getProperty("feeling").toString());

					posts.add(p);
					break;
				}
			}

		}

		return posts;
	}

	/**
	 * Like Post
	 * 
	 * @param postid
	 *            provided post id
	 * @param userid
	 *            provided user id
	 * @return Status Boolean
	 */
	public Boolean LikePost(String postid, String userid) throws ParseException {
		q = new Query("Post");
		pq = datastore.prepare(q);

		for (Entity entity : pq.asIterable()) {

			if (Long.toString(entity.getKey().getId()).equals(postid)) {

				JSONParser parser = new JSONParser();
				Object obj = parser.parse(entity.getProperty("Likes")
						.toString());
				JSONArray array = (JSONArray) obj;
				if (array.contains(userid)) {
					return true;
				} else {
					array.add(userid);
					entity.setProperty("Likes", array.toJSONString());
					datastore.put(entity);
					return true;
				}

			}
		}

		return false;

	}
}