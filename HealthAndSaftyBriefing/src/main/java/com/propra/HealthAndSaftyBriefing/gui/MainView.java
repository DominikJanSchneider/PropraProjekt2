package com.propra.HealthAndSaftyBriefing.gui;

import java.util.List;

import com.propra.HealthAndSaftyBriefing.Person;
import com.propra.HealthAndSaftyBriefing.PersonManager;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

/**
 * The main view contains a button and a click listener.
 */
@SuppressWarnings("serial")
@Route
@PWA(name = "My Application", shortName = "My Application")
public class MainView extends VerticalLayout {

	private Grid<Person> grid;
	private List<Person> list;
	private PersonManager persons;
    public MainView() {
		persons = new PersonManager();
		grid = new Grid<>();
        configureGrid();
        
        add(grid);
        updateList();
    }
	private void configureGrid() {
		grid.addColumn(Person::getId)
        			.setHeader("ID")
        			.setKey("id")
        			.setSortable(true);
        grid.addColumn(Person::getFName)
        			.setHeader("Vorname")
        			.setKey("fName")
        			.setSortable(true);
        grid.addColumn(Person::getLName)
        			.setHeader("Name")
        			.setKey("lName")
        			.setSortable(true);
        grid.addColumn(Person::getDate)
					.setHeader("Datum")
					.setKey("date")
					.setSortable(true);
        grid.addColumn(Person::getIfwt)
					.setHeader("Ifwt")
					.setKey("ifwt")
					.setSortable(true);
        grid.addColumn(Person::getMNaF)
					.setHeader("MNaF")
					.setKey("mnaf")
					.setSortable(true);
        grid.addColumn(Person::getIntern)
					.setHeader("Intern")
					.setKey("intern")
					.setSortable(true);
        grid.addColumn(Person::getEmployment)
					.setHeader("Beschaeftigungsverh\u00e4ltnis")
					.setKey("employment")
					.setSortable(true);
        grid.addColumn(Person::getBegin)
					.setHeader("Beginn")
					.setKey("begin")
					.setSortable(true);
        grid.addColumn(Person::getEnd)
					.setHeader("Ende")
					.setKey("end")
					.setSortable(true);
        grid.addColumn(Person::getEMail)
					.setHeader("E-Mail Adresse")
					.setKey("eMail")
					.setSortable(true);
	}
	private void updateList() {
		list = persons.getPersons();
        grid.setItems(list);
	}
}
