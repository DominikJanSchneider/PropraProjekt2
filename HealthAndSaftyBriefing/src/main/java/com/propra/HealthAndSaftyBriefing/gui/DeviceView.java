package com.propra.HealthAndSaftyBriefing.gui;


import java.util.List;

import com.propra.HealthAndSaftyBriefing.Device;
import com.propra.HealthAndSaftyBriefing.DeviceManager;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;

@SuppressWarnings("serial")
public class DeviceView extends VerticalLayout {
	private Grid<Device> deviceGrid;
	private DeviceManager deviceM;
	private TextField searchTF;
	private Button searchButton;
	private Button deviceStatsButton;
	private Tabs searchTabs;
	
	DeviceView() {
		deviceM = new DeviceManager();
		
		//Building searchComponents
		Component searchComponents = configureSearchComponents();
		add(searchComponents);
		
		//Building the deviceGrid
		configureDeviceGrid();
        add(deviceGrid);
        updateDeviceGrid();
        
        deviceStatsButton = new Button("Öffne Gerätestatistik");
        deviceStatsButton.addClickListener(e -> deviceButtonPressed());
        add(deviceStatsButton);
	}
	
	private void deviceButtonPressed() {
		// TODO Auto-generated method stub
	}

	private void configureDeviceGrid() {
		deviceGrid = new Grid<>();
		
		deviceGrid.addColumn(Device::getId)
        			.setHeader("ID")
        			.setKey("id")
        			.setSortable(true);
        deviceGrid.addColumn(Device::getName)
        			.setHeader("Name")
        			.setKey("name")
        			.setSortable(true);
        deviceGrid.addColumn(Device::getDescription)
        			.setHeader("Beschreibung")
        			.setKey("description")
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
	
	private Component configureSearchComponents() {
		searchTF = new TextField();
		searchButton = new Button("Suchen");
		searchButton.addClickListener(e -> searchPressed());
		searchTF.setWidth("200px");
		searchTF.setPlaceholder("Suche");
		Tab idTab = new Tab("GeräteID");
		Tab nameTab = new Tab("Name");
		Tab descriptTab = new Tab("Beschreibung");
		Tab roomTab = new Tab("Raum");
		searchTabs = new Tabs(idTab, nameTab, descriptTab, roomTab);
		VerticalLayout searchComponent1 = new VerticalLayout(searchTF, searchButton);
		VerticalLayout searchComponent2 = new VerticalLayout(new Label("Suchen nach:"), searchTabs);
		return new HorizontalLayout(searchComponent1, searchComponent2);
	}

	private void searchPressed() {
		// TODO Auto-generated method stub
	}
}
