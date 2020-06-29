package com.propra.HealthAndSaftyBriefing.gui;

import java.util.List;
import com.propra.HealthAndSaftyBriefing.Room;
import com.propra.HealthAndSaftyBriefing.RoomManager;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@SuppressWarnings("serial")
public class RoomsView extends VerticalLayout {
	private Grid<Room> roomGrid;
	private RoomManager roomM;
	
	RoomsView() {
		roomM = new RoomManager();
		
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
}
