package com.FCI.HHK.Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.parser.*;
import org.json.simple.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import android.content.Context;

import com.FCI.HHK.Models.UserEntity;
import com.FCI.HHK.SocialNetwork.*;

public class UserController {

    private static UserEntity currentActiveUser;
    private static UserController userController;
    private SharedPreferences shared;
    private int position = 0;
    HomeActivity h = new HomeActivity();
    public static Bundle bundle;
    public static boolean creategroup=false;
    static Intent homeIntent = new Intent(Application.getAppContext(),
            HomeActivity.class);


    public static tab_group group_tab;
    public static tab_home home_tab;
    public static tab_request request_tab;
    public static tab_search search_tab;
    public static tab_timeline timeline_tab;
    public static tab_page page_tab;

    public static UserController getInstance() {
        if (userController == null)
            userController = new UserController();

        if (bundle == null)
            bundle = new Bundle();

        return userController;
    }

    private UserController() {

    }

    public String GetActiveUserName() {
        return currentActiveUser.getName().toString();
    }

    public String GetActiveUserEmail() {
        return currentActiveUser.getEmail().toString();
    }

    public String GetActiveUserId() {
        return currentActiveUser.getID().toString();
    }

    public String GetActiveUserPass() {
        return currentActiveUser.getPass().toString();
    }

    public void login(String userName, String password) {



        new Connection().execute(
                "http://fci-sn-hhk.appspot.com/rest/LoginService", userName,
                password, "LoginService");

           }

    public void Notification(String userid) {

        new Connection().execute(
                "http://fci-sn-hhk.appspot.com/rest/NotificationsService", userid
                , "NotificationsService");

    }

    public void MsgChatGroup(String id,String sender,String content){
        new Connection().execute(
                "http://fci-sn-hhk.appspot.com/rest/MsgChatGroupService", id,
                sender,content, "MsgChatGroupService");

    }
    public void listfriend(String userid) {

        new Connection().execute(
                "http://fci-sn-hhk.appspot.com/rest/FriendList", userid
                , "FriendList");

    }

    public void viewchatgroup(String userid){
        new Connection().execute(
                "http://fci-sn-hhk.appspot.com/rest/ViewChatGroupService", userid
                , "ViewChatGroupService");
    }


    public void viewmsgchatgroup(String groupid){
        new Connection().execute(
                "http://fci-sn-hhk.appspot.com/rest/ViewMsgChatGroupService", groupid
                , "ViewMsgChatGroupService");
    }


    public void CreateGroupChat(String groupname,String username,String names,String ides){
        new Connection().execute(
                "http://fci-sn-hhk.appspot.com/rest/CreateGroupChatService", groupname,
                username,names,ides, "CreateGroupChatService");
    }
    public void sendmessage(String userid,String friendid,String username,String friendname,String msg) {

        new Connection().execute(
                "http://fci-sn-hhk.appspot.com/rest/SendMessageService", userid,friendid,
                 username,friendname,msg, "SendMessageService");

    }

    public void logout() {
        currentActiveUser = null;
        SharedPreferences.Editor editor = Application.getAppContext().getSharedPreferences("social", 0).edit();
        editor.clear();
        editor.commit();
        Intent mainIntent = new Intent(Application.getAppContext(),
                MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Application.getAppContext().startActivity(mainIntent);
    }

    public void signUp(String userName, String email, String password) {
        new Connection().execute(
                "http://fci-sn-hhk.appspot.com/rest/RegistrationService", userName,
                email, password, "RegistrationService");
    }

    public void search(String friendName, String userName, String userId) {

        new Connection().execute(
                "http://fci-sn-hhk.appspot.com/rest/SearchService", friendName,
                userId, userName, "SearchService");
    }

    public void viewRequest(String userId) {
        new Connection().execute(
                "http://fci-sn-hhk.appspot.com/rest/viewrequestService", userId,
                "viewrequestService");
    }

    public void sendRequest(String friendName, String friendId, String userName, String userId) {

        new Connection().execute(
                "http://fci-sn-hhk.appspot.com/rest/RequestService", friendName,
                friendId, userName, userId, "RequestService");
    }

    public void acceptRequest(String userId, String friendId) {
        new Connection().execute(
                "http://fci-sn-hhk.appspot.com/rest/acceptrequestService", userId,
                friendId, "acceptrequestService");
    }




    public void CreatePostService(String uname,String uid,String feel,String content,String type){
        new Connection().execute(
                "http://fci-sn-hhk.appspot.com/rest/CreatePostService", uname,
                uid,feel,content,type, "CreatePostService");
    }

    public void ViewUserPostService(String userid){
        new Connection().execute(
                "http://fci-sn-hhk.appspot.com/rest/ViewUserPostService", userid,
                "ViewUserPostService");

    }

    public void Createpage(String owner,String pagename,String pagecategory){
        new Connection().execute(
                "http://fci-sn-hhk.appspot.com/rest/CreatenewpageService", owner,
                pagename,pagecategory,"CreatenewpageService");
    }

    public void SearchPageService(String name,String type){
        new Connection().execute(
                "http://fci-sn-hhk.appspot.com/rest/SearchPageService", name,
                type,"SearchPageService");
    }

    public void ViewPageService(String name,String type){
        new Connection().execute(
                "http://fci-sn-hhk.appspot.com/rest/SearchPageService", name,
                type,"ViewPageService");
    }

    static private class Connection extends AsyncTask<String, String, String> {

        String serviceType;

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            URL url;
            serviceType = params[params.length - 1];
            String urlParameters = "";
            if (serviceType.equals("LoginService"))
                urlParameters = "uname=" + params[1] + "&password=" + params[2];

            else if (serviceType.equals("RegistrationService")) { //sign up
                urlParameters = "uname=" + params[1] + "&email=" + params[2]
                        + "&password=" + params[3];
            } else if (serviceType.equals("SearchService")) {
                urlParameters = "searchname=" + params[1];
            } else if (serviceType.equals("viewrequestService")) {
                urlParameters = "user_id=" + params[1];
            } else if (serviceType.equals("RequestService")) { //send request
                urlParameters = "friend_name=" + params[1] + "&friend_id=" + params[2] +
                        "&user_name=" + params[3] + "&user_id=" + params[4]
                        + "&status=send";
            } else if (serviceType.equals("acceptrequestService")) {
                urlParameters = "friend_id=" + params[1] + "&user_id=" + params[2];
            } else if (serviceType.equals("NotificationsService")) {
            urlParameters = "userid=" + params[1];
            }
            else if (serviceType.equals("FriendList")) {
                urlParameters = "userid=" + params[1];
            }
            else if (serviceType.equals("MsgChatGroupService")) {
                urlParameters = "gid=" + params[1]+"&sender="+params[2]
                +"&msg="+params[3];
            }
            else if (serviceType.equals("ViewMsgChatGroupService")) {
                urlParameters = "GroupId=" + params[1];
            }
            else if (serviceType.equals("ViewChatGroupService")) {
                urlParameters = "user_id=" + params[1];
            }else if (serviceType.equals("SendMessageService")) {
                urlParameters = "user_id=" + params[1]+"&friend_id="+params[2]+
                        "&user_name=" + params[3]+"&friend_name="+params[4]+"&content="+params[5];
            }else if (serviceType.equals("NotificationsService")) {
                urlParameters = "userid=" + params[1];
            }else if (serviceType.equals("CreateGroupChatService")) {
                urlParameters = "gname=" + params[1]+"&owner="+params[2]+
                        "&names=" + params[3]+"&ides="+params[4];
            }


            else if (serviceType.equals("CreatePostService")) {
                urlParameters = "user=" + params[1]+"&UID="+params[2]+
                        "&feeling=" + params[3]+"&content="+params[4]+"&type="+params[5];
            }
            else if (serviceType.equals("ViewUserPostService")) {
                System.out.println(params[1]);
                urlParameters = "user_id=" + params[1];
            }
            else if (serviceType.equals("CreatenewpageService")) {
                System.out.println(params[1]);
                urlParameters = "owner=" + params[1]+"&name="+params[2]+"&cateagory="+params[3];
            }
            else if (serviceType.equals("SearchPageService")) {
                System.out.println(params[1]);
                urlParameters = "name=" + params[1]+"&type="+params[2];
            }
            else if (serviceType.equals("ViewPageService")) {
                System.out.println(params[1]);
                urlParameters = "name=" + params[1]+"&type="+params[2];
            }


            HttpURLConnection connection;
            try {
                url = new URL(params[0]);

                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setInstanceFollowRedirects(false);
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(60000); // 60 Seconds
                connection.setReadTimeout(60000); // 60 Seconds

                connection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded;charset=UTF-8");
                OutputStreamWriter writer = new OutputStreamWriter(
                        connection.getOutputStream());
                writer.write(urlParameters);
                writer.flush();
                String line, retJson = "";
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));

                while ((line = reader.readLine()) != null) {
                    retJson += line;
                }
                return retJson;

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try {


                if (serviceType.equals("LoginService")) {

                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONObject object = (JSONObject) obj;

                    if (!object.containsKey("Status") || object.get("Status").equals("Failed")) {
                        Toast.makeText(Application.getAppContext(), "Username or Password may be wrong.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    currentActiveUser = UserEntity.createLoginUser(result);

                    SharedPreferences.Editor editor = Application.getAppContext().getSharedPreferences("social", 0).edit();
                    editor.putString("loginname",currentActiveUser.getName());
                    editor.putString("loginpass",currentActiveUser.getPass());
                    editor.commit();
                    	 homeIntent = new Intent(Application.getAppContext(),
                    			HomeActivity.class);
                    System.out.println("--- " + serviceType + "IN LOGIN " + object.get("Status"));

                    bundle.putString("search_service", "null");
                    bundle.putString("Notification_service", "null");
                    bundle.putString("FriendList_service", "null");
                    bundle.putString("ViewChatGroupService_service", "null");
                    bundle.putString("ViewMsgChatGroupService_service", "null");
                    bundle.putString("viewrequest_service", "null");
                    bundle.putString("sendrequest_service", "null");
                    bundle.putString("acceptrequest_service", "null");
                    bundle.putString("sendmsgrequest_service", "null");
                    bundle.putString("CreateGroupChat_service", "null");
                    bundle.putString("ViewUserPostService_service", "null");
                    bundle.putString("SearchPageService_service", "null");
                    bundle.putString("ViewPageService_service", "null");





                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    homeIntent.putExtra("status", object.get("Status").toString());
                    homeIntent.putExtra("username", object.get("name").toString());
                    homeIntent.putExtra("userid", object.get("ID").toString());
                    homeIntent.putExtra("userpassword", object.get("password").toString());
                    homeIntent.putExtra("useremail", object.get("email").toString());
                    homeIntent.putExtra("service", "login");
                    homeIntent.putExtra("FriendList_service", "null");
                userController.Notification(currentActiveUser.getID());
                    Application.getAppContext().startActivity(homeIntent);
                }

                else if (serviceType.equals("RegistrationService")) {

                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONObject object = (JSONObject) obj;

                    if (!object.containsKey("Status") || object.get("Status").equals("Failed")) {
                        Toast.makeText(Application.getAppContext(), "Registration failed, Please try again.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Intent homeIntent = new Intent(Application.getAppContext(),
                            MainActivity.class);

                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    homeIntent.putExtra("status", "Registered successfully");
                    homeIntent.putExtra("service", "signup");
                    Toast.makeText(Application.getAppContext(), "Registered successfully, now you can login.", Toast.LENGTH_LONG).show();
                    Application.getAppContext().startActivity(homeIntent);
                }


                else if (serviceType.equals("SearchService")) {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONArray object = (JSONArray) obj;
                    JSONObject ret = (JSONObject) object.get(0);

                    if (!ret.containsKey("Status") || ret.get("Status").equals("Failed")) {
                        Toast.makeText(Application.getAppContext(), "Name not found, search again.", Toast.LENGTH_LONG).show();
                        homeIntent.putExtra("search_service", "null");
                        return;
                    }



                    bundle.putString("status", "ok");
                    bundle.putString("search_service", "search");
                    bundle.putString("search_arraysize",  String.valueOf(object.size()));
                    bundle.putString("search_array",result );
                    ((HomeActivity) Application.Homecontext).getSupportFragmentManager().beginTransaction()
                            .detach(search_tab)
                            .attach(search_tab)
                            .commit();
                  }


                else if (serviceType.equals("NotificationsService")) {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONArray object = (JSONArray) obj;
                    JSONObject ret = (JSONObject) object.get(0);

                    if (!ret.containsKey("Status") || ret.get("Status").equals("Failed")) {
                        Toast.makeText(Application.getAppContext(), "no notification.", Toast.LENGTH_LONG).show();
                        homeIntent.putExtra("Notification_service", "null");

                    }


                    bundle.putString("status", "ok");
                    bundle.putString("Notification_service", "Notification");
                    bundle.putString("Notification_arraysize",  String.valueOf(object.size()));
                    bundle.putString("Notification_array",result );
                    ((HomeActivity) Application.Homecontext).getSupportFragmentManager().beginTransaction()
                            .detach(home_tab)
                            .attach(home_tab)
                            .commit();

              }

                else if (serviceType.equals("FriendList")) {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONArray object = (JSONArray) obj;
                    JSONObject ret = (JSONObject) object.get(0);

                    if (!ret.containsKey("Status") || ret.get("Status").equals("Failed")) {
                        Toast.makeText(Application.getAppContext(), "There are no Friends.", Toast.LENGTH_LONG).show();
                        homeIntent.putExtra("FriendList_service", "null");
                        return;
                    }


                        bundle.putString("status", "ok");
                        bundle.putString("FriendList_service", "FriendList");
                        bundle.putString("FriendList_arraysize", String.valueOf(object.size()));
                        bundle.putString("FriendList_array", result);
                        ((HomeActivity) Application.Homecontext).getSupportFragmentManager().beginTransaction()
                                .detach(search_tab)
                                .attach(search_tab)
                                .commit();
                    ((HomeActivity) Application.Homecontext).getSupportFragmentManager().beginTransaction()
                                .detach(group_tab)
                                .attach(group_tab)
                                .commit();

                    }

                else if (serviceType.equals("ViewMsgChatGroupService")) {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONArray object = (JSONArray) obj;
                    JSONObject ret = (JSONObject) object.get(0);

                    if (!ret.containsKey("Status") || ret.get("Status").equals("Failed")) {
                        Toast.makeText(Application.getAppContext(), "There are no massage .", Toast.LENGTH_LONG).show();
                        bundle.putString("ViewMsgChatGroupService_service", "ViewMsgChatGroupService");
                        bundle.putString("ViewMsgChatGroupService_arraysize", "-1");
                        bundle.putString("ViewMsgChatGroupService_array", ret.get("chatid").toString());
                        ((HomeActivity) Application.Homecontext).getSupportFragmentManager().beginTransaction()
                                .detach(group_tab)
                                .attach(group_tab)
                                .commit();
                    return;
                    }



                    bundle.putString("status", "ok");
                    bundle.putString("ViewMsgChatGroupService_service", "ViewMsgChatGroupService");
                    bundle.putString("ViewMsgChatGroupService_arraysize", String.valueOf(object.size()));
                    bundle.putString("ViewMsgChatGroupService_array", result);
                    ((HomeActivity) Application.Homecontext).getSupportFragmentManager().beginTransaction()
                            .detach(group_tab)
                            .attach(group_tab)
                            .commit();

                      }

                else if (serviceType.equals("ViewChatGroupService")) {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONArray object = (JSONArray) obj;
                    JSONObject ret = (JSONObject) object.get(0);

                    if (!ret.containsKey("Status") || ret.get("Status").equals("Failed")) {
                        Toast.makeText(Application.getAppContext(), "There are no Chat group.", Toast.LENGTH_LONG).show();
                        homeIntent.putExtra("ViewChatGroup_service", "null");
                        return;
                    }

                    //    Intent homeIntent = new Intent(Application.getAppContext(),
                    //           HomeActivity.class);

                    //   homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                  //  homeIntent.putExtras(bundle);

                    bundle.putString("status", "ok");
                    bundle.putString("ViewChatGroupService_service", "ViewChatGroupService");
                    bundle.putString("ViewChatGroupService_arraysize", String.valueOf(object.size()));
                    bundle.putString("ViewChatGroupService_array", result);
                    ((HomeActivity) Application.Homecontext).getSupportFragmentManager().beginTransaction()
                            .detach(group_tab)
                            .attach(group_tab)
                            .commit();

/*
                    homeIntent.putExtra("status", "lool you find");
                    homeIntent.putExtra("ViewChatGroupService_service", "ViewChatGroupService");
                    homeIntent.putExtra("ViewChatGroupService_arraysize", String.valueOf(object.size()));
                    System.out.println("size " + object.size());
                    homeIntent.putExtra("ViewChatGroupService_array", result);

                    Application.getAppContext().startActivity(homeIntent);
  */              }


                else if (serviceType.equals("viewrequestService")) {

                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONArray object = (JSONArray) obj;
                    JSONObject ret = (JSONObject) object.get(0);


                    if (!ret.containsKey("Status") || ret.get("Status").equals("Failed")) {
                        Toast.makeText(Application.getAppContext(), "No request found.", Toast.LENGTH_LONG).show();
                        homeIntent.putExtra("viewrequest_service", "null");
                        return;
                    }


                    bundle.putString("update","called");
                    bundle.putString("status", "ok");
                    bundle.putString("viewrequest_service", "viewrequest");
                    bundle.putString("viewrequest_arraysize", String.valueOf(object.size()));
                    bundle.putString("viewrequest_array", result);
                    ((HomeActivity) Application.Homecontext).getSupportFragmentManager().beginTransaction()
                            .detach(request_tab)
                            .attach(request_tab)
                            .commit();


                }

                else if (serviceType.equals("RequestService")) {

                    bundle.putString("status", "sending");
                    bundle.putString("sendrequest_service", "sendrequest");
                    ((HomeActivity) Application.Homecontext).getSupportFragmentManager().beginTransaction()
                            .detach(request_tab)
                            .attach(request_tab)
                            .commit();
             }

                else if (serviceType.equals("acceptrequestService")) {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONObject ret = (JSONObject) obj;


                    bundle.putString("status", "ok");
                    bundle.putString("acceptrequest_service", "acceptrequest");
                    ((HomeActivity) Application.Homecontext).getSupportFragmentManager().beginTransaction()
                            .detach(request_tab)
                            .attach(request_tab)
                            .commit();


                }
                else if (serviceType.equals("SendMessageService")) {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONObject object = (JSONObject) obj;

                    if (!object.containsKey("Status") || object.get("Status").equals("Failed")) {
                        Toast.makeText(Application.getAppContext(), "Registration failed, Please try again.", Toast.LENGTH_LONG).show();
                        return;
                    }


                    bundle.putString("status", "ok");
                    bundle.putString("sendmsgrequest_service", "SendMessage");
                    ((HomeActivity) Application.Homecontext).getSupportFragmentManager().beginTransaction()
                            .detach(search_tab)
                            .attach(search_tab)
                            .commit();

                        Toast.makeText(Application.getAppContext(), "Message sent.", Toast.LENGTH_LONG).show();

                }

                else if (serviceType.equals("MsgChatGroupService")) {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONObject object = (JSONObject) obj;

                    if (!object.containsKey("Status") || object.get("Status").equals("Failed")) {
                        Toast.makeText(Application.getAppContext(), "send message failed, Please try again.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Toast.makeText(Application.getAppContext(), "Message sent.", Toast.LENGTH_LONG).show();

                }

                else if (serviceType.equals("CreateGroupChatService")) {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONObject object = (JSONObject) obj;

                    if (!object.containsKey("Status") || object.get("Status").equals("Failed")) {
                        Toast.makeText(Application.getAppContext(), "Create group failed, Please try again.", Toast.LENGTH_LONG).show();
                        return;
                    }


                    bundle.putString("status", "ok");
                    bundle.putString("CreateGroupChat_service", "CreateGroupChat");
                    ((HomeActivity) Application.Homecontext).getSupportFragmentManager().beginTransaction()
                            .detach(group_tab)
                            .attach(group_tab)
                            .commit();

                    Toast.makeText(Application.getAppContext(), "Group created successfully.", Toast.LENGTH_LONG).show();
                }

                else if (serviceType.equals("CreatePostService")) {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONObject object = (JSONObject) obj;

                    if (!object.containsKey("Status") || object.get("Status").equals("Failed")) {
                        Toast.makeText(Application.getAppContext(), "Please try again.", Toast.LENGTH_LONG).show();
                        return;
                    }


                    Toast.makeText(Application.getAppContext(), "post created successfully.", Toast.LENGTH_LONG).show();
                }

                else if (serviceType.equals("ViewUserPostService")) {

                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONArray object = (JSONArray) obj;
                    JSONObject ret = (JSONObject) object.get(0);


                    if (!ret.containsKey("Status") || ret.get("Status").equals("Failed")) {
                       Toast.makeText(Application.getAppContext(), "No request found.", Toast.LENGTH_LONG).show();
                     //   homeIntent.putExtra("viewrequest_service", "null");
                        return;
                    }


                    bundle.putString("update","called");
                    bundle.putString("status", "ok");
                    bundle.putString("ViewUserPostService_service", "ViewUserPostService");
                    bundle.putString("ViewUserPostService_arraysize", String.valueOf(object.size()));
                    bundle.putString("ViewUserPostService_array", result);
                    ((NewsFeedActivity) Application.NewsFeedActivity).getSupportFragmentManager().beginTransaction()
                            .detach(timeline_tab)
                            .attach(timeline_tab)
                            .commit();


                }

                else if (serviceType.equals("CreatenewpageService")) {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONObject object = (JSONObject) obj;

                    if (!object.containsKey("Status") || object.get("Status").equals("Failed")) {
                        Toast.makeText(Application.getAppContext(), "Create Page failed, Please try again.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Toast.makeText(Application.getAppContext(), "Page created successfully.", Toast.LENGTH_LONG).show();
                }

                else if (serviceType.equals("SearchPageService")) {

                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONArray object = (JSONArray) obj;
                    JSONObject ret = (JSONObject) object.get(0);


                    if (!ret.containsKey("Status") || ret.get("Status").equals("Failed")) {
                        Toast.makeText(Application.getAppContext(), "No request found.", Toast.LENGTH_LONG).show();
                        //   homeIntent.putExtra("viewrequest_service", "null");
                        return;
                    }


                    bundle.putString("update","called");
                    bundle.putString("status", "ok");
                    bundle.putString("SearchPageService_service", "SearchPageService");
                    bundle.putString("SearchPageService_arraysize", String.valueOf(object.size()));
                    bundle.putString("SearchPageService_array", result);
                    ((NewsFeedActivity) Application.NewsFeedActivity).getSupportFragmentManager().beginTransaction()
                            .detach(page_tab)
                            .attach(page_tab)
                            .commit();


                }


                else if (serviceType.equals("ViewPageService")) {

                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONArray object = (JSONArray) obj;
                    JSONObject ret = (JSONObject) object.get(0);


                    if (!ret.containsKey("Status") || ret.get("Status").equals("Failed")) {
                        Toast.makeText(Application.getAppContext(), "No request found.", Toast.LENGTH_LONG).show();
                        //   homeIntent.putExtra("viewrequest_service", "null");
                        return;
                    }


                    bundle.putString("update","called");
                    bundle.putString("status", "ok");
                    bundle.putString("ViewPageService_service", "ViewPageService");
                    bundle.putString("ViewPageService_arraysize", String.valueOf(object.size()));
                    bundle.putString("ViewPageService_array", result);
                    ((NewsFeedActivity) Application.NewsFeedActivity).getSupportFragmentManager().beginTransaction()
                            .detach(page_tab)
                            .attach(page_tab)
                            .commit();


                }


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

}
/*

@Override
public void onBackPressed() {
    // your code.
}


 */