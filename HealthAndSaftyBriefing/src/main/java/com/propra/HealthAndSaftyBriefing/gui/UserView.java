package com.propra.HealthAndSaftyBriefing.gui;


import com.propra.HealthAndSaftyBriefing.UserManager;
import com.propra.HealthAndSaftyBriefing.authentication.AccessControl;
import com.propra.HealthAndSaftyBriefing.authentication.AccessControlFactory;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.html.Label;
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
		accessControl = AccessControlFactory.getInstance().createAccessControl();
		String[] userData = userM.getUserData(accessControl.getPrincipalName());
		Label lblUser = new Label("Benutzer: "+ userData[1]+" "+userData[0]);
		lblUser.setHeight("75px");
		add(lblUser);
		
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
		add(userInfoHead);
		
		HorizontalLayout userInfo = new HorizontalLayout();
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
		lblEmail.setWidth("250px");
		userInfo.add(lblInstructionDate, lblIfwt, lblMnaf, lblIntern, lblEmploymentType, lblBegin, lblEnd, lblExtern, lblEmail);
		add(userInfo);
		
		Label lblSpace = new Label("");
		lblSpace.setHeight("50px");
		add(lblSpace);
		
		Label lblGeneralInstruction = new Label("Allgemeine Unterweisungen");
		Label lblGeneralInstructionContent = new Label(userData[11]);
		add(lblGeneralInstruction, lblGeneralInstructionContent);
		
		
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
