package com.propra.HealthAndSaftyBriefing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.propra.HealthAndSaftyBriefing.database.DBConnector;

public class DeviceStatsManager {
	public List<DeviceStats> getDeviceStatsData(int dID) {
		String tableName1 = "Ger\u00e4tezuordnung";
		String tableName2 = "Personen";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<DeviceStats> list = new LinkedList<DeviceStats>();
		try {
			pstmt = con.prepareStatement("SELECT ID, Name, Vorname, Nutzungszeit FROM "+tableName1+" INNER JOIN "+tableName2+" ON "+tableName1+".PersonenID = "+tableName2+".ID WHERE Ger\u00e4teID='"+dID+"'");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				DeviceStats deviceStats = new DeviceStats(
							rs.getInt("ID"),
							rs.getString("Name"),
							rs.getString("Vorname"),
							rs.getDouble("Nutzungszeit")
						);
				list.add(deviceStats);
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
	
	public List<DeviceStats> getDeviceStatsByPersonID(int dID, int pID) {
		String tableName1 = "Ger\u00e4tezuordnung";
		String tableName2 = "Personen";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<DeviceStats> list = new LinkedList<DeviceStats>();
		try {
			pstmt = con.prepareStatement("SELECT ID, Name, Vorname, Nutzungszeit FROM "+tableName1+" INNER JOIN "+tableName2+" ON "+tableName1+".PersonenID = "+tableName2+".ID WHERE Ger\u00e4teID='"+dID+"' AND ID='"+pID+"'");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				DeviceStats deviceStats = new DeviceStats(
							rs.getInt("ID"),
							rs.getString("Name"),
							rs.getString("Vorname"),
							rs.getDouble("Nutzungszeit")
						);
				list.add(deviceStats);
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
	
	public List<DeviceStats> getDeviceStatsByPersonLName(int dID, String name) {
		String tableName1 = "Ger\u00e4tezuordnung";
		String tableName2 = "Personen";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<DeviceStats> list = new LinkedList<DeviceStats>();
		try {
			pstmt = con.prepareStatement("SELECT ID, Name, Vorname, Nutzungszeit FROM "+tableName1+" INNER JOIN "+tableName2+" ON "+tableName1+".PersonenID = "+tableName2+".ID WHERE Ger\u00e4teID='"+dID+"' AND Name LIKE '%"+name+"%';");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				DeviceStats deviceStats = new DeviceStats(
							rs.getInt("ID"),
							rs.getString("Name"),
							rs.getString("Vorname"),
							rs.getDouble("Nutzungszeit")
						);
				list.add(deviceStats);
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
