package com.propra.HealthAndSaftyBriefing;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CalcDateDiff {
	
	// method to calculate the difference between given date and actual date as days count
	public static int date (Date date) {
		Date date1 = date;
		Date date2 = new Date();
		
		// calculate difference in milliseconds and convert it to days
		long diff = date2.getTime() - date1.getTime();
		int x = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		
		return x;
	}
	
}
