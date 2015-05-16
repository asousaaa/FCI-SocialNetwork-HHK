package com.FCI.SWE.Services;

import java.util.*;
import javax.ws.rs.*;
import com.FCI.SWE.Models.*;
import org.json.simple.*;
import org.json.simple.parser.ParseException;

@Path("/")
@Produces("text/html")
public class ChatService {
	static JSONObject object;
	static JSONArray array;
	static Messages msg;
	static Chat chat;
	static ArrayList<Chat> group;

	
	/**
	 * Send Massage Rest service, this service will be called to send massage.
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
	@Path("/SendMessageService")
	public String SendMessageService(@FormParam("user_id") String user_id,
			@FormParam("friend_id") String friend_id,
			@FormParam("user_name") String user_name,
			@FormParam("friend_name") String friend_name,
			@FormParam("content") String content) {
		msg = new Messages();

		object = new JSONObject();

		if (msg.sendmsg(user_id, friend_id, user_name, friend_name, content) == "accept") {
			object.put("Status", "OK");
		} else {
			object.put("Status", "Failed");
		}

		return object.toString();
	}

	/**
	 * Create Group chat Rest service, this service will be called to create
	 * group. This function will store group in data store
	 * 
	 * @param gname
	 *            provided group chat name
	 * @param owner
	 *            provided group chat owner
	 * @param names
	 *            provided names of group members
	 * @param ides
	 *            provided ides of group members
	 * @return Status json
	 */
	@POST
	@Path("/CreateGroupChatService")
	public String CreateGroupChatService(@FormParam("gname") String Gname,
			@FormParam("owner") String owner, @FormParam("names") String names,
			@FormParam("ides") String ides) {

		object = new JSONObject();
		object.put("Status", "OK");
		chat = new Chat();
		if (!chat.CreateChatGroup(Gname, owner, names, ides)) {
			object.put("Status", "Failed");
		} else {
			object.put("Status", "OK");
		}
		return object.toString();

	}

	/**
	 * Msg Chat Group Rest service, this service will be called to send massage
	 * in group service This function will store group in data store
	 * 
	 * @param gid
	 *            provided group chat id
	 * @param sender
	 *            provided sender of the massage
	 * @param msg
	 *            provided content of massage
	 * @return Status json
	 */
	@POST
	@Path("/MsgChatGroupService")
	public String MsgChatGroup(@FormParam("gid") String Gid,
			@FormParam("sender") String sender, @FormParam("msg") String content) {

		chat = new Chat();
		object = new JSONObject();
		if (!chat.MsgChatGroup(Gid, sender, content)) {
			object.put("Status", "Failed");
		} else {
			object.put("Status", "OK");
		}
		return object.toString();

	}

	/**
	 * View Chat Group Rest service, this service will be view group chat This
	 * function will view group from data store
	 * 
	 * @param gid
	 *            provided group chat id
	 * @return Status json
	 */
	@POST
	@Path("/ViewChatGroupService")
	public String viewChatGroup(@FormParam("user_id") String userid)
			throws ParseException {
		array = new JSONArray();
		object = new JSONObject();
		chat = new Chat();
		group = chat.ViewChatGroup(userid);

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

	/**
	 * view Msg Chat Group Rest service, this service will be called to view
	 * massage in chat group service This function will store group in data
	 * store
	 * 
	 * @param groupid
	 *            provided group chat id
	 * @return Status json
	 */
	@POST
	@Path("/ViewMsgChatGroupService")
	public String viewMsgChatGroup(@FormParam("GroupId") String id)
			throws ParseException {

		chat = new Chat();
		group = chat.ViewMsgChatGroup(id);
		array = new JSONArray();
		object = new JSONObject();

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

	}