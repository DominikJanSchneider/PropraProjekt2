package com.propra.HealthAndSaftyBriefing.gui;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.propra.HealthAndSaftyBriefing.backend.UserManager;
import com.propra.HealthAndSaftyBriefing.backend.data.User;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.FocusNotifier;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.ShortcutRegistration;
import com.vaadin.flow.component.BlurNotifier.BlurEvent;
import com.vaadin.flow.component.FocusNotifier.FocusEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
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
	private Button btnSearch;
	private Button btnAll;
	private Button btnAdmin;
	private Button btnUser;
	private Button btnBack;
	private UserAddForm addForm;
	private UserEditForm editForm;
	private TextField tfSearch;
	
	protected ShortcutRegistration shortReg;
	private Tabs searchTabs;

	
	public UserManagementView() {
		userM = new UserManager();
		addForm = new UserAddForm(this, userM);
		editForm = new UserEditForm(this, userM);
		addForm.setSizeFull();
		addForm.setVisible(false);
		editForm.setSizeFull();
		editForm.setVisible(false);
		
		Component searchComponents = configureSearchComponents();
		
		//create Buttons and their clickListener
		
		//btnBack
		btnBack = new Button("Zurück");

		btnBack.setIcon(VaadinIcon.ARROW_BACKWARD.create());
		btnBack.addClickListener(e -> { UI.getCurrent().navigate("AdminView");});

		
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
	
		configureUserGrid();
		updateUserGrid();
		
		add(new VerticalLayout(btnBack, searchComponents, new HorizontalLayout(btnAddUser, btnDeleteUser), userGrid, addForm, editForm));
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
	
	private Component configureSearchComponents() {
		tfSearch = new TextField();
		btnSearch = new Button("Suchen");
		btnSearch.setIcon(VaadinIcon.SEARCH.create());
		btnSearch.addClickListener(e -> searchPressed());
		tfSearch.setWidth("200px");
		tfSearch.setPlaceholder("Suche");
		tfSearch.setAutoselect(true);
		tfSearch.addFocusListener(new ComponentEventListener<FocusNotifier.FocusEvent<TextField>>() {

			@Override
			public void onComponentEvent(FocusEvent<TextField> event) {
				shortReg = btnSearch.addClickShortcut(Key.ENTER);
			}
			
		});
		tfSearch.addBlurListener(new ComponentEventListener<BlurEvent<TextField>>() {

			@Override
			public void onComponentEvent(BlurEvent<TextField> event) {
				shortReg.remove();
			}
			
		});
		
		btnAll = new Button("Alle", e -> updateUserGrid());
		btnAdmin = new Button("Admin", e -> updateUserGridByRole("Admin"));
		btnUser = new Button("Benutzer", e -> updateUserGridByRole("Benutzer"));
		
		Tab idTab = new Tab("BenutzerID");
		Tab nameTab = new Tab("Name");
		searchTabs = new Tabs(idTab, nameTab);
		VerticalLayout searchComponent1 = new VerticalLayout(tfSearch, btnSearch);
		Label label = new Label("Suchen nach:");
		label.addComponentAsFirst(VaadinIcon.FILTER.create());
		VerticalLayout searchComponent2 = new VerticalLayout(label, searchTabs);
		label = new Label("Filter");
		label.addComponentAsFirst(VaadinIcon.FILTER.create());
		VerticalLayout searchComponent3 = new VerticalLayout(label, new HorizontalLayout(btnAll, btnAdmin, btnUser));
		return new HorizontalLayout(searchComponent1, searchComponent2, searchComponent3);
	}

	private void searchPressed() {
		String tabName = searchTabs.getSelectedTab().getLabel();
		String searchTxt = tfSearch.getValue();
		switch(tabName) {
			case "BenutzerID":
				try {
					if(searchTxt.isEmpty()) {
						updateUserGrid();
					}
					else {
						updateUserGridByID(Integer.parseInt(searchTxt));
					}
				}
				catch(NumberFormatException e) {
					Notification.show("Bitte geben Sie eine Zahl ein.");
				}
				break;
			case "Name":
				updateUserGridByName(searchTxt);
				break;
		}
	}

	public void updateUserGrid() {
		List<User> users = userM.getAllUsers();
        userGrid.setItems(users);
	}
	
	private void updateUserGridByID(int id) {
		List<User> user = userM.getUserByID(id);
        userGrid.setItems(user);
	}
	
	private void updateUserGridByName(String name) {
		List<User> user = userM.getUserByName(name);
        userGrid.setItems(user);
	}
	
	private void updateUserGridByRole(String role) {
		List<User> user = userM.getUserByRole(role);
        userGrid.setItems(user);
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
		editForm.setRole(selectedUser.getValue().getUserRole());
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
							userM.editUser(this.userId, tfUserName.getValue(), tfPassword.getValue(), slRole.getValue());
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
			    Button close = new Button("Schließen", e -> this.setVisible(false));
			    close.setIcon(VaadinIcon.CLOSE_CIRCLE.create());
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
		    this.setVisible(true);
		    tfUserName = new TextField("Benutzername");
		    tfPassword = new TextField("Passwort");
		    slRole = new Select<String>("Admin", "Benutzer");
		    slRole.setLabel("Rolle");
		    
		    Button save = new Button("Speichern", e -> 
		    {
		    	if(!(tfUserName.isEmpty()) && !(tfPassword.isEmpty()) && !(slRole.getValue().isEmpty())) {
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
		    save.setIcon(VaadinIcon.ADD_DOCK.create());
		    Button close = new Button("Schließen", e -> this.setVisible(false));
		    close.setIcon(VaadinIcon.CLOSE_CIRCLE.create());
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