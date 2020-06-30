package com.propra.HealthAndSaftyBriefing.gui;

import java.util.List;

import com.propra.HealthAndSaftyBriefing.DangerSubst;
import com.propra.HealthAndSaftyBriefing.DangerSubstManager;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

@SuppressWarnings("serial")
public class DangerSubstView extends  VerticalLayout {
	private Grid<DangerSubst> dangerSubstGrid;
	private DangerSubstManager dangerSubstM;
	private TextField searchTF;
	private Button searchButton;
	
	DangerSubstView() {
		dangerSubstM = new DangerSubstManager();
		
		//Building searchComponents
		Component searchComponents = configureSearchComponents();
		add(searchComponents);
		//Building the dangerSubstGrid
		configureDangerSubstGrid();
        add(dangerSubstGrid);
        updateDangerSubstGrid();
	}
	
	private void configureDangerSubstGrid() {
		dangerSubstGrid = new Grid<>();
		
		dangerSubstGrid.addColumn(DangerSubst::getName)
        			.setHeader("Gefahrstoffe")
        			.setKey("dangerSubst")
        			.setSortable(true);
	}
	
	private void updateDangerSubstGrid() {
		List<DangerSubst> dangerSubst = dangerSubstM.getDangerSubstsData();
        dangerSubstGrid.setItems(dangerSubst);
	}
	
	private Component configureSearchComponents() {
		searchTF = new TextField();
		searchButton = new Button("Suchen");
		searchButton.addClickListener(e -> searchPressed());
		searchTF.setWidth("200px");
		searchTF.setPlaceholder("Suche");
		return new VerticalLayout(searchTF, searchButton);
	}

	private void searchPressed() {
		// TODO Auto-generated method stub
	}
}
