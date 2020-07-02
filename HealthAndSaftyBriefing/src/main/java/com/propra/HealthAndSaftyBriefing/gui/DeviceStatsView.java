package com.propra.HealthAndSaftyBriefing.gui;

import java.util.List;
import com.propra.HealthAndSaftyBriefing.DeviceStats;
import com.propra.HealthAndSaftyBriefing.DeviceStatsManager;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.FocusNotifier;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.ShortcutRegistration;
import com.vaadin.flow.component.BlurNotifier.BlurEvent;
import com.vaadin.flow.component.FocusNotifier.FocusEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@SuppressWarnings("serial")
@Route("DeviceStatsView")
public class DeviceStatsView extends VerticalLayout implements HasUrlParameter<String> {
	private DeviceStatsManager deviceStatsM;
	private TextField tfSearch;
	private Button btnSearch;
	private Tabs searchTabs;
	private Grid<DeviceStats> deviceStatsGrid;
	private int dID;
	private ShortcutRegistration shortReg;

	public DeviceStatsView() {
		deviceStatsM = new DeviceStatsManager();
		//Building searchComponents
		Component searchComponents = configureSearchComponents();
		add(searchComponents);
		
		//Building the deviceGrid
		configureDeviceStatsGrid();
        add(deviceStatsGrid);
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
		Tab idTab = new Tab("PersonenID");
		Tab lNameTab = new Tab("Name");
		searchTabs = new Tabs(idTab, lNameTab);
		VerticalLayout searchComponent1 = new VerticalLayout(tfSearch, btnSearch);
		VerticalLayout searchComponent2 = new VerticalLayout(new Label("Suchen nach:"), searchTabs);
		return new HorizontalLayout(searchComponent1, searchComponent2);
	}
	
	private void configureDeviceStatsGrid() {
		deviceStatsGrid = new Grid<>();
		
		deviceStatsGrid.addColumn(DeviceStats::getPersonID)
        			.setHeader("PersonenID")
        			.setKey("id")
        			.setResizable(true)
        			.setSortable(true);
        deviceStatsGrid.addColumn(DeviceStats::getPersonLName)
        			.setHeader("Name")
        			.setKey("lName")
        			.setResizable(true)
        			.setSortable(true);
        deviceStatsGrid.addColumn(DeviceStats::getPersonFName)
        			.setHeader("Vorname")
        			.setKey("fName")
        			.setResizable(true)
        			.setSortable(true);
        deviceStatsGrid.addColumn(DeviceStats::getUseTime)
					.setHeader("Nutzungszeit (in Std)")
					.setKey("useTime")
					.setSortable(true);
	}
	
	private void updateDeviceStatsGridByPersonID(int dID, int pID) {
		List<DeviceStats> devices = deviceStatsM.getDeviceStatsByPersonID(dID, pID);
        deviceStatsGrid.setItems(devices);
	}

	private void updateDeviceStatsGrid(int dID) {
		List<DeviceStats> devices = deviceStatsM.getDeviceStatsData(dID);
        deviceStatsGrid.setItems(devices);
	}
	
	private void updateDeviceStatsGridByPersonLName(int dID, String name) {
		List<DeviceStats> devices = deviceStatsM.getDeviceStatsByPersonLName(dID, name);
        deviceStatsGrid.setItems(devices);
	}
	
	private void searchPressed() {
		String tabName = searchTabs.getSelectedTab().getLabel();
		String searchTxt = tfSearch.getValue();
		switch(tabName) {
			case "PersonenID":
				if(searchTxt.isEmpty()) {
					updateDeviceStatsGrid(dID);
				}
				else {
					updateDeviceStatsGridByPersonID(dID, Integer.parseInt(searchTxt));
				}
				break;
			case "Name":
				updateDeviceStatsGridByPersonLName(dID, searchTxt);
				break;
		}
	}
	
	@Override
	public void setParameter(BeforeEvent event, String parameter) {
		dID = Integer.parseInt(parameter);
		updateDeviceStatsGrid(dID);
	}
	
}
