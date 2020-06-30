package com.propra.HealthAndSaftyBriefing;

public class User {
	
	private int userID;
	private String userName;
	private String userPassword;
	
	
	User(int userID, String userName, String userPassword) {
		this.setUserID(userID);
		this.setUserName(userName);
		this.setUserPassword(userPassword);
	}

	
	//Getter and Setter
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
}
