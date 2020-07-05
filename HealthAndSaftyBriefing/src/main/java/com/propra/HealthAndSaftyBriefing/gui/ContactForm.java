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
	  TextField tfRole = new TextField("Rolle");
	  
	  
	  
	  Button save; 
	  Button close;
	  UserManager userM;
	  

	  public ContactForm() {
	    addClassName("contact-form"); 
	    
	    userM = new UserManager();
	    
	    this.setVisible(true);
	    
	    save = new Button("Speichern", e -> 
	    {
	    	if(!(tfUsername.isEmpty()) && !(tfPassword.isEmpty())){
	    		try {
					UserManager.addUser(tfUsername.getValue(), tfPassword.getValue(), tfRole.getValue());
					Notification.show("Nutzer wurde hinzugefügt!");
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    		  UserManagementView.updateUserGrid();
	    		  }else {
	    			  Notification.show("Bitte füllen Sie alle Felder aus!");
	    		  }
	    	}
	  );
	    
	    close = new Button("Beenden", e -> this.setVisible(false));
	    add(tfUsername, tfPassword, tfRole, new HorizontalLayout(save, close));
	    
	  }
	  
	  public ContactForm(int i) {
		    addClassName("contact-form"); 
		    
		    userM = new UserManager();
		    
		    this.setVisible(true);
		    
		   
		    
		    save = new Button("Speichern", e -> 
		    {
		    	if(!(tfUsername.isEmpty()) && !(tfPassword.isEmpty())){
		    		try {
						UserManager.editUser(UserManagementView.userId, tfUsername.getValue(), tfPassword.getValue(), tfRole.getValue());
						Notification.show("Nutzerdaten wurden erfolgreich bearbeitet!");
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
		    add(tfUsername, tfPassword, tfRole, new HorizontalLayout(save, close));
		    
		  }
	  
	 
	 
	  
	  
}
