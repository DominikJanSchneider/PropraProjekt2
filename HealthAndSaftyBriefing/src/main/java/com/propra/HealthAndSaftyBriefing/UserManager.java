package com.propra.HealthAndSaftyBriefing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.propra.HealthAndSaftyBriefing.database.DBConnector;





public class UserManager {
	
	public List<User> getAllUsers() {
		String tableName = "Benutzer";
		Connection con = DBConnector.connectLogin();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<User> list = new LinkedList<User>();
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = new User(
							rs.getInt("ID"),
							rs.getString("Benutzername"),
							rs.getString("Passwort")
							
						);
				list.add(user);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			DBConnector.deconnect();
      }
	}
	
	public void addUser(String username, String password) {
		String tableName = "Benutzer";
		Connection con = DBConnector.connectLogin();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement("INSERT INTO "+tableName+ "(Benutzername, Passwort) VALUES(?, ?)");
			
			pstmt.setString(1, username);
			
			//TODO: Pull from master for pwencrypt class 
			pstmt.setString(2, password);
			
			pstmt.executeUpdate();
			
				
			}catch (SQLException e) {
			e.printStackTrace();
			}
		finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			DBConnector.deconnect();
      }
	}

}
