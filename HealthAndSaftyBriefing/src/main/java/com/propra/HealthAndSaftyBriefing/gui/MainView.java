package com.propra.HealthAndSaftyBriefing.gui;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

/**
 * The main view contains a button and a click listener.
 */
@SuppressWarnings("serial")
@Route("")
@PWA(name = "My Application", shortName = "My Application")
public class MainView extends VerticalLayout {

	private LoginView loginView;
	
    public MainView() {
    	//The ServiceInitListener makes this class / view useless
//    	loginView = new LoginView();
//    	add(loginView);
    }
}
