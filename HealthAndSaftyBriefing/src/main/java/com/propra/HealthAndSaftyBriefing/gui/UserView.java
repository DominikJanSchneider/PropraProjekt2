package com.propra.HealthAndSaftyBriefing.gui;


import com.propra.HealthAndSaftyBriefing.UserManager;
import com.propra.HealthAndSaftyBriefing.authentication.AccessControl;
import com.propra.HealthAndSaftyBriefing.authentication.AccessControlFactory;
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
		Label lblUser = new Label("Benutzer: "+ userM.getUsersName(accessControl.getPrincipalName()));
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
		lblInstructionDate = new Label(userM.getUserData(accessControl.getPrincipalName())[2]);
		lblInstructionDate.setWidth("200px");
		lblIfwt = new Label(userM.getUserData(accessControl.getPrincipalName())[3]);
		lblIfwt.setWidth("100px");
		lblMnaf = new Label(userM.getUserData(accessControl.getPrincipalName())[4]);
		lblMnaf.setWidth("100px");
		lblIntern = new Label(userM.getUserData(accessControl.getPrincipalName())[5]);
		lblIntern.setWidth("100px");
		lblEmploymentType = new Label(userM.getUserData(accessControl.getPrincipalName())[6]);
		lblEmploymentType.setWidth("200px");
		lblBegin = new Label(userM.getUserData(accessControl.getPrincipalName())[7]);
		lblBegin.setWidth("100px");
		lblEnd = new Label(userM.getUserData(accessControl.getPrincipalName())[8]);
		lblEnd.setWidth("100px");
		lblExtern = new Label(userM.getUserData(accessControl.getPrincipalName())[9]);
		lblExtern.setWidth("200px");
		lblEmail = new Label(userM.getUserData(accessControl.getPrincipalName())[10]);
		lblEmail.setWidth("250px");
		userInfo.add(lblInstructionDate, lblIfwt, lblMnaf, lblIntern, lblEmploymentType, lblBegin, lblEnd, lblExtern, lblEmail);
		add(userInfo);
	}
}
