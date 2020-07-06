package com.propra.HealthAndSaftyBriefing;


import com.propra.HealthAndSaftyBriefing.authentication.AccessControl;
import com.propra.HealthAndSaftyBriefing.authentication.AccessControlFactory;
import com.propra.HealthAndSaftyBriefing.gui.AdminView;
import com.propra.HealthAndSaftyBriefing.gui.LoginView;
import com.propra.HealthAndSaftyBriefing.gui.UserView;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

@SuppressWarnings("serial")
public class HealthAndSaftyBriefingInitListener implements VaadinServiceInitListener {
	
	@Override
	public void serviceInit(ServiceInitEvent initEvent) {
		final AccessControl accessControl = AccessControlFactory.getInstance()
                .createAccessControl();

        initEvent.getSource().addUIInitListener(uiInitEvent -> {
            uiInitEvent.getUI().addBeforeEnterListener(enterEvent -> {
            	
                if (!accessControl.isUserSignedIn() && !LoginView.class
                        .equals(enterEvent.getNavigationTarget())) {
                    enterEvent.rerouteTo(LoginView.class);
                }
                
                if(accessControl.isUserSignedIn() && !accessControl.isUserAdmin() && AdminView.class
                		.equals(enterEvent.getNavigationTarget())) {
                	enterEvent.rerouteTo(LoginView.class);
                }
                
                if(accessControl.isUserSignedIn() && accessControl.isUserAdmin() && UserView.class
                		.equals(enterEvent.getNavigationTarget())) {
                	enterEvent.rerouteTo(LoginView.class);
                }
            });
        });
	}
}
