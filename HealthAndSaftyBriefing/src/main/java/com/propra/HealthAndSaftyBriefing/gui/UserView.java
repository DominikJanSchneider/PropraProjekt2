package com.propra.HealthAndSaftyBriefing.gui;


import com.propra.HealthAndSaftyBriefing.UserManager;
import com.propra.HealthAndSaftyBriefing.authentication.AccessControl;
import com.propra.HealthAndSaftyBriefing.authentication.AccessControlFactory;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("UserView")
@PageTitle("User")
public class UserView extends VerticalLayout {
	
	private UserManager userM = new UserManager();
	private AccessControl accessControl; 
	
	public UserView() {
		
		Button btnLogout = new Button("Logout");
		btnLogout.setIcon(VaadinIcon.SIGN_OUT.create());
		btnLogout.getElement().getStyle().set("margin-left", "auto");
		btnLogout.addClickListener(e -> logout());
		add(btnLogout);
		
		VerticalLayout userInfo = configureUserInfo();
		add(configureUserInfo());
		
	}
	
	private VerticalLayout configureUserInfo() {
		accessControl = AccessControlFactory.getInstance().createAccessControl();
		String[] userData = userM.getUserData(accessControl.getPrincipalName());
		
		VerticalLayout userInfo = new VerticalLayout();
		
		Label lblUser = new Label("Benutzer: "+ userData[1]+" "+userData[0]);
		lblUser.setHeight("50px");
		
		// Creating horzintal layout where user informations are stored
		HorizontalLayout userInfoHead = new HorizontalLayout();
		Label lblInstructionDate = new Label("Unterweisungsdatum");
		lblInstructionDate.setWidth("200px");
		Label lblIfwt = new Label("Ifwt");
		lblIfwt.setWidth("100px");
		Label lblMnaf = new Label("MNaF");
		lblMnaf.setWidth("100px");
		Label lblIntern = new Label("Intern");
		lblIntern.setWidth("100px");
		Label lblEmploymentType = new Label("Besch\u00e4ftigungsverh\u00e4ltnis");
		lblEmploymentType.setWidth("200px");
		Label lblBegin = new Label("Beginn");
		lblBegin.setWidth("100px");
		Label lblEnd = new Label("Ende");
		lblEnd.setWidth("100px");
		Label lblExtern = new Label("Extern");
		lblExtern.setWidth("200px");
		Label lblEmail = new Label("E-Mail Adresse");
		lblEmail.setWidth("250px");
		userInfoHead.add(lblInstructionDate, lblIfwt, lblMnaf, lblIntern, lblEmploymentType, lblBegin, lblEnd, lblExtern, lblEmail);
		
		HorizontalLayout userInfoContent = new HorizontalLayout();
		lblInstructionDate = new Label(userData[2]);
		lblInstructionDate.setWidth("200px");
		lblIfwt = new Label(userData[3]);
		lblIfwt.setWidth("100px");
		lblMnaf = new Label(userData[4]);
		lblMnaf.setWidth("100px");
		lblIntern = new Label(userData[5]);
		lblIntern.setWidth("100px");
		lblEmploymentType = new Label(userData[6]);
		lblEmploymentType.setWidth("200px");
		lblBegin = new Label(userData[7]);
		lblBegin.setWidth("100px");
		lblEnd = new Label(userData[8]);
		lblEnd.setWidth("100px");
		lblExtern = new Label(userData[9]);
		lblExtern.setWidth("200px");
		lblEmail = new Label(userData[10]);
		lblEmail.setWidth("300px");
		userInfoContent.add(lblInstructionDate, lblIfwt, lblMnaf, lblIntern, lblEmploymentType, lblBegin, lblEnd, lblExtern, lblEmail);
		
		Label lblSpace = new Label("");
		lblSpace.setHeight("50px");
		
		Label lblGeneralInstruction = new Label("Allgemeine Unterweisungen");
		lblGeneralInstruction.setWidth("400px");
		Label lblGeneralInstructionContent = new Label(userData[11]);
		lblGeneralInstructionContent.setWidth("400px");
		
		userInfo.add(lblUser, userInfoHead, userInfoContent, lblSpace, lblGeneralInstruction, lblGeneralInstructionContent);
		
		return userInfo;
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
}
