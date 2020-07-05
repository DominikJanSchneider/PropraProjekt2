package com.propra.HealthAndSaftyBriefing.gui;

import java.util.List;

import com.propra.HealthAndSaftyBriefing.Room;
import com.propra.HealthAndSaftyBriefing.RoomManager;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.FocusNotifier;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.ShortcutRegistration;
import com.vaadin.flow.component.BlurNotifier.BlurEvent;
import com.vaadin.flow.component.FocusNotifier.FocusEvent;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

@SuppressWarnings("serial")
public class RoomsView extends VerticalLayout {
	private Grid<Room> roomGrid;
	private RoomManager roomM;
	private TextField tfSearch;
	private Button btnSearch;
	private ShortcutRegistration shortReg;

	
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
		return new VerticalLayout(tfSearch, btnSearch);
	}

	private void configureRoomGrid() {
		roomGrid = new Grid<>();
		
		roomGrid.addColumn(Room::getName)
        			.setHeader("Name")
        			.setKey("name")
        			.setResizable(true)
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
	

	private void updateRoomGridByName(String name) {
		List<Room> rooms = roomM.getRoomsByName(name);
        roomGrid.setItems(rooms);
	}
	
	private void searchPressed() {
		String searchTxt = tfSearch.getValue();
		updateRoomGridByName(searchTxt);
	}

}
