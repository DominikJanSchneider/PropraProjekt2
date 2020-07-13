package com.propra.HealthAndSaftyBriefing.database;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConverter {
	
	public static void convertToCSV() {
		String tableName = "Personen";
		String fileName = "src/main/resources/database/CoreDatabase.csv";
		String head;
		String line;
		String[] columnNames;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BufferedWriter fileWriter= null;
		
		try {
			con = DBConnector.connectCore();
			
			//Write Person Table
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+";");
			rs = pstmt.executeQuery();
			fileWriter = new BufferedWriter(new FileWriter(fileName));
			
			columnNames = new String[rs.getMetaData().getColumnCount()];
			for (int i = 0; i < columnNames.length; i++) {
				columnNames[i] = rs.getMetaData().getColumnName(i+1); //Careful Arrays start counting at 0, sql column count starts at 1
			}
			head = String.format("%s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s;%n",
					columnNames[0], columnNames[1], columnNames[2], columnNames[3], columnNames[4], columnNames[5], columnNames[6],
					columnNames[7], columnNames[8], columnNames[9], columnNames[10], columnNames[11], columnNames[12], columnNames[13],
					columnNames[14], columnNames[15], columnNames[16]);
			fileWriter.write(head);
			
			while (rs.next()) {
				int id = rs.getInt("ID");
				String lName = rs.getString("Name");
				String fName = rs.getString("Vorname");
				String date = rs.getString("Datum");
				String ifwt = rs.getString("Ifwt");
				String mnaf = rs.getString("MNaF");
				String intern = rs.getString("Intern");
				String employmentType = rs.getString("Beschaeftigungsverhaeltnis");
				String begin = rs.getString("Beginn");
				String end = rs.getString("Ende");
				String extern = rs.getString("Extern");
				String email = rs.getString("E-Mail Adresse");
				String generalInstructions = rs.getString("Allgemeine Unterweisung");
				String labSetup = rs.getString("Laboreinrichtungen");
				String dangerousSubstances = rs.getString("Gefahrstoffe");
				String labComment = rs.getString("LabKommentar");
				String dangerSubstComment = rs.getString("GefKommentar");
				
				line = String.format("%d; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s;%n",
						id, lName, fName, date, ifwt, mnaf, intern, employmentType, begin, end, extern, email,
						generalInstructions, labSetup, dangerousSubstances, labComment, dangerSubstComment);
				fileWriter.write(line);
			}
			pstmt.close();
			fileWriter.write("\n\n");
			
			
			tableName = "Ger\u00e4te";
			//Write Device Table
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+";");
			rs = pstmt.executeQuery();
			
			columnNames = new String[rs.getMetaData().getColumnCount()];
			for (int i = 0; i < columnNames.length; i++) {
				columnNames[i] = rs.getMetaData().getColumnName(i+1);
			}
			head = String.format("%s; %s; %s; %s;%n",
					columnNames[0], columnNames[1], columnNames[2], columnNames[3]);
			fileWriter.write(head);
			
			while (rs.next()) {
				int id = rs.getInt("Ger\u00e4teID");
				String name = rs.getString("Name");
				String description = rs.getString("Beschreibung");
				String room = rs.getString("Raum");
				
				line = String.format("%d; %s; %s; %s; %n",
						id, name, description, room);
				fileWriter.write(line);
			}
			pstmt.close();
			fileWriter.write("\n\n");
			
			
			tableName = "R\u00e4ume";
			//Write Room Table
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+";");
			rs = pstmt.executeQuery();
			
			columnNames = new String[rs.getMetaData().getColumnCount()];
			for (int i = 0; i < columnNames.length; i++) {
				columnNames[i] = rs.getMetaData().getColumnName(i+1);
			}
			head = String.format("%s; %s;%n",
					columnNames[0], columnNames[1]);
			fileWriter.write(head);
			
			while (rs.next()) {
				String name = rs.getString("Name");
				String description = rs.getString("Beschreibung");
				
				line = String.format("%s; %s;%n",
						name, description);
				fileWriter.write(line);
			}
			pstmt.close();
			fileWriter.write("\n\n");
			
			
			tableName = "Ger\u00e4tezuordnung";
			//Write Device Assignment Table
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+";");
			rs = pstmt.executeQuery();
			
			columnNames = new String[rs.getMetaData().getColumnCount()];
			for (int i = 0; i < columnNames.length; i++) {
				columnNames[i] = rs.getMetaData().getColumnName(i+1);
			}
			head = String.format("%s; %s; %s;%n",
					columnNames[0], columnNames[1], columnNames[2]);
			fileWriter.write(head);
			
			while (rs.next()) {
				int deviceId = rs.getInt("Ger\u00e4teID");
				int personId = rs.getInt("PersonenID");
				double usageTime = rs.getDouble("Nutzungszeit");
				
				line = String.format("%d; %d; %f;%n",
						deviceId, personId, usageTime);
				fileWriter.write(line);
			}
			pstmt.close();
			fileWriter.write("\n\n");
			
			
			tableName = "Gefahrstoffe";
			//Write Dangerous Substances Table
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+";");
			rs = pstmt.executeQuery();
			
			columnNames = new String[rs.getMetaData().getColumnCount()];
			for (int i = 0; i < columnNames.length; i++) {
				columnNames[i] = rs.getMetaData().getColumnName(i+1);
			}
			head = String.format("%s;%n", columnNames[0]);
			fileWriter.write(head);
			
			while (rs.next()) {
				String dangerSubst = rs.getString("Name");
				
				line = String.format("%s;%n", dangerSubst);
				fileWriter.write(line);
			}
			pstmt.close();
			fileWriter.write("\n\n");
			
			
			tableName = "Gefahrstoffzuordnung";
			//Write Dangerous Substance Assignment Table
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+";");
			rs = pstmt.executeQuery();
			
			columnNames = new String[rs.getMetaData().getColumnCount()];
			for (int i = 0; i < columnNames.length; i++) {
				columnNames[i] = rs.getMetaData().getColumnName(i+1);
			}
			head = String.format("%s; %s;%n",
					columnNames[0], columnNames[1]);
			fileWriter.write(head);
			
			while (rs.next()) {
				String dangerSubst = rs.getString("Gefahrstoff");
				int personId = rs.getInt("PersonenID");
				
				line = String.format("%s; %d;%n",
						dangerSubst, personId);
				fileWriter.write(line);
			}
			pstmt.close();
			fileWriter.write("\n\n");
			
			
			tableName = "sqlite_sequence";
			//Write sqlite_sequence Table (Is used for autoincrement)
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+";");
			rs = pstmt.executeQuery();
			
			columnNames = new String[rs.getMetaData().getColumnCount()];
			for (int i = 0; i < columnNames.length; i++) {
				columnNames[i] = rs.getMetaData().getColumnName(i+1);
			}
			head = String.format("%s; %s;%n",
					columnNames[0],  columnNames[1]);
			fileWriter.write(head);
			
			while (rs.next()) {
				String name = rs.getString("name");
				int seq = rs.getInt("seq");
				
				line = String.format("%s; %d;%n",
						name, seq);
				fileWriter.write(line);
			}
			pstmt.close();
			fileWriter.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
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
