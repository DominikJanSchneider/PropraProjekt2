package com.propra.HealthAndSaftyBriefing.gui;


import java.util.Comparator;
import java.util.List;
import com.propra.HealthAndSaftyBriefing.authentication.AccessControl;
import com.propra.HealthAndSaftyBriefing.authentication.AccessControlFactory;
import com.propra.HealthAndSaftyBriefing.backend.DeviceManager;
import com.propra.HealthAndSaftyBriefing.backend.UserManager;
import com.propra.HealthAndSaftyBriefing.backend.data.AssignedDevice;
import com.propra.HealthAndSaftyBriefing.backend.data.Device;
import com.propra.HealthAndSaftyBriefing.backend.data.Person;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.FocusNotifier;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.ShortcutRegistration;
import com.vaadin.flow.component.BlurNotifier.BlurEvent;
import com.vaadin.flow.component.FocusNotifier.FocusEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
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
	
	private TextArea taGeneralInstruction;
	private TextArea taLabSetup;
	private TextArea taDangerSubst;
	private TextField tfDate;
	private TextField tfIfwt;
	private TextField tfMNaF;
	private TextField tfIntern;
	private TextField tfExtern;
	private TextField tfEmployment;
	private TextField tfBegin;
	private TextField tfEnd;
	private TextField tfEMail;
	private Person userData;
	private TextField tfSearch;
	private Button btnSearch;
	protected ShortcutRegistration shortReg;
	private Tabs searchTabs;
	
	public UserView() {
		accessControl = AccessControlFactory.getInstance().createAccessControl();
		userData = userM.getUserData(accessControl.getPrincipalName());
		
		//logout button
		Button btnLogout = new Button("Logout");
		btnLogout.setIcon(VaadinIcon.SIGN_OUT.create());
		btnLogout.getElement().getStyle().set("margin-left", "auto");
		btnLogout.addClickListener(e -> logout());
		
		//label with name of current user
		lblUser = new Label("Benutzer: "+ userData.getFName()+" "+userData.getLName());
		lblUser.setHeight("50px");
		
		//Building and updating of ui
		add(btnLogout, lblUser, configureUserInfoComponent(), configureBriefingInformationComponent(), configureTimeSettingsComponent());
		updateUserDeviceGrid();
		updateUserInfo();
		
	}
	
	private Component configureTimeSettingsComponent() {
		configureUserDeviceGrid();
		tfUsageTime = new TextField();
		tfUsageTime.setPlaceholder("Nutzungszeit");
		tfUsageTime.setClearButtonVisible(true);
		tfUsageTime.addKeyPressListener(Key.ENTER, e -> setUsageTime());
		btnSetUsageTime = new Button("Zeit Setzen");
		btnSetUsageTime.addClickListener(e -> setUsageTime());
		HorizontalLayout usageTimeLayout = new HorizontalLayout(tfUsageTime, btnSetUsageTime);
		VerticalLayout timeSettingsContent = new VerticalLayout(configureSearchComponents(), userDeviceGrid, usageTimeLayout);
		Details timeSettings = new Details("Nutzungszeiteinstellungen", timeSettingsContent);
		return timeSettings;
	}
	
	private Component configureUserInfoComponent() {
		tfDate = new TextField();
		tfDate.setLabel("Unterweisungsdatum");
		tfDate.setReadOnly(true);
		
		tfIfwt = new TextField();
		tfIfwt.setLabel("Ifwt");
		tfIfwt.setReadOnly(true);
		
		tfMNaF = new TextField();
		tfMNaF.setLabel("MNaF");
		tfMNaF.setReadOnly(true);
		
		tfIntern = new TextField();
		tfIntern.setLabel("Intern");
		tfIntern.setReadOnly(true);
		
		tfExtern = new TextField();
		tfExtern.setLabel("Extern");
		tfExtern.setReadOnly(true);
		
		tfEmployment = new TextField();
		tfEmployment.setLabel("Beschäftigungverhältnis");
		tfEmployment.setReadOnly(true);
		
		tfBegin = new TextField();
		tfBegin.setLabel("Beginn");
		tfBegin.setReadOnly(true);
		
		tfEnd = new TextField();
		tfEnd.setLabel("Ende");
		tfEnd.setReadOnly(true);
		
		tfEMail = new TextField();
		tfEMail.setLabel("E-Mail-Adresse");
		tfEMail.setReadOnly(true);
		tfEMail.setWidth("350px");
		
		VerticalLayout userInfoContent = new VerticalLayout(
														tfDate, 
														new HorizontalLayout(tfIfwt, tfMNaF, tfIntern, tfExtern), 
														new HorizontalLayout(tfEmployment, tfBegin, tfEnd), 
														tfEMail
													);
		//VerticalLayout userInfoContent = new VerticalLayout(lblUser
		Details userInfo = new Details("Allgeimene Benutzerinformationen", userInfoContent);
		return userInfo;
	}
	
	public void configureUserDeviceGrid() {
		userDeviceGrid = new Grid<>();
		userDeviceGrid.setSelectionMode(SelectionMode.SINGLE);
		userDeviceGrid.addColumn(AssignedDevice::getId)
						.setHeader("Ger\u00e4teID")
						.setKey("id")
						.setSortable(true);
		userDeviceGrid.addColumn(AssignedDevice::getName)
						.setHeader("Name")
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
		userDeviceGrid.setWidth("1200px");
	}
	
	private Component configureBriefingInformationComponent() {
		taGeneralInstruction = new TextArea();
		taLabSetup = new TextArea();
        taDangerSubst = new TextArea();
        
        String height = "150px";
        String width = "400px";
        
        taGeneralInstruction.setHeight(height);
        taGeneralInstruction.setWidth(width);
        taGeneralInstruction.setReadOnly(true);
        taGeneralInstruction.setLabel("Allgemeine Unterweisung");
        taLabSetup.setHeight(height);
        taLabSetup.setWidth(width);
        taLabSetup.setReadOnly(true);
        taLabSetup.setLabel("Laboreinrichtungen");
        taDangerSubst.setHeight(height);
        taDangerSubst.setWidth(width);
        taDangerSubst.setReadOnly(true);
        taDangerSubst.setLabel("Gefahrstoffe");
        HorizontalLayout briefingInformationContent = new HorizontalLayout(taGeneralInstruction, taLabSetup, taDangerSubst);
        Details briefingInformation = new Details("Unterweisungsinformationen", briefingInformationContent);
		return briefingInformation;
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
		Tab idTab = new Tab("Ger\u00e4teID");
		Tab nameTab = new Tab("Name");
		Tab roomTab = new Tab("Raum");
		searchTabs = new Tabs(idTab, nameTab, roomTab);
		VerticalLayout searchComponent1 = new VerticalLayout(tfSearch, btnSearch);
		Label label = new Label("Suchen nach:");
		label.addComponentAsFirst(VaadinIcon.FILTER.create());
		VerticalLayout searchComponent2 = new VerticalLayout(label , searchTabs);
		return new HorizontalLayout(searchComponent1, searchComponent2);
	}
	
	private void updateUserInfo() {
		tfDate.setValue(userData.getDate());
		tfIfwt.setValue(userData.getIfwt());
		tfMNaF.setValue(userData.getMNaF());
		tfIntern.setValue(userData.getIntern());
		tfExtern.setValue(userData.getExtern());
		tfEmployment.setValue(userData.getEmployment());
		tfBegin.setValue(userData.getBegin());
		tfEnd.setValue(userData.getEnd());
		tfEMail.setValue(userData.getEMail());
		taGeneralInstruction.setValue(userData.getGenInstr());
		String labComment = userData.getLabComment();
		String labSetup = userData.getLabSetup();
		String dangerSubstComment = userData.getDangerSubstComment();
		String dangerSubsts = userData.getDangerSubsts();
		if(labComment.isEmpty()) {
			taLabSetup.setValue(labSetup);
		}
		else {
			taLabSetup.setValue(labComment+"\n"+labSetup);
		}
		if(dangerSubstComment.isEmpty()) {
			taDangerSubst.setValue(dangerSubsts);
		}
		else {
			taDangerSubst.setValue(dangerSubstComment+"\n"+dangerSubsts);
		}
	}
	
	private void updateUserDeviceGrid() {
		List<AssignedDevice> devices = deviceM.getAssignedDevices(userData.getId());
		userDeviceGrid.setItems(devices);
	}
	
	private void updateUserDeviceGridByID(int dID) {
		List<AssignedDevice> devices = deviceM.getAssignedDevicesByID(userData.getId(), dID);
		devices.sort(Comparator.comparing(Device::getId));
        userDeviceGrid.setItems(devices);
	}
	
	private void updateUserDeviceGridByName(String name) {
		List<AssignedDevice> devices = deviceM.getAssignedDevicesByName(userData.getId(), name);
		devices.sort(Comparator.comparing(Device::getId));
		userDeviceGrid.setItems(devices);
	}
	
	private void updateUserDeviceGridByRoom(String room) {
		List<AssignedDevice> devices = deviceM.getAssignedDevicesByRoom(userData.getId(), room);
		devices.sort(Comparator.comparing(Device::getId));
		userDeviceGrid.setItems(devices);
	}
	
	private void setUsageTime() {
		AssignedDevice selectedDevice = userDeviceGrid.asSingleSelect().getValue();
		if(selectedDevice != null) {
			int userID = userData.getId();
			int dID = selectedDevice.getId();
			try {
				double usageTime = Double.parseDouble(tfUsageTime.getValue());
				deviceM.setUsageTime(dID, userID, usageTime);
				updateUserDeviceGrid();
			} catch (NumberFormatException e) {
				Notification.show("Bitte geben Sie eine Zahl ein.");
			}
		}
		else {
			Notification.show("Bitte w\u00e4hlen Sie ein Ger\u00e4t aus der Tabelle.");
		}
	}
	
	private void logout() {
       accessControl.signOut();
    }
	
	@Override 
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		
		// User can quickly activate logout with Ctrl+L
		attachEvent.getUI().addShortcutListener(() -> logout(), Key.KEY_L, KeyModifier.CONTROL);
		
	}
	
	private void searchPressed() {
		String tabName = searchTabs.getSelectedTab().getLabel();
		String searchTxt = tfSearch.getValue();
		switch(tabName) {
			case "GeräteID":
				try {
					if(searchTxt.isEmpty()) {
						updateUserDeviceGrid();
					}
					else {
						updateUserDeviceGridByID(Integer.parseInt(searchTxt));
					}
				}
				catch(NumberFormatException e) {
					Notification.show("Bitte geben Sie eine Zahl ein.");
				}
				
				break;
			case "Name":
				updateUserDeviceGridByName(searchTxt);
				break;
			case "Raum":
				updateUserDeviceGridByRoom(searchTxt);
				break;
		}
	}
}
