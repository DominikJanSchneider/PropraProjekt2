package com.propra.HealthAndSaftyBriefing.gui;

import java.util.List;

import com.propra.HealthAndSaftyBriefing.Person;
import com.propra.HealthAndSaftyBriefing.PersonManager;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@SuppressWarnings("serial")
public class PersonView extends VerticalLayout {
	
	private Grid<Person> personGrid;
	private PersonManager personM;
	
	PersonView() {
		personM = new PersonManager();
		
		//Bulding the personGrid
		configurePersonGrid();
        add(personGrid);
        updatePersonGrid();
	}
	
	private void configurePersonGrid() {
		personGrid = new Grid<>();
		
		personGrid.addColumn(Person::getId)
        			.setHeader("ID")
        			.setKey("id")
        			.setSortable(true);
        personGrid.addColumn(Person::getFName)
        			.setHeader("Vorname")
        			.setKey("fName")
        			.setSortable(true);
        personGrid.addColumn(Person::getLName)
        			.setHeader("Name")
        			.setKey("lName")
        			.setSortable(true);
        personGrid.addColumn(Person::getDate)
					.setHeader("Datum")
					.setKey("date")
					.setSortable(true);
        personGrid.addColumn(Person::getIfwt)
					.setHeader("Ifwt")
					.setKey("ifwt")
					.setSortable(true);
        personGrid.addColumn(Person::getMNaF)
					.setHeader("MNaF")
					.setKey("mnaf")
					.setSortable(true);
        personGrid.addColumn(Person::getIntern)
					.setHeader("Intern")
					.setKey("intern")
					.setSortable(true);
        personGrid.addColumn(Person::getEmployment)
					.setHeader("Beschaeftigungsverh\u00e4ltnis")
					.setKey("employment")
					.setSortable(true);
        personGrid.addColumn(Person::getBegin)
					.setHeader("Beginn")
					.setKey("begin")
					.setSortable(true);
        personGrid.addColumn(Person::getEnd)
					.setHeader("Ende")
					.setKey("end")
					.setSortable(true);
        personGrid.addColumn(Person::getEMail)
					.setHeader("E-Mail Adresse")
					.setKey("eMail")
					.setSortable(true);
	}
	
	private void updatePersonGrid() {
		List<Person> persons = personM.getPersons();
        personGrid.setItems(persons);
	}
}
