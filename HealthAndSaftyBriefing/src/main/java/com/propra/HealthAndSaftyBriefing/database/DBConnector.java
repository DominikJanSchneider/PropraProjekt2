package com.propra.HealthAndSaftyBriefing.database;

import java.sql.*;

public class DBConnector {
	private static String urlCore = "jdbc:sqlite:src/main/resources/database/CoreDatabase.db";
	private static String urlLogin = "jdbc:sqlite:src/main/resources/database/UserDatabase.db";
	private static Connection con = null;
		
	public static Connection connectCore() {
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(urlCore);
			return con;
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public static Connection connectLogin() {
	       try {
	    	   Class.forName("org.sqlite.JDBC");
	    	   con = DriverManager.getConnection(urlLogin);
	    	   return con;
	       }catch(Exception e) {
	    	   System.out.println(e.getMessage());
	    	   return null;
	       }
	}
	
	public static void deconnect() {
		try {
			if(con != null) {
				con.close();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Getter and Setter
	public static String getURLCore() {
		return urlCore;
	}
			
	public static void setURLCore(String path) {
		urlCore = path;
	}
}