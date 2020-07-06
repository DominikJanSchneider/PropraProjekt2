package com.propra.HealthAndSaftyBriefing.backend.data;

public class Room {
	private String name;
	private String description;
	
	public Room(String name, String description) {
		this.setName(name);
		this.setDescription(description);
	}
	
	//getter und setter

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
}
