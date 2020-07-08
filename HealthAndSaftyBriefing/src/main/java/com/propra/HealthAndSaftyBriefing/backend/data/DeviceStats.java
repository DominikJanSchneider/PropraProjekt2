package com.propra.HealthAndSaftyBriefing.backend.data;

public class DeviceStats {
	private int personID;
	private String personLName;
	private String personFName;
	private double useTime;
	
	public DeviceStats(int personID, String personLName, String personFName, double useTime) {
		this.setPersonID(personID);
		this.setPersonLName(personLName);
		this.setPersonFName(personFName);
		this.setUseTime(useTime);
	}
	//getter and setter
	public int getPersonID() {
		return personID;
	}
	public void setPersonID(int personID) {
		this.personID = personID;
	}
	public String getPersonLName() {
		return personLName;
	}
	public void setPersonLName(String personLName) {
		this.personLName = personLName;
	}
	public String getPersonFName() {
		return personFName;
	}
	public void setPersonFName(String personFName) {
		this.personFName = personFName;
	}
	public double getUseTime() {
		return useTime;
	}
	public void setUseTime(double useTime) {
		this.useTime = useTime;
	}
	
	
}
