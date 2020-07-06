package com.propra.HealthAndSaftyBriefing.gui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.propra.HealthAndSaftyBriefing.Person;
import com.propra.HealthAndSaftyBriefing.authentication.AccessControlFactory;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tabs.SelectedChangeEvent;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("AdminView")
@PageTitle("Admin | Sicherheitsunterweisungen")
@SuppressWarnings("serial")
public class AdminView extends VerticalLayout implements HasUrlParameter<String> {
	private MenuBar menuBar;
	private Tabs tabs;
	private MenuItem printMenu;
	private PersonView personView;
	private DeviceView deviceView;
	private RoomsView roomsView;
	private DangerSubstView dangerSubstView;
	private Div pages;
	
	public AdminView() {

	    personView = new PersonView();
	    deviceView = new DeviceView();
	    dangerSubstView = new DangerSubstView();
	    roomsView = new RoomsView();
	    
	    Button btnLogout = new Button("Logout");
		btnLogout.setIcon(VaadinIcon.SIGN_OUT.create());
		btnLogout.getElement().getStyle().set("margin-left", "auto");
		btnLogout.addClickListener(e -> logout());
	    add(btnLogout);
	    
	    //Building header
	  	Label header = new Label("Sicherheitsunterweisung am Institut für Werkstofftechnik und Ger\u00e4tezentrum MNaF");
	  	add(header);
	  	
	    //MenuBar
	    configureMenuBar();
	    add(menuBar);
	    	
	   	//Tabs
	   	configureTabs();
	   	add(tabs);
		add(pages);
	}
	    
	private void configureTabs() {
		//personTab
		Tab personTab = new Tab("Personen");
		Div personPage = new Div(personView);
			
		//deviceTab
		Tab deviceTab = new Tab("Ger\u00e4te");
		Div devicePage = new Div(deviceView);
		devicePage.setVisible(false);
			
		//roomsTab
		Tab roomsTab = new Tab("R\u00e4ume");
		Div roomsPage = new Div(roomsView);
		roomsPage.setVisible(false);
			
		//dangerSubstTab
		Tab dangerSubstTab = new Tab("Gefahrstoffe");
		Div dangerSubstPage = new Div(dangerSubstView);
		dangerSubstPage.setVisible(false);

		Map<Tab, Component> tabsToPages = new HashMap<>();
		tabsToPages.put(personTab, personPage);
		tabsToPages.put(deviceTab, devicePage);
		tabsToPages.put(roomsTab, roomsPage);
		tabsToPages.put(dangerSubstTab, dangerSubstPage);
		tabs = new Tabs(personTab, deviceTab, roomsTab, dangerSubstTab);
		pages = new Div(personPage, devicePage, roomsPage, dangerSubstPage);
		pages.setSizeFull();
		Set<Component> pagesShown = Stream.of(personPage).collect(Collectors.toSet());
			
		tabs.addSelectedChangeListener(event -> {
			pagesShown.forEach(page -> page.setVisible(false));
			pagesShown.clear();
			Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
			selectedPage.setVisible(true);
			pagesShown.add(selectedPage);
		});
		tabs.addSelectedChangeListener(new ComponentEventListener<Tabs.SelectedChangeEvent>() {

			@Override
			public void onComponentEvent(SelectedChangeEvent event) {
				if(!event.getSelectedTab().getLabel().equals("Personen")) {
					printMenu.setEnabled(false);
				}
				else {
					printMenu.setEnabled(true);
				}
			}
			
		});
	}

	@SuppressWarnings("unused")
	private void configureMenuBar() {
		menuBar = new MenuBar();
		MenuItem fileMenu = menuBar.addItem("Datei");
		MenuItem editDataMenu = menuBar.addItem("Daten bearbeiten", e -> editDataPressed());
		printMenu = menuBar.addItem("Drucken", e -> printPressed());
		MenuItem editUserMenu = menuBar.addItem("Benutzerverwaltung", e -> editUserPressed());
		SubMenu fileSubMenu = fileMenu.getSubMenu();
		MenuItem saveMenu = fileSubMenu.addItem("Datenbank Speichern");
		MenuItem importMenu = fileSubMenu.addItem("Datenbank Importieren");
		MenuItem exportMenu = fileSubMenu.addItem("Datenbank als CSV exportieren");
	}

	private void editDataPressed() {
		// TODO Auto-generated method stub
		System.out.println("Daten bearbeiten gedrückt");
	}

	private void editUserPressed() {
		UI.getCurrent().navigate("UserManagementView");
	}
	
	private void logout() {
        AccessControlFactory.getInstance().createAccessControl().signOut();
    }
	
	@Override 
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		
		// User can quickly activate logout with Ctrl+L
		attachEvent.getUI().addShortcutListener(() -> logout(), Key.KEY_L, KeyModifier.CONTROL);
		
	}

	private void printPressed() {
		Set<Person> personSet = personView.getSelectedPerson();
		Iterator<Person> it = personSet.iterator();
		if(it.hasNext()) {
			Person person = it.next();
			UI.getCurrent().getPage().open("PrintView/"+person.getId());
		}
		else {
			Notification.show("Kein Eintrag Ausgewählt!");
		}
	}
	
	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		if(parameter != null) {
			if(parameter.equals("PersonTab")) {
				tabs.setSelectedIndex(0);
			}
			else if(parameter.equals("DeviceTab")) {
				tabs.setSelectedIndex(1);
			}
			else if(parameter.equals("RoomTab")) {
				tabs.setSelectedIndex(2);
			}
			else if(parameter.equals("DangerSubstTab")) {
				tabs.setSelectedIndex(3);
			}
		}
	}
}
