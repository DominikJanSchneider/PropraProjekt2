package com.propra.HealthAndSaftyBriefing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.propra.HealthAndSaftyBriefing.database.DBConnector;

public class PersonManager {
	
	public List<Person> getPersonsData() {
		String tableName = "Personen";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Person> list = new LinkedList<Person>();
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Person person = new Person(
							rs.getInt("ID"),
							rs.getString("Name"),
							rs.getString("Vorname"),
							rs.getString("Datum"),
							rs.getString("Ifwt"),
							rs.getString("MNaF"),
							rs.getString("Intern"),
							rs.getString("Beschaeftigungsverhaeltnis"),
							rs.getString("Beginn"),
							rs.getString("Ende"),
							rs.getString("Extern"),
							rs.getString("E-Mail Adresse")
						);
				list.add(person);
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
