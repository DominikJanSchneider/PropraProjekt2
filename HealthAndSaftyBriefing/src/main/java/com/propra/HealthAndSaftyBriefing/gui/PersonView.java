package com.propra.HealthAndSaftyBriefing.gui;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.propra.HealthAndSaftyBriefing.Person;
import com.propra.HealthAndSaftyBriefing.PersonManager;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.FocusNotifier;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.ShortcutRegistration;
import com.vaadin.flow.component.BlurNotifier.BlurEvent;
import com.vaadin.flow.component.FocusNotifier.FocusEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.data.selection.SelectionListener;

@SuppressWarnings("serial")
public class PersonView extends VerticalLayout {
	
	private Grid<Person> personGrid;
	private PersonManager personM;
	TextArea taGeneralInstruction;
	TextArea taLabSetup;
	TextArea taDangerSubst;
	private TextField tfSearch;
	private Button btnSearch;
	private Button btnAll;
	private Button btnIfwt;
	private Button btnLMN;
	private Button btnLOT;
	private Button btnLWF;
	private Button btnLMW;
	private Button btnMNaF;
	private Button btnIntern;
	private Button btnExtern;
	private ShortcutRegistration shortReg;
	
	PersonView() {
		personM = new PersonManager();
		
		//Building searchComponents
		Component searchComponents = configureSearchComponents();
		add(searchComponents);
		
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
        taGeneralInstruction.setReadOnly(true);
        taLabSetup.setHeight(height);
        taLabSetup.setWidth(width);
        taLabSetup.setReadOnly(true);
        taDangerSubst.setHeight(height);
        taDangerSubst.setWidth(width);
        taDangerSubst.setReadOnly(true);
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
        			.setResizable(true)
        			.setSortable(true);
        personGrid.addColumn(Person::getFName)
        			.setHeader("Vorname")
        			.setKey("fName")
        			.setResizable(true)
        			.setSortable(true);
        personGrid.addColumn(Person::getLName)
        			.setHeader("Name")
        			.setKey("lName")
        			.setResizable(true)
        			.setSortable(true);
        personGrid.addColumn(Person::getDate)
					.setHeader("Datum")
					.setKey("date")
					.setResizable(true)
					.setSortable(true);
        personGrid.addColumn(Person::getIfwt)
					.setHeader("Ifwt")
					.setKey("ifwt")
					.setResizable(true)
					.setSortable(true);
        personGrid.addColumn(Person::getMNaF)
					.setHeader("MNaF")
					.setKey("mnaf")
					.setResizable(true)
					.setSortable(true);
        personGrid.addColumn(Person::getIntern)
					.setHeader("Intern")
					.setKey("intern")
					.setResizable(true)
					.setSortable(true);
        personGrid.addColumn(Person::getExtern)
        			.setHeader("Extern")
        			.setKey("extern")
        			.setResizable(true)
        			.setSortable(true);
        personGrid.addColumn(Person::getEmployment)
					.setHeader("Beschaeftigungsverh\u00e4ltnis")
					.setKey("employment")
					.setResizable(true)
					.setSortable(true);
        personGrid.addColumn(Person::getBegin)
					.setHeader("Beginn")
					.setKey("begin")
					.setResizable(true)
					.setSortable(true);
        personGrid.addColumn(Person::getEnd)
					.setHeader("Ende")
					.setKey("end")
					.setResizable(true)
					.setSortable(true);
        personGrid.addColumn(Person::getEMail)
					.setHeader("E-Mail Adresse")
					.setKey("eMail")
					.setSortable(true);
        personGrid.addSelectionListener(new SelectionListener<Grid<Person>,Person>() {

			@Override
			public void selectionChange(SelectionEvent<Grid<Person>,Person> event) {
				updateTextAreas();
			}
        	
        });
	}
	
	protected void updateTextAreas() {
		Set<Person> personSet = personGrid.getSelectedItems();
		Iterator<Person> it = personSet.iterator();
		if(it.hasNext()) {
			Person person = it.next();
			String genInstr = person.getGenInstr();
			String labSetup = person.getLabSetup();
			String dangerSubsts = person.getDangerSubsts();
			taGeneralInstruction.setValue(genInstr);
			taLabSetup.setValue(labSetup);
			taDangerSubst.setValue(dangerSubsts);
		}
	}

	private Component configureSearchComponents() {
		tfSearch = new TextField();
		btnSearch = new Button("Suchen");
		btnSearch.addClickListener(e -> searchPressed());
		tfSearch.setWidth("200px");
		tfSearch.setPlaceholder("Suche");
		tfSearch.setAutoselect(true);
		tfSearch.addFocusListener(new ComponentEventListener<FocusNotifier.FocusEvent<TextField>>() {

			@Override
			public void onComponentEvent(FocusEvent<TextField> event) {
				shortReg = btnSearch.addClickShortcut(Key.ENTER);
			}
			
		});
		tfSearch.addBlurListener(new ComponentEventListener<BlurEvent<TextField>>() {

			@Override
			public void onComponentEvent(BlurEvent<TextField> event) {
				shortReg.remove();
			}
			
		});
		
		btnAll = new Button("Alle", e -> updatePersonGrid());
		btnIfwt = new Button("Ifwt", e -> {updatePersonGridIfwt();});
		btnLMN = new Button("LMN", e -> {updatePersonGridLMN();});
		btnLOT = new Button("LOT", e -> {updatePersonGridLOT();});
		btnLWF = new Button("LWF", e -> {updatePersonGridLWF();});
		btnLMW = new Button("LMW", e -> {updatePersonGridLMW();});
		btnMNaF = new Button("MNaF", e -> {updatePersonGridMNaF();});
		btnIntern = new Button("Intern", e ->{updatePersonGridIntern();});
		btnExtern = new Button("Extern", e ->{updatePersonGridExtern();});
		
		VerticalLayout searchComponent1 = new VerticalLayout(tfSearch, btnSearch);
		Label lbAll = new Label("Alle anzeigen");
		lbAll.setWidth("100px");
		VerticalLayout searchComponent2 = new VerticalLayout(lbAll , btnAll);
		VerticalLayout searchComponent3 = new VerticalLayout(new Label("Institut für Werkstoffstechnik (Ifwt)"), new HorizontalLayout(btnIfwt, btnLMN, btnLMW, btnLOT, btnLWF));
		VerticalLayout searchComponent4 = new VerticalLayout(new Label("Ger\u00e4tezentrum"), new HorizontalLayout(btnMNaF));
		VerticalLayout searchComponent5 = new VerticalLayout(new Label("Intern"), new HorizontalLayout(btnIntern));
		VerticalLayout searchComponent6 = new VerticalLayout(new Label("Extern"), new HorizontalLayout(btnExtern));
		return new HorizontalLayout(searchComponent1, searchComponent2, searchComponent3, searchComponent4, searchComponent5, searchComponent6);
	}
	
	private void updatePersonGridByName(String name) {
		List<Person> persons = personM.getPersonsByName(name);
		personGrid.setItems(persons);
	}
	
	private void updatePersonGridIfwt() {
		List<Person> persons = personM.getIfwtPersons();
        personGrid.setItems(persons);
	}
	
	private void updatePersonGridLMN() {
		List<Person> LMNpersons = personM.getLMNPersons();
		personGrid.setItems(LMNpersons);
	}
	
	private void updatePersonGridLOT() {
		List<Person> LOTpersons = personM.getLOTPersons();
		personGrid.setItems(LOTpersons);
	}

	private void updatePersonGridLWF() {
		List<Person> LWFpersons = personM.getLWFPersons();
		personGrid.setItems(LWFpersons);
	}

	private void updatePersonGridLMW() {
		List<Person> LMWpersons = personM.getLMWPersons();
		personGrid.setItems(LMWpersons);
	}

	
	private void updatePersonGridMNaF() {
		List<Person> MNaFpersons = personM.getMNaFPersons();
		personGrid.setItems(MNaFpersons);
	}

	private void updatePersonGridIntern() {
		List<Person> Internpersons = personM.getInternPersons();
		personGrid.setItems(Internpersons);
	}
	
	private void updatePersonGridExtern() {
		List<Person> Externpersons = personM.getExternPersons();
		personGrid.setItems(Externpersons);
	}

	private void updatePersonGrid() {
		List<Person> persons = personM.getPersonsData();
        personGrid.setItems(persons);
	}
	
	public Set<Person> getSelectedPerson() {
		Set<Person> personSet = personGrid.getSelectedItems();
		return personSet;
	}
	
	private void searchPressed() {
		String searchTxt = tfSearch.getValue();
		updatePersonGridByName(searchTxt);
	}

}
