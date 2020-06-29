package com.propra.HealthAndSaftyBriefing.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

/**
 * The main view contains a button and a click listener.
 */
@SuppressWarnings("serial")
@Route("")
@PWA(name = "My Application", shortName = "My Application")
public class MainView extends VerticalLayout {

	private MenuBar menuBar;
	private Tabs tabs;
	private PersonView personView;
	private LoginView loginView;
	
    public MainView() {
    	loginView = new LoginView();
    	//add(loginView);
    	personView = new PersonView();
    	
    	configureMenuBar();
    	configureTabs();
    }
    
	private void configureTabs() {
		//personTab
		Tab personTab = new Tab("Personen");
		Div personPage = new Div(personView);
		personPage.setSizeFull();
		
		//deviceTab
		Tab deviceTab = new Tab("Ger\u00e4te");
		Div devicePage = new Div();
		devicePage.setText("Geräte-Tab");
		devicePage.setVisible(false);
		
		//roomsTab
		Tab roomsTab = new Tab("Räume");
		Div roomsPage = new Div();
		roomsPage.setText("Räume-Tab");
		roomsPage.setVisible(false);
		
		//dangerSubstTab
		Tab dangerSubstTab = new Tab("Gefahrstoffe");
		Div dangerSubstPage = new Div();
		dangerSubstPage.setText("Gefahrstoff-Tab");
		dangerSubstPage.setVisible(false);

		Map<Tab, Component> tabsToPages = new HashMap<>();
		tabsToPages.put(personTab, personPage);
		tabsToPages.put(deviceTab, devicePage);
		tabsToPages.put(roomsTab, roomsPage);
		tabsToPages.put(dangerSubstTab, dangerSubstPage);
		tabs = new Tabs(personTab, deviceTab, roomsTab, dangerSubstTab);
		Div pages = new Div(personPage, devicePage, roomsPage, dangerSubstPage);
		pages.setSizeFull();
		Set<Component> pagesShown = Stream.of(personPage).collect(Collectors.toSet());
		
		tabs.addSelectedChangeListener(event -> {
		    pagesShown.forEach(page -> page.setVisible(false));
		    pagesShown.clear();
		    Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
		    selectedPage.setVisible(true);
		    pagesShown.add(selectedPage);
		});
		add(tabs);
		add(pages);
	}

	private void configureMenuBar() {
		menuBar = new MenuBar();
		MenuItem fileMenu = menuBar.addItem("Datei");
		MenuItem editDataMenu = menuBar.addItem("Daten bearbeiten", e -> editDataPressed());
		MenuItem printMenu = menuBar.addItem("Drucken", e -> printPressed());
		MenuItem editUserMenu = menuBar.addItem("Benutzer verwaltung", e -> editUserPressed());
		SubMenu fileSubMenu = fileMenu.getSubMenu();
		MenuItem saveMenu = fileSubMenu.addItem("Datenbank Speichern");
		MenuItem importMenu = fileSubMenu.addItem("Datenbank Importieren");
		MenuItem exportMenu = fileSubMenu.addItem("Datenbank als CSV exportieren");
		add(menuBar);
	}

	private void editDataPressed() {
		// TODO Auto-generated method stub
		System.out.println("Daten bearbeiten gedrückt");
	}

	private void editUserPressed() {
		// TODO Auto-generated method stub
		System.out.println("Benutzer verwaltung gedrückt");
	}

	private void printPressed() {
		//TODO Auto-generated method stub
		System.out.println("Drucken gedrückt");
	}

	
}
