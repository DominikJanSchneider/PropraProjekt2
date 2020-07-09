package com.propra.HealthAndSaftyBriefing.backend.data;

public class AssignedDangerSubst extends DangerSubst {
	
	private int personId;
	
	public AssignedDangerSubst(String name, int personId) {
		super(name);
		this.setPersonId(personId);
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}
}