package com.propra.HealthAndSaftyBriefing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.propra.HealthAndSaftyBriefing.database.DBConnector;
import com.propra.HealthAndSaftyBriefing.gui.RoomsView;

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
	
	public List<Room> getRoomsByName() {
		String tableName = "R\u00e4ume";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Room> list = new LinkedList<Room>();
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName + " WHERE Name LIKE '%" + RoomsView.getTfRoomName().getValue() + "%'");
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
}
