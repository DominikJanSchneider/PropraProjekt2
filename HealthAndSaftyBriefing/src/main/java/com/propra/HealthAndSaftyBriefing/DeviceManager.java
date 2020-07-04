package com.propra.HealthAndSaftyBriefing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

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
}
