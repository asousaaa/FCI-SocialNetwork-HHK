package com.FCI.SWE.Models;
import org.json.JSONException;
import org.json.JSONObject;

public class UserEntity {
	private String name;
	private String email;
	private String password;
    private String id;
	/**
	 * Constructor accepts user data
	 * 
	 * @param name
	 *            user name
	 * @param email
	 *            user email
	 * @param password
	 *            user provided password
	 */
	
	
	private UserEntity(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;

	}

    private UserEntity(String name, String email, String password,String ID) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = ID;

    }

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPass() {
		return password;
	}

    public String getID() {
        return id;
    }

	/**
	 * 
	 * This static method will form UserEntity class using json format contains
	 * user data
	 * 
	 * @param json
	 *            String in json format contains user data
	 * @return Constructed user entity
	 */
	public static UserEntity createLoginUser(String json) {

		
			JSONObject object;
			try {
				object = new JSONObject(json);
				return  new UserEntity(object.get("name").toString(), object.get(
						"email").toString(), object.get("password").toString(),
                        object.get("ID").toString());
				
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return null;

	}
}
