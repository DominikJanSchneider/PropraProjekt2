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
	
	
	public String getRole() {
		if (user.getUserRole().equals("admin")) {
			return user.getUserRole(); // returns the admin role
		} else {
			return user.getUserRole(); // if user isn't admin, so he will be a benutzer (user)
		}
	}
	
	
//	public String getUsersName(String username) {
//		String tableName = "Benutzer";
//		String usersName;
//		Connection con = DBConnector.connectLogin();
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			con.prepareStatement("ATTACH DATABASE 'database/CoreDatabase.db' AS p").execute(); // Attach CoreDatabase to UserDatabase
//			pstmt = con.prepareStatement("SELECT * FROM "+tableName+" u join p.Personen on u.Benutzername = p.Personen.'E-Mail Adresse' WHERE Benutzername='"+username+"';"); //join user with his person data
//			rs = pstmt.executeQuery();
//			usersName = rs.getString("Vorname")+" "+rs.getString("Name");
//			return usersName;
//		} catch(SQLException e) {
//			e.printStackTrace();
//			return "";
//		}
//		finally {
//			try {
//				if(pstmt != null) {
//					pstmt.close();
//				}
//			} catch(SQLException e) {
//				e.printStackTrace();
//			}
//			DBConnector.deconnect();
//		}
//	}
	
	
	public String[] getUserData(String username) {
		String tableName = "Benutzer";
		String[] userData;
		Connection con = DBConnector.connectLogin();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con.prepareStatement("ATTACH DATABASE 'database/CoreDatabase.db' AS p").execute(); // Attach CoreDatabase to UserDatabase
			pstmt = con.prepareStatement("SELECT * FROM p.Personen WHERE 'E-Mail Adresse' = '"+username+"'");
			rs = pstmt.executeQuery();
			userData = new String[rs.getMetaData().getColumnCount()];
			pstmt.close();
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+" u join p.Personen on u.Benutzername = p.Personen.'E-Mail Adresse' WHERE Benutzername='"+username+"';"); //join user with his person data
			rs = pstmt.executeQuery();
			
			userData[0] = rs.getString("ID");
			userData[1] = rs.getString("Name");
			userData[2] = rs.getString("Vorname");
			userData[3] = rs.getString("Datum");
			userData[4] = rs.getString("Ifwt");
			userData[5] = rs.getString("MNaF");
			userData[6] = rs.getString("Intern");
			userData[7] = rs.getString("Beschaeftigungsverhaeltnis");
			userData[8] = rs.getString("Beginn");
			userData[9] = rs.getString("Ende");
			userData[10] = rs.getString("Extern");
			userData[11] = rs.getString("E-Mail Adresse");
			userData[12] = rs.getString("Allgemeine Unterweisung");
			userData[13] = rs.getString("Laboreinrichtungen");
			userData[14] = rs.getString("Gefahrstoffe");
			userData[15] = rs.getString("LabKommentar");
			userData[16] = rs.getString("GefKommentar");
			
			return userData;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
			DBConnector.deconnect();
		}
	}
}
