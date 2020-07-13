package com.propra.HealthAndSaftyBriefing.gui;

import java.security.NoSuchAlgorithmException;

import com.propra.HealthAndSaftyBriefing.backend.PersonManager;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class PersonEditForm extends FormLayout {
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

	PersonEditForm(PersonManagementView personManagementView, PersonManager personM, int personId) {
		// addClassName("contact-form");
		this.setVisible(true);
		tfLName = new TextField("Name");
		tfLName = new TextField("Vorname");
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
		this.id = personId;

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
		add(tfLName, tfLName, tfDate, tfIfwt, tfMnaf, tfIntern,
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

	public void setPersonId(int personId) {
		// TODO Auto-generated method stub
		this.id = personId;
	}

	public void setTfLName(String name) {
		tfLName.setValue(name);
	}

}
