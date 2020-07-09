package com.propra.HealthAndSaftyBriefing.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.propra.HealthAndSaftyBriefing.backend.data.AssignedDangerSubst;
import com.propra.HealthAndSaftyBriefing.backend.data.AssignedDevice;
import com.propra.HealthAndSaftyBriefing.backend.data.DangerSubst;
import com.propra.HealthAndSaftyBriefing.database.DBConnector;

public class DangerSubstManager {
	public List<DangerSubst> getDangerSubstsData() {
		String tableName = "Gefahrstoffe";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<DangerSubst> list = new LinkedList<DangerSubst>();
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				DangerSubst dangerSubst = new DangerSubst(
							rs.getString("Name")
						);
				list.add(dangerSubst);
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
		
	public List<DangerSubst> getDangerSubstsByName(String dangerSubstName) {
			
		String tableName = "Gefahrstoffe";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<DangerSubst> list = new LinkedList<DangerSubst>();
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName + " WHERE Name LIKE '%" +dangerSubstName +"%'" );
			rs = pstmt.executeQuery();
			while (rs.next()) {
				DangerSubst dangerSubst = new DangerSubst(
							rs.getString("Name")
						);
				list.add(dangerSubst);
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
	
	public List<AssignedDangerSubst> getAssignedDangerSubst(int pID) {
		String tableName1 = "Gefahrstoffzuordnung";
		String tableName2 = "Gefahrstoffe";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		List<AssignedDangerSubst> list = new LinkedList<AssignedDangerSubst>();
		try {
			pstmt = con.prepareStatement("SELECT "+tableName1+".Gefahrstoff, PersonenID FROM "+tableName1+" INNER JOIN "+tableName2+" ON "+tableName1+".Gefahrstoff = "+tableName2+".name WHERE PersonenID='"+pID+"'");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				AssignedDangerSubst dangerSubst = new AssignedDangerSubst(
						rs.getString("Gefahrstoff"),
						rs.getInt("PersonenID")

						);
				list.add(dangerSubst);
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
