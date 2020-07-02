package com.propra.HealthAndSaftyBriefing.gui;

import java.util.List;

import com.propra.HealthAndSaftyBriefing.DangerSubst;
import com.propra.HealthAndSaftyBriefing.DangerSubstManager;
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
public class DangerSubstView extends  VerticalLayout {
	private Grid<DangerSubst> dangerSubstGrid;
	private DangerSubstManager dangerSubstM;
	private TextField tfSearch;
	private Button btnSearch;
	private ShortcutRegistration shortReg;
	
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
	
	private void updateDangerSubstGridByName(String name) {
		List<DangerSubst> dangerSubst = dangerSubstM.getDangerSubstsByName(name);
        dangerSubstGrid.setItems(dangerSubst);
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

	private void searchPressed() {
		String searchTxt = tfSearch.getValue();
		updateDangerSubstGridByName(searchTxt);
	}
}
