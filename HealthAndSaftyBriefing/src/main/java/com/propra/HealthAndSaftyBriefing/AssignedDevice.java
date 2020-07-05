package com.propra.HealthAndSaftyBriefing;

public class AssignedDevice extends Device {
	
	private double usageTime;
	
	public AssignedDevice(int id, String name, String description, String room, double usageTime) {
		super(id, name, description, room);
		this.setUsageTime(usageTime);
	}

	public double getUsageTime() {
		return usageTime;
	}

	public void setUsageTime(double usageTime) {
		this.usageTime = usageTime;
	}
}
