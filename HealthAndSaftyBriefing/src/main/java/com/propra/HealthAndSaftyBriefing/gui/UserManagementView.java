package com.propra.HealthAndSaftyBriefing.gui;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.propra.HealthAndSaftyBriefing.backend.UserManager;
import com.propra.HealthAndSaftyBriefing.backend.data.User;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
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
	private Button btnEditUser;
	private Div content;
	private Div editContent;
	private UserAddForm addForm;
	private UserEditForm editForm;
	private SingleSelect<Grid<User>, User> selectedUser;
	
	public UserManagementView() {
		
		addForm = null;
		editForm = null;
		userM = new UserManager();
		
		//create Buttons and their clickListener
		
		//addUser
		btnAddUser = new Button("Neuen Nutzer anlegen");
		btnAddUser.addClickListener(e -> {
			if(addForm == null) {
				if(editForm!= null)
				editForm.setVisible(false);
			    content = new Div(userGrid, addForm = new UserAddForm(this, userM));
		    	content.setSizeFull();
		        add(content);
		        
			}else {
				if(editForm != null)
				editForm.setVisible(false);
				addForm.setVisible(true);
				addForm.setUserName("");
				addForm.setPassword("");
				addForm.setRole("");

				add(content);
				
			}
			selectedUser = null;
		    });
		
		//button delete User
		btnDeleteUser =  new Button("Nutzer löschen", e -> {
			try {
				selectedUser = userGrid.asSingleSelect();
				userM.deleteUser(selectedUser.getValue().getUserID());
				updateUserGrid();
				selectedUser = null;
				Notification.show("Nutzer wurde aus der Datenbank entfernt!");
			}
			catch(Exception ex) {
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
						int userId = selectedUser.getValue().getUserID();
						editContent = new Div(userGrid, editForm = new UserEditForm(this, userM, userId));
						editForm.setUserName(selectedUser.getValue().getUserName());
						editForm.setPassword("");
						editForm.setRole(selectedUser.getValue().getUserRole());
				    
						editContent.setSizeFull();
						add(editContent);
						updateUserGrid();
					}
					else {
						editForm.setVisible(true);
						editForm.setUserName(selectedUser.getValue().getUserName());
						editForm.setPassword("");
						editForm.setRole(selectedUser.getValue().getUserRole());
						int userId = selectedUser.getValue().getUserID();
						editForm.setUserId(userId);
						editContent.setSizeFull();
						add(editContent);
					}
					
				}
				catch(Exception ex) {
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
	}

	public void updateUserGrid() {
		List<User> users = userM.getAllUsers();
        userGrid.setItems(users);
	}
	
	class UserEditForm extends FormLayout {
		private TextField tfUserName; 
		private TextField tfPassword;
		//private TextField tfRole;
		private Select<String> slRole;
		private int userId;
		
		  UserEditForm(UserManagementView userMView, UserManager userM, int userId) {
			    //addClassName("contact-form"); 
			    this.setVisible(true);
			    tfUserName = new TextField("Benutzername");
			    tfPassword = new TextField("Passwort");
			    //tfRole = new TextField("Rolle");
			    slRole = new Select<String>("Benutzer", "Admin");
			    slRole.setLabel("Rolle");
			    this.userId = userId;
			    
			    Button save = new Button("Speichern", e -> 
			    {
			    	if(!(tfUserName.isEmpty()) && !(tfPassword.isEmpty()) && !(slRole.isEmpty())){
			    		try {
							userM.editUser(this.userId, tfUserName.getValue(), tfPassword.getValue(), slRole.getValue());
							Notification.show("Nutzerdaten wurden erfolgreich bearbeitet!");
						} 
			    		catch (NoSuchAlgorithmException ex) {
							ex.printStackTrace();
						}
			    		  userMView.updateUserGrid();
			    	}
			    	else {
			    		Notification.show("Bitte geben Sie sowohl einen Benutzernamen als auch ein Passwort ein!");
			    	}
			    });
			    Button close = new Button("Beenden", e -> this.setVisible(false));
			    add(tfUserName, tfPassword, slRole, new HorizontalLayout(save, close));
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
		    //addClassName("contact-form"); 
		    this.setVisible(true);
		    tfUserName = new TextField("Benutzername");
		    tfPassword = new TextField("Passwort");
		    slRole = new Select<String>("Benutzer", "Admin");
		    slRole.setLabel("Rolle");
		    
		    Button save = new Button("Speichern", e -> 
		    {
		    	if(!(tfUserName.isEmpty()) && !(tfPassword.isEmpty()) && !(slRole.isEmpty())) {
		    		try {
		    			if(userM.existsUser(tfUserName.getValue())) {
		    				Notification.show("Nutzer existiert bereits!");
		    			}
		    			else {
		    				userM.addUser(tfUserName.getValue(), tfPassword.getValue(), slRole.getValue());
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
		    
		    Button close = new Button("Beenden", e -> this.setVisible(false));
		    add(tfUserName, tfPassword, slRole, new HorizontalLayout(save, close));
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
