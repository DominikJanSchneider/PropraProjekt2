package com.propra.HealthAndSaftyBriefing.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

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
	

}
