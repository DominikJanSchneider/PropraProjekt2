package com.propra.HealthAndSaftyBriefing.gui;

import java.util.List;

import com.propra.HealthAndSaftyBriefing.Person;
import com.propra.HealthAndSaftyBriefing.PersonManager;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

@SuppressWarnings("serial")
public class PersonView extends VerticalLayout {
	
	private Grid<Person> personGrid;
	private PersonManager personM;
	private Button btnIfwt;
	private Button btnLMN;
	private Button btnLMW;
	private Button btnLOT;
	private Button btnLWF;
	private Button btnMnAF;
	private Button btnIntern;
	private Button btnExtern;
	private static TextField tfName;
	private Button btnFilterByName;
	
	
	PersonView() {
		personM = new PersonManager();
		
		
		//TextField and Button for filter by name
		tfName = new TextField("Nach Namen suchen");
		btnFilterByName = new Button("Suchen", e -> {updatePersonsGridByName();});
	
		
		//button declarations and clickListener
		btnIfwt = new Button("Ifwt", e -> {updateIfwtPersonGrid();});
		btnLMN = new Button("LMN", e -> {updateLMNPersonGrid();});
		btnLOT = new Button("LOT", e -> {updateLOTPersonGrid();});
		btnLWF = new Button("LWF", e -> {updateLWFPersonGrid();});
		btnLMW = new Button("LMW", e -> {updateLMWPersonGrid();});
		btnMnAF = new Button("MNaF", e -> {updateMNaFPersonGrid();});
		btnIntern = new Button("Intern", e ->{updateInternPersonGrid();});
		btnExtern = new Button("Extern", e ->{updateExternPersonGrid();});
		
		add(new HorizontalLayout(new VerticalLayout(tfName, btnFilterByName), btnIfwt, btnLMN, btnLMW, btnLOT, btnLWF, btnMnAF, btnIntern, btnExtern));
		
		
		
		
		//Building the personGrid
		configurePersonGrid();
        add(personGrid);
        updatePersonGrid();
        
        btnIfwt = new Button("Ifwt");
        
        
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
		List<Person> persons = personM.getPersonsData();
        personGrid.setItems(persons);
	}
	
	private void updatePersonsGridByName() {
		List<Person> persons = personM.getPersonsByName();
		personGrid.setItems(persons);
	}
	
	private void updateIfwtPersonGrid() {
		List<Person> persons = personM.getIfwtPersons();
        personGrid.setItems(persons);
	}
	
	private void updateLMNPersonGrid() {
		List<Person> LMNpersons = personM.getLMNPersons();
		personGrid.setItems(LMNpersons);
	}
	
	private void updateLMWPersonGrid() {
		List<Person> LMWpersons = personM.getLMWPersons();
		personGrid.setItems(LMWpersons);
	}
	
	private void updateLOTPersonGrid() {
		List<Person> LOTpersons = personM.getLOTPersons();
		personGrid.setItems(LOTpersons);
	}
	
	private void updateLWFPersonGrid() {
		List<Person> LWFpersons = personM.getLWFPersons();
		personGrid.setItems(LWFpersons);
	}
	
	private void updateMNaFPersonGrid() {
		List<Person> MNaFpersons = personM.getMNaFPersons();
		personGrid.setItems(MNaFpersons);
	}
	
	private void updateInternPersonGrid() {
		List<Person> Internpersons = personM.getInternPersons();
		personGrid.setItems(Internpersons);
	}
	
	private void updateExternPersonGrid() {
		List<Person> Externpersons = personM.getExternPersons();
		personGrid.setItems(Externpersons);
	}
	
	
	
	public Grid<Person> getPersonGrid() {
		return personGrid;
	}
	
	public static TextField getTfName() {
		return tfName;
	}

	public void setTfName(TextField tfName) {
		this.tfName = tfName;
	}
}
