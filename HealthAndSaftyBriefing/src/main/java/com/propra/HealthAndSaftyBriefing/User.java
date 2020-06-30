package com.propra.HealthAndSaftyBriefing;

public class User {
	
	private int userID;
	private String userName;
	private String userPassword;
	private String userRole;
	
	
	User(int userID, String userName, String userPassword, String userRole) {
		this.setUserID(userID);
		this.setUserName(userName);
		this.setUserPassword(userPassword);
		this.setUserRole(userRole);
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

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
}
