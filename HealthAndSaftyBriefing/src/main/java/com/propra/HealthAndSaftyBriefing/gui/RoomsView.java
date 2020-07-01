package com.propra.HealthAndSaftyBriefing.gui;

import java.util.List;

import com.propra.HealthAndSaftyBriefing.Room;
import com.propra.HealthAndSaftyBriefing.RoomManager;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

@SuppressWarnings("serial")
public class RoomsView extends VerticalLayout {
	private Grid<Room> roomGrid;
	private RoomManager roomM;
	private static TextField tfRoomName;
	private Button btnRoomName;
	
	RoomsView() {
		roomM = new RoomManager();
		
		//textField & button for filter
		tfRoomName = new TextField("Raumname eingeben");
		btnRoomName = new Button("Suchen", e -> {updateRoomByNameGrid();});
		add(tfRoomName, btnRoomName);
		
		//Building the roomGrid
		configureRoomGrid();
        add(roomGrid);
        updateRoomGrid();
	}
	
	private void configureRoomGrid() {
		roomGrid = new Grid<>();
		
		roomGrid.addColumn(Room::getName)
        			.setHeader("Name")
        			.setKey("name")
        			.setSortable(true);
        roomGrid.addColumn(Room::getDescription)
        			.setHeader("Beschreibung")
        			.setKey("description")
        			.setSortable(true);
	}
	
	private void updateRoomGrid() {
		List<Room> rooms = roomM.getRoomsData();
        roomGrid.setItems(rooms);
	}
	
	private void updateRoomByNameGrid() {
		List<Room> rooms = roomM.getRoomsByName();
        roomGrid.setItems(rooms);
	}

	public static TextField getTfRoomName() {
		return tfRoomName;
	}

	public static void setTfRoomName(TextField tfRoomName) {
		RoomsView.tfRoomName = tfRoomName;
	}
	
}
