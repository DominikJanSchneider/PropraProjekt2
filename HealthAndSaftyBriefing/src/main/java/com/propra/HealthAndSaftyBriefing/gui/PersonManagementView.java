package com.propra.HealthAndSaftyBriefing.gui;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import com.propra.HealthAndSaftyBriefing.backend.PersonManager;
import com.propra.HealthAndSaftyBriefing.backend.data.Person;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.FocusNotifier;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.ShortcutRegistration;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.BlurNotifier.BlurEvent;
import com.vaadin.flow.component.FocusNotifier.FocusEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.data.selection.SelectionListener;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("PersonManagementView")
@PageTitle("Personenverwaltung | Sicherheitsunterweisung")
@SuppressWarnings("serial")
public class PersonManagementView extends VerticalLayout {

	private Grid<Person> personGrid;
	private PersonManager personM;
	private Button btnAddPerson;
	private Button btnDeletePerson;
	private Button btnSearch;
	private Button btnBack;
	private Button btnDeviceAssignment;
	private Button btnDangerSubstAssignment;
	private PersonAddForm addForm;
	private PersonEditForm editForm;
	private TextField tfSearch;
	protected ShortcutRegistration shortReg;

	public PersonManagementView() {
		personM = new PersonManager();
		addForm = new PersonAddForm(this, personM);
		editForm = new PersonEditForm(this, personM);
		addForm.setSizeFull();
		addForm.setVisible(false);
		editForm.setSizeFull();
		editForm.setVisible(false);

		// create Buttons and their clickListener

		//btnBack
		btnBack = new Button("Zurück");
		btnBack.setIcon(VaadinIcon.ARROW_BACKWARD.create());
		btnBack.addClickListener(e -> { UI.getCurrent().navigate("AdminView");});
		add(btnBack);
		
		add(configureSearchComponents());
		
		// addPerson
		btnAddPerson = new Button("Neue Person anlegen");
		btnAddPerson.setIcon(VaadinIcon.PLUS_CIRCLE.create());
		btnAddPerson.addClickListener(e -> {
			editForm.setVisible(false);
			addForm.setVisible(true);
			addForm.setVisible(true);
			addForm.setLName("");
			addForm.setFName("");
			addForm.setDate("");
			addForm.setIfwt("");
			addForm.setMNaF("");
			addForm.setIntern("");
			addForm.setExtern("");
			addForm.setEmployment("");
			addForm.setEmail("");
			addForm.setBegin("");
			addForm.setEnd("");
			addForm.setGeneralInstruction("");
			addForm.setLabComment("");
			addForm.setDangerSubstComment("");
		});

		// button delete User
		btnDeletePerson = new Button("Person löschen", e -> {
			Person selectedPerson = personGrid.asSingleSelect().getValue();
			if(selectedPerson != null) {
				personM.deletePerson(selectedPerson.getId());
				updatePersonGrid();
				Notification.show("Person wurde aus der Datenbank entfernt!");
			}
			else {
				Notification.show("Wähle eine Person aus, um sie zu löschen!");
			}
		});
		btnDeletePerson.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
		btnDeletePerson.setIcon(VaadinIcon.MINUS_CIRCLE.create());

		add(new HorizontalLayout(btnAddPerson, btnDeletePerson));

		// Building the UserGrid
		configurePersonGrid();
		add(personGrid, editForm, addForm);
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
		personGrid.addSelectionListener(new SelectionListener<Grid<Person>, Person>(){
			@Override
			public void selectionChange(SelectionEvent<Grid<Person>, Person> event) {
				if(event.isFromClient())
					showEditPersonForm();
			}
		});
	}
	
	private Component configureSearchComponents() {
		tfSearch = new TextField();
		btnSearch = new Button("Suchen");
		btnSearch.setIcon(VaadinIcon.SEARCH.create());
		btnSearch.addClickListener(e -> searchPressed());
		tfSearch.setWidth("200px");
		tfSearch.setPlaceholder("Suche nach Name");
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
		VerticalLayout searchComponent = new VerticalLayout(tfSearch, btnSearch);
		return searchComponent;
	}
	
	public void updatePersonGrid() {
		List<Person> persons = personM.getPersonsData();
		personGrid.setItems(persons);
	}
	
	private void updatePersonGridByName(String name) {
		List<Person> persons = personM.getPersonsByName(name);
		personGrid.setItems(persons);
	}
	
	private void showEditPersonForm() {
	    SingleSelect<Grid<Person>, Person> selectedPerson = personGrid.asSingleSelect();	
		if (selectedPerson.isEmpty()) {
			return;
		}else {
		addForm.setVisible(false);
		editForm.setVisible(true);
		editForm.setId(selectedPerson.getValue().getId());
		editForm.setLName(selectedPerson.getValue().getLName());
		editForm.setFName(selectedPerson.getValue().getFName());
		editForm.setDate(selectedPerson.getValue().getDate());
		editForm.setIfwt(selectedPerson.getValue().getIfwt());
		editForm.setMnaf(selectedPerson.getValue().getMNaF());
		editForm.setIntern(selectedPerson.getValue().getIntern());
		editForm.setExtern(selectedPerson.getValue().getExtern());
		editForm.setEmployment(selectedPerson.getValue().getEmployment());
		editForm.setEmail(selectedPerson.getValue().getEMail());
		editForm.setBegin(selectedPerson.getValue().getBegin());
		editForm.setEnd(selectedPerson.getValue().getEnd());
		editForm.setGeneralInstruction(selectedPerson.getValue().getGenInstr());
		editForm.setLabComment(selectedPerson.getValue().getLabComment());
		editForm.setDangerSubstComment(selectedPerson.getValue().getDangerSubstComment());
		
		}
	}
	
 class PersonEditForm extends FormLayout {
		private TextField tfLName;
		private TextField tfFName;
		private TextField tfDate;
		private TextField tfIfwt;
		private TextField tfMNaF;
		private TextField tfIntern;
		private TextField tfExtern;
		private TextField tfEmployment;
		private TextField tfEmail;
		private TextField tfBegin;
		private TextField tfEnd;
		private TextArea taGenInstr;
		private TextArea taLabComment;
		private TextArea taDangerSubstComment;
		private int id;

		PersonEditForm(PersonManagementView personManagementView, PersonManager personM) {
			// addClassName("contact-form");
			this.setVisible(true);
			this.id = 0;
			tfLName = new TextField("Name");
			tfFName = new TextField("Vorname");
			tfDate = new TextField("Datum");
			tfIfwt = new TextField("Ifwt");
			tfMNaF = new TextField("MNaF");
			tfIntern = new TextField("Intern");
			tfExtern = new TextField("Extern");
			tfEmployment = new TextField("Beschäftigungsverhältnis");
			tfEmail = new TextField("E-Mail");
			tfBegin = new TextField("Beginn");
			tfEnd = new TextField("Ende");
			taGenInstr = new TextArea("Allgemeine Unterweisung");
			taLabComment = new TextArea("Laboreinrichtungen (Kommentar)");
			taDangerSubstComment = new TextArea("Gefahrstoffe (Kommentar)");
			
			String height = "150px";
			String width = "450px";
			taGenInstr.setHeight(height);
			taGenInstr.setWidth(width);
			taLabComment.setHeight(height);
			taLabComment.setWidth(width);
			taDangerSubstComment.setHeight(height);
			taDangerSubstComment.setWidth(width);
			tfEmail.setWidth("350px");
			tfEmployment.setWidth("300px");

			Button save = new Button("Speichern", e -> {
				if (!tfLName.isEmpty() && !tfFName.isEmpty() && !tfEmail.isEmpty()) {
					try {
						personM.editPerson(this.id, tfLName.getValue(), tfFName.getValue(),
								tfDate.getValue(), tfIfwt.getValue(), tfMNaF.getValue(),
								tfIntern.getValue(), tfExtern.getValue(), tfEmployment.getValue(),
								tfEmail.getValue(), tfBegin.getValue(), tfEnd.getValue(),
								taGenInstr.getValue(), taLabComment.getValue(), taDangerSubstComment.getValue());

						Notification.show("Personendaten wurden erfolgreich bearbeitet!");
					} catch (NoSuchAlgorithmException ex) {
						ex.printStackTrace();
					}
					personManagementView.updatePersonGrid();
				} else {
					Notification.show("Bitte geben Sie sowohl einen Namen als auch einen Vornamen ein!");
				}
			});
			
			//button device assignment
			btnDeviceAssignment = new Button("Gerätezuordung bearbeiten", e -> {
				UI.getCurrent().navigate("DeviceAssignmentView/"+id);
			});
			btnDeviceAssignment.setIcon(VaadinIcon.AUTOMATION.create());
			
			//button dangerous substances assignment
			btnDangerSubstAssignment = new Button("Gefahrstoffzuordnung bearbeiten", e -> {
				UI.getCurrent().navigate("DangerSubstAssignmentView/"+id);
			});
			btnDangerSubstAssignment.setIcon(VaadinIcon.BOMB.create());
			
			save.setIcon(VaadinIcon.ADD_DOCK.create());
			Button close = new Button("Schließen", e -> this.setVisible(false));
			add( new VerticalLayout(
						new HorizontalLayout(tfLName, tfFName, tfEmail, tfDate),
						new HorizontalLayout(tfIfwt, tfMNaF, tfIntern, tfExtern),
						new HorizontalLayout(tfEmployment, tfBegin, tfEnd),
						new HorizontalLayout(taGenInstr, taLabComment, taDangerSubstComment),
						new HorizontalLayout(btnDangerSubstAssignment, btnDeviceAssignment),
						new HorizontalLayout(save, close)
					));
			close.setIcon(VaadinIcon.CLOSE_CIRCLE.create());
		}

		public void setEnd(String end) {
			tfEnd.setValue(end);
		}
		
		public void setMnaf(String mnaf) {
			tfMNaF.setValue(mnaf);
		}

		public void setBegin(String begin) {
			tfBegin.setValue(begin);
		}

		public void setDangerSubstComment(String dangerSubstComment) {
			taDangerSubstComment.setValue(dangerSubstComment);
		}

		public void setEmail(String eMail) {
			tfEmail.setValue(eMail);
		}

		public void setLabComment(String labComment) {
			taLabComment.setValue(labComment);
		}

		public void setGeneralInstruction(String genInstr) {
			taGenInstr.setValue(genInstr);
		}

		public void setEmployment(String employment) {
			tfEmployment.setValue(employment);
		}

		public void setExtern(String extern) {
			tfExtern.setValue(extern);
		}

		public void setIntern(String intern) {
			tfIntern.setValue(intern);
		}

		public void setIfwt(String ifwt) {
			tfIfwt.setValue(ifwt);
		}

		public void setDate(String date) {
			tfDate.setValue(date);
		}

		public void setFName(String fName) {
			tfFName.setValue(fName);
		}

		public void setId(int i) {
			this.id = i;
		}

		public void setLName(String lName) {
			tfLName.setValue(lName);
		}

	}
 
 class PersonAddForm extends FormLayout {
		private TextField tfLName;
		private TextField tfFName;
		private TextField tfDate;
		private TextField tfIfwt;
		private TextField tfMNaF;
		private TextField tfIntern;
		private TextField tfExtern;
		private TextField tfEmployment;
		private TextField tfEmail;
		private TextField tfBegin;
		private TextField tfEnd;
		private TextArea taGenInstr;
		private TextArea taLabComment;
		private TextArea taDangerSubstComment;

		PersonAddForm(PersonManagementView personManagementView, PersonManager personM) {
			// addClassName("contact-form");
			this.setVisible(true);
			tfLName = new TextField("Name");
			tfFName = new TextField("Vorname");
			tfDate = new TextField("Datum");
			tfIfwt = new TextField("Ifwt");
			tfMNaF = new TextField("MNaF");
			tfIntern = new TextField("Intern");
			tfExtern = new TextField("Extern");
			tfEmployment = new TextField("Beschäftigungsverhältnis");
			tfEmail = new TextField("E-Mail");
			tfBegin = new TextField("Beginn");
			tfEnd = new TextField("Ende");
			taGenInstr = new TextArea("Allgemeine Unterweisung");
			taLabComment = new TextArea("Laboreinrichtungen (Kommentar)");
			taDangerSubstComment = new TextArea("Gefahrstoffe (Kommentar)");
			
			String height = "150px";
			String width = "450px";
			taGenInstr.setHeight(height);
			taGenInstr.setWidth(width);
			taLabComment.setHeight(height);
			taLabComment.setWidth(width);
			taDangerSubstComment.setHeight(height);
			taDangerSubstComment.setWidth(width);
			tfEmail.setWidth("350px");
			tfEmployment.setWidth("300px");

			Button save = new Button("Speichern", e -> {
				try {
					personM.addPerson(tfLName.getValue(), tfFName.getValue(), tfDate.getValue(),
							tfIfwt.getValue(), tfMNaF.getValue(), tfIntern.getValue(),
							tfExtern.getValue(), tfEmployment.getValue(), tfEmail.getValue(),
							tfBegin.getValue(), tfEnd.getValue(), taGenInstr.getValue(),
							taLabComment.getValue(), taDangerSubstComment.getValue());
					Notification.show("Person wurde hinzugefügt!");
				}
				catch (NoSuchAlgorithmException ex) {
					ex.printStackTrace();
				}
				personManagementView.updatePersonGrid();

			});
			save.setIcon(VaadinIcon.ADD_DOCK.create());
			Button close = new Button("Schließen", e -> this.setVisible(false));
			add( new VerticalLayout(
					new HorizontalLayout(tfLName, tfFName, tfEmail, tfDate),
					new HorizontalLayout(tfIfwt, tfMNaF, tfIntern, tfExtern),
					new HorizontalLayout(tfEmployment, tfBegin, tfEnd),
					new HorizontalLayout(taGenInstr, taLabComment, taDangerSubstComment),
					new HorizontalLayout(save, close)
				));
			close.setIcon(VaadinIcon.CLOSE_CIRCLE.create());
		}

		public void setEnd(String end) {
			tfEnd.setValue(end);
		}

		public void setBegin(String begin) {
			tfBegin.setValue(begin);
		}

		public void setDangerSubstComment(String dangerSubstComment) {
			taDangerSubstComment.setValue(dangerSubstComment);
		}

		public void setLabComment(String labComment) {
			taLabComment.setValue(labComment);
		}

		public void setGeneralInstruction(String genInstr) {
			taGenInstr.setValue(genInstr);
		}

		public void setEmail(String email) {
			tfEmail.setValue(email);
		}

		public void setEmployment(String employment) {
			tfEmployment.setValue(employment);
		}

		public void setExtern(String extern) {
			tfExtern.setValue(extern);
		}

		public void setIntern(String intern) {
			tfIntern.setValue(intern);
		}

		public void setMNaF(String mnaf) {
			tfMNaF.setValue(mnaf);
		}

		public void setIfwt(String ifwt) {
			tfIfwt.setValue(ifwt);
		}

		public void setFName(String fName) {
			tfFName.setValue(fName);
		}

		public void setDate(String date) {
			tfDate.setValue(date);
		}

		public void setLName(String lName) {
			tfLName.setValue(lName);
		}
	}
 
	private void searchPressed() {
		String searchTxt = tfSearch.getValue();
		updatePersonGridByName(searchTxt);
	}
}