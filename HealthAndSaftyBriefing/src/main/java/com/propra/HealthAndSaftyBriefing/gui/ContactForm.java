package com.propra.HealthAndSaftyBriefing.gui;

import com.propra.HealthAndSaftyBriefing.UserManager;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;

public class ContactForm extends FormLayout { 

	  TextField tfUsername = new TextField("Benutzername"); 
	  TextField tfPassword = new TextField("Passwort");
	  
	  Button save; 
	  Button close;
	  UserManager userM;
	  UserManagementView umv;

	  public ContactForm() {
	    addClassName("contact-form"); 
	    
	    userM = new UserManager();
	    
	    save = new Button("Speichern");
	    close = new Button("Beenden");
	    add(tfUsername, tfPassword, new HorizontalLayout(save, close));
	    
	  }
	  
	 
	  }
