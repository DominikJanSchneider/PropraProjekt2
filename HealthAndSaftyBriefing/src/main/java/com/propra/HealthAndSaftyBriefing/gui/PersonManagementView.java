package com.propra.HealthAndSaftyBriefing.gui;

import java.util.List;

import com.propra.HealthAndSaftyBriefing.backend.PersonManager;
import com.propra.HealthAndSaftyBriefing.backend.data.Person;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("PersonManagementView")
@PageTitle("Personenverwaltung")
@SuppressWarnings("serial")
public class PersonManagementView extends VerticalLayout {

	private Grid<Person> personGrid;
	private PersonManager personM;
	private Button btnAddPerson;
	private Button btnDeletePerson;
	private Button btnEditPerson;
	private Div content;
	private Div editContent;
	private PersonAddForm addForm;
	private PersonEditForm editForm;
	private Button btnBack;
	//TODO
	private SingleSelect<Grid<Person>, Person> selectedPerson {
		@Override
		public void selectionChange(SelectionEvent<Grid<Person>,Person> event) {
			if (event.isFromClient()) {
				showEditForm();
			}
	};

	public PersonManagementView() {

		addForm = null;
		editForm = null;
		personM = new PersonManager();

		// create Buttons and their clickListener

		//btnBack
		btnBack = new Button("Zurück");
		btnBack.setIcon(VaadinIcon.ARROW_BACKWARD.create());
		btnBack.addClickListener(e -> { UI.getCurrent().navigate("AdminView");});
		add(btnBack);
		
		// addPerson
		btnAddPerson = new Button("Neue Person anlegen");
		btnAddPerson.addClickListener(e -> {
			if (addForm == null) {
				if (editForm != null)
					editForm.setVisible(false);
				content = new Div(personGrid, addForm = new PersonAddForm(this, personM));
				content.setSizeFull();
				add(content);

			} else {
				if (editForm != null)
					editForm.setVisible(false);
				addForm.setVisible(true);
				addForm.setPersonName("");
				addForm.setPersonVorname("");
				addForm.setPersonDatum("");
				addForm.setPersonIfwt("");
				addForm.setPersonMNaF("");
				addForm.setPersonIntern("");
				addForm.setPersonExtern("");
				addForm.setPersonBeschaeftigungsverhaeltnis("");
				addForm.setPersonEmail("");
				addForm.setPersonBeginn("");
				addForm.setPersonEnde("");
				addForm.setPersonAu("");
				addForm.setPersonLabKommentar("");
				addForm.setPersonGefKommentar("");

				add(content);

			}
			selectedPerson = null;
		});

		// button delete User
		btnDeletePerson = new Button("Person löschen", e -> {
			try {
				selectedPerson = personGrid.asSingleSelect();
				personM.deletePerson(selectedPerson.getValue().getId());
				updatePersonGrid();
				selectedPerson = null;
				Notification.show("Person wurde aus der Datenbank entfernt!");
			} catch (Exception ex) {
				Notification.show("Wähle eine Person aus, um sie zu löschen!");
			}

		});

		// button edit User
		btnEditPerson = new Button("Person bearbeiten", e -> {
			try {
				if (addForm != null)
					addForm.setVisible(false);
				
				selectedPerson = personGrid.asSingleSelect();
				int personId = selectedPerson.getValue().getId();
				editContent = new Div(personGrid, editForm = new PersonEditForm(this, personM, personId));
				editForm.setPersonId(selectedPerson.getValue().getId());
				editForm.setTfLName(selectedPerson.getValue().getLName());
				editForm.setTfFName(selectedPerson.getValue().getFName());
				editForm.setTfDate(selectedPerson.getValue().getDate());
				editForm.setTfIfwt(selectedPerson.getValue().getIfwt());
				editForm.setTfMnaf(selectedPerson.getValue().getMNaF());
				editForm.setTfIntern(selectedPerson.getValue().getIntern());
				editForm.setTfExtern(selectedPerson.getValue().getExtern());
				editForm.setTfEmployment(selectedPerson.getValue().getEmployment());
				editForm.setTfEmail(selectedPerson.getValue().getEMail());
				editForm.setTfBegin(selectedPerson.getValue().getBegin());
				editForm.setTfEnd(selectedPerson.getValue().getEnd());
				editForm.setTaGeneralInstruction(selectedPerson.getValue().getGenInstr());
				editForm.setTaLabComment(selectedPerson.getValue().getLabComment());
				editForm.setTaDangerSubstComment(selectedPerson.getValue().getDangerSubstComment());
				editContent.setSizeFull();
				add(editContent);
				updatePersonGrid();
				

			} catch (Exception ex) {
				add(personGrid);
				editForm.setVisible(false);
				Notification.show("Wähle eine Person aus, um Daten bearbeiten zu können!");

			}
		});

		add(new HorizontalLayout(btnAddPerson, btnDeletePerson, btnEditPerson));

		// Building the UserGrid
		configurePersonGrid();
		add(personGrid);
		updatePersonGrid();
	}

	public void configurePersonGrid() {
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
		}
	}

	public void updatePersonGrid() {
		List<Person> persons = personM.getPersonsData();
		personGrid.setItems(persons);
	}
	
	private void showEditForm() {
		selectedPerson = personGrid.asSingleSelect();
		if (selectedPerson.isEmpty()) {
			return;
		}
		addForm.setVisible(false);
		editForm.setVisible(true);
		editForm.setPersonId(selectedPerson.getValue().getId());
		editForm.setTfLName(selectedPerson.getValue().getLName());
		editForm.setTfFName(selectedPerson.getValue().getFName());
		editForm.setTfDate(selectedPerson.getValue().getDate());
		editForm.setTfIfwt(selectedPerson.getValue().getIfwt());
		editForm.setTfMnaf(selectedPerson.getValue().getMNaF());
		editForm.setTfIntern(selectedPerson.getValue().getIntern());
		editForm.setTfExtern(selectedPerson.getValue().getExtern());
		editForm.setTfEmployment(selectedPerson.getValue().getEmployment());
		editForm.setTfEmail(selectedPerson.getValue().getEMail());
		editForm.setTfBegin(selectedPerson.getValue().getBegin());
		editForm.setTfEnd(selectedPerson.getValue().getEnd());
		editForm.setTaGeneralInstruction(selectedPerson.getValue().getGenInstr());
		editForm.setTaLabComment(selectedPerson.getValue().getLabComment());
		editForm.setTaDangerSubstComment(selectedPerson.getValue().getDangerSubstComment());
	}
}