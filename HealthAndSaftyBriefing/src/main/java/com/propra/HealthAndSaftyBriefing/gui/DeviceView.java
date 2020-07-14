package com.propra.HealthAndSaftyBriefing.gui;


import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.propra.HealthAndSaftyBriefing.backend.DeviceManager;
import com.propra.HealthAndSaftyBriefing.backend.data.Device;
import com.vaadin.flow.component.BlurNotifier.BlurEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.FocusNotifier;
import com.vaadin.flow.component.FocusNotifier.FocusEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.ShortcutRegistration;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.data.selection.SelectionListener;
import com.vaadin.flow.data.selection.SingleSelect;

@SuppressWarnings("serial")
public class DeviceView extends VerticalLayout {
	private Grid<Device> deviceGrid;
	private DeviceManager deviceM;
	private TextField tfSearch;
	private Button btnSearch;
	private Button btnDeviceStats;
	private Button btnAddDevice;
	private Button btnDeleteDevice;
	private Tabs searchTabs;
	private ShortcutRegistration shortReg;
	private DeviceAddForm addForm;
	private DeviceEditForm editForm;

	DeviceView() {
		deviceM = new DeviceManager();
		addForm = new DeviceAddForm(this, deviceM);
		editForm = new DeviceEditForm(this, deviceM);
		addForm.setSizeFull();
		addForm.setVisible(false);
		editForm.setSizeFull();
		editForm.setVisible(false);
		
		//Building searchComponents
		Component searchComponents = configureSearchComponents();
		add(searchComponents);
		
		// addDevice
		btnAddDevice = new Button("Neues Ger\u00e4t anlegen");
		btnAddDevice.setIcon(VaadinIcon.PLUS_CIRCLE.create());
		btnAddDevice.addClickListener(e -> {
			editForm.setVisible(false);
			addForm.setVisible(true);
			addForm.setVisible(true);
			addForm.setName("");
			addForm.setDescription("");
		});

		// button delete User
		btnDeleteDevice = new Button("Ger\u00e4t löschen", e -> {
			Device selectedDevice = deviceGrid.asSingleSelect().getValue();
			if(selectedDevice != null) {
				editForm.setVisible(false);
				deviceM.deleteDevice(selectedDevice.getId());
				updateDeviceGrid();
				Notification.show("Ger\u00e4t wurde aus der Datenbank entfernt!");
			}
			else {
				Notification.show("Wähle ein Ger\u00e4t aus, um es zu löschen!");
			}
		});
		btnDeleteDevice.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
		btnDeleteDevice.setIcon(VaadinIcon.MINUS_CIRCLE.create());
		
		//device stats
		btnDeviceStats = new Button("Öffne Gerätestatistik");
        btnDeviceStats.setIcon(VaadinIcon.CHART.create());
        btnDeviceStats.addClickListener(e -> deviceStatsButtonPressed());

		add(new HorizontalLayout(btnAddDevice, btnDeleteDevice, btnDeviceStats));

		//Building the deviceGrid
		configureDeviceGrid();
        add(deviceGrid, editForm, addForm);
        updateDeviceGrid();
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
        deviceGrid.addSelectionListener(new SelectionListener<Grid<Device>, Device>(){
			@Override
			public void selectionChange(SelectionEvent<Grid<Device>, Device> event) {
				if(event.isFromClient())
					showEditDeviceForm();
			}
		});
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
		Tab idTab = new Tab("GeräteID");
		Tab nameTab = new Tab("Name");
		Tab roomTab = new Tab("Raum");
		searchTabs = new Tabs(idTab, nameTab, roomTab);
		VerticalLayout searchComponent1 = new VerticalLayout(tfSearch, btnSearch);
		Label label = new Label("Suchen nach:");
		label.addComponentAsFirst(VaadinIcon.FILTER.create());
		VerticalLayout searchComponent2 = new VerticalLayout(label, searchTabs);
		return new HorizontalLayout(searchComponent1, searchComponent2);
	}
	
	private void searchPressed() {
		String tabName = searchTabs.getSelectedTab().getLabel();
		String searchTxt = tfSearch.getValue();
		switch(tabName) {
			case "GeräteID":
				try {
					if(searchTxt.isEmpty()) {
						updateDeviceGrid();
					}
					else {
						updateDeviceGridByID(Integer.parseInt(searchTxt));
					}
				}
				catch(NumberFormatException e) {
					Notification.show("Bitte geben Sie eine Zahl ein.");
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
	
	public void reloadGrid() {
		List<Device> devices = deviceM.getDevicesData();
		deviceGrid.setItems(devices);
	}
	
	private void showEditDeviceForm() {
	    SingleSelect<Grid<Device>, Device> selectedDevice = deviceGrid.asSingleSelect();	
		if (selectedDevice.isEmpty()) {
			return;
		}
		else {
			addForm.setVisible(false);
			editForm.setVisible(true);
			editForm.setId(selectedDevice.getValue().getId());
			editForm.setName(selectedDevice.getValue().getName());
			editForm.setDescription(selectedDevice.getValue().getDescription());
		}
	}
	
	class DeviceEditForm extends FormLayout {
		private TextField tfName;
		private TextField tfDescript;
		private Button btnRoomAssignment;
		private int id;

		DeviceEditForm(DeviceView deviceView, DeviceManager deviceM) {
			// addClassName("contact-form");
			this.setVisible(true);
			this.id = 0;
			tfName = new TextField("Name");
			tfName.setWidth("300px");
			tfDescript = new TextField("Beschreibung");
			tfDescript.setWidth("300px");

			Button save = new Button("Speichern", e -> {
				if (!tfName.isEmpty()) {
					try {
						deviceM.editDevice(this.id, tfName.getValue(), tfDescript.getValue());

						Notification.show("Ger\u00e4tedaten wurden erfolgreich bearbeitet!");
					} catch (NoSuchAlgorithmException ex) {
						ex.printStackTrace();
					}
					deviceView.updateDeviceGrid();
				} else {
					Notification.show("Bitte geben Sie einen Namen ein!");
				}
			});
			save.setIcon(VaadinIcon.ADD_DOCK.create());
			
			//button room assignment
			btnRoomAssignment = new Button("Raumzuordung bearbeiten", e -> {
				UI.getCurrent().navigate("RoomAssignmentView/"+id);
			});
			btnRoomAssignment.setIcon(VaadinIcon.HOME.create());
			
			Button close = new Button("Schließen", e -> this.setVisible(false));
			HorizontalLayout editLayout = new HorizontalLayout(tfName, tfDescript, btnRoomAssignment);
			editLayout.setAlignItems(Alignment.END);
			add( new VerticalLayout(
						editLayout,
						new HorizontalLayout(save, close)
					));
			close.setIcon(VaadinIcon.CLOSE_CIRCLE.create());
		}

		public void setName(String name) {
			tfName.setValue(name);
		}

		public void setId(int i) {
			this.id = i;
		}

		public void setDescription(String descript) {
			tfDescript.setValue(descript);
		}

	}
 
 class DeviceAddForm extends FormLayout {
	 	private TextField tfName;
		private TextField tfDescript;

		DeviceAddForm(DeviceView deviceView, DeviceManager deviceM) {
			// addClassName("contact-form");
			this.setVisible(true);
			tfName = new TextField("Name");
			tfName.setWidth("300px");
			tfDescript = new TextField("Beschreibung");
			tfDescript.setWidth("300px");

			Button save = new Button("Speichern", e -> {
				try {
					deviceM.addDevice(tfName.getValue(), tfDescript.getValue());
					Notification.show("Person wurde hinzugefügt!");
				}
				catch (NoSuchAlgorithmException ex) {
					ex.printStackTrace();
				}
				deviceView.updateDeviceGrid();

			});
			save.setIcon(VaadinIcon.ADD_DOCK.create());
			
			Button close = new Button("Schließen", e -> this.setVisible(false));
			add( new VerticalLayout(
						new HorizontalLayout(tfName, tfDescript),
						new HorizontalLayout(save, close)
					));
			close.setIcon(VaadinIcon.CLOSE_CIRCLE.create());
		}

		public void setName(String name) {
			tfName.setValue(name);
		}

		public void setDescription(String descript) {
			tfDescript.setValue(descript);
		}
	}
}
