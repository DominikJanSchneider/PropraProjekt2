package com.propra.HealthAndSaftyBriefing.gui;

import java.security.NoSuchAlgorithmException;

import com.propra.HealthAndSaftyBriefing.backend.PersonManager;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

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
