package com.propra.HealthAndSaftyBriefing.gui;

import java.util.List;
import com.propra.HealthAndSaftyBriefing.User;
import com.propra.HealthAndSaftyBriefing.UserManager;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.Route;

@Route("UserManagementView")
@SuppressWarnings("serial")
public class UserManagementView extends VerticalLayout{
	
	private static Grid<User> userGrid;
	static UserManager UserM;
	Button btnAddUser;
	Button btnDeleteUser;
	Button btnEditUser;
	Div content;
	Div editContent;
	ContactForm addForm = new ContactForm();
	ContactForm editForm = new ContactForm(1);
	SingleSelect<Grid<User>, User> selectedUser;
	public static int userId;
	
	public UserManagementView() {
		
		addForm = null;
		editForm = null;
		
		
		UserM = new UserManager();
		
		//create Buttons and their clickListener
		
		//addUser
		btnAddUser = new Button("Neuen Nutzer anlegen");
		btnAddUser.addClickListener(e -> {
			if(addForm == null) {
				if(editForm!= null)
				editForm.setVisible(false);
			    content = new Div(userGrid, addForm = new ContactForm());
		    	content.setSizeFull();
		        add(content);
		        
			}else {
				if(editForm != null)
				editForm.setVisible(false);
				addForm.setVisible(true);
				addForm.tfUsername.setValue("");
				addForm.tfPassword.setValue("");
				addForm.tfRole.setValue("");

				add(content);
				
			}
			selectedUser = null;
		    });
		
		//button delete User
		btnDeleteUser =  new Button("Nutzer löschen", e -> {
			try {
			selectedUser = userGrid.asSingleSelect();
			UserM.deleteUser(selectedUser.getValue().getUserID());
			updateUserGrid();
			selectedUser = null;
			Notification.show("Nutzer wurde aus der Datenbank entfernt!");
			}catch(Exception ex) {
				Notification.show("Wähle einen Nutzer aus, um ihn zu löschen!");
			}
			
		});
		
		//button edit User
		btnEditUser = new Button("Nutzer bearbeiten", e -> {
			try {
				     if(addForm != null)
				     addForm.setVisible(false);
				     selectedUser = userGrid.asSingleSelect();
			        
					if(editForm == null) {
							
				    editContent = new Div(userGrid, editForm = new ContactForm(1));
				    editForm.tfUsername.setValue(selectedUser.getValue().getUserName());
				    editForm.tfPassword.setValue("");
				    editForm.tfRole.setValue(selectedUser.getValue().getUserRole());
				    userId = selectedUser.getValue().getUserID();
			    	editContent.setSizeFull();
			        add(editContent);
			        updateUserGrid();
			        
					}else {
						
						editForm.setVisible(true);
						editForm.tfUsername.setValue(selectedUser.getValue().getUserName());
						editForm.tfPassword.setValue("");
						editForm.tfRole.setValue(selectedUser.getValue().getUserRole());
						userId = selectedUser.getValue().getUserID();
						editContent.setSizeFull();
						add(editContent);
					}
					
				}catch(Exception ex) {
				    add(userGrid);
				    editForm.setVisible(false);;
					Notification.show("Wähle einen Nutzer aus, um Daten bearbeiten zu können!");
					
		        }
			});
		
		add(new HorizontalLayout(btnAddUser, btnDeleteUser, btnEditUser));
		
		//Building the UserGrid
				configureUserGrid();
		        add(userGrid);
		        updateUserGrid();
	}
	
	public void configureUserGrid() {
		userGrid = new Grid<>();
		userGrid.addColumn(User::getUserID)
        			.setHeader("ID")
        			.setKey("id")
        			.setSortable(true);
        userGrid.addColumn(User::getUserName)
        			.setHeader("Benutzername")
        			.setKey("username")
        			.setSortable(true);
        userGrid.addColumn(User::getUserPassword)
        			.setHeader("Passwort")
        			.setKey("password")
        			.setSortable(true);
        userGrid.addColumn(User::getUserRole)
		.setHeader("Rolle")
		.setKey("role")
		.setSortable(true);   
	}

	public static void updateUserGrid() {
		List<User> users = UserM.getAllUsers();
        userGrid.setItems(users);
	}
}
