package com.propra.HealthAndSaftyBriefing.gui;


import java.util.List;
import java.util.NoSuchElementException;

import com.propra.HealthAndSaftyBriefing.authentication.AccessControl;
import com.propra.HealthAndSaftyBriefing.authentication.AccessControlFactory;
import com.propra.HealthAndSaftyBriefing.backend.DeviceManager;
import com.propra.HealthAndSaftyBriefing.backend.UserManager;
import com.propra.HealthAndSaftyBriefing.backend.data.AssignedDevice;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@SuppressWarnings("serial")
@Route("UserView")
@PageTitle("User | Sicherheitsunterweisungen")
public class UserView extends VerticalLayout {
	
	private Label lblUser;
	private Grid<AssignedDevice> userDeviceGrid;
	private TextField tfUsageTime;
	private Button btnSetUsageTime;
	
	private UserManager userM = new UserManager();
	private DeviceManager deviceM = new DeviceManager();
	private AccessControl accessControl; 
	
	public UserView() {
		
		Button btnLogout = new Button("Logout");
		btnLogout.setIcon(VaadinIcon.SIGN_OUT.create());
		btnLogout.getElement().getStyle().set("margin-left", "auto");
		btnLogout.addClickListener(e -> logout());
		
		configureUserDeviceGrid();
		
		add(btnLogout);
		add(configureUserInfo());
		
		HorizontalLayout usageTimeLayout = new HorizontalLayout();
		tfUsageTime = new TextField();
		tfUsageTime.setPlaceholder("Nutzungszeit");
		tfUsageTime.setClearButtonVisible(true);
		tfUsageTime.addKeyPressListener(Key.ENTER, e -> setUsageTime());
		btnSetUsageTime = new Button("Zeit Setzen");
		btnSetUsageTime.addClickListener(e -> setUsageTime());
		usageTimeLayout.add(tfUsageTime, btnSetUsageTime);
		
		add(usageTimeLayout);
		add(userDeviceGrid);
		updateUserDeviceGrid();
		
	}
	
	private VerticalLayout configureUserInfo() {
		accessControl = AccessControlFactory.getInstance().createAccessControl();
		String[] userData = userM.getUserData(accessControl.getPrincipalName());
		
		VerticalLayout userInfo = new VerticalLayout();
		
		lblUser = new Label("Benutzer: "+ userData[2]+" "+userData[1]);
		lblUser.setHeight("50px");
		
		// Creating horizontal layout where user informations are stored
		HorizontalLayout userInfoHead = new HorizontalLayout();
		Label lblInstructionDate = new Label("Unterweisungsdatum");
		lblInstructionDate.setWidth("200px");
		Label lblIfwt = new Label("Ifwt");
		lblIfwt.setWidth("100px");
		Label lblMnaf = new Label("MNaF");
		lblMnaf.setWidth("100px");
		Label lblIntern = new Label("Intern");
		lblIntern.setWidth("100px");
		Label lblEmploymentType = new Label("Besch\u00e4ftigungsverh\u00e4ltnis");
		lblEmploymentType.setWidth("200px");
		Label lblBegin = new Label("Beginn");
		lblBegin.setWidth("100px");
		Label lblEnd = new Label("Ende");
		lblEnd.setWidth("100px");
		Label lblExtern = new Label("Extern");
		lblExtern.setWidth("200px");
		Label lblEmail = new Label("E-Mail Adresse");
		lblEmail.setWidth("250px");
		userInfoHead.add(lblInstructionDate, lblIfwt, lblMnaf, lblIntern, lblEmploymentType, lblBegin, lblEnd, lblExtern, lblEmail);
		
		HorizontalLayout userInfoContent = new HorizontalLayout();
		lblInstructionDate = new Label(userData[3]);
		lblInstructionDate.setWidth("200px");
		lblIfwt = new Label(userData[4]);
		lblIfwt.setWidth("100px");
		lblMnaf = new Label(userData[5]);
		lblMnaf.setWidth("100px");
		lblIntern = new Label(userData[6]);
		lblIntern.setWidth("100px");
		lblEmploymentType = new Label(userData[7]);
		lblEmploymentType.setWidth("200px");
		lblBegin = new Label(userData[8]);
		lblBegin.setWidth("100px");
		lblEnd = new Label(userData[9]);
		lblEnd.setWidth("100px");
		lblExtern = new Label(userData[10]);
		lblExtern.setWidth("200px");
		lblEmail = new Label(userData[11]);
		lblEmail.setWidth("300px");
		userInfoContent.add(lblInstructionDate, lblIfwt, lblMnaf, lblIntern, lblEmploymentType, lblBegin, lblEnd, lblExtern, lblEmail);
		
		Label lblSpace = new Label("");
		lblSpace.setHeight("50px");
		
		Label lblGeneralInstruction = new Label("Allgemeine Unterweisungen");
		lblGeneralInstruction.setWidth("400px");
		Label lblGeneralInstructionContent = new Label(userData[12].toString());
		lblGeneralInstructionContent.setWidth("400px");
		
		userInfo.add(lblUser, userInfoHead, userInfoContent, lblSpace, lblGeneralInstruction, lblGeneralInstructionContent);
		
		return userInfo;
	}
	
	public void configureUserDeviceGrid() {
		accessControl = AccessControlFactory.getInstance().createAccessControl();
		
		userDeviceGrid = new Grid<>();
		userDeviceGrid.setSelectionMode(SelectionMode.SINGLE);
//		userDeviceGrid.addColumn(AssignedDevice::getId)
//						.setHeader("ID")
//						.setKey("id")
//						.setSortable(true);
		userDeviceGrid.addColumn(AssignedDevice::getName)
						.setHeader("Ger\u00e4t")
						.setKey("name")
						.setSortable(true);
		userDeviceGrid.addColumn(AssignedDevice::getDescription)
						.setHeader("Beschreibung")
						.setKey("description")
						.setSortable(true);
		userDeviceGrid.addColumn(AssignedDevice::getRoom)
						.setHeader("Raum")
						.setKey("room")
						.setSortable(true);
		userDeviceGrid.addColumn(AssignedDevice::getUsageTime)
						.setHeader("Nutzungszeit")
						.setKey("usageTime")
						.setSortable(true);
	}
	
	private void updateUserDeviceGrid() {
		accessControl = AccessControlFactory.getInstance().createAccessControl();
		String[] userData = userM.getUserData(accessControl.getPrincipalName());
		
		List<AssignedDevice> devices = deviceM.getAssignedDevices(Integer.parseInt(userData[0]));
		userDeviceGrid.setItems(devices);
	}
	
	private void setUsageTime() {
		try {
			accessControl = AccessControlFactory.getInstance().createAccessControl();
			int userID = Integer.parseInt(userM.getUserData(accessControl.getPrincipalName())[0]);
			int selectedDevice = userDeviceGrid.getSelectionModel().getFirstSelectedItem().get().getId();
			double usageTime = Double.parseDouble(tfUsageTime.getValue());
			
			deviceM.setUsageTime(selectedDevice, userID, usageTime);
			updateUserDeviceGrid();
		} catch (NoSuchElementException e) {
			Notification.show("Bitte w\u00e4hlen Sie ein Ger\u00e4t aus der Tabelle.");
		}
	}
	
	private void logout() {
        AccessControlFactory.getInstance().createAccessControl().signOut();
    }
	
	@Override 
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		
		// User can quickly activate logout with Ctrl+L
		attachEvent.getUI().addShortcutListener(() -> logout(), Key.KEY_L, KeyModifier.CONTROL);
		
	}
}
