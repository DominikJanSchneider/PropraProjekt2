package com.propra.HealthAndSaftyBriefing.gui;


import java.util.List;

import com.propra.HealthAndSaftyBriefing.Device;
import com.propra.HealthAndSaftyBriefing.DeviceManager;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

@SuppressWarnings("serial")
public class DeviceView extends VerticalLayout {
	private Grid<Device> deviceGrid;
	private DeviceManager deviceM;
	private Label lblSearchDevice;
	private static TextField tfDeviceName;
	private static TextField tfDeviceID;
	private static TextField tfDeviceRoom;
	private Button btnDeviceName;
	private Button btnDeviceID;
	private Button btnDeviceRoom;
	private Button btnshowAllDevices;
	
	DeviceView() {
		deviceM = new DeviceManager();
		
		lblSearchDevice = new Label("Gerät suchen");
		add(lblSearchDevice);
		
		//building textfields and buttons
		tfDeviceName = new TextField("Gerätename eingeben");
		tfDeviceID = new TextField("Geräte-ID eingeben");
		tfDeviceRoom = new TextField("Geräteraum eingeben");
		btnDeviceName = new Button("Suchen", e-> {updateDeviceByNameGrid();});
		btnDeviceID = new Button("Suchen", e-> {updateDeviceByIDGrid();});
		btnDeviceRoom = new Button("Suchen", e-> {updateDeviceByRoomGrid();});
		btnshowAllDevices  = new Button ("Alle Geräte anzeigen", e -> {updateDeviceGrid();});
		
		add(new HorizontalLayout(new VerticalLayout(tfDeviceID, btnDeviceID), new VerticalLayout(tfDeviceName, btnDeviceName), new VerticalLayout(tfDeviceRoom, btnDeviceRoom), btnshowAllDevices));
		
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
		List<Device> devices = deviceM.getDevicesData();
        deviceGrid.setItems(devices);
	}
	
	private void updateDeviceByIDGrid() {
		List<Device> devices = deviceM.getDevicesByID();
        deviceGrid.setItems(devices);
	}
	
	private void updateDeviceByNameGrid() {
		List<Device> devices = deviceM.getDevicesByName();
        deviceGrid.setItems(devices);
	}
	
	private void updateDeviceByRoomGrid() {
		List<Device> devices = deviceM.getDevicesByRoom();
        deviceGrid.setItems(devices);
	}
	
	public static TextField getTfDeviceName() {
		return tfDeviceName;
	}

	public static void setTfDeviceName(TextField tfDeviceName) {
		DeviceView.tfDeviceName = tfDeviceName;
	}

	public static TextField getTfDeviceID() {
		return tfDeviceID;
	}

	public static void setTfDeviceID(TextField tfDeviceID) {
		DeviceView.tfDeviceID = tfDeviceID;
	}

	public static TextField getTfDeviceRoom() {
		return tfDeviceRoom;
	}

	public static void setTfDeviceRoom(TextField tfDeviceRoom) {
		DeviceView.tfDeviceRoom = tfDeviceRoom;
	}
}
