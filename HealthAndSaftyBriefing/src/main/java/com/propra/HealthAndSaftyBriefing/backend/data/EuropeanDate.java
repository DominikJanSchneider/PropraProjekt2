package com.propra.HealthAndSaftyBriefing.backend.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class EuropeanDate extends Date {
	EuropeanDate(Date date) {
		super();
		this.setTime(date.getTime());
	}
	
	@Override
	public String toString() {
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		return df.format(this);
	}
}
