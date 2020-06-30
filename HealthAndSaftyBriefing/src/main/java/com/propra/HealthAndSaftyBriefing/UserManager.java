package com.propra.HealthAndSaftyBriefing;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.propra.HealthAndSaftyBriefing.database.DBConnector;
import com.propra.HealthAndSaftyBriefing.security.pwEncrypt;

public class UserManager {
	
	User user;
	
	public boolean checkUser(String username, String password) {
		String tableName = "Benutzer";
		Connection con = DBConnector.connectLogin();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try  {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+" WHERE Benutzername='"+username+"';");
			rs = pstmt.executeQuery();
			if (rs.isAfterLast()) {
				return false;
			}
			user = new User(
					rs.getInt("ID"),
					rs.getString("Benutzername"),
					rs.getString("Passwort"),
					rs.getString("Rolle")
					);
			
			if (username.equals(user.getUserName()) && pwEncrypt.toHexString(pwEncrypt.getSHA(password)).equals(user.getUserPassword())) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnector.deconnect();
		}
	}
	
	//TODO
	public boolean isAdmin(String role) {
		if (user.getUserRole().equals("admin")) {
			return true;
		}
		return false;
	}
}
