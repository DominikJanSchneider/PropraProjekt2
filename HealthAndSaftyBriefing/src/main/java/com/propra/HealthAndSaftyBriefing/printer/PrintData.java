package com.propra.HealthAndSaftyBriefing.printer;

public class PrintData 
{
	private String lName;
	private String fName;
	private String date;
	private String ifwt;
	private String mnaf;
	private String intern;
	private String extern;
	private String genInstr;
	private String labSetup;
	private String dangerSubst;
	
	public PrintData(String lName, String fName, String date, String ifwt, String mnaf, String intern, String extern, String genInstr, String labSetup, String DangerSubst)
	{
		this.setLName(lName);
		this.setFName(fName);
		this.setDate(date);
		this.setIfwt(ifwt);
		this.setMNaF(mnaf);
		this.setIntern(intern);
		this.setExtern(extern);
		this.setGeneralInstructions(genInstr);
		this.setLabSetup(labSetup);
		this.setDangerousSubstances(DangerSubst);
	}
	
	//Setter
	public void setLName(String lName)
	{
		this.lName = lName;
	}
	
	public void setFName(String fName)
	{
		this.fName = fName;
	}
	
	public void setDate(String date)
	{
		this.date = date;
	}
	
	public void setIfwt(String ifwt)
	{
		this.ifwt = ifwt;
	}
	
	public void setMNaF(String mnaf)
	{
		this.mnaf = mnaf;
	}
	
	public void setIntern(String intern)
	{
		this.intern = intern;
	}
	
	public void setExtern(String extern)
	{
		this.extern = extern;
	}
	
	public void setGeneralInstructions(String genInstr)
	{
		this.genInstr = genInstr;
	}
	
	public void setLabSetup(String labSetup)
	{
		this.labSetup = labSetup;
	}
	
	public void setDangerousSubstances(String dangerSubst)
	{
		this.dangerSubst = dangerSubst;
	}
	
	//Getter
	public String getLName()
	{
		return lName;
	}
	
	public String getFName()
	{
		return fName;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public String getIfwt()
	{
		return ifwt;
	}
	
	public String getMNaF()
	{
		return mnaf;
	}
	
	public String getIntern()
	{
		return intern;
	}
	
	public String getExtern()
	{
		return extern;
	}
	
	public String getGeneralInstructions()
	{
		return genInstr;
	}
	
	public String getLabSetup()
	{
		return labSetup;
	}
	
	public String getDangerousSubstances()
	{
		return dangerSubst;
	}
}
