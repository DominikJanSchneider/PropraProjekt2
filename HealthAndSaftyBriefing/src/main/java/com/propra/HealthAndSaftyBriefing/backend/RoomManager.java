package com.propra.HealthAndSaftyBriefing.backend;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.propra.HealthAndSaftyBriefing.backend.data.AssignedRoom;
import com.propra.HealthAndSaftyBriefing.backend.data.Room;
import com.propra.HealthAndSaftyBriefing.database.DBConnector;
import com.propra.HealthAndSaftyBriefing.security.pwEncrypt;

public class RoomManager {
	public List<Room> getRoomsData() {
		String tableName = "R\u00e4ume";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Room> list = new LinkedList<Room>();
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Room room = new Room(
							rs.getString("Name"),
							rs.getString("Beschreibung")
						);
				list.add(room);
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
	

	public List<Room> getRoomsByName(String name) {
		String tableName = "R\u00e4ume";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Room> list = new LinkedList<Room>();
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName + " WHERE Name LIKE '%"+name+"%'");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Room room = new Room(
							rs.getString("Name"),
							rs.getString("Beschreibung")
						);
				list.add(room);
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
	
	public List<AssignedRoom> getAssignedRooms(int dID) {
		String tableName1 = "Ger\u00e4te";
		String tableName2 = "R\u00e4ume";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		List<AssignedRoom> list = new LinkedList<AssignedRoom>();
		try {
			pstmt = con.prepareStatement("SELECT "+tableName1+".Raum, "+tableName1+".Ger\u00e4teID, "+tableName2+".Beschreibung FROM "+tableName1+" INNER JOIN "+tableName2+" ON "+tableName1+".Raum = "+tableName2+".Name WHERE Ger\u00e4teID='"+ dID +"'");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				AssignedRoom room = new AssignedRoom(
						rs.getString("Raum"),
						rs.getString("Beschreibung"),
						rs.getInt("Ger\u00e4teID")
						);
				list.add(room);
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
	
	public List<Room> getUnassignedRooms(int dID) {
		String tableName1 = "Ger\u00e4te";
		String tableName2 = "R\u00e4ume";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		List<Room> list = new LinkedList<Room>();
		try {
			pstmt = con.prepareStatement("SELECT Name, Beschreibung FROM "+tableName2+" WHERE "
					+ "("+tableName2+".Name IN ("
						+"SELECT "+tableName2+".Name FROM "+tableName2+" EXCEPT SELECT "+tableName1+".Raum FROM "+tableName1+" WHERE Ger\u00e4teID='"+dID+"'))");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Room room = new Room(
						rs.getString("Name"),
						rs.getString("Beschreibung")
						);
				list.add(room);
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
	
	public List<Room> getUnassignedRoomsByName(int dID, String name) {
		String tableName1 = "Ger\u00e4te";
		String tableName2 = "R\u00e4ume";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		List<Room> list = new LinkedList<Room>();
		try {
			pstmt = con.prepareStatement("SELECT Name, Beschreibung FROM "+tableName2+" WHERE "+tableName2+".Name Like '%"+name+"%' AND"
					+ "("+tableName2+".Name IN ("
						+"SELECT "+tableName2+".Name FROM "+tableName2+" EXCEPT SELECT "+tableName1+".Raum FROM "+tableName1+" WHERE Ger\u00e4teID='"+dID+"'))");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Room room = new Room(
						rs.getString("Name"),
						rs.getString("Beschreibung")
						);
				list.add(room);
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
	
	public void assignRoom(int dID, String room){
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement("UPDATE Ger\u00e4te SET Raum='"+room+"' WHERE Ger\u00e4teID="+dID+" ;");
			pstmt.execute();
			con.commit();
		}
		catch (SQLException e) {
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
	
	public void unassignRoom(int dID) {
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("UPDATE Ger\u00e4te SET Raum='' WHERE Ger\u00e4teID="+dID+";");
			con.setAutoCommit(false);
			pstmt.executeUpdate();
			con.commit();
		}
	    catch (SQLException e) {
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
	
	public void addRoom(String name, String description) throws NoSuchAlgorithmException {
		String tableName = "R\u00e4ume";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement("INSERT INTO "+tableName+ "(Name, Beschreibung) VALUES(?, ?)");
			
			pstmt.setString(1, name);
			pstmt.setString(2, description);
			
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
	
	public void deleteRoom(String name) {
		String tableName = "R\u00e4ume";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		try {
			
			pstmt = con.prepareStatement("DELETE FROM " +tableName+ " WHERE Name ='"+name+"'");
			con.setAutoCommit(false);
			pstmt.execute();
			pstmt.close();
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
	
	public void editRoom(String roomName, String newName, String description) throws NoSuchAlgorithmException {
		String tableName = "R\u00e4ume";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("UPDATE "+tableName+" SET Name='"+newName+"', Beschreibung='"+description+"' WHERE Name='"+roomName+"'");
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
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
	
	public boolean existsRoom(String name) {
		String tableName = "R\u00e4ume";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+" WHERE Name='"+name+"'");
			rs = pstmt.executeQuery();
			if (rs.isAfterLast()) {
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
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnector.deconnect();
		}
	}
}
