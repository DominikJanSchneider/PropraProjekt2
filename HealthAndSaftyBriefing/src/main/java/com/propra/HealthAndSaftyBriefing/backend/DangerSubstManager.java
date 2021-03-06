package com.propra.HealthAndSaftyBriefing.backend;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.propra.HealthAndSaftyBriefing.backend.data.AssignedDangerSubst;
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
	
	public List<AssignedDangerSubst> getAssignedDangerSubstByName(int pID, String name) {
		String tableName1 = "Gefahrstoffzuordnung";
		String tableName2 = "Gefahrstoffe";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		List<AssignedDangerSubst> list = new LinkedList<AssignedDangerSubst>();
		try {
			pstmt = con.prepareStatement("SELECT "+tableName1+".Gefahrstoff, PersonenID FROM "+tableName1+" INNER JOIN "+tableName2+" ON "+tableName1+".Gefahrstoff = "+tableName2+".name WHERE PersonenID='"+pID+"' AND "+tableName2+".Name LIKE '%"+name+"%'");
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
	
	public List<DangerSubst> getUnassignedDangerSubst(int pID) {
		String tableName1 = "Gefahrstoffzuordnung";
		String tableName2 = "Gefahrstoffe";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		List<DangerSubst> list = new LinkedList<DangerSubst>();
		try {
			pstmt = con.prepareStatement("SELECT Name FROM "+tableName2+" WHERE "
					+ "("+tableName2+".Name IN ("
						+"SELECT "+tableName2+".Name FROM "+tableName2+" EXCEPT SELECT "+tableName1+".Gefahrstoff FROM "+tableName1+" WHERE PersonenID='"+pID+"'))");
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
	
	public List<DangerSubst> getUnassignedDangerSubstByName(int pID, String name) {
		String tableName1 = "Gefahrstoffzuordnung";
		String tableName2 = "Gefahrstoffe";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		List<DangerSubst> list = new LinkedList<DangerSubst>();
		try {
			pstmt = con.prepareStatement("SELECT Name FROM "+tableName2+" WHERE "+tableName2+".Name LIKE '%"+name+"%' AND"
					+ "("+tableName2+".Name IN ("
						+"SELECT "+tableName2+".Name FROM "+tableName2+" EXCEPT SELECT "+tableName1+".Gefahrstoff FROM "+tableName1+" WHERE PersonenID='"+pID+"'))");
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
	
	public void assignDangerSubst(String dangerSubst, int pID){
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			String stmt = "INSERT INTO Gefahrstoffzuordnung (Gefahrstoff, PersonenID) "
					+ "VALUES (?, ?)";
			pstmt = con.prepareStatement(stmt);
			pstmt.setString(1, dangerSubst);
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
	
	public void unassignDangerSubst(String dangerSubst, int pID) {
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		try {
			String stmt="DELETE FROM Gefahrstoffzuordnung WHERE Gefahrstoff='"+dangerSubst+"' AND PersonenID='"+pID+"'";
			pstmt = con.prepareStatement(stmt);
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
	
	public void addDangerSubst(String name) throws NoSuchAlgorithmException {
		String tableName = "Gefahrstoffe";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement("INSERT INTO "+tableName+ "(Name) VALUES(?)");
			pstmt.setString(1, name);
			pstmt.executeUpdate();
			
				
			}catch (SQLException e) {
				System.out.println(e.getMessage());
				System.out.println(e.getErrorCode());
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
	
	public void deleteDangerSubst(String name) {
		String tableName = "Gefahrstoffe";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		try {
			
			pstmt = con.prepareStatement("DELETE FROM " +tableName+ " WHERE Name ='"+name+"'");
			con.setAutoCommit(false);
			pstmt.execute();
			pstmt.close();
			con.commit();
			
		}catch (SQLException e) {
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
	
	public void editDangerSubst(String name, String newName) throws NoSuchAlgorithmException {
		String tableName = "Gefahrstoffe";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("UPDATE "+tableName+" SET Name='"+newName+"' WHERE Name='"+name+"'");
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
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
	
	public boolean existsDangerSubst(String name) {
		String tableName = "Gefahrstoffe";
		Connection con = DBConnector.connectCore();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+" WHERE Name='"+name+"'");
			rs = pstmt.executeQuery();
			if (rs.isAfterLast()) {
				return false;
			}
			else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
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
