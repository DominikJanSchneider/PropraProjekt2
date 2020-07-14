package com.propra.HealthAndSaftyBriefing.gui;

import java.util.Comparator;
import java.util.List;
import com.propra.HealthAndSaftyBriefing.backend.RoomManager;
import com.propra.HealthAndSaftyBriefing.backend.data.AssignedRoom;
import com.propra.HealthAndSaftyBriefing.backend.data.Room;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@SuppressWarnings("serial")
@Route("RoomAssignmentView")
@PageTitle("Raumzuordnung | Sicherheitsunterweisung")
public class RoomAssignmentView extends VerticalLayout implements HasUrlParameter<String> {
	private int dID;
	private Grid<Room> unassignedGrid;
	private Grid<AssignedRoom> assignedGrid;
	private Button btnAssign;
	private Button btnUnassign;
	private Button btnSearch;
	private Button btnBack;
	private TextField tfSearch;
	private RoomManager roomM;
	protected ShortcutRegistration shortReg;
	
	
	public RoomAssignmentView() {
		roomM = new RoomManager();
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
		VerticalLayout assignedComponent = new VerticalLayout(new Label("Zugewiesen"), assignedGrid);
		assignedComponent.setWidth(width);
		HorizontalLayout gridComponent = new HorizontalLayout(unassignedComponent, assignButtons, assignedComponent);
		gridComponent.setVerticalComponentAlignment(Alignment.CENTER, assignButtons);
		gridComponent.setSizeFull();
		add(searchComponents, gridComponent);
	}
	
	private void unassignButtonPressed() {
		roomM.unassignRoom(dID);
		updateAssignedGrid(dID);
		updateUnassignedGrid(dID);
	}

	private void assignButtonPressed() {
		SingleSelect<Grid<Room>, Room> selectedRoom = unassignedGrid.asSingleSelect();
		if(!selectedRoom.isEmpty()) {
			String name = selectedRoom.getValue().getName();
			roomM.assignRoom(dID, name);
			updateAssignedGrid(dID);
			updateUnassignedGrid(dID);
		}
		else {
			Notification.show("Kein Eintrag Ausgewählt!");
		}
	}

	private void configureAssignedGrid() {
		assignedGrid = new Grid<>();
		assignedGrid.addColumn(AssignedRoom::getName)
        			.setHeader("Name")
        			.setKey("room")
        			.setResizable(true)
        			.setSortable(true);
		assignedGrid.addColumn(AssignedRoom::getDescription)
					.setHeader("Beschreibung")
					.setKey("descript")
					.setSortable(true);
	}
	
	private void configureUnassignedGrid() {
		unassignedGrid = new Grid<>();
        unassignedGrid.addColumn(Room::getName)
        			.setHeader("Name")
        			.setKey("name")
        			.setResizable(true)
        			.setSortable(true);
        unassignedGrid.addColumn(Room::getDescription)
					.setHeader("Beschreibung")
					.setKey("descript")
					.setSortable(true);
	}
	
	private Component configureSearchComponents() {
		tfSearch = new TextField();
		btnSearch = new Button("Suchen");
		btnSearch.setIcon(VaadinIcon.SEARCH.create());
		btnSearch.addClickListener(e -> searchPressed());
		tfSearch.setWidth("200px");
		tfSearch.setPlaceholder("Suche nach Name");
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
		VerticalLayout searchComponent = new VerticalLayout(tfSearch, btnSearch);
		return searchComponent;
	}
	
	@Override
	public void setParameter(BeforeEvent event, String parameter) {
		dID = Integer.parseInt(parameter);
		updateAssignedGrid(dID);
		updateUnassignedGrid(dID);
	}
	
	private void updateAssignedGrid(int dID) {
		List<AssignedRoom> rooms = roomM.getAssignedRooms(dID);
		rooms.sort(Comparator.comparing(Room::getName));
        assignedGrid.setItems(rooms);
	}
	
	private void updateUnassignedGrid(int dID) {
		List<Room> rooms = roomM.getUnassignedRooms(dID);
		rooms.sort(Comparator.comparing(Room::getName));
        unassignedGrid.setItems(rooms);
	}
	
	private void updateUnassignedGridByName(int dID, String name) {
		List<Room> rooms = roomM.getUnassignedRoomsByName(dID, name);
		rooms.sort(Comparator.comparing(Room::getName));
        unassignedGrid.setItems(rooms);
	}
	
	private void searchPressed() {
		String searchTxt = tfSearch.getValue();
		updateUnassignedGridByName(dID, searchTxt);	
	}
	
	private void backButtonPressed() {
		UI.getCurrent().navigate("AdminView/DeviceTab");
	}
}
