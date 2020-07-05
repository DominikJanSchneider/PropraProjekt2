package com.propra.HealthAndSaftyBriefing;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.propra.HealthAndSaftyBriefing.database.DBConnector;
import com.propra.HealthAndSaftyBriefing.security.pwEncrypt;





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
							"Passwort gesetzt"
							
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
	
	public static void addUser(String username, String password) throws NoSuchAlgorithmException {
		String tableName = "Benutzer";
		Connection con = DBConnector.connectLogin();
		PreparedStatement pstmt = null;
		
		
		try {
			pstmt = con.prepareStatement("INSERT INTO "+tableName+ "(Benutzername, Passwort) VALUES(?, ?)");
			
			pstmt.setString(1, username);
			
			//TODO: Pull from master for pwencrypt class 
			pstmt.setString(2, pwEncrypt.toHexString(pwEncrypt.getSHA(password)));
			
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
	
	
	@SuppressWarnings("resource")
	public void deleteUser(int id) {
		
		String tableName = "Benutzer";
		Connection con = DBConnector.connectLogin();
		PreparedStatement pstmt = null;
		
		try {
			
			pstmt = con.prepareStatement("DELETE FROM " +tableName+ " WHERE ID ="+ id);
			con.setAutoCommit(false);
			pstmt.execute();
			con.commit();
			pstmt = con.prepareStatement("UPDATE sqlite_sequence SET seq='"+(id-1)+"' WHERE name='Benutzer';");
			con.setAutoCommit(false);
			pstmt.executeUpdate();
			con.commit();
			
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
	
	
	public static void editUser(int id, String username, String password) throws NoSuchAlgorithmException {
		String tableName = "Benutzer";
		Connection con = DBConnector.connectLogin();
		PreparedStatement pstmt = null;
		
		
		try {
			pstmt = con.prepareStatement("UPDATE Benutzer SET Benutzername='"+username+"', Passwort='"+pwEncrypt.toHexString(pwEncrypt.getSHA(password))+"' WHERE ID="+id);
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
