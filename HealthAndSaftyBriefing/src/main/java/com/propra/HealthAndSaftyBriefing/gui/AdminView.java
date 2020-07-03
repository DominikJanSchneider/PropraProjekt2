package com.propra.HealthAndSaftyBriefing.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.propra.HealthAndSaftyBriefing.authentication.AccessControl;
import com.propra.HealthAndSaftyBriefing.authentication.AccessControlFactory;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;

@Route("AdminView")
@SuppressWarnings("serial")
public class AdminView extends VerticalLayout {
	
	private AccessControl accessControl; 
	
	private MenuBar menuBar;
	private Tabs tabs;
	private PersonView personView;
	private DeviceView deviceView;
	private RoomsView roomsView;
	private DangerSubstView dangerSubstView;
	private Div pages;
	//private FormDocPrinter fPrinter; TODO
	
	public AdminView() {

	    personView = new PersonView();
	    deviceView = new DeviceView();
	    dangerSubstView = new DangerSubstView();
	    roomsView = new RoomsView();
	    
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
	}

	private void editDataPressed() {
		// TODO Auto-generated method stub
		System.out.println("Daten bearbeiten gedrückt");
	}

	private void editUserPressed() {
		// TODO Auto-generated method stub
		System.out.println("Benutzer verwaltung gedrückt");
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

	@SuppressWarnings("deprecation")
	private void printPressed() {
		Page page = UI.getCurrent().getPage();
		page.executeJavaScript("print();");
			//TODO Auto-generated method stub
			System.out.println("Drucken gedrückt");
//			Set<Person> personSet = personView.getSelectedPerson();
//			Iterator<Person> it = personSet.iterator();
//			Person person = it.next();
//			
//			String lName = person.getLName();
//			String fName = person.getFName();
//			String date = person.getDate();
//			String ifwt = person.getIfwt();
//			String mnaf = person.getMNaF();
//			String intern = person.getIntern();
//			String extern = person.getExtern();
//			//TODO 
////			String genInstr = taGeneralInstruction.getText();
////			String labSetup = taLabSetup.getText();
////			String dangerSubst = taDangerSubst.getText();
//			
//			//setup of printData
//			PrintData printData = new PrintData(
//						lName,
//						fName,
//						date,
//						ifwt,
//						mnaf,
//						intern,
//						extern,
//						"test",
//						"test",
//						"test"
//					);
//			//start printing process
//			try {
//				fPrinter.print(printData);
//			} catch(IOException e) {
//				e.printStackTrace();
//			} catch(PrinterException e) {
//				e.printStackTrace();
//			}
			
		}
	
}
