package com.propra.HealthAndSaftyBriefing.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.propra.HealthAndSaftyBriefing.backend.data.AssignedDevice;
import com.propra.HealthAndSaftyBriefing.backend.data.Device;
import com.propra.HealthAndSaftyBriefing.database.DBConnector;

public class DeviceManager {
	public List<Device> getDevicesData() {
		String tableName = "Ger\u00e4te";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Device> list = new LinkedList<Device>();
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Device device = new Device(
							rs.getInt("Ger\u00e4teID"),
							rs.getString("Name"),
							rs.getString("Beschreibung"),
							rs.getString("Raum")
						);
				list.add(device);
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
	
	public List<Device> getDevicesByID(int id) {
		String tableName = "Ger\u00e4te";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Device> list = new LinkedList<Device>();
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+ " WHERE Ger\u00e4teID='"+id+"'");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Device device = new Device(
							rs.getInt("Ger\u00e4teID"),
							rs.getString("Name"),
							rs.getString("Beschreibung"),
							rs.getString("Raum")
						);
				list.add(device);
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
	
	public List<Device> getDevicesByName(String name) {
		String tableName = "Ger\u00e4te";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Device> list = new LinkedList<Device>();
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+ " WHERE Name LIKE '%" +name+ "%'");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Device device = new Device(
							rs.getInt("Ger\u00e4teID"),
							rs.getString("Name"),
							rs.getString("Beschreibung"),
							rs.getString("Raum")
						);
				list.add(device);
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
	
	public List<Device> getDevicesByRoom(String room) {
		String tableName = "Ger\u00e4te";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Device> list = new LinkedList<Device>();
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+" WHERE Raum LIKE '%"+room+"%'");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Device device = new Device(
							rs.getInt("Ger\u00e4teID"),
							rs.getString("Name"),
							rs.getString("Beschreibung"),
							rs.getString("Raum")
						);
				list.add(device);
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
	
	public List<AssignedDevice> getAssignedDevices(int pID) {
		String tableName1 = "Ger\u00e4tezuordnung";
		String tableName2 = "Ger\u00e4te";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		List<AssignedDevice> list = new LinkedList<AssignedDevice>();
		try {
			pstmt = con.prepareStatement("SELECT "+tableName1+".Ger\u00e4teID, Name, Beschreibung, Raum, Nutzungszeit FROM "+tableName1+" INNER JOIN "+tableName2+" ON "+tableName1+".Ger\u00e4teID = "+tableName2+".Ger\u00e4teID WHERE PersonenID='"+pID+"'");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				AssignedDevice device = new AssignedDevice(
						rs.getInt("Ger\u00e4teID"),
						rs.getString("Name"),
						rs.getString("Beschreibung"),
						rs.getString("Raum"),
						rs.getDouble("Nutzungszeit")

						);
				list.add(device);
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
	
	public List<AssignedDevice> getAssignedDevicesByID(int pID, int dID) {
		String tableName1 = "Ger\u00e4tezuordnung";
		String tableName2 = "Ger\u00e4te";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		List<AssignedDevice> list = new LinkedList<AssignedDevice>();
		try {
			pstmt = con.prepareStatement("SELECT "+tableName1+".Ger\u00e4teID, Name, Beschreibung, Raum, Nutzungszeit FROM "+tableName1+" INNER JOIN "+tableName2+" ON "+tableName1+".Ger\u00e4teID = "+tableName2+".Ger\u00e4teID WHERE PersonenID='"+pID+"' AND "+tableName2+".Ger\u00e4teID ='"+dID+"'");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				AssignedDevice device = new AssignedDevice(
						rs.getInt("Ger\u00e4teID"),
						rs.getString("Name"),
						rs.getString("Beschreibung"),
						rs.getString("Raum"),
						rs.getDouble("Nutzungszeit")

						);
				list.add(device);
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
	
	public List<AssignedDevice> getAssignedDevicesByName(int pID, String name) {
		String tableName1 = "Ger\u00e4tezuordnung";
		String tableName2 = "Ger\u00e4te";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		List<AssignedDevice> list = new LinkedList<AssignedDevice>();
		try {
			pstmt = con.prepareStatement("SELECT "+tableName1+".Ger\u00e4teID, Name, Beschreibung, Raum, Nutzungszeit FROM "+tableName1+" INNER JOIN "+tableName2+" ON "+tableName1+".Ger\u00e4teID = "+tableName2+".Ger\u00e4teID WHERE PersonenID='"+pID+"' AND "+tableName2+".Name LIKE '%"+name+"%'");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				AssignedDevice device = new AssignedDevice(
						rs.getInt("Ger\u00e4teID"),
						rs.getString("Name"),
						rs.getString("Beschreibung"),
						rs.getString("Raum"),
						rs.getDouble("Nutzungszeit")

						);
				list.add(device);
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
	
	public List<AssignedDevice> getAssignedDevicesByRoom(int pID, String room) {
		String tableName1 = "Ger\u00e4tezuordnung";
		String tableName2 = "Ger\u00e4te";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		List<AssignedDevice> list = new LinkedList<AssignedDevice>();
		try {
			pstmt = con.prepareStatement("SELECT "+tableName1+".Ger\u00e4teID, Name, Beschreibung, Raum, Nutzungszeit FROM "+tableName1+" INNER JOIN "+tableName2+" ON "+tableName1+".Ger\u00e4teID = "+tableName2+".Ger\u00e4teID WHERE PersonenID='"+pID+"' AND "+tableName2+".Raum LIKE '%"+room+"%'");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				AssignedDevice device = new AssignedDevice(
						rs.getInt("Ger\u00e4teID"),
						rs.getString("Name"),
						rs.getString("Beschreibung"),
						rs.getString("Raum"),
						rs.getDouble("Nutzungszeit")

						);
				list.add(device);
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
	
	public List<Device> getUnassignedDevices(int pID) {
		String tableName1 = "Ger\u00e4tezuordnung";
		String tableName2 = "Ger\u00e4te";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		List<Device> list = new LinkedList<Device>();
		try {
			pstmt = con.prepareStatement("SELECT Ger\u00e4teID, Name, Beschreibung, Raum FROM "+tableName2+" WHERE "
					+ "("+tableName2+".Ger\u00e4teID IN ("
						+"SELECT "+tableName2+".Ger\u00e4teID FROM "+tableName2+" EXCEPT SELECT "+tableName1+".Ger\u00e4teID FROM "+tableName1+" WHERE PersonenID='"+pID+"'))");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Device device = new Device(
						rs.getInt("Ger\u00e4teID"),
						rs.getString("Name"),
						rs.getString("Beschreibung"),
						rs.getString("Raum")
						);
				list.add(device);
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
	
	public List<Device> getUnassignedDevicesByID(int pID, int dID) {
		String tableName1 = "Ger\u00e4tezuordnung";
		String tableName2 = "Ger\u00e4te";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		List<Device> list = new LinkedList<Device>();
		try {
			pstmt = con.prepareStatement("SELECT Ger\u00e4teID, Name, Beschreibung, Raum FROM "+tableName2+" WHERE "+tableName2+".Ger\u00e4teID='"+dID+"' AND"
					+ "("+tableName2+".Ger\u00e4teID IN ("
						+"SELECT "+tableName2+".Ger\u00e4teID FROM "+tableName2+" EXCEPT SELECT "+tableName1+".Ger\u00e4teID FROM "+tableName1+" WHERE PersonenID='"+pID+"'))");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Device device = new Device(
						rs.getInt("Ger\u00e4teID"),
						rs.getString("Name"),
						rs.getString("Beschreibung"),
						rs.getString("Raum")
						);
				list.add(device);
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
	
	public List<Device> getUnassignedDevicesByName(int pID, String name) {
		String tableName1 = "Ger\u00e4tezuordnung";
		String tableName2 = "Ger\u00e4te";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		List<Device> list = new LinkedList<Device>();
		try {
			pstmt = con.prepareStatement("SELECT Ger\u00e4teID, Name, Beschreibung, Raum FROM "+tableName2+" WHERE "+tableName2+".Name LIKE '%"+name+"%' AND"
					+ "("+tableName2+".Ger\u00e4teID IN ("
						+"SELECT "+tableName2+".Ger\u00e4teID FROM "+tableName2+" EXCEPT SELECT "+tableName1+".Ger\u00e4teID FROM "+tableName1+" WHERE PersonenID='"+pID+"'))");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Device device = new Device(
						rs.getInt("Ger\u00e4teID"),
						rs.getString("Name"),
						rs.getString("Beschreibung"),
						rs.getString("Raum")
						);
				list.add(device);
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
	
	public List<Device> getUnassignedDevicesByRoom(int pID, String room) {
		String tableName1 = "Ger\u00e4tezuordnung";
		String tableName2 = "Ger\u00e4te";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		List<Device> list = new LinkedList<Device>();
		try {
			pstmt = con.prepareStatement("SELECT Ger\u00e4teID, Name, Beschreibung, Raum FROM "+tableName2+" WHERE "+tableName2+".Raum LIKE '%"+room+"%' AND"
					+ "("+tableName2+".Ger\u00e4teID IN ("
						+"SELECT "+tableName2+".Ger\u00e4teID FROM "+tableName2+" EXCEPT SELECT "+tableName1+".Ger\u00e4teID FROM "+tableName1+" WHERE PersonenID='"+pID+"'))");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Device device = new Device(
						rs.getInt("Ger\u00e4teID"),
						rs.getString("Name"),
						rs.getString("Beschreibung"),
						rs.getString("Raum")
						);
				list.add(device);
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
	
	public void setUsageTime(int deviceID, int personID, double usageTime) {
		String tableName = "Ger\u00e4tezuordnung";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement("UPDATE "+tableName+" SET Nutzungszeit="+usageTime+" WHERE Ger\u00e4teID='"+deviceID+"' AND PersonenID='"+personID+"'");
			pstmt.executeUpdate();
			con.commit();
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
	
	public void assignDevice(int dID, int pID){
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			String stmt = "INSERT INTO Ger\u00e4tezuordnung (Ger\u00e4teID, PersonenID) "
					+ "VALUES (?, ?)";
			pstmt = con.prepareStatement(stmt);
			pstmt.setInt(1, dID);
			pstmt.setInt(2, pID);
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
	
	public void unassignDevice(int dID, int pID) {
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("delete from Ger\u00e4tezuordnung where Ger\u00e4teID='"+dID+"' AND PersonenID='"+pID+"'");
			con.setAutoCommit(false);
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
}
