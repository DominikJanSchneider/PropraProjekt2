package com.propra.HealthAndSaftyBriefing.gui;


import java.util.List;

import com.propra.HealthAndSaftyBriefing.Device;
import com.propra.HealthAndSaftyBriefing.DeviceManager;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@SuppressWarnings("serial")
public class DeviceView extends VerticalLayout {
	private Grid<Device> deviceGrid;
	private DeviceManager deviceM;
	
	DeviceView() {
		deviceM = new DeviceManager();
		
		//Building the deviceGrid
		configureDeviceGrid();
        add(deviceGrid);
        updateDeviceGrid();
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
		List<Device> devices = deviceM.getDevices();
        deviceGrid.setItems(devices);
	}
}
