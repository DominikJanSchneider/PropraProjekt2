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
							rs.getString("Extern"),
							rs.getString("Beschaeftigungsverhaeltnis"),
							rs.getString("Beginn"),
							rs.getString("Ende"),
							rs.getString("E-Mail Adresse"),
							rs.getString("Allgemeine Unterweisung"),
							rs.getString("Laboreinrichtungen"),
							rs.getString("Gefahrstoffe"),
							rs.getString("LabKommentar"),
							rs.getString("GefKommentar")
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
	
	public List<Person> getPersonsByName(String name) {
		String tableName = "Personen";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Person> list = new LinkedList<Person>();
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+ " WHERE Name LIKE '%"+name+"%' ;");
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
							rs.getString("Extern"),
							rs.getString("Beschaeftigungsverhaeltnis"),
							rs.getString("Beginn"),
							rs.getString("Ende"),
							rs.getString("E-Mail Adresse"),
							rs.getString("Allgemeine Unterweisung"),
							rs.getString("Laboreinrichtungen"),
							rs.getString("Gefahrstoffe"),
							rs.getString("LabKommentar"),
							rs.getString("GefKommentar")
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
	
	public List<Person> getIfwtPersons() {
		String tableName = "Personen";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Person> list = new LinkedList<Person>();
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+ " WHERE Ifwt!='' AND Ifwt IS NOT NULL");
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
							rs.getString("Extern"),
							rs.getString("Beschaeftigungsverhaeltnis"),
							rs.getString("Beginn"),
							rs.getString("Ende"),
							rs.getString("E-Mail Adresse"),
							rs.getString("Allgemeine Unterweisung"),
							rs.getString("Laboreinrichtungen"),
							rs.getString("Gefahrstoffe"),
							rs.getString("LabKommentar"),
							rs.getString("GefKommentar")
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
	
	public List<Person> getLMNPersons() {
		String tableName = "Personen";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Person> list = new LinkedList<Person>();
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+ " WHERE Ifwt='LMN'");
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
							rs.getString("Extern"),
							rs.getString("Beschaeftigungsverhaeltnis"),
							rs.getString("Beginn"),
							rs.getString("Ende"),
							rs.getString("E-Mail Adresse"),
							rs.getString("Allgemeine Unterweisung"),
							rs.getString("Laboreinrichtungen"),
							rs.getString("Gefahrstoffe"),
							rs.getString("LabKommentar"),
							rs.getString("GefKommentar")
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
	
	public List<Person> getLMWPersons() {
		String tableName = "Personen";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Person> list = new LinkedList<Person>();
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+ " WHERE Ifwt='LMW'");
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
							rs.getString("Extern"),
							rs.getString("Beschaeftigungsverhaeltnis"),
							rs.getString("Beginn"),
							rs.getString("Ende"),
							rs.getString("E-Mail Adresse"),
							rs.getString("Allgemeine Unterweisung"),
							rs.getString("Laboreinrichtungen"),
							rs.getString("Gefahrstoffe"),
							rs.getString("LabKommentar"),
							rs.getString("GefKommentar")
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
	
	public List<Person> getLOTPersons() {
		String tableName = "Personen";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Person> list = new LinkedList<Person>();
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+ " WHERE Ifwt='LOT'");
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
							rs.getString("Extern"),
							rs.getString("Beschaeftigungsverhaeltnis"),
							rs.getString("Beginn"),
							rs.getString("Ende"),
							rs.getString("E-Mail Adresse"),
							rs.getString("Allgemeine Unterweisung"),
							rs.getString("Laboreinrichtungen"),
							rs.getString("Gefahrstoffe"),
							rs.getString("LabKommentar"),
							rs.getString("GefKommentar")
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
	
	public List<Person> getLWFPersons() {
		String tableName = "Personen";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Person> list = new LinkedList<Person>();
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+ " WHERE Ifwt='LWF'");
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
							rs.getString("Extern"),
							rs.getString("Beschaeftigungsverhaeltnis"),
							rs.getString("Beginn"),
							rs.getString("Ende"),
							rs.getString("E-Mail Adresse"),
							rs.getString("Allgemeine Unterweisung"),
							rs.getString("Laboreinrichtungen"),
							rs.getString("Gefahrstoffe"),
							rs.getString("LabKommentar"),
							rs.getString("GefKommentar")
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
	
	public List<Person> getMNaFPersons() {
		String tableName = "Personen";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Person> list = new LinkedList<Person>();
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+ " WHERE MNaF!='' AND NOT MNaF IS NULL");
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
							rs.getString("Extern"),
							rs.getString("Beschaeftigungsverhaeltnis"),
							rs.getString("Beginn"),
							rs.getString("Ende"),
							rs.getString("E-Mail Adresse"),
							rs.getString("Allgemeine Unterweisung"),
							rs.getString("Laboreinrichtungen"),
							rs.getString("Gefahrstoffe"),
							rs.getString("LabKommentar"),
							rs.getString("GefKommentar")
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
	
	public List<Person> getInternPersons() {
		String tableName = "Personen";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Person> list = new LinkedList<Person>();
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+ " WHERE Intern!='' AND NOT Intern IS NULL");
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
							rs.getString("Extern"),
							rs.getString("Beschaeftigungsverhaeltnis"),
							rs.getString("Beginn"),
							rs.getString("Ende"),
							rs.getString("E-Mail Adresse"),
							rs.getString("Allgemeine Unterweisung"),
							rs.getString("Laboreinrichtungen"),
							rs.getString("Gefahrstoffe"),
							rs.getString("LabKommentar"),
							rs.getString("GefKommentar")
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
	
	public List<Person> getExternPersons() {
		String tableName = "Personen";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Person> list = new LinkedList<Person>();
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+ " WHERE Extern!='' AND NOT Extern IS NULL");
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
							rs.getString("Extern"),
							rs.getString("Beschaeftigungsverhaeltnis"),
							rs.getString("Beginn"),
							rs.getString("Ende"),
							rs.getString("E-Mail Adresse"),
							rs.getString("Allgemeine Unterweisung"),
							rs.getString("Laboreinrichtungen"),
							rs.getString("Gefahrstoffe"),
							rs.getString("LabKommentar"),
							rs.getString("GefKommentar")
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
