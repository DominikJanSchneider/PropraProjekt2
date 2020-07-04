package com.propra.HealthAndSaftyBriefing.gui;


import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.propra.HealthAndSaftyBriefing.Device;
import com.propra.HealthAndSaftyBriefing.DeviceManager;
import com.vaadin.flow.component.BlurNotifier.BlurEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.FocusNotifier;
import com.vaadin.flow.component.FocusNotifier.FocusEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.ShortcutRegistration;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;

@SuppressWarnings("serial")
public class DeviceView extends VerticalLayout {
	private Grid<Device> deviceGrid;
	private DeviceManager deviceM;
	private TextField tfSearch;
	private Button btnSearch;
	private Button btnDeviceStats;
	private Tabs searchTabs;
	private ShortcutRegistration shortReg;
	
	DeviceView() {
		deviceM = new DeviceManager();
		
		//Building searchComponents
		Component searchComponents = configureSearchComponents();
		add(searchComponents);
		
		//Building the deviceGrid
		configureDeviceGrid();
        add(deviceGrid);
        updateDeviceGrid();
        
        btnDeviceStats = new Button("Öffne Gerätestatistik");
        btnDeviceStats.addClickListener(e -> deviceStatsButtonPressed());
        add(btnDeviceStats);
	}
	
	private void deviceStatsButtonPressed() {
		Set<Device> deviceSet = deviceGrid.getSelectedItems();
		Iterator<Device> it = deviceSet.iterator();
		if(it.hasNext()) {
			Device device = it.next();
			getUI().get().navigate("DeviceStatsView/"+device.getId());
			
		}
		else {
			Notification.show("Kein Eintrag Ausgewählt!");
		}
	}

	private void configureDeviceGrid() {
		deviceGrid = new Grid<>();
		
		deviceGrid.addColumn(Device::getId)
        			.setHeader("ID")
        			.setKey("id")
        			.setResizable(true)
        			.setSortable(true);
        deviceGrid.addColumn(Device::getName)
        			.setHeader("Name")
        			.setKey("name")
        			.setResizable(true)
        			.setSortable(true);
        deviceGrid.addColumn(Device::getDescription)
        			.setHeader("Beschreibung")
        			.setKey("description")
        			.setResizable(true)
        			.setSortable(true);
        deviceGrid.addColumn(Device::getRoom)
					.setHeader("Raum")
					.setKey("room")
					.setSortable(true);
	}
	
	private void updateDeviceGrid() {
		List<Device> devices = deviceM.getDevicesData();
        deviceGrid.setItems(devices);
	}
	
	private void updateDeviceGridByID(int id) {
		List<Device> devices = deviceM.getDevicesByID(id);
        deviceGrid.setItems(devices);
	}
	
	private void updateDeviceGridByName(String name) {
		List<Device> devices = deviceM.getDevicesByName(name);
        deviceGrid.setItems(devices);
	}
	
	private void updateDeviceGridByRoom(String room) {
		List<Device> devices = deviceM.getDevicesByRoom(room);
        deviceGrid.setItems(devices);
	}
	
	private Component configureSearchComponents() {
		tfSearch = new TextField();
		btnSearch = new Button("Suchen");
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
		Tab idTab = new Tab("GeräteID");
		Tab nameTab = new Tab("Name");
		Tab roomTab = new Tab("Raum");
		searchTabs = new Tabs(idTab, nameTab, roomTab);
		VerticalLayout searchComponent1 = new VerticalLayout(tfSearch, btnSearch);
		VerticalLayout searchComponent2 = new VerticalLayout(new Label("Suchen nach:"), searchTabs);
		return new HorizontalLayout(searchComponent1, searchComponent2);
	}
	
	

	private void searchPressed() {
		String tabName = searchTabs.getSelectedTab().getLabel();
		String searchTxt = tfSearch.getValue();
		switch(tabName) {
			case "GeräteID":
				if(searchTxt.isEmpty()) {
					updateDeviceGrid();
				}
				else {
					updateDeviceGridByID(Integer.parseInt(searchTxt));
				}
				break;
			case "Name":
				updateDeviceGridByName(searchTxt);
				break;
			case "Raum":
				updateDeviceGridByRoom(searchTxt);
				break;
		}
	}
}
