package com.propra.HealthAndSaftyBriefing.backend.data;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Person implements Serializable, Cloneable {
	private int id;
	private String fName;
	private String lName;
	private Date date;
	private String ifwt;
	private String mnaf;
	private String intern;
	private String extern;
	private String employment;
	private Date begin;
	private Date end;
	private String eMail;
	private String genInstr;
	private String labSetup;
	private String dangerSubsts;
	private String labComment;
	private String dangerSubstComment;
	
	public Person(int id, String lName, String fName, Date date, String ifwt, String mnaf, String intern, String extern, String employment, Date begin, Date end, String eMail, String genInstr, String labSetup, String dangerSubsts, String labComment, String dangerSubstComment) {
		this.setId(id);
		this.setFName(fName);
		this.setLName(lName);
		this.setDate(date);
		this.setIfwt(ifwt);
		this.setMNaF(mnaf);
		this.setIntern(intern);
		this.setExtern(extern);
		this.setEmployment(employment);
		this.setBegin(begin);
		this.setEnd(end);
		this.setEMail(eMail);
		this.setGenInstr(genInstr);
		this.setLabSetup(labSetup);
		this.setDangerSubsts(dangerSubsts);
		this.setLabComment(labComment);
		this.setDangerSubstComment(dangerSubstComment);
	}
	
	//getter and setter
	public String getLName() {
		return lName;
	}
	
	public void setLName(String lName) {
		this.lName = lName;
	}
	
	public String getFName() {
		return fName;
	}
	
	public void setFName(String fName) {
		this.fName = fName;
	}
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public EuropeanDate getDateInEuropeanFormat() {
		if(date != null) {
			return new EuropeanDate(date);
		}
		else {
			return null;
		}
	}
	
	public String getIfwt() {
		return ifwt;
	}

	public void setIfwt(String ifwt) {
		this.ifwt = ifwt;
	}

	public String getMNaF() {
		return mnaf;
	}

	public void setMNaF(String mnaf) {
		this.mnaf = mnaf;
	}

	public String getIntern() {
		return intern;
	}

	public void setIntern(String intern) {
		this.intern = intern;
	}

	public String getExtern() {
		return extern;
	}

	public void setExtern(String extern) {
		this.extern = extern;
	}

	public String getEmployment() {
		return employment;
	}

	public void setEmployment(String employment) {
		this.employment = employment;
	}

	public Date getBegin() {
		return begin;
	}
	
	public EuropeanDate getBeginInEuropeanFormat() {
		if(begin != null) {
			return new EuropeanDate(begin);
		}
		else {
			return null;
		}
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}
	
	public EuropeanDate getEndInEuropeanFormat() {
		if(end != null) {
			return new EuropeanDate(end);
		}
		else {
			return null;
		}
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getEMail() {
		return eMail;
	}

	public void setEMail(String eMail) {
		this.eMail = eMail;
	}

	public String getGenInstr() {
		return genInstr;
	}

	public void setGenInstr(String genInstr) {
		this.genInstr = genInstr;
	}

	public String getLabSetup() {
		return labSetup;
	}

	public void setLabSetup(String labSetup) {
		this.labSetup = labSetup;
	}

	public String getDangerSubsts() {
		return dangerSubsts;
	}

	public void setDangerSubsts(String dangerSubsts) {
		this.dangerSubsts = dangerSubsts;
	}

	public String getLabComment() {
		return labComment;
	}

	public void setLabComment(String labComment) {
		this.labComment = labComment;
	}

	public String getDangerSubstComment() {
		return dangerSubstComment;
	}

	public void setDangerSubstComment(String dangerSubstComment) {
		this.dangerSubstComment = dangerSubstComment;
	}
}
