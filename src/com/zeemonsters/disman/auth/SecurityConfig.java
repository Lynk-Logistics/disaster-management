package com.zeemonsters.disman.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SecurityConfig {

	public static final String ROLE_VOLUNTEER = "VOLUNTEER";
//	public static final String ROLE_ADMIN = "ADMIN";

	private static final Map<String, List<String>> mapConfig = new HashMap<String, List<String>>();

	static {
		init();
	}

	private static void init() {
//		List<String> urlPatterns = new ArrayList<String>();
//
//		urlPatterns.add("/createPortal");
//		urlPatterns.add("/profile");
//		mapConfig.put(ROLE_ADMIN, urlPatterns);

		List<String> urlPatterns2 = new ArrayList<String>();

		urlPatterns2.add("/profile");
		urlPatterns2.add("/createPortal");
		

		mapConfig.put(ROLE_VOLUNTEER, urlPatterns2);
	}

	public static Set<String> getAllAppRoles() {
		return mapConfig.keySet();
	}

	public static List<String> getUrlPatternsForRole(String role) {
		return mapConfig.get(role);
	}

}