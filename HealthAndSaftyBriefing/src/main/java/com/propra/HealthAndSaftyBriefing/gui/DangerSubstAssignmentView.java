package com.propra.HealthAndSaftyBriefing.gui;

import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.List;

import com.propra.HealthAndSaftyBriefing.backend.DangerSubstManager;
import com.propra.HealthAndSaftyBriefing.backend.PersonManager;
import com.propra.HealthAndSaftyBriefing.backend.data.AssignedDangerSubst;
import com.propra.HealthAndSaftyBriefing.backend.data.DangerSubst;
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
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@SuppressWarnings("serial")
@Route("DangerSubstAssignmentView")
@PageTitle("Gefahrstoffzuordnung | Sicherheitsunterweisung")
public class DangerSubstAssignmentView extends VerticalLayout implements HasUrlParameter<String> {
	private int pID;
	private Grid<DangerSubst> unassignedGrid;
	private Grid<AssignedDangerSubst> assignedGrid;
	private Button btnAssign;
	private Button btnUnassign;
	private Button btnSearch;
	private Button btnBack;
	private Tabs searchTabs;
	private TextField tfSearch;
	private DangerSubstManager dangerSubstM;
	private PersonManager personM;
	protected ShortcutRegistration shortReg;
	
	
	public DangerSubstAssignmentView() {
		dangerSubstM = new DangerSubstManager();
		personM = new PersonManager();
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
		SingleSelect<Grid<AssignedDangerSubst>, AssignedDangerSubst> selectedDangerSubst = assignedGrid.asSingleSelect();
		if(!selectedDangerSubst.isEmpty()) {
			String name = selectedDangerSubst.getValue().getName();
			dangerSubstM.unassignDangerSubst(name, pID);
			updateAssignedGrid(pID);
			updateUnassignedGrid(pID);
			try {
				String dangerSubstTxt = getDangerSubstTxt(pID);
				personM.setDangerSubst(pID, dangerSubstTxt);
			}
			catch(NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		else {
			Notification.show("Kein Eintrag Ausgewählt!");
		}
	}

	private void assignButtonPressed() {
		SingleSelect<Grid<DangerSubst>, DangerSubst> selectedDangerSubst = unassignedGrid.asSingleSelect();
		if(!selectedDangerSubst.isEmpty()) {
			String name = selectedDangerSubst.getValue().getName();
			dangerSubstM.assignDangerSubst(name, pID);
			updateAssignedGrid(pID);
			updateUnassignedGrid(pID);
			try {
				String dangerSubstTxt = getDangerSubstTxt(pID);
				personM.setDangerSubst(pID, dangerSubstTxt);
			}
			catch(NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		else {
			Notification.show("Kein Eintrag Ausgewählt!");
		}
	}

	private void configureAssignedGrid() {
		assignedGrid = new Grid<>();
		assignedGrid.addColumn(AssignedDangerSubst::getName)
        			.setHeader("Gefahrstoff")
        			.setKey("dangerSubst")
        			.setSortable(true);
	}
	
	private void configureUnassignedGrid() {
		unassignedGrid = new Grid<>();
        unassignedGrid.addColumn(DangerSubst::getName)
        			.setHeader("Gefahrstoff")
        			.setKey("dangerSubst")
        			.setSortable(true);
	}
	
	private Component configureSearchComponents() {
		tfSearch = new TextField();
		btnSearch = new Button("Suchen");
		btnSearch.setIcon(VaadinIcon.SEARCH.create());
		btnSearch.addClickListener(e -> searchPressed());
		tfSearch.setWidth("200px");
		tfSearch.setPlaceholder("Suche nach Gefahrstoff");
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
		Tab assignedTab = new Tab("Zugewiesen");
		Tab unassignedTab = new Tab("Nicht Zugewiesen");
		searchTabs = new Tabs(unassignedTab, assignedTab);
		searchTabs.setWidth("300px");
		VerticalLayout searchComponent1 = new VerticalLayout(tfSearch, btnSearch);
		Label label = new Label("Suchen nach:");
		label.addComponentAsFirst(VaadinIcon.FILTER.create());
		VerticalLayout searchComponent2 = new VerticalLayout(label, searchTabs);
		return new HorizontalLayout(searchComponent1, searchComponent2);
	}
	
	@Override
	public void setParameter(BeforeEvent event, String parameter) {
		pID = Integer.parseInt(parameter);
		updateAssignedGrid(pID);
		updateUnassignedGrid(pID);
	}
	
	private void updateAssignedGrid(int pID) {
		List<AssignedDangerSubst> dangerSubst = dangerSubstM.getAssignedDangerSubst(pID);
		dangerSubst.sort(Comparator.comparing(DangerSubst::getName));
        assignedGrid.setItems(dangerSubst);
	}
	
	private void updateAssignedGridByName(int pID, String name) {
		List<AssignedDangerSubst> dangerSubst = dangerSubstM.getAssignedDangerSubstByName(pID, name);
		dangerSubst.sort(Comparator.comparing(DangerSubst::getName));
        assignedGrid.setItems(dangerSubst);
	}
	
	private void updateUnassignedGrid(int pID) {
		List<DangerSubst> dangerSubst = dangerSubstM.getUnassignedDangerSubst(pID);
		dangerSubst.sort(Comparator.comparing(DangerSubst::getName));
        unassignedGrid.setItems(dangerSubst);
	}
	
	private void updateUnassignedGridByName(int pID, String name) {
		List<DangerSubst> dangerSubst = dangerSubstM.getUnassignedDangerSubstByName(pID, name);
		dangerSubst.sort(Comparator.comparing(DangerSubst::getName));
        unassignedGrid.setItems(dangerSubst);
	}
	
	private void searchPressed() {
		String tabName = searchTabs.getSelectedTab().getLabel();
		String searchTxt = tfSearch.getValue();
		switch(tabName) {
			case "Zugewiesen":
				updateAssignedGridByName(pID, searchTxt);
				break;
			case "Nicht Zugewiesen":
				updateUnassignedGridByName(pID, searchTxt);	
				break;
		}
	}
	
	private void backButtonPressed() {
		UI.getCurrent().navigate("PersonManagementView");
	}
	
	private String getDangerSubstTxt(int pID)
	{
		String res = "";
		List<AssignedDangerSubst> dangerSubsts = dangerSubstM.getAssignedDangerSubst(pID);
		dangerSubsts.sort(Comparator.comparing(DangerSubst::getName));
		if(!dangerSubsts.isEmpty()) {
			res = "Gefahrstoffe mit denen gearbeitet wird:";
			for(AssignedDangerSubst dangerSubst : dangerSubsts) {
				String name = dangerSubst.getName();
				res = res.concat("\n-"+name);
			}
		}
		return res;
	}
}
