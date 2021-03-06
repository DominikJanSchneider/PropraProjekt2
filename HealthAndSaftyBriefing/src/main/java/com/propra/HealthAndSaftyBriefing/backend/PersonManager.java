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
import com.propra.HealthAndSaftyBriefing.database.DBConnector;

public class PersonManager {
	public List<Person> getPersonsData() {
		String tableName = "Personen";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Person> list = new LinkedList<Person>();
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName);
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
				Person person = new Person(
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
				list.add(person);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		catch (ParseException e) {
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
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+ " WHERE Name LIKE '%"+name+"%' ;");
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
				Person person = new Person(
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
				list.add(person);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		catch (ParseException e) {
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
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+ " WHERE Ifwt!='' AND Ifwt IS NOT NULL");
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
				Person person = new Person(
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
				list.add(person);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		catch (ParseException e) {
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
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+ " WHERE Ifwt='LMN'");
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
				Person person = new Person(
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
				list.add(person);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		catch (ParseException e) {
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
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+ " WHERE Ifwt='LMW'");
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
				Person person = new Person(
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
				list.add(person);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		catch (ParseException e) {
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
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+ " WHERE Ifwt='LOT'");
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
				Person person = new Person(
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
				list.add(person);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		catch (ParseException e) {
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
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+ " WHERE Ifwt='LWF'");
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
				Person person = new Person(
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
				list.add(person);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		catch (ParseException e) {
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
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+ " WHERE MNaF!='' AND NOT MNaF IS NULL");
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
				Person person = new Person(
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
				list.add(person);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		catch (ParseException e) {
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
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+ " WHERE Intern!='' AND NOT Intern IS NULL");
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
				Person person = new Person(
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
				list.add(person);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		catch (ParseException e) {
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
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+ " WHERE Extern!='' AND NOT Extern IS NULL");
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
				Person person = new Person(
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
				list.add(person);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		catch (ParseException e) {
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

	public Person getPersonByID(int pID) {
		String tableName = "Personen";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Person person = null;
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+" Where ID='"+pID+"'");
			rs = pstmt.executeQuery();
			if (rs.next()) {
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
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			DBConnector.deconnect();
      }
	}
	
	public void deletePerson(int id) {
		String tableName = "Personen";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		try {

			pstmt = con.prepareStatement("DELETE FROM " + tableName + " WHERE ID =" + id);
			con.setAutoCommit(false);
			pstmt.execute();
			pstmt.close();
			con.commit();
			
			pstmt = con.prepareStatement("UPDATE sqlite_sequence SET seq='"+(id-1)+"' WHERE name='Personen';");
			con.setAutoCommit(false);
			pstmt.executeUpdate();
			con.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
	
	public void editPerson(int id, String lName, String fName, Date date, String ifwt, String mnaf, String intern,
			String extern, String employment, Date begin, Date end, String email, String gI, String lC, String dC)
			throws NoSuchAlgorithmException {
		String tableName = "Personen";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		String dateString = "";
		if(date != null) {
			dateString = df.format(date);
		}
		String beginString = "";
		if(begin != null) {
			beginString = df.format(begin);
		}
		String endString = "";
		if(end != null) {
			endString = df.format(end);
		}
		try {
			pstmt = con.prepareStatement("UPDATE " + tableName + " SET Name='" + lName + "', Vorname='" + fName
					+ "' ,Datum= '" + dateString + "', Ifwt='" + ifwt + "', MNaF='" + mnaf + "', Intern='" + intern
					+ "', Extern='" + extern + "', Beschaeftigungsverhaeltnis='" + employment + "', Beginn='" + beginString
					+ "', Ende='" + endString + "', 'E-Mail Adresse'='" + email + "', 'Allgemeine Unterweisung'='" + gI
					+ "', LabKommentar='" + lC + "', GefKommentar='" + dC + "' WHERE ID=" + id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
	
	public void addPerson(String lName, String fName, Date date, String ifwt, String mnaf, String intern,
			String extern, String employment, Date begin, Date end, String email, String gI, String lC, String dC)
			throws NoSuchAlgorithmException {
		String tableName = "Personen";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		String dateString = "";
		if(date != null) {
			dateString = df.format(date);
		}
		String beginString = "";
		if(begin != null) {
			beginString = df.format(begin);
		}
		String endString = "";
		if(end != null) {
			endString = df.format(end);
		}
		try {
			pstmt = con.prepareStatement("INSERT INTO " + tableName
					+ "(Name, Vorname, Datum, Ifwt, MNaF, Intern, Extern, Beschaeftigungsverhaeltnis, Beginn, Ende, 'E-Mail Adresse', 'Allgemeine Unterweisung', Laboreinrichtungen, Gefahrstoffe, LabKommentar, GefKommentar) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setString(1, lName);
			pstmt.setString(2, fName);
			pstmt.setString(3, dateString);
			pstmt.setString(4, ifwt);
			pstmt.setString(5, mnaf);
			pstmt.setString(6, intern);
			pstmt.setString(7, extern);
			pstmt.setString(8, employment);
			pstmt.setString(9, beginString);
			pstmt.setString(10, endString);
			pstmt.setString(11, email);
			pstmt.setString(12, gI);
			pstmt.setString(13, "");
			pstmt.setString(14, "");
			pstmt.setString(15, lC);
			pstmt.setString(16, dC);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getErrorCode());
			e.printStackTrace();
		} finally {
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
	
	public void setLabSetup(int pID, String labSetup) throws NoSuchAlgorithmException {
		String tableName = "Personen";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement("UPDATE "+tableName+" SET Laboreinrichtungen='"+labSetup+"' WHERE ID=" +pID);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getErrorCode());
			e.printStackTrace();
		} finally {
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
	
	public void setDangerSubst(int pID, String dangerSubst) throws NoSuchAlgorithmException {
		String tableName = "Personen";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement("UPDATE "+tableName+" SET Gefahrstoffe='"+dangerSubst+"' WHERE ID=" +pID);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getErrorCode());
			e.printStackTrace();
		} finally {
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
