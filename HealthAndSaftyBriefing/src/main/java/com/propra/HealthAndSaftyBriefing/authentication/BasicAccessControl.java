package com.propra.HealthAndSaftyBriefing.authentication;

import com.propra.HealthAndSaftyBriefing.UserManager;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;

@SuppressWarnings("serial")
public class BasicAccessControl implements AccessControl {
	
	private UserManager userM = new UserManager();
	
	@Override
	public boolean signIn(String username, String password) {
		
		if (userM.checkUser(username, password)) {
			CurrentUser.set(username);
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean isUserSignedIn() {
		return !CurrentUser.get().isEmpty();
	}
	
	@Override
	public boolean isUserAdmin() {
		if (userM.getRole().equals("admin")) {
			//Only the admin user is in the admin role
			return true;
		}
		
		// All users are in all non-admin roles
		return false;
	}
	
	@Override
	public String getPrincipalName() {
		return CurrentUser.get();
	}
	
	@Override
	public void signOut() {
		VaadinSession.getCurrent().getSession().invalidate();
		UI.getCurrent().navigate("LoginView");
	}
}
