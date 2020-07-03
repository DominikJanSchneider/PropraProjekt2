package com.propra.HealthAndSaftyBriefing.gui;

import java.security.NoSuchAlgorithmException;

import com.propra.HealthAndSaftyBriefing.UserManager;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class ContactForm extends FormLayout { 

	  TextField tfUsername = new TextField("Benutzername"); 
	  TextField tfPassword = new TextField("Passwort");
	  TextField tfID = new TextField("ID");
	  
	  
	  Button save; 
	  Button close;
	  UserManager userM;
	  UserManagementView umv;

	  public ContactForm() {
	    addClassName("contact-form"); 
	    
	    userM = new UserManager();
	    
	    this.setVisible(true);
	    
	    save = new Button("Speichern", e -> 
	    {
	    	if(!(tfUsername.isEmpty()) && !(tfPassword.isEmpty())){
	    		try {
					UserManager.addUser(tfUsername.getValue(), tfPassword.getValue());
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    		  UserManagementView.updateUserGrid();
	    		  }else {
	    			  Notification.show("Bitte geben Sie sowohl einen Benutzernamen als auch ein Passwort ein!");
	    		  }
	    	}
	  );
	    
	    close = new Button("Beenden", e -> this.setVisible(false));
	    add(tfUsername, tfPassword, new HorizontalLayout(save, close));
	    
	  }
	  
	  public ContactForm(int i) {
		    addClassName("contact-form"); 
		    
		    userM = new UserManager();
		    
		    this.setVisible(true);
		    
		   
		    
		    save = new Button("Speichern", e -> 
		    {
		    	if(!(tfUsername.isEmpty()) && !(tfPassword.isEmpty())){
		    		try {
						UserManager.editUser(UserManagementView.userId, tfUsername.getValue(), tfPassword.getValue());
					} catch (NoSuchAlgorithmException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		    		  UserManagementView.updateUserGrid();
		    		  }else {
		    			  Notification.show("Bitte geben Sie sowohl einen Benutzernamen als auch ein Passwort ein!");
		    		  }
		    	}
		  );
		    
		    close = new Button("Beenden", e -> this.setVisible(false));
		    add(tfID, tfUsername, tfPassword, new HorizontalLayout(save, close));
		    
		  }
	  
	 
	 
	  
	  
}
