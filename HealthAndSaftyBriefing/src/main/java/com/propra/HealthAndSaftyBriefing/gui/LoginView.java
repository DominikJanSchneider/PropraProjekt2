package com.propra.HealthAndSaftyBriefing.gui;

import com.propra.HealthAndSaftyBriefing.authentication.AccessControl;
import com.propra.HealthAndSaftyBriefing.authentication.AccessControlFactory;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.StreamResource;

@PWA(name = "HealthAndSafetyBriefing", shortName = "HASB")
@SuppressWarnings("serial")
@Route("LoginView")
@PageTitle("Login | Sicherheitsunterweisungen")
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
		
		//logo and header
		StreamResource resource = new StreamResource("uni_logo.jpg", () -> getClass().getResourceAsStream("/images/uni_logo.jpg"));
		Image image = new Image(resource, "Universität Siegen Logo");
		image.setMaxWidth("100px");
		Label header = new Label("Sicherheitsunterweisung am Institut für Werkstofftechnik und Ger\u00e4tezentrum MNaF");
		header.addClassName("app-header");
		
		// login form, centered in the available part of the screen
		LoginForm loginForm = new LoginForm();
		loginForm.setI18n(createGermanI18n());
		loginForm.addLoginListener(this::login);
		loginForm.addForgotPasswordListener(event -> Notification.show("Bitte wenden Sie sich an Ihren Administrator."));
		
		// layout to center login form when there is sufficient screen space
		FlexLayout centeringLayout = new FlexLayout();
		centeringLayout.setSizeFull();
		centeringLayout.setJustifyContentMode(JustifyContentMode.CENTER);
		centeringLayout.setAlignItems(Alignment.CENTER);
		centeringLayout.add(loginForm);
		
		VerticalLayout components = new VerticalLayout(image, header, centeringLayout);
		components.setHorizontalComponentAlignment(Alignment.CENTER, image, header);
		add(components);
	}
	
	// Translating the login to the german language
	private LoginI18n createGermanI18n() {
	    final LoginI18n i18n = LoginI18n.createDefault();

	    i18n.setHeader(new LoginI18n.Header());
	    // i18n.getHeader().setTitle("Portal für Sicherheitsunterweisungen der Universität Siegen");
	    //i18n.getHeader().setDescription("Beschreibung");
	    i18n.getForm().setUsername("Benutzername");
	    i18n.getForm().setTitle("Login");
	    i18n.getForm().setSubmit("Anmelden");
	    i18n.getForm().setPassword("Passwort");
	    i18n.getForm().setForgotPassword("Passwort Vergessen?");
	    i18n.getErrorMessage().setTitle("Benutzername oder Passwort falsch");
	    i18n.getErrorMessage().setMessage("Bitte prüfen Sie Ihren Benutzernamen und Ihr Passwort nochmal.");
	    //i18n.setAdditionalInformation("");
	    
	    return i18n;
	}
	
	private void login(LoginForm.LoginEvent event) {
		if (accessControl.signIn(event.getUsername(), event.getPassword()) && accessControl.isUserAdmin()) {
			getUI().get().navigate("AdminView");
		} 
		else if (accessControl.signIn(event.getUsername(), event.getPassword()) && !accessControl.isUserAdmin()) {
			getUI().get().navigate("UserView"); 
		} 
		else {
			event.getSource().setError(true);
		}
	}
}
