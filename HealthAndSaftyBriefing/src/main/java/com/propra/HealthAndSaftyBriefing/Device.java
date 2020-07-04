package com.propra.HealthAndSaftyBriefing;

public class Device {
	
	private int id;
	private String name;
	private String description;
	private String room;
	
	Device(int id, String name, String description, String room) {
		this.setId(id);
		this.setName(name);
		this.setDescription(description);
		this.setRoom(room);
	}
	
	//getter and setter
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}
}
