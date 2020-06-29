package com.propra.HealthAndSaftyBriefing.gui;

import java.util.List;

import com.propra.HealthAndSaftyBriefing.DangerSubst;
import com.propra.HealthAndSaftyBriefing.DangerSubstManager;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@SuppressWarnings("serial")
public class DangerSubstView extends  VerticalLayout {
	private Grid<DangerSubst> dangerSubstGrid;
	private DangerSubstManager dangerSubstM;
	
	DangerSubstView() {
		dangerSubstM = new DangerSubstManager();
		
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
}
