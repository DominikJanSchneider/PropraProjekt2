package com.propra.HealthAndSaftyBriefing;


import com.propra.HealthAndSaftyBriefing.authentication.AccessControl;
import com.propra.HealthAndSaftyBriefing.authentication.AccessControlFactory;
import com.propra.HealthAndSaftyBriefing.gui.LoginView;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

public class HealthAndSaftyBriefingInitListener implements VaadinServiceInitListener {
	
	@Override
	public void serviceInit(ServiceInitEvent initEvent) {
		final AccessControl accessControl = AccessControlFactory.getInstance()
                .createAccessControl();

        initEvent.getSource().addUIInitListener(uiInitEvent -> {
            uiInitEvent.getUI().addBeforeEnterListener(enterEvent -> {
                if (!accessControl.isUserSignedIn() && !LoginView.class
                        .equals(enterEvent.getNavigationTarget()))
                	System.out.println("LISTENER DUH");
                    enterEvent.rerouteTo(LoginView.class);
            });
        });
	}
}
