package com.propra.HealthAndSaftyBriefing.gui;

import java.util.List;

import com.propra.HealthAndSaftyBriefing.User;
import com.propra.HealthAndSaftyBriefing.UserManager;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route("UserManagementView")
@SuppressWarnings("serial")
public class UserManagementView extends VerticalLayout{
	
	private Grid<User> userGrid;
	UserManager UserM;
	Button btnAddUser;
	Button btnDeleteUser;
	Button btnEditUser;
	ContactForm form = new ContactForm();
	Div content;
	
	public UserManagementView() {
		
		
		
		UserM = new UserManager();
		
		//create Buttons and their clickListener
		btnAddUser = new Button("Neuen Nutzer anlegen");
		btnAddUser.addClickListener(e -> {
			    content = new Div(userGrid, form);
		    	content.setSizeFull();
		        add(content);
		    });
		btnDeleteUser = new Button("Nutzer löschen");
		btnEditUser = new Button("Nutzer bearbeiten");
		
		add(new HorizontalLayout(btnAddUser, btnDeleteUser, btnEditUser));
		
		//Building the UserGrid
				configureUserGrid();
		        add(userGrid);
		        updateUserGrid();
		        
		        
	}
	
	
	
	public void configureUserGrid() {
		userGrid = new Grid<>();
		userGrid.addColumn(User::getId)
        			.setHeader("ID")
        			.setKey("id")
        			.setSortable(true);
        userGrid.addColumn(User::getUsername)
        			.setHeader("Benutzername")
        			.setKey("username")
        			.setSortable(true);
        userGrid.addColumn(User::getPassword)
        			.setHeader("Passwort")
        			.setKey("password")
        			.setSortable(true);
	}
	
	
	public void updateUserGrid() {
		List<User> users = UserM.getAllUsers();
        userGrid.setItems(users);
	}
	

}
