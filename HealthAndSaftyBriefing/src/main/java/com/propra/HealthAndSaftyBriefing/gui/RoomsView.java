package com.propra.HealthAndSaftyBriefing.gui;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import com.propra.HealthAndSaftyBriefing.backend.RoomManager;
import com.propra.HealthAndSaftyBriefing.backend.data.Room;
import com.vaadin.flow.component.BlurNotifier.BlurEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.FocusNotifier;
import com.vaadin.flow.component.FocusNotifier.FocusEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.ShortcutRegistration;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.data.selection.SelectionListener;
import com.vaadin.flow.data.selection.SingleSelect;

@SuppressWarnings("serial")
public class RoomsView extends VerticalLayout {
	private Grid<Room> roomGrid;
	private RoomManager roomM;
	private TextField tfSearch;
	private Button btnSearch;
	private Button btnAddRoom;
	private Button btnDeleteRoom;
	private ShortcutRegistration shortReg;
	private RoomAddForm addForm;
	private RoomEditForm editForm;

	
	RoomsView() {
		roomM = new RoomManager();
		addForm = new RoomAddForm(this, roomM);
		editForm = new RoomEditForm(this, roomM);
		addForm.setSizeFull();
		addForm.setVisible(false);
		editForm.setSizeFull();
		editForm.setVisible(false);

		//Building searchComponents
		Component searchComponents = configureSearchComponents();
		
		//create Buttons and their clickListener
		
		//btnAddRoom
		btnAddRoom = new Button("Neuen Raum anlegen");
		btnAddRoom.setIcon(VaadinIcon.PLUS_CIRCLE.create());
		btnAddRoom.addClickListener(e -> {
			editForm.setVisible(false);
			addForm.setVisible(true);
			addForm.setRoomName("");
			addForm.setRoomDescription("");
		});
		
		//btnDeleteRoom
		btnDeleteRoom = new Button("Raum löschen", e -> {
			SingleSelect<Grid<Room>, Room> selectedRoom = roomGrid.asSingleSelect();
			if (!selectedRoom.isEmpty()) {
				roomM.deleteRoom(selectedRoom.getValue().getName());
				Notification.show("Raum wurde aus der Datenbank entfernt!");
				roomGrid.deselectAll();
				updateRoomGrid();
				editForm.setVisible(false);
			}
			else {
				Notification.show("Wählen Sie einen Raum aus, um ihn zu löschen!");
			}
		});
		btnDeleteRoom.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
		btnDeleteRoom.setIcon(VaadinIcon.MINUS_CIRCLE.create());
		
		//Building the roomGrid
		configureRoomGrid();
        updateRoomGrid();
        
        add(searchComponents, new HorizontalLayout(btnAddRoom, btnDeleteRoom), roomGrid, addForm, editForm);
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
        roomGrid.addSelectionListener(new SelectionListener<Grid<Room>,Room>() {

			@Override
			public void selectionChange(SelectionEvent<Grid<Room>,Room> event) {
				if(event.isFromClient()) {
					showEditForm();
				}
			}
        	
        });
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
	
	public void reloadGrid() {
		List<Room> rooms = roomM.getRoomsData();
		roomGrid.setItems(rooms);
	}

	private void showEditForm() {
		SingleSelect<Grid<Room>, Room> selectedRoom = roomGrid.asSingleSelect();
		if (selectedRoom.isEmpty()) {
			return;
		}
		addForm.setVisible(false);
		editForm.setVisible(true);
		editForm.setRoomName(selectedRoom.getValue().getName());
		editForm.setRoomDescription(selectedRoom.getValue().getDescription());
	}
	
	class RoomEditForm extends FormLayout {
		private TextField tfRoomName;
		private TextField tfRoomDescription;
		
		public RoomEditForm(RoomsView roomsView, RoomManager roomM) {
			this.setVisible(true);
			tfRoomName = new TextField("Raumname");
			tfRoomDescription = new TextField("Raumbeschreibung");
			
			Button btnSave = new Button("Speichern", e -> {
				if (!tfRoomName.isEmpty()) {
					try {
						SingleSelect<Grid<Room>, Room> selectedRoom = roomGrid.asSingleSelect();
						String oldName = selectedRoom.getValue().getName();
						roomM.editRoom(oldName, tfRoomName.getValue(), tfRoomDescription.getValue());
						Notification.show("Raumdaten wurden erfolgreich bearbeitet");
					} catch (NoSuchAlgorithmException ex) {
						ex.printStackTrace();
					}
					roomsView.updateRoomGrid();
				}
				else {
					Notification.show("Bitte geben Sie einen Raumnamen ein!");
				}
			});
			btnSave.setIcon(VaadinIcon.ADD_DOCK.create());
			Button btnClose = new Button("Schließen", e -> this.setVisible(false));
			btnClose.setIcon(VaadinIcon.CLOSE_CIRCLE.create());
			add(tfRoomName, tfRoomDescription, new HorizontalLayout(btnSave, btnClose));
		}
		
		public void setRoomName(String name) {
			tfRoomName.setValue(name);
		}
		
		public void setRoomDescription(String description) {
			tfRoomDescription.setValue(description);
		}
	}
	
	class RoomAddForm extends FormLayout {
		private TextField tfRoomName;
		private TextField tfRoomDescription;
		
		public RoomAddForm(RoomsView roomsView, RoomManager roomM) {
			this.setVisible(true);
			tfRoomName = new TextField("Raumname");
			tfRoomDescription = new TextField("Raumbeschreibung");
			
			Button btnSave = new Button("Speichern", e -> {
				if (!tfRoomName.isEmpty()) {
					try {
						if (roomM.existsRoom(tfRoomName.getValue())) {
							Notification.show("Raum existiert bereits!");
						}
						else {
							roomM.addRoom(tfRoomName.getValue(), tfRoomDescription.getValue());
							Notification.show("Raum wurde hinzugefügt!");
						}
					} catch (NoSuchAlgorithmException ex) {
						ex.printStackTrace();
					}
					roomsView.updateRoomGrid();
				}
				else {
					Notification.show("Bitte geben Sie einen Raumnamen ein!");
				}
			});
			btnSave.setIcon(VaadinIcon.ADD_DOCK.create());
			Button btnClose = new Button("Schließen", e -> this.setVisible(false));
			btnClose.setIcon(VaadinIcon.CLOSE_CIRCLE.create());
			add(tfRoomName, tfRoomDescription, new HorizontalLayout(btnSave, btnClose));
		}
		
		public void setRoomName(String name) {
			tfRoomName.setValue(name);
		}
		
		public void setRoomDescription(String description) {
			tfRoomDescription.setValue(description);
		}
	}
}
