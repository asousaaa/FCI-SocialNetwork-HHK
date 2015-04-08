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

    public static UserController getInstance() {
        if (userController == null)
            userController = new UserController();
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

    public void listfriend(String userid) {

        new Connection().execute(
                "http://fci-sn-hhk.appspot.com/rest/FriendList", userid
                , "FriendList");

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
            }else if (serviceType.equals("SendMessageService")) {
                urlParameters = "user_id=" + params[1]+"&friend_id="+params[2]+
                        "&user_name=" + params[3]+"&friend_name="+params[4]+"&content="+params[5];
            }else if (serviceType.equals("NotificationsService")) {
                urlParameters = "userid=" + params[1];
            }else if (serviceType.equals("CreateGroupChatService")) {
                urlParameters = "gname=" + params[1]+"&owner="+params[2]+
                        "&names=" + params[3]+"&ides="+params[4];
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
                    //	Intent homeIntent = new Intent(Application.getAppContext(),
                    //			HomeActivity.class);
                    System.out.println("--- " + serviceType + "IN LOGIN " + object.get("Status"));

                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    /* here you should initialize user entity */
                    homeIntent.putExtra("status", object.get("Status").toString());
                    homeIntent.putExtra("username", object.get("name").toString());
                    homeIntent.putExtra("userid", object.get("ID").toString());
                    homeIntent.putExtra("userpassword", object.get("password").toString());
                    homeIntent.putExtra("useremail", object.get("email").toString());
                    homeIntent.putExtra("service", "login");
                    homeIntent.putExtra("search_service", "null");
                    homeIntent.putExtra("Notification_service", "null");
                    homeIntent.putExtra("FriendList_service", "null");
                    homeIntent.putExtra("viewrequest_service", "null");
                    homeIntent.putExtra("sendrequest_service", "null");
                    homeIntent.putExtra("acceptrequest_service", "null");
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

                else if (serviceType.equals("SendMessage")) {

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

                    //    Intent homeIntent = new Intent(Application.getAppContext(),
                    //           HomeActivity.class);

                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    homeIntent.putExtra("status", "lool you find");
                    homeIntent.putExtra("search_service", "search");
                    homeIntent.putExtra("search_arraysize", String.valueOf(object.size()));
                    homeIntent.putExtra("search_array", result);

                    Application.getAppContext().startActivity(homeIntent);
                }


                else if (serviceType.equals("NotificationsService")) {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONArray object = (JSONArray) obj;
                    JSONObject ret = (JSONObject) object.get(0);

                    if (!ret.containsKey("Status") || ret.get("Status").equals("Failed")) {
                        Toast.makeText(Application.getAppContext(), "no notification.", Toast.LENGTH_LONG).show();
                        homeIntent.putExtra("Notification_service", "null");
                       // return;
                    }

                    //    Intent homeIntent = new Intent(Application.getAppContext(),
                    //           HomeActivity.class);

                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    homeIntent.putExtra("status", "lool you find");
                    homeIntent.putExtra("Notification_service", "Notification");
                    homeIntent.putExtra("Notification_arraysize", String.valueOf(object.size()));
                    System.out.println("size " + object.size());
                    homeIntent.putExtra("Notification_array", result);

                    Application.getAppContext().startActivity(homeIntent);
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

                    //    Intent homeIntent = new Intent(Application.getAppContext(),
                    //           HomeActivity.class);

                 //   homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    homeIntent.putExtras(bundle);
                    homeIntent.putExtra("status", "lool you find");
                    homeIntent.putExtra("FriendList_service", "FriendList");
                    homeIntent.putExtra("FriendList_arraysize", String.valueOf(object.size()));
                    System.out.println("size " + object.size());
                    homeIntent.putExtra("FriendList_array", result);

                    Application.getAppContext().startActivity(homeIntent);
                }


                else if (serviceType.equals("viewrequestService")) {
                    //  Intent homeIntent = new Intent(Application.getAppContext(),
                    //           HomeActivity.class);
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONArray object = (JSONArray) obj;
                    JSONObject ret = (JSONObject) object.get(0);


                    if (!ret.containsKey("Status") || ret.get("Status").equals("Failed")) {
                        Toast.makeText(Application.getAppContext(), "No request found.", Toast.LENGTH_LONG).show();
                        homeIntent.putExtra("viewrequest_service", "null");
                        return;
                    }

                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    homeIntent.putExtra("status", "ok");
                    homeIntent.putExtra("viewrequest_service", "viewrequest");
                    homeIntent.putExtra("viewrequest_arraysize", String.valueOf(object.size()));
                    System.out.println("size " + object.size());
                    homeIntent.putExtra("viewrequest_array", result);


                    Application.getAppContext().startActivity(homeIntent);
                }

                else if (serviceType.equals("RequestService")) {
                    //   Intent homeIntent = new Intent(Application.getAppContext(),
                    //           HomeActivity.class);
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    homeIntent.putExtra("status", "sending");
                    homeIntent.putExtra("sendrequest_service", "sendrequest");
                 //   Application.getAppContext().startActivity(homeIntent);
                }

                else if (serviceType.equals("acceptrequestService")) {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONObject ret = (JSONObject) obj;

                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    homeIntent.putExtra("status", "ok");
                    homeIntent.putExtra("viewrequest_service", "null");
                    homeIntent.putExtra("acceptrequest_service", "acceptrequest");
                   // Application.getAppContext().startActivity(homeIntent);
                }
                else if (serviceType.equals("SendMessageService")) {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONObject object = (JSONObject) obj;

                    if (!object.containsKey("Status") || object.get("Status").equals("Failed")) {
                        Toast.makeText(Application.getAppContext(), "Registration failed, Please try again.", Toast.LENGTH_LONG).show();
                        return;
                    }

                        Toast.makeText(Application.getAppContext(), "Message sent.", Toast.LENGTH_LONG).show();
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    homeIntent.putExtra("status", "ok");
                    homeIntent.putExtra("sendmsgrequest_service", "SendMessage");
                    // Application.getAppContext().startActivity(homeIntent);
                }
                else if (serviceType.equals("CreateGroupChatService")) {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONObject object = (JSONObject) obj;

                    if (!object.containsKey("Status") || object.get("Status").equals("Failed")) {
                        Toast.makeText(Application.getAppContext(), "Create group failed, Please try again.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Toast.makeText(Application.getAppContext(), "Group created successfully.", Toast.LENGTH_LONG).show();
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    homeIntent.putExtra("status", "ok");
                    homeIntent.putExtra("CreateGroupChat_service", "CreateGroupChat");
                    // Application.getAppContext().startActivity(homeIntent);
                }





            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

}
