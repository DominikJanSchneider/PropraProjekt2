package com.propra.HealthAndSaftyBriefing.gui;

import com.propra.HealthAndSaftyBriefing.authentication.AccessControl;
import com.propra.HealthAndSaftyBriefing.authentication.AccessControlFactory;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("Login")
@PageTitle("Login")
@CssImport("./styles/shared-styles.css")
public class LoginView extends FlexLayout {
	
	private AccessControl accessControl; 
	//public static final String ROUTE = "login";
	
	public LoginView() {
		accessControl = AccessControlFactory.getInstance().createAccessControl();
		buildUI();
		
		
	}
	
	private void buildUI() {
		setSizeFull();
		setClassName("login-view");
		
		// login form, centered in the available part of the screen
		LoginForm loginForm = new LoginForm();
		loginForm.addLoginListener(this::login);
		loginForm.addForgotPasswordListener(event -> Notification.show("Read the man page lmao"));
		
		// layout to center login form when there is sufficient screen space
		FlexLayout centeringLayout = new FlexLayout();
		centeringLayout.setSizeFull();
		centeringLayout.setJustifyContentMode(JustifyContentMode.CENTER);
		centeringLayout.setAlignItems(Alignment.CENTER);
		centeringLayout.add(loginForm);
		
		add(centeringLayout);
	}
	
	private void login(LoginForm.LoginEvent event) {
		if (accessControl.signIn(event.getUsername(), event.getPassword()) && accessControl.isUserAdmin()) {
			getUI().get().navigate("AdminView");
		} else {
			getUI().get().navigate("UserView"); //TODO Change to UserView when is created
		}
	}
}
