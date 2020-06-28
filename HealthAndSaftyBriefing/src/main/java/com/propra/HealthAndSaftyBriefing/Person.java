package com.propra.HealthAndSaftyBriefing;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Person implements Serializable, Cloneable {
	private int id;
	private String fName;
	private String lName;
	private String date;
	private String ifwt;
	private String mnaf;
	private String intern;
	private String extern;
	private String employment;
	private String begin;
	private String end;
	private String eMail;
	
	Person(int id, String lName, String fName, String date, String ifwt, String mnaf, String intern, String extern, String employment, String begin, String end, String eMail) {
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getEMail() {
		return eMail;
	}

	public void setEMail(String eMail) {
		this.eMail = eMail;
	}
	
	
}
