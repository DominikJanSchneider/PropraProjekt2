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
	private User user;
	
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
							"Passwort gesetzt",
							rs.getString("Rolle")
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
	
	public static void addUser(String username, String password, String role) throws NoSuchAlgorithmException {
		String tableName = "Benutzer";
		Connection con = DBConnector.connectLogin();
		PreparedStatement pstmt = null;
		
		
		try {
			pstmt = con.prepareStatement("INSERT INTO "+tableName+ "(Benutzername, Passwort, Rolle) VALUES(?, ?, ?)");
			
			pstmt.setString(1, username);
			
			//TODO: Pull from master for pwencrypt class 
			pstmt.setString(2, pwEncrypt.toHexString(pwEncrypt.getSHA(password)));
			pstmt.setString(3, role);
			
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
	
	public void deleteUser(int id) {
		String tableName = "Benutzer";
		Connection con = DBConnector.connectLogin();
		PreparedStatement pstmt = null;
		try {
			
			pstmt = con.prepareStatement("DELETE FROM " +tableName+ " WHERE ID ="+ id);
			con.setAutoCommit(false);
			pstmt.execute();
			pstmt.close();
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
	
	public static void editUser(int id, String username, String password, String role) throws NoSuchAlgorithmException {
		String tableName = "Benutzer";
		Connection con = DBConnector.connectLogin();
		PreparedStatement pstmt = null;
		
		
		try {
			pstmt = con.prepareStatement("UPDATE "+tableName+" SET Benutzername='"+username+"', Passwort='"+pwEncrypt.toHexString(pwEncrypt.getSHA(password))+ "' ,Rolle= '"+role+"' WHERE ID="+id);
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



