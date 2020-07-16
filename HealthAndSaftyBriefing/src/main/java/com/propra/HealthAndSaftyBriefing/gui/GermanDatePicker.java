package com.propra.HealthAndSaftyBriefing.gui;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import com.vaadin.flow.component.datepicker.DatePicker;

@SuppressWarnings("serial")
public class GermanDatePicker extends DatePicker {
	private final String europeanDatePattern;
	public GermanDatePicker(String label, LocalDate initialDate) {
		super(label, initialDate);
		europeanDatePattern = "dd.MM.yyyy";
		this.setLocale(Locale.GERMAN);
		this.setClearButtonVisible(true);
		
		this.setI18n(new DatePickerI18n().setWeek("Woche")
		        .setCalendar("Kalender").setClear("Löschen")
		        .setToday("Heute").setCancel("Abbrechen").setFirstDayOfWeek(1)
		        .setMonthNames(Arrays.asList("Januar", "Februar", "März",
		                "April", "Mai", "Juni", "Juli", "August",
		                "September", "Oktober", "November", "Dezember"))
		        .setWeekdays(Arrays.asList("Montag", "Dienstag", "Mittwoch",
		                "Donnerstag", "Freitag", "Samstag", "Sonntag"))
		        .setWeekdaysShort(Arrays.asList("Mo", "Di", "Mi", "Do", "Fr",
		                "Sa", "So")));
	}
	
//	public String getDateInEuropeanFormat() {
//		LocalDate date = this.getValue();
//		if(date != null) {
//			return date.format(DateTimeFormatter.ofPattern(europeanDatePattern));
//		}
//		else {
//			return "";
//		}
//	}
//	
//	public void setDateInEuropeanFormat(String date) {
//		if(date.isEmpty()) {
//			this.setValue(null);
//		}
//		else {
//			this.setValue(LocalDate.parse(date, DateTimeFormatter.ofPattern(europeanDatePattern)));
//		}
//	}
	
	public Date getDate() {
		LocalDate date = this.getValue();
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		if(date != null) {
			try {
				return df.parse(date.format(DateTimeFormatter.ofPattern(europeanDatePattern)));
			}
			catch (ParseException e){
				e.printStackTrace();
				return null;
			}
		}
		else {
			return null;
		}
	}
	
	public void setDate(Date date) {
		if(date == null) {
			this.setValue(null);
		}
		else {
			DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
			String dateString = df.format(date);
			this.setValue(LocalDate.parse(dateString, DateTimeFormatter.ofPattern(europeanDatePattern)));
		}
	}
}
