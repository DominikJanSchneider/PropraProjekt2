package com.propra.HealthAndSaftyBriefing.gui;

import java.util.List;

import com.propra.HealthAndSaftyBriefing.Person;
import com.propra.HealthAndSaftyBriefing.PersonManager;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;

@SuppressWarnings("serial")
public class PersonView extends VerticalLayout {
	
	private Grid<Person> personGrid;
	private PersonManager personM;
	TextArea taGeneralInstruction;
	TextArea taLabSetup;
	TextArea taDangerSubst;
	
	PersonView() {
		personM = new PersonManager();
		
		//Building the personGrid
		configurePersonGrid();
        add(personGrid);
        updatePersonGrid();
        configureTextAreas();
        HorizontalLayout textAreas = configureTextAreas();
        add(textAreas);
	}
	
	private HorizontalLayout configureTextAreas() {
		taGeneralInstruction = new TextArea();
        taLabSetup = new TextArea();
        taDangerSubst = new TextArea();
        
        String height = "150px";
        String width = "400px";
        
        taGeneralInstruction.setHeight(height);
        taGeneralInstruction.setWidth(width);
        taLabSetup.setHeight(height);
        taLabSetup.setWidth(width);
        taDangerSubst.setHeight(height);
        taDangerSubst.setWidth(width);
        VerticalLayout gInstrVL = new VerticalLayout(new Label("Allgemeine Unterweisung (Datum s.o.)"), taGeneralInstruction);
        VerticalLayout labSetupVL = new VerticalLayout(new Label("Laboreinrichtungen"), taLabSetup);
        VerticalLayout dangerSubstVL = new VerticalLayout(new Label("Gefahrstoffe"), taDangerSubst);
        
		return new HorizontalLayout(gInstrVL, labSetupVL, dangerSubstVL);
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
        personGrid.addColumn(Person::getExtern)
        			.setHeader("Extern")
        			.setKey("extern")
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
		List<Person> persons = personM.getPersonsData();
        personGrid.setItems(persons);
	}
	
	public Grid<Person> getPersonGrid() {
		return personGrid;
	}
}
