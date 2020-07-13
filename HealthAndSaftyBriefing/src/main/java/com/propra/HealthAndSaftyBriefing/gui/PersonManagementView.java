package com.propra.HealthAndSaftyBriefing.gui;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.propra.HealthAndSaftyBriefing.backend.PersonManager;
import com.propra.HealthAndSaftyBriefing.backend.data.Person;
import com.propra.HealthAndSaftyBriefing.backend.data.User;
import com.propra.HealthAndSaftyBriefing.gui.UserManagementView.UserAddForm;
import com.propra.HealthAndSaftyBriefing.gui.UserManagementView.UserEditForm;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
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
		
		// addPerson
		btnAddPerson = new Button("Neue Person anlegen");
		btnAddPerson.setIcon(VaadinIcon.PLUS_CIRCLE.create());
		btnAddPerson.addClickListener(e -> {
			
			editForm.setVisible(false);
			addForm.setVisible(true);
					
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

				

			
			
		});

		// button delete User
		btnDeletePerson = new Button("Person löschen", e -> {
			try {
				SingleSelect<Grid<Person>, Person> selectedPerson = personGrid.asSingleSelect();
				personM.deletePerson(selectedPerson.getValue().getId());
				updatePersonGrid();
				selectedPerson = null;
				Notification.show("Person wurde aus der Datenbank entfernt!");
			} catch (Exception ex) {
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
				// TODO Auto-generated method stub
				if(event.isFromClient())
					showEditPersonForm();
			}

			
				
			}
		);
	
		
		 
		}
	

	public void updatePersonGrid() {
		List<Person> persons = personM.getPersonsData();
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
	
 class PersonEditForm extends FormLayout {
		private TextField tfLName;
		private TextField tfFName;
		private TextField tfDate;
		private TextField tfIfwt;
		private TextField tfMnaf;
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
			tfMnaf = new TextField("MNaF");
			tfIntern = new TextField("Intern");
			tfExtern = new TextField("Extern");
			tfEmployment = new TextField("Beschäftigungsverhältnis");
			tfEmail = new TextField("E-Mail");
			tfBegin = new TextField("Beginn");
			tfEnd = new TextField("Ende");
			taGenInstr = new TextArea("Allgemeine Unterweisung");
			taLabComment = new TextArea("LabKommentar");
			taDangerSubstComment = new TextArea("GefKommentar");
			

			Button save = new Button("Speichern", e -> {
				if (!tfLName.isEmpty() && !tfFName.isEmpty() && !tfEmail.isEmpty()) {
					try {
						personM.editPerson(this.id, tfLName.getValue(), tfFName.getValue(),
								tfDate.getValue(), tfIfwt.getValue(), tfMnaf.getValue(),
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
			Button close = new Button("Beenden", e -> this.setVisible(false));
			add(tfLName, tfFName, tfDate, tfIfwt, tfMnaf, tfIntern,
					tfExtern, tfEmployment, tfEmail, tfBegin, tfEnd, taGenInstr, /*taPersonLk,*/
					taLabComment, taDangerSubstComment, new HorizontalLayout(save, close));
		}

		public void setTfEnd(String end) {
			// TODO Auto-generated method stub
			tfEnd.setValue(end);
		}
		
		public void setTfMnaf(String mnaf) {
			tfMnaf.setValue(mnaf);
		}

		public void setTfBegin(String begin) {
			// TODO Auto-generated method stub
			tfBegin.setValue(begin);
		}

		public void setTaDangerSubstComment(String dangerSubstComment) {
			// TODO Auto-generated method stub
			taDangerSubstComment.setValue(dangerSubstComment);
		}

		public void setTfEmail(String eMail) {
			// TODO Auto-generated method stub
			tfEmail.setValue(eMail);
		}

		public void setTaLabComment(String labComment) {
			// TODO Auto-generated method stub
			taLabComment.setValue(labComment);
		}

		public void setTaGeneralInstruction(String dangerSubsts) {
			// TODO Auto-generated method stub
			taGenInstr.setValue(dangerSubsts);
		}

		public void setTfEmployment(String employment) {
			// TODO Auto-generated method stub
			tfEmployment.setValue(employment);
		}

		public void setTfExtern(String extern) {
			// TODO Auto-generated method stub
			tfExtern.setValue(extern);
		}

		public void setTfIntern(String intern) {
			// TODO Auto-generated method stub
			tfIntern.setValue(intern);
		}

		public void setTfIfwt(String ifwt) {
			// TODO Auto-generated method stub
			tfIfwt.setValue(ifwt);
		}

		public void setTfDate(String date) {
			// TODO Auto-generated method stub
			tfDate.setValue(date);
		}

		public void setTfFName(String fName) {
			// TODO Auto-generated method stub
			tfFName.setValue(fName);
		}

		public void setId(int i) {
			this.id = i;
		}

		public void setTfLName(String name) {
			tfLName.setValue(name);
		}

	}
 
 class PersonAddForm extends FormLayout {
		private TextField tfPersonName;
		private TextField tfPersonVorname;
		private TextField tfPersonDatum;
		private TextField tfPersonIfwt;
		private TextField tfPersonMNaF;
		private TextField tfPersonIntern;
		private TextField tfPersonExtern;
		private TextField tfPersonBv;
		private TextField tfPersonEmail;
		private TextField tfPersonBeginn;
		private TextField tfPersonEnde;
		private TextArea taPersonAu;
		private TextArea taPersonLk;
		private TextArea taPersonGk;

		PersonAddForm(PersonManagementView personManagementView, PersonManager personM) {
			// addClassName("contact-form");
			this.setVisible(true);
			tfPersonName = new TextField("Name");
			tfPersonVorname = new TextField("Vorname");
			tfPersonDatum = new TextField("Datum");
			tfPersonIfwt = new TextField("Ifwt");
			tfPersonMNaF = new TextField("MNaF");
			tfPersonIntern = new TextField("Intern");
			tfPersonExtern = new TextField("Extern");
			tfPersonBv = new TextField("Beschäftigungsverhältnis");
			tfPersonEmail = new TextField("E-Mail");
			tfPersonBeginn = new TextField("Beginn");
			tfPersonEnde = new TextField("Ende");
			taPersonAu = new TextArea("Allgemeine Unterweisung");
			taPersonLk = new TextArea("LabKommentar");
			taPersonGk = new TextArea("GefKommentar");

			Button save = new Button("Speichern", e -> {

				try {

					personM.addPerson(tfPersonName.getValue(), tfPersonVorname.getValue(), tfPersonDatum.getValue(),
							tfPersonIfwt.getValue(), tfPersonMNaF.getValue(), tfPersonIntern.getValue(),
							tfPersonExtern.getValue(), tfPersonBv.getValue(), tfPersonEmail.getValue(),
							tfPersonBeginn.getValue(), tfPersonEnde.getValue(), taPersonAu.getValue(),
							taPersonLk.getValue(), taPersonGk.getValue());
					Notification.show("Person wurde hinzugefügt!");
				}

				catch (NoSuchAlgorithmException ex) {
					ex.printStackTrace();
				}
				personManagementView.updatePersonGrid();

			});

			Button close = new Button("Beenden", e -> this.setVisible(false));

			add(tfPersonName, tfPersonVorname, tfPersonDatum, tfPersonIfwt, tfPersonMNaF, tfPersonIntern,
					tfPersonExtern, tfPersonBv, tfPersonEmail, tfPersonBeginn, tfPersonEnde, taPersonAu, taPersonLk,
					taPersonGk, new HorizontalLayout(save, close));
		}

		public void setPersonEnde(String ende) {
			// TODO Auto-generated method stub
			tfPersonEnde.setValue(ende);
		}

		public void setPersonBeginn(String beginn) {
			// TODO Auto-generated method stub
			tfPersonBeginn.setValue(beginn);
		}

		public void setPersonGefKommentar(String gk) {
			// TODO Auto-generated method stub
			taPersonGk.setValue(gk);
		}

		public void setPersonLabKommentar(String lk) {
			// TODO Auto-generated method stub
			taPersonLk.setValue(lk);
		}

		public void setPersonAu(String au) {
			// TODO Auto-generated method stub
			taPersonAu.setValue(au);
		}

		public void setPersonEmail(String email) {
			// TODO Auto-generated method stub
			tfPersonEmail.setValue(email);
		}

		public void setPersonBeschaeftigungsverhaeltnis(String bv) {
			// TODO Auto-generated method stub
			tfPersonBv.setValue(bv);
		}

		public void setPersonExtern(String extern) {
			// TODO Auto-generated method stub
			tfPersonExtern.setValue(extern);
		}

		public void setPersonIntern(String intern) {
			// TODO Auto-generated method stub
			tfPersonIntern.setValue(intern);
		}

		public void setPersonMNaF(String mnaf) {
			// TODO Auto-generated method stub
			tfPersonMNaF.setValue(mnaf);
		}

		public void setPersonIfwt(String ifwt) {
			// TODO Auto-generated method stub
			tfPersonIfwt.setValue(ifwt);
		}

		public void setPersonVorname(String vorname) {
			// TODO Auto-generated method stub
			tfPersonVorname.setValue(vorname);
		}

		public void setPersonDatum(String datum) {
			// TODO Auto-generated method stub
			tfPersonDatum.setValue(datum);
		}

		public void setPersonName(String name) {
			tfPersonName.setValue(name);
		}
	}

}