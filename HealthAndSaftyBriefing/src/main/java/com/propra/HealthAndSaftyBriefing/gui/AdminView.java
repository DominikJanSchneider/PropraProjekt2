package com.propra.HealthAndSaftyBriefing.gui;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.propra.HealthAndSaftyBriefing.authentication.AccessControlFactory;
import com.propra.HealthAndSaftyBriefing.backend.data.Person;
import com.propra.HealthAndSaftyBriefing.database.DBConnector;
import com.propra.HealthAndSaftyBriefing.database.DBConverter;
import com.propra.HealthAndSaftyBriefing.database.DBExporter;
import com.propra.HealthAndSaftyBriefing.database.DBUploader;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tabs.SelectedChangeEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.UploadI18N;
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
	private Dialog settingsDialog;
	private Select<String> selDatabase;
	
	private DBUploader dbUploader = new DBUploader();
	private DBExporter dbExporter = new DBExporter();
	
	
	public AdminView() {

	    personView = new PersonView();
	    deviceView = new DeviceView();
	    dangerSubstView = new DangerSubstView();
	    roomsView = new RoomsView();
	    
	    Button btnLogout = new Button("Logout");
		btnLogout.setIcon(VaadinIcon.SIGN_OUT.create());
		btnLogout.getElement().getStyle().set("margin-left", "auto");
		btnLogout.addClickListener(e -> logout());
		btnLogout.addClickShortcut(Key.KEY_L, KeyModifier.CONTROL);
	    add(btnLogout);
	  	
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
		personTab.addComponentAsFirst(VaadinIcon.CHILD.create());
		Div personPage = new Div(personView);
			
		//deviceTab
		Tab deviceTab = new Tab("Ger\u00e4te");
		deviceTab.addComponentAsFirst(VaadinIcon.AUTOMATION.create());
		Div devicePage = new Div(deviceView);
		devicePage.setVisible(false);
			
		//roomsTab
		Tab roomsTab = new Tab("R\u00e4ume");
		roomsTab.addComponentAsFirst(VaadinIcon.HOME.create());
		Div roomsPage = new Div(roomsView);
		roomsPage.setVisible(false);
			
		//dangerSubstTab
		Tab dangerSubstTab = new Tab("Gefahrstoffe");
		dangerSubstTab.addComponentAsFirst(VaadinIcon.BOMB.create());
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
		fileMenu.addComponentAsFirst(VaadinIcon.FOLDER.create());
		printMenu = menuBar.addItem("Drucken", e -> printPressed());
		printMenu.addComponentAsFirst(VaadinIcon.PRINT.create());
		MenuItem editUserMenu = menuBar.addItem("Benutzerverwaltung", e -> editUserPressed());
		editUserMenu.addComponentAsFirst(VaadinIcon.GROUP.create());
		SubMenu fileSubMenu = fileMenu.getSubMenu();
		// Creating Download Database item
		Anchor downloadLink = new Anchor(dbExporter.getResource(), "Datenbank Herunterladen");
		downloadLink.getElement().setAttribute("download", true);
		MenuItem saveMenu = fileSubMenu.addItem(downloadLink);
		// Creating Export As CSV item
		downloadLink = new Anchor(dbExporter.getCSVResource(), "Datenbank als CSV Exportieren");
		downloadLink.getElement().setAttribute("download", true);
		MenuItem exportMenu = fileSubMenu.addItem(downloadLink);
		exportMenu.addClickListener(e -> DBConverter.convertToCSV());
		// Creating Import Database item
		Upload upload = new Upload(dbUploader);
		upload.setI18n(createGermanI18N());
		upload.setAcceptedFileTypes(".db");
		upload.setSizeFull();
		MenuItem importMenu = fileSubMenu.addItem(upload);
		MenuItem settingsMenu = menuBar.addItem("Einstellungen", e -> settingsPressed());
		settingsMenu.addComponentAsFirst(VaadinIcon.COG.create()); //COG or SLIDERS
	}
	
	private void configureSettingsMenu() {
		 settingsDialog = new Dialog();
		 settingsDialog.setCloseOnEsc(true);
		 settingsDialog.setCloseOnOutsideClick(true);
		 
		 VerticalLayout dialogLayout = new VerticalLayout();
		 
		 Label lblHeader = new Label("Einstellungen");
		 Label lblSpace = new Label("");
		 Label lblSelectDB = new Label("Wählen Sie die zu ladende Datenbank aus");
		 
		 selDatabase = new Select<String>(dbUploader.getUploadedFiles());
		 selDatabase.setSizeFull();
		 
		 Button btnLoadDB = new Button("Datenbank Laden");
		 btnLoadDB.addClickListener(e -> loadDBPressed());
		 
		 Button btnLoadDefaultDB = new Button("Standarddatenbank Laden");
		 btnLoadDefaultDB.addClickListener(e -> loadDefaultDBPressed());
		 
		 Button btnDeleteDB = new Button("Datenbank Löschen");
		 btnDeleteDB.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
		 btnDeleteDB.setIcon(VaadinIcon.MINUS_CIRCLE.create());
		 btnDeleteDB.addClickListener(e -> deleteDBPressed());
		 
		 dialogLayout.add(lblHeader, lblSpace, lblSelectDB, selDatabase, btnLoadDB, btnLoadDefaultDB, btnDeleteDB);
		 settingsDialog.add(dialogLayout);
		 
		 settingsDialog.open();
	}
	
	private void deleteDBPressed() {
		String selFile = selDatabase.getValue();
		if (selFile == null) {
			Notification.show("Bitte w\u00e4hlen Sie eine Datenbank aus.");
			return;
		}
		File file = new File("src/main/resources/upload/"+selFile);
		if (file.delete()) {
			Notification.show("Ausgewählte Datenbank gelöscht.");
		} else {
			Notification.show("Fehler beim löschen der Datenbank!");
		}
		settingsDialog.close();
	}
	
	private void loadDBPressed() {
		String selFile = selDatabase.getValue();
		if (selFile == null) {
			Notification.show("Bitte w\u00e4hlen Sie eine Datenbank aus.");
			return;
		}
		DBConnector.setURLCore("jdbc:sqlite:src/main/resources/upload/"+selFile);
		personView.reloadGrid();
		deviceView.reloadGrid();
		dangerSubstView.reloadGrid();
		roomsView.reloadGrid();
		settingsDialog.close();
	}
	
	private void loadDefaultDBPressed() {
		DBConnector.setURLCore("jdbc:sqlite:src/main/resources/database/CoreDatabase.db");
		personView.reloadGrid();
		deviceView.reloadGrid();
		dangerSubstView.reloadGrid();
		roomsView.reloadGrid();
		settingsDialog.close();
	}

	private void settingsPressed() {
		configureSettingsMenu();
	}

	private void editUserPressed() {
		UI.getCurrent().navigate("UserManagementView");
	}
	
	private void logout() {
        AccessControlFactory.getInstance().createAccessControl().signOut();
    }

	private void printPressed() {
		Person person = personView.getSelectedPerson();
		if(person != null) {
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
	
	// Translating the upload to german
	private UploadI18N createGermanI18N() {
		final UploadI18N i18n = new UploadI18N();
		
		i18n.setDropFiles(new UploadI18N.DropFiles().setOne("Datenbank hierher ziehen")
													.setMany("Datenbanken hierher ziehen"))
										.setAddFiles(new UploadI18N.AddFiles()
													.setOne("Datenbank Hochladen")
													.setMany("Datenbanken Hochladen"))
										.setCancel("Abbrechen")
										.setError(new UploadI18N.Error()
													.setTooManyFiles("Zu viele Datenbanken ausgewählt!")
													.setFileIsTooBig("Die Datenbank ist zu groß!")
													.setIncorrectFileType("Falscher Dateityp. Bitte eine .db Datei hochladen!"))
										.setUploading(new UploadI18N.Uploading()
													.setStatus(new UploadI18N.Uploading.Status()
															.setConnecting("Verbindet...")
															.setStalled("Wartet...")
															.setProcessing("Verarbeitet"))
													.setRemainingTime(new UploadI18N.Uploading.RemainingTime()
															.setPrefix("Verbleibende Zeit: ")
															.setUnknown("Unbekannt"))
													.setError(new UploadI18N.Uploading.Error()
															.setServerUnavailable("Server nicht erreichbar!")
															.setUnexpectedServerError("Unerwarteter Serverfehler")
															.setForbidden("Aktion nicht möglich")))
										.setUnits(Stream.of("B", "kB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB")
												.collect(Collectors.toList()));
		
		return i18n;
	}
}
