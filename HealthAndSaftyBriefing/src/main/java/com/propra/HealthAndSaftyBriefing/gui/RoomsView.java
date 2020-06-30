package com.propra.HealthAndSaftyBriefing.gui;

import java.util.List;
import com.propra.HealthAndSaftyBriefing.Room;
import com.propra.HealthAndSaftyBriefing.RoomManager;
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
public class RoomsView extends VerticalLayout {
	private Grid<Room> roomGrid;
	private RoomManager roomM;
	private TextField searchTF;
	private Button searchButton;
	private Tabs searchTabs;
	
	RoomsView() {
		roomM = new RoomManager();
		
		//Building searchComponents
		Component searchComponents = configureSearchComponents();
		add(searchComponents);
		//Building the roomGrid
		configureRoomGrid();
        add(roomGrid);
        updateRoomGrid();
	}
	
	private Component configureSearchComponents() {
		searchTF = new TextField();
		searchButton = new Button("Suchen");
		searchButton.addClickListener(e -> searchPressed());
		searchTF.setWidth("200px");
		searchTF.setPlaceholder("Suche");
		Tab nameTab = new Tab("Name");
		Tab descriptTab = new Tab("Beschreibung");
		searchTabs = new Tabs(nameTab, descriptTab);
		VerticalLayout searchComponent1 = new VerticalLayout(searchTF, searchButton);
		VerticalLayout searchComponent2 = new VerticalLayout(new Label("Suchen nach:"), searchTabs);
		return new HorizontalLayout(searchComponent1, searchComponent2);
	}

	private void searchPressed() {
		// TODO Auto-generated method stub
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
