package com.propra.HealthAndSaftyBriefing.backend.data;

public class AssignedRoom extends Room {
	private int deviceId;
	public AssignedRoom(String name, String description, int deviceId) {
		super(name, description);
		this.setDeviceId(deviceId);
	}
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
}
