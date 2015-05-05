package edu.ubbcluj.web;


import com.vaadin.annotations.JavaScript;
import com.vaadin.cdi.UIScoped;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.JavaScriptComponentState;
import com.vaadin.ui.AbstractJavaScriptComponent;

import edu.ubbcluj.backend.model.Services;

@JavaScript({"admin.js"})

public class AdminJsConnecter extends AbstractJavaScriptComponent {
	
	private static final long serialVersionUID = 1L;

	public AdminJsConnecter(String stype,String s,Float lat, Float lng,String action){
		getState().searchType = stype;
		getState().serviceName = s;
		getState().serviceLat = lat;
		getState().serviceLng = lng;
		getState().actionType = action;
	}

	@Override
	protected AdminMessageState getState() {		
		return (AdminMessageState) super.getState();
	}
	
}
