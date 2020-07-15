package com.propra.HealthAndSaftyBriefing.gui;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import com.propra.HealthAndSaftyBriefing.backend.DangerSubstManager;
import com.propra.HealthAndSaftyBriefing.backend.data.DangerSubst;
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
public class DangerSubstView extends  VerticalLayout {
	private Grid<DangerSubst> dangerSubstGrid;
	private DangerSubstManager dangerSubstM;
	private TextField tfSearch;
	private Button btnSearch;
	private Button btnAddDangerSubst;
	private Button btnDeleteDangerSubst;
	private ShortcutRegistration shortReg;
	private DangerSubstAddForm addForm;
	private DangerSubstEditForm editForm;
	
	DangerSubstView() {
		dangerSubstM = new DangerSubstManager();
		addForm = new DangerSubstAddForm(this, dangerSubstM);
		editForm = new DangerSubstEditForm(this, dangerSubstM);
		addForm.setSizeFull();
		addForm.setVisible(false);
		editForm.setSizeFull();
		editForm.setVisible(false);
		
		//Building searchComponents
		Component searchComponents = configureSearchComponents();
		
		//Create Buttons and their clicklistener
		
		//btnAddDangerSubst
		btnAddDangerSubst = new Button("Neuen Gefahrstoff anlegen");
		btnAddDangerSubst.setIcon(VaadinIcon.PLUS_CIRCLE.create());
		btnAddDangerSubst.addClickListener(e -> {
			editForm.setVisible(false);
			addForm.setVisible(true);
			addForm.setDangerSubst("");
		});
		
		//btnDeleteDangerSubst
		btnDeleteDangerSubst = new Button("Gefahrstoff löschen", e -> {
			SingleSelect<Grid<DangerSubst>, DangerSubst> selectedDangerSubst = dangerSubstGrid.asSingleSelect();
			if (!selectedDangerSubst.isEmpty()) {
				dangerSubstM.deleteDangerSubst(selectedDangerSubst.getValue().getName());
				Notification.show("Gefahrstoff wurde aus der Datenbank entfernt!");
				dangerSubstGrid.deselectAll();
				updateDangerSubstGrid();
				editForm.setVisible(false);
			}
			else {
				Notification.show("Wählen Sie einen Gefahrstoff aus, um ihn zu löschen!");
			}
		});
		btnDeleteDangerSubst.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
		btnDeleteDangerSubst.setIcon(VaadinIcon.MINUS_CIRCLE.create());
		
		//Building the dangerSubstGrid
		configureDangerSubstGrid();
        //add(dangerSubstGrid);
        updateDangerSubstGrid();
        
        add(searchComponents, new HorizontalLayout(btnAddDangerSubst, btnDeleteDangerSubst), dangerSubstGrid, addForm, editForm);
	}
	
	private void configureDangerSubstGrid() {
		dangerSubstGrid = new Grid<>();
		
		dangerSubstGrid.addColumn(DangerSubst::getName)
        			.setHeader("Gefahrstoffe")
        			.setKey("dangerSubst")
        			.setSortable(true);
		 dangerSubstGrid.addSelectionListener(new SelectionListener<Grid<DangerSubst>,DangerSubst>() {

				@Override
				public void selectionChange(SelectionEvent<Grid<DangerSubst>,DangerSubst> event) {
					if(event.isFromClient()) {
						showEditForm();
					}
				}
	        	
	        });
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

	private void searchPressed() {
		String searchTxt = tfSearch.getValue();
		updateDangerSubstGridByName(searchTxt);
	}
	
	public void reloadGrid() {
		List<DangerSubst> dangerSubst = dangerSubstM.getDangerSubstsData();
		dangerSubstGrid.setItems(dangerSubst);
	}
	
	private void showEditForm() {
		SingleSelect<Grid<DangerSubst>, DangerSubst> selectedDangerSubst = dangerSubstGrid.asSingleSelect();
		if (selectedDangerSubst.isEmpty()) {
			return;
		}
		addForm.setVisible(false);
		editForm.setVisible(true);
		editForm.setDangerSubst(selectedDangerSubst.getValue().getName());
	}
	
	class DangerSubstEditForm extends FormLayout {
		private TextField tfDangerSubst;
		
		public DangerSubstEditForm(DangerSubstView dsView, DangerSubstManager dsManager) {
			this.setVisible(true);
			tfDangerSubst = new TextField("Gefahrstoff");
			
			Button btnSave = new Button("Speichern", e -> {
				if (!tfDangerSubst.isEmpty()) {
					try {
						SingleSelect<Grid<DangerSubst>, DangerSubst> selectedDangerSubst = dangerSubstGrid.asSingleSelect();
						String oldName = selectedDangerSubst.getValue().getName();
						dsManager.editDangerSubst(oldName,tfDangerSubst.getValue());
						Notification.show("Gefahrstoff wurde erfolgreich bearbeitet");
					} catch (NoSuchAlgorithmException ex) {
						ex.printStackTrace();
					}
					dsView.updateDangerSubstGrid();
				} else {
					Notification.show("Bitte geben Sie einen Gefahrstoff ein!");
				}
			});
			btnSave.setIcon(VaadinIcon.ADD_DOCK.create());
			Button btnClose = new Button("Schließen", e -> this.setVisible(false));
			btnClose.setIcon(VaadinIcon.CLOSE_CIRCLE.create());
			add(tfDangerSubst, new HorizontalLayout(btnSave, btnClose));
		}
		
		public void setDangerSubst(String name) {
			tfDangerSubst.setValue(name);
		}
	}
	
	class DangerSubstAddForm extends FormLayout {
		private TextField tfDangerSubst;
		
		public DangerSubstAddForm(DangerSubstView dsView, DangerSubstManager dsManager) {
			this.setVisible(true);
			tfDangerSubst = new TextField("Gefahrstoff");
			
			Button btnSave = new Button("Speichern", e -> {
				if (!tfDangerSubst.isEmpty()) {
					try {
						if (dsManager.existsDangerSubst(tfDangerSubst.getValue())) {
							Notification.show("Gefahrstoff existiert bereits!");
						}
						else {
							dsManager.addDangerSubst(tfDangerSubst.getValue());
							Notification.show("Gefahrstoff wurde hinzugefügt!");
						}
					} catch (NoSuchAlgorithmException ex) {
						ex.printStackTrace();
					}
					dsView.updateDangerSubstGrid();
				} else {
					Notification.show("Bitte geben Sie einen Gefahrstoff ein!");
				}
			});
			btnSave.setIcon(VaadinIcon.ADD_DOCK.create());
			Button btnClose = new Button("Schließen", e -> this.setVisible(false));
			btnClose.setIcon(VaadinIcon.CLOSE_CIRCLE.create());
			add(tfDangerSubst, new HorizontalLayout(btnSave, btnClose));
		}
		
		public void setDangerSubst(String name) {
			tfDangerSubst.setValue(name);
		}
	}
}
