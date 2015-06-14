package edu.ubbcluj.web.admin;


import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

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
