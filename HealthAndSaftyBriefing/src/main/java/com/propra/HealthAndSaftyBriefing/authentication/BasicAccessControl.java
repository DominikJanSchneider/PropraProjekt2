package com.propra.HealthAndSaftyBriefing.authentication;

import com.propra.HealthAndSaftyBriefing.UserManager;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;

public class BasicAccessControl implements AccessControl {
	
	private UserManager userM = new UserManager();
	
	@Override
	public boolean signIn(String username, String password) {
		
		if (username == null || username.isEmpty()) {
			return false;
		}
		
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
	public boolean isUserInRole(String role) {
		if ("admin".equals(role)) {
			//Only the admin user is in the admin role
			return getPrincipalName().equals("admin");
		}
		
		// All users are in all non-admin roles
		return true;
	}
	
	@Override
	public String getPrincipalName() {
		return CurrentUser.get();
	}
	
	@Override
	public void signOut() {
		VaadinSession.getCurrent().getSession().invalidate();
		UI.getCurrent().navigate("");
	}
}