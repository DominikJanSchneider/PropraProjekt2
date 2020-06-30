package com.propra.HealthAndSaftyBriefing;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.propra.HealthAndSaftyBriefing.database.DBConnector;
import com.propra.HealthAndSaftyBriefing.security.pwEncrypt;

public class UserManager {
	
	public boolean checkUser(String username, String password) {
		String tableName = "Benutzer";
		String usrn;
		String pswd;
		Connection con = DBConnector.connectLogin();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try  {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+" WHERE Benutzername='"+username+"';");
			rs = pstmt.executeQuery();
//			usrn = rs.getString("Benutzername");
//			pswd = rs.getString("Passwort");
			User user = new User(
					rs.getInt("ID"),
					rs.getString("Benutzername"),
					rs.getString("Passwort")
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
}
