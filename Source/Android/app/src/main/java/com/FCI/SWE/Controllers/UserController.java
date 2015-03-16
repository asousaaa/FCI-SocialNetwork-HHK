package com.FCI.SWE.Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.parser.*;
import org.json.simple.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.FCI.SWE.Models.UserEntity;
import com.FCI.SWE.SocialNetwork.*;

public class UserController {

	private static UserEntity currentActiveUser;
	private static UserController userController;
    static Intent homeIntent = new Intent(Application.getAppContext(),
            HomeActivity.class);

	public static UserController getInstance() {
		if (userController == null)
			userController = new UserController();
		return userController;
	}

	private UserController() {

	}

    public String GetActiveUserName(){
        return  currentActiveUser.getName().toString();
    }

    public String GetActiveUserEmail (){
        return  currentActiveUser.getEmail().toString();
    }

    public String GetActiveUserId (){
        return  currentActiveUser.getID().toString();
    }

    public String GetActiveUserPass(){
        return  currentActiveUser.getPass().toString();
    }

	public  void login(String userName, String password) {

		new Connection().execute(
				"http://fci-sn-hhk.appspot.com/rest/LoginService", userName,
				password, "LoginService");


	}

	public void signUp(String userName, String email, String password) {
		new Connection().execute(
				"http://fci-sn-hhk.appspot.com/rest/RegistrationService", userName,
				email, password, "RegistrationService");
	}

    public void search(String friendName,String userName, String userId) {

        new Connection().execute(
                "http://fci-sn-hhk.appspot.com/rest/SearchService", friendName,
                userId,userName, "SearchService");
    }

    public void viewRequest(String userId) {
        new Connection().execute(
                "http://fci-sn-hhk.appspot.com/rest/viewrequestService", userId,
                 "viewrequestService");
    }

    public void sendRequest(String friendName,String friendId,String userName, String userId) {

        new Connection().execute(
                "http://fci-sn-hhk.appspot.com/rest/RequestService",friendName,
                friendId,userName,userId, "RequestService");
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
			String urlParameters="";
			if (serviceType.equals("LoginService"))
				urlParameters = "uname=" + params[1] + "&password=" + params[2];

			else if(serviceType.equals("RegistrationService")) { //sign up
                urlParameters = "uname=" + params[1] + "&email=" + params[2]
                        + "&password=" + params[3];
            }
            else if(serviceType.equals("SearchService")) {
                urlParameters = "searchname=" + params[1] ;
            }
            else if(serviceType.equals("viewrequestService")) {
                urlParameters = "user_id=" + params[1]  ;
            }
            else if(serviceType.equals("RequestService")) { //send request
                urlParameters = "friend_name=" + params[1] +"&friend_id="+params[2]+
                        "&user_name=" + params[3] +"&user_id="+params[4]
                        +"&status=send";
            }
            else if(serviceType.equals("acceptrequestService")) {
                urlParameters = "friend_id=" + params[1] +"&user_id="+params[2];
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
                    JSONObject object = (JSONObject) obj ;

                    if(!object.containsKey("Status") || object.get("Status").equals("Failed")){
                        Toast.makeText(Application.getAppContext(), "Username or Password may be wrong.", Toast.LENGTH_LONG).show();
                        return;
                    }

					currentActiveUser = UserEntity.createLoginUser(result);

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
                    homeIntent.putExtra("service","login");
    				Application.getAppContext().startActivity(homeIntent);
				}

				else if (serviceType.equals("RegistrationService")){

                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONObject object = (JSONObject) obj ;

                    if(!object.containsKey("Status") || object.get("Status").equals("Failed")){
                        Toast.makeText(Application.getAppContext(), "Registration failed, Please try again.", Toast.LENGTH_LONG).show();
                        return;
                    }

					homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					homeIntent.putExtra("status", "Registered successfully");
                    homeIntent.putExtra("service","signup");
					Application.getAppContext().startActivity(homeIntent);
				}
                else if (serviceType.equals("SearchService")){
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONArray object = (JSONArray) obj;
                    JSONObject ret= (JSONObject) object.get(0);

                    if(!ret.containsKey("Status") || ret.get("Status").equals("Failed")){
                        Toast.makeText(Application.getAppContext(), "Name not found, search again.", Toast.LENGTH_LONG).show();
                        return;
                    }

                //    Intent homeIntent = new Intent(Application.getAppContext(),
                 //           HomeActivity.class);
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    homeIntent.putExtra("status","lool you find");
                    homeIntent.putExtra("service","search");
                    homeIntent.putExtra("friendname",ret.get("name").toString());
                    homeIntent.putExtra("friendid",ret.get("ID").toString());
                    homeIntent.putExtra("friendemail",ret.get("email").toString());
                    homeIntent.putExtra("friendpassword",ret.get("password").toString());
                    Application.getAppContext().startActivity(homeIntent);
                }
                else if (serviceType.equals("viewrequestService")){
                  //  Intent homeIntent = new Intent(Application.getAppContext(),
                 //           HomeActivity.class);
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONObject ret = (JSONObject) obj ;


                    if(!ret.containsKey("Status") || ret.get("Status").equals("Failed")){
                        Toast.makeText(Application.getAppContext(), "no Request found.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    homeIntent.putExtra("status", "ok");
                    homeIntent.putExtra("service", "viewrequest");
                    homeIntent.putExtra("vfriendname", ret.get("friend_name").toString());
                    homeIntent.putExtra("vfriendid",ret.get("friend_id").toString() );
                    Application.getAppContext().startActivity(homeIntent);
                }
                else if (serviceType.equals("RequestService")){
                 //   Intent homeIntent = new Intent(Application.getAppContext(),
                 //           HomeActivity.class);
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    homeIntent.putExtra("status", "sending");
                    homeIntent.putExtra("service", "sendrequest");
                    Application.getAppContext().startActivity(homeIntent);
                }
                else if (serviceType.equals("acceptrequestService")){
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(result);
                    JSONObject ret = (JSONObject) obj ;

                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    homeIntent.putExtra("status", "ok");
                    homeIntent.putExtra("service", "acceptrequest");
                    Application.getAppContext().startActivity(homeIntent);
                }

			}  catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
			
		}

	}

}
