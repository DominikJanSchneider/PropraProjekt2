package com.propra.HealthAndSaftyBriefing.gui;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import com.propra.HealthAndSaftyBriefing.backend.UserManager;
import com.propra.HealthAndSaftyBriefing.backend.data.User;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.data.selection.SelectionListener;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("UserManagementView")
@PageTitle("Benutzerverwaltung | Sicherheitsunterweisung")
@SuppressWarnings("serial")
public class UserManagementView extends VerticalLayout{
	
	private Grid<User> userGrid;
	private UserManager userM;
	private Button btnAddUser;
	private Button btnDeleteUser;
	private UserAddForm addForm;
	private UserEditForm editForm;
	
	public UserManagementView() {
		userM = new UserManager();
		addForm = new UserAddForm(this, userM);
		editForm = new UserEditForm(this, userM);
		addForm.setSizeFull();
		addForm.setVisible(false);
		editForm.setSizeFull();
		editForm.setVisible(false);
		
		//create Buttons and their clickListener
		
		//addUser
		btnAddUser = new Button("Neuen Nutzer anlegen");
		btnAddUser.setIcon(VaadinIcon.PLUS_CIRCLE.create());
		btnAddUser.addClickListener(e -> {
			editForm.setVisible(false);
			addForm.setVisible(true);
			addForm.setUserName("");
			addForm.setPassword("");
			addForm.setRole("");
		});
		
		//button delete User
		btnDeleteUser =  new Button("Nutzer löschen", e -> {
			SingleSelect<Grid<User>, User> selectedUser = userGrid.asSingleSelect();
			if(!selectedUser.isEmpty()) {
				userM.deleteUser(selectedUser.getValue().getUserID());
				Notification.show("Nutzer wurde aus der Datenbank entfernt!");
				userGrid.deselectAll();
				updateUserGrid();
				editForm.setVisible(false);
			}
			else{
				Notification.show("Wähle einen Nutzer aus, um ihn zu löschen!");
			}
		});
		btnDeleteUser.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
		btnDeleteUser.setIcon(VaadinIcon.MINUS_CIRCLE.create());
		add(new HorizontalLayout(btnAddUser, btnDeleteUser));
		
		configureUserGrid();
		add(new VerticalLayout(userGrid, addForm, editForm));
		updateUserGrid();
	}
	
	public void configureUserGrid() {
		userGrid = new Grid<>();
		userGrid.addColumn(User::getUserID)
        			.setHeader("ID")
        			.setKey("id")
        			.setSortable(true)
					.setResizable(true);
        userGrid.addColumn(User::getUserName)
        			.setHeader("Benutzername")
        			.setKey("username")
        			.setSortable(true)
        			.setResizable(true);
        userGrid.addColumn(User::getUserPassword)
        			.setHeader("Passwort")
        			.setKey("password")
        			.setSortable(true)
        			.setResizable(true);
        userGrid.addColumn(User::getUserRole)
					.setHeader("Rolle")
					.setKey("role")
					.setSortable(true);
        userGrid.addSelectionListener(new SelectionListener<Grid<User>,User>() {

			@Override
			public void selectionChange(SelectionEvent<Grid<User>,User> event) {
				if(event.isFromClient()) {
					showEditForm();
				}
			}
        	
        });
	}

	public void updateUserGrid() {
		List<User> users = userM.getAllUsers();
        userGrid.setItems(users);
	}
	
	private void showEditForm() {
		SingleSelect<Grid<User>, User> selectedUser = userGrid.asSingleSelect();
		if(selectedUser.isEmpty()) {
	    	return;
	    }
		addForm.setVisible(false);
		editForm.setVisible(true);
		editForm.setUserName(selectedUser.getValue().getUserName());
		editForm.setPassword("");
		String role = null;
		if(selectedUser.getValue().getUserRole().equals("admin")) {
			role = "Admin";
		}
		else {
			role = "Benutzer";
		}
		editForm.setRole(role);
		int userId = selectedUser.getValue().getUserID();
		editForm.setUserId(userId);
	}
	
	class UserEditForm extends FormLayout {
		private TextField tfUserName; 
		private TextField tfPassword;
		private Select<String> slRole;
		private int userId;
		
		  UserEditForm(UserManagementView userMView, UserManager userM) {
			    this.setVisible(true);
			    tfUserName = new TextField("Benutzername");
			    tfPassword = new TextField("Passwort");
			    slRole = new Select<String>("Admin", "Benutzer");
			    slRole.setLabel("Rolle");
			    this.userId = -1;
			    
			    Button save = new Button("Speichern", e -> 
			    {
			    	if(!tfUserName.isEmpty()){
			    		try {
			    			String role = null;
		    				if(slRole.getValue() == "Admin") {
		    					role = "admin";
		    				}
		    				else {
		    					role = "benutzer";
		    				}
							userM.editUser(this.userId, tfUserName.getValue(), tfPassword.getValue(), role);
							Notification.show("Nutzerdaten wurden erfolgreich bearbeitet!");
						} 
			    		catch (NoSuchAlgorithmException ex) {
							ex.printStackTrace();
						}
			    		  userMView.updateUserGrid();
			    	}
			    	else {
			    		Notification.show("Bitte geben Sie einen Benutzernamen ein!");
			    	}
			    });
			    save.setIcon(VaadinIcon.ADD_DOCK.create());
			    Button cancel = new Button("Schließen", e -> this.setVisible(false));
			    cancel.setIcon(VaadinIcon.CLOSE_CIRCLE.create());
			    add(tfUserName, tfPassword, slRole, new HorizontalLayout(save, cancel));
		  } 
		  
		  public void setUserName(String name) {
			  tfUserName.setValue(name);
		  }
		  
		  public void setPassword(String password) {
			  tfPassword.setValue(password);
		  }
		  
		  public void setRole(String role) {
			  slRole.setValue(role);
		  }
		  
		  public void setUserId(int userId) {
			  this.userId = userId;
		  }
	}
	
	class UserAddForm extends FormLayout { 
		private TextField tfUserName; 
		private TextField tfPassword;
		private Select<String> slRole;
		  UserAddForm(UserManagementView userMView, UserManager userM) {
		    this.setVisible(true);
		    tfUserName = new TextField("Benutzername");
		    tfPassword = new TextField("Passwort");
		    slRole = new Select<String>("Admin", "Benutzer");
		    slRole.setLabel("Rolle");
		    
		    Button save = new Button("Speichern", e -> 
		    {
		    	if(!(tfUserName.isEmpty()) && !(tfPassword.isEmpty()) && !(slRole.isEmpty())) {
		    		try {
		    			if(userM.existsUser(tfUserName.getValue())) {
		    				Notification.show("Nutzer existiert bereits!");
		    			}
		    			else {
		    				String role = null;
		    				if(slRole.getValue() == "Admin") {
		    					role = "admin";
		    				}
		    				else {
		    					role = "benutzer";
		    				}
		    				userM.addUser(tfUserName.getValue(), tfPassword.getValue(), role);
							Notification.show("Nutzer wurde hinzugefügt!");
		    			}
					} 
		    		catch (NoSuchAlgorithmException ex) {
						ex.printStackTrace();
					}
		    		userMView.updateUserGrid();
		    	}
		    	else {
		    		Notification.show("Bitte füllen Sie alle Felder aus!");
		    	}
		    });
		    save.setIcon(VaadinIcon.ADD_DOCK.create());
		    Button cancel = new Button("Schließen", e -> this.setVisible(false));
		    cancel.setIcon(VaadinIcon.CLOSE_CIRCLE.create());
		    add(tfUserName, tfPassword, slRole, new HorizontalLayout(save, cancel));
		  }
		  
		  public void setUserName(String name) {
			  tfUserName.setValue(name);
		  }
		  
		  public void setPassword(String password) {
			  tfPassword.setValue(password);
		  }
		  
		  public void setRole(String role) {
			  slRole.setValue(role);
		  }
	}
}