package com.propra.HealthAndSaftyBriefing.backend;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.propra.HealthAndSaftyBriefing.backend.data.Person;
import com.propra.HealthAndSaftyBriefing.backend.data.User;
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
	
	public Person getUserData(String username) {
		String tableName = "Benutzer";
		Connection con = DBConnector.connectLogin();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Person person = null;
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		try {
			con.prepareStatement("ATTACH DATABASE '"+DBConnector.getURLCore().substring(12)+"' AS p").execute(); // Attach CoreDatabase to UserDatabase
			pstmt = con.prepareStatement("SELECT p.Personen.ID, p.Personen.Name, p.Personen.Vorname, p.Personen.Datum, p.Personen.Ifwt, p.Personen.MNaF, p.Personen.Intern, p.Personen.Extern, "
					+ "p.Personen.Beschaeftigungsverhaeltnis, p.Personen.Beginn, p.Personen.Ende, p.Personen.'E-Mail Adresse', p.Personen.'Allgemeine Unterweisung', p.Personen.Laboreinrichtungen, "
					+ "p.Personen.Gefahrstoffe, p.Personen.LabKommentar, p.Personen.GefKommentar"
					+ " FROM "+tableName+" u join p.Personen on u.Benutzername = p.Personen.'E-Mail Adresse' WHERE Benutzername='"+username+"';"); //join user with his person data
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String dateString = rs.getString("Datum");
				Date date = null;
				if(!(dateString == null || dateString.isEmpty())) {
					date = df.parse(dateString);
				}
				String beginString = rs.getString("Beginn");
				Date begin = null;
				if(!(beginString == null || beginString.isEmpty())) {
					begin = df.parse(beginString);
				}
				String endString = rs.getString("Ende");
				Date end = null;
				if(!(endString == null || endString.isEmpty())) {
					end = df.parse(endString);
				}
				person = new Person(
							rs.getInt("ID"),
							rs.getString("Name"),
							rs.getString("Vorname"),
							date,
							rs.getString("Ifwt"),
							rs.getString("MNaF"),
							rs.getString("Intern"),
							rs.getString("Extern"),
							rs.getString("Beschaeftigungsverhaeltnis"),
							begin,
							end,
							rs.getString("E-Mail Adresse"),
							rs.getString("Allgemeine Unterweisung"),
							rs.getString("Laboreinrichtungen"),
							rs.getString("Gefahrstoffe"),
							rs.getString("LabKommentar"),
							rs.getString("GefKommentar")
						);
			}
			return person;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return person;
		}
		catch (ParseException e) {
			e.printStackTrace();
			return person;
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
	
	public List<User> getUserByID(int id) {
		String tableName = "Benutzer";
		Connection con = DBConnector.connectLogin();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<User> list = new LinkedList<User>();
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+ " WHERE ID='"+id+"'");
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
	
	public List<User> getUserByName(String name) {
		String tableName = "Benutzer";
		Connection con = DBConnector.connectLogin();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<User> list = new LinkedList<User>();
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+ " WHERE Benutzername LIKE '%"+name+"%'");
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
	
	public List<User> getUserByRole(String role) {
		String tableName = "Benutzer";
		Connection con = DBConnector.connectLogin();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<User> list = new LinkedList<User>();
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+ " WHERE Rolle='"+role+"'");
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
	
	public void addUser(String username, String password, String role) throws NoSuchAlgorithmException {
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
				System.out.println(e.getMessage());
				System.out.println(e.getErrorCode());
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
	
	public void editUser(int id, String username, String password, String role) throws NoSuchAlgorithmException {
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
	
	public boolean existsUser(String name) {
		String tableName = "Benutzer";
		Connection con = DBConnector.connectLogin();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+" WHERE Benutzername='"+name+"'");
			rs = pstmt.executeQuery();
			if(rs.isAfterLast()) {
				return false;
			}
			else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
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



