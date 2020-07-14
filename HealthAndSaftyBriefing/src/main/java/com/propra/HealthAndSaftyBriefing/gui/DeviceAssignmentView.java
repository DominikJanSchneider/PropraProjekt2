package com.propra.HealthAndSaftyBriefing.gui;

import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.List;
import com.propra.HealthAndSaftyBriefing.backend.DeviceManager;
import com.propra.HealthAndSaftyBriefing.backend.PersonManager;
import com.propra.HealthAndSaftyBriefing.backend.data.AssignedDevice;
import com.propra.HealthAndSaftyBriefing.backend.data.Device;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.FocusNotifier;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.ShortcutRegistration;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.BlurNotifier.BlurEvent;
import com.vaadin.flow.component.FocusNotifier.FocusEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@SuppressWarnings("serial")
@Route("DeviceAssignmentView")
@PageTitle("Gerätezuordnung | Sicherheitsunterweisung")
public class DeviceAssignmentView extends VerticalLayout implements HasUrlParameter<String> {
	private int pID;
	private Grid<Device> unassignedGrid;
	private Grid<AssignedDevice> assignedGrid;
	private Button btnAssign;
	private Button btnUnassign;
	private Button btnSearch;
	private Button btnSetUsageTime;
	private Button btnBack;
	private Tabs searchTabs1;
	private Tabs searchTabs2;
	private TextField tfSearch;
	private TextField tfUsageTime;
	private DeviceManager deviceM;
	private PersonManager personM;
	protected ShortcutRegistration shortReg;
	
	public DeviceAssignmentView() {
		deviceM = new DeviceManager();
		personM = new PersonManager();
		//back button
		btnBack = new Button("Zurück", e -> backButtonPressed());
		btnBack.setIcon(VaadinIcon.ARROW_BACKWARD.create());
		add(btnBack);
		
		//searchComponents
		Component searchComponents = configureSearchComponents();
		
		//grid component
		btnAssign = new Button("", e -> assignButtonPressed());
		btnAssign.setIcon(VaadinIcon.ARROW_CIRCLE_RIGHT.create());
		btnUnassign = new Button("", e -> unassignButtonPressed());
		btnUnassign.setIcon(VaadinIcon.ARROW_CIRCLE_LEFT.create());
		VerticalLayout assignButtons = new VerticalLayout(btnAssign, btnUnassign);
		assignButtons.setWidth("50px");
		assignButtons.setHorizontalComponentAlignment(Alignment.CENTER, btnAssign, btnUnassign);
		
		String width = "800px";
		configureAssignedGrid();
		configureUnassignedGrid();
		VerticalLayout unassignedComponent = new VerticalLayout(new Label("Nicht Zugewiesen"), unassignedGrid);
		unassignedComponent.setWidth(width);
		VerticalLayout assignedComponent = new VerticalLayout(new Label("Zugewiesen"), assignedGrid, configureTimeSettingsComponent());
		assignedComponent.setWidth(width);
		HorizontalLayout gridComponent = new HorizontalLayout(unassignedComponent, assignButtons, assignedComponent);
		gridComponent.setVerticalComponentAlignment(Alignment.CENTER, assignButtons);
		gridComponent.setSizeFull();
		add(searchComponents, gridComponent);
	}
	
	private void unassignButtonPressed() {
		SingleSelect<Grid<AssignedDevice>, AssignedDevice> selectedDevice = assignedGrid.asSingleSelect();
		if(!selectedDevice.isEmpty()) {
			int dID = selectedDevice.getValue().getId();
			deviceM.unassignDevice(dID, pID);
			updateAssignedGrid(pID);
			updateUnassignedGrid(pID);
			try {
				String labSetupTxt = getLabSetupTxt(pID);
				personM.setLabSetup(pID, labSetupTxt);
			}
			catch(NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		else {
			Notification.show("Kein Eintrag Ausgewählt!");
		}
	}

	private void assignButtonPressed() {
		SingleSelect<Grid<Device>, Device> selectedDevice = unassignedGrid.asSingleSelect();
		if(!selectedDevice.isEmpty()) {
			int dID = selectedDevice.getValue().getId();
			deviceM.assignDevice(dID, pID);
			updateAssignedGrid(pID);
			updateUnassignedGrid(pID);
			try {
				String labSetupTxt = getLabSetupTxt(pID);
				personM.setLabSetup(pID, labSetupTxt);
			}
			catch(NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		else {
			Notification.show("Kein Eintrag Ausgewählt!");
		}
	}

	private void configureAssignedGrid() {
		assignedGrid = new Grid<>();
		
		assignedGrid.addColumn(AssignedDevice::getId)
        			.setHeader("Ger\u00e4teID")
        			.setKey("id")
        			.setResizable(true)
        			.setSortable(true);
		assignedGrid.addColumn(AssignedDevice::getName)
        			.setHeader("Name")
        			.setKey("name")
        			.setResizable(true)
        			.setSortable(true);
		assignedGrid.addColumn(AssignedDevice::getDescription)
        			.setHeader("Beschreibung")
        			.setKey("descript")
        			.setResizable(true)
        			.setSortable(true);
		assignedGrid.addColumn(AssignedDevice::getRoom)
					.setHeader("Raum")
					.setKey("room")
					.setResizable(true)
					.setSortable(true);
		assignedGrid.addColumn(AssignedDevice::getUsageTime)
					.setHeader("Nutzungszeit (in Std)")
					.setKey("useTime")
					.setSortable(true);
	}
	
	private void configureUnassignedGrid() {
		unassignedGrid = new Grid<>();
		
		unassignedGrid.addColumn(Device::getId)
        			.setHeader("Ger\u00e4teID")
        			.setKey("id")
        			.setResizable(true)
        			.setSortable(true);
        unassignedGrid.addColumn(Device::getName)
        			.setHeader("Name")
        			.setKey("name")
        			.setResizable(true)
        			.setSortable(true);
        unassignedGrid.addColumn(Device::getDescription)
        			.setHeader("Beschreibung")
        			.setKey("description")
        			.setResizable(true)
        			.setSortable(true);
        unassignedGrid.addColumn(Device::getRoom)
					.setHeader("Raum")
					.setKey("room")
					.setSortable(true);
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
		Tab assignedTab = new Tab("Zugewiesen");
		Tab unassignedTab = new Tab("Nicht Zugewiesen");
		searchTabs1 = new Tabs(unassignedTab, assignedTab);
		searchTabs1.setWidth("300px");
		Tab idTab = new Tab("GeräteID");
		Tab nameTab = new Tab("Name");
		Tab roomTab = new Tab("Raum");
		searchTabs2 = new Tabs(idTab, nameTab, roomTab);
		VerticalLayout searchComponent1 = new VerticalLayout(tfSearch, btnSearch);
		Label label = new Label("Suchen nach:");
		label.addComponentAsFirst(VaadinIcon.FILTER.create());
		HorizontalLayout searchTabs = new HorizontalLayout(searchTabs1, searchTabs2);
		searchTabs.setSizeFull();
		VerticalLayout searchComponent2 = new VerticalLayout(label, searchTabs);
		return new HorizontalLayout(searchComponent1, searchComponent2);
	}
	
	private Component configureTimeSettingsComponent() {
		tfUsageTime = new TextField();
		tfUsageTime.setPlaceholder("Nutzungszeit");
		tfUsageTime.setClearButtonVisible(true);
		tfUsageTime.addKeyPressListener(Key.ENTER, e -> setUsageTime());
		btnSetUsageTime = new Button("Zeit Setzen");
		btnSetUsageTime.addClickListener(e -> setUsageTime());
		HorizontalLayout usageTimeLayout = new HorizontalLayout(tfUsageTime, btnSetUsageTime);
		return usageTimeLayout;
	}
	
	@Override
	public void setParameter(BeforeEvent event, String parameter) {
		pID = Integer.parseInt(parameter);
		updateAssignedGrid(pID);
		updateUnassignedGrid(pID);
	}
	
	private void updateAssignedGrid(int pID) {
		List<AssignedDevice> devices = deviceM.getAssignedDevices(pID);
		devices.sort(Comparator.comparing(Device::getId));
        assignedGrid.setItems(devices);
	}
	
	private void updateAssignedGridByID(int pID, int dID) {
		List<AssignedDevice> devices = deviceM.getAssignedDevicesByID(pID, dID);
		devices.sort(Comparator.comparing(Device::getId));
        assignedGrid.setItems(devices);
	}
	
	private void updateAssignedGridByName(int pID, String name) {
		List<AssignedDevice> devices = deviceM.getAssignedDevicesByName(pID, name);
		devices.sort(Comparator.comparing(Device::getId));
        assignedGrid.setItems(devices);
	}
	
	private void updateAssignedGridByRoom(int pID, String room) {
		List<AssignedDevice> devices = deviceM.getAssignedDevicesByRoom(pID, room);
		devices.sort(Comparator.comparing(Device::getId));
        assignedGrid.setItems(devices);
	}
	
	private void updateUnassignedGrid(int pID) {
		List<Device> devices = deviceM.getUnassignedDevices(pID);
		devices.sort(Comparator.comparing(Device::getId));
        unassignedGrid.setItems(devices);
	}
	
	private void updateUnassignedGridByID(int pID, int dID) {
		List<Device> devices = deviceM.getUnassignedDevicesByID(pID, dID);
		devices.sort(Comparator.comparing(Device::getId));
        unassignedGrid.setItems(devices);
	}
	
	private void updateUnassignedGridByName(int pID, String name) {
		List<Device> devices = deviceM.getUnassignedDevicesByName(pID, name);
		devices.sort(Comparator.comparing(Device::getId));
        unassignedGrid.setItems(devices);
	}
	
	private void updateUnassignedGridByRoom(int pID, String room) {
		List<Device> devices = deviceM.getUnassignedDevicesByRoom(pID, room);
		devices.sort(Comparator.comparing(Device::getId));
        unassignedGrid.setItems(devices);
	}
	
	private void searchPressed() {
		String tabName1 = searchTabs1.getSelectedTab().getLabel();
		String tabName2 = searchTabs2.getSelectedTab().getLabel();
		String searchTxt = tfSearch.getValue();
		switch(tabName2) {
			case "GeräteID":
				try {
					if(searchTxt.isEmpty()) {
						if(tabName1.contentEquals("Zugewiesen")) {
							updateAssignedGrid(pID);
						}
						else {
							updateUnassignedGrid(pID);
						}
					}
					else {
						if(tabName1.contentEquals("Zugewiesen")) {
							updateAssignedGridByID(pID, Integer.parseInt(searchTxt));
						}
						else {
							updateUnassignedGridByID(pID, Integer.parseInt(searchTxt));
						}
					}
				}
				catch(NumberFormatException e) {
					Notification.show("Bitte geben Sie eine Zahl ein.");
				}
				
				break;
			case "Name":
				if(tabName1.contentEquals("Zugewiesen")) {
					updateAssignedGridByName(pID, searchTxt);
				}
				else {
					updateUnassignedGridByName(pID, searchTxt);
				}
				
				break;
			case "Raum":
				if(tabName1.contentEquals("Zugewiesen")) {
					updateAssignedGridByRoom(pID, searchTxt);
				}
				else {
					updateUnassignedGridByRoom(pID, searchTxt);
				}
				break;
		}
	}
	
	private void setUsageTime() {
		AssignedDevice selectedDevice = assignedGrid.asSingleSelect().getValue();
		if(selectedDevice != null) {
			int dID = selectedDevice.getId();
			try {
				double usageTime = Double.parseDouble(tfUsageTime.getValue());
				deviceM.setUsageTime(dID, pID, usageTime);
				updateAssignedGrid(pID);
			}
			catch(NumberFormatException e) {
				Notification.show("Bitte geben Sie eine Zahl ein.");
			}
		}
		else {
			Notification.show("Bitte w\u00e4hlen Sie ein Ger\u00e4t aus der Tabelle.");
		}
	}
	
	private void backButtonPressed() {
		UI.getCurrent().navigate("PersonManagementView");
	}
	
	private String getLabSetupTxt(int pID)
	{
		String res = "Ger\u00e4te mit denen gearbeitet wird:";
		List<AssignedDevice> devices = deviceM.getAssignedDevices(pID);
		for(AssignedDevice device : devices) {
			int id = device.getId();
			String name = device.getName();
			String room = device.getRoom();
			res = res.concat("\n-Ger\u00e4teID: "+id+", Name: "+name+", Raum: "+room);
		}
		return res;
	}
}
