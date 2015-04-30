package edu.ubbcluj.web;

import java.util.List;

import com.vaadin.annotations.JavaScript;
import com.vaadin.cdi.UIScoped;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.JavaScriptComponentState;
import com.vaadin.ui.AbstractJavaScriptComponent;

@JavaScript({"mylib.js"})

public class JsConnecter extends AbstractJavaScriptComponent {
	
	private static final long serialVersionUID = 1L;

	public JsConnecter(String rtype, String stype,String name,Float lat, Float lng,
			List<String> sa,List<Float> lats,List<Float> lngs,int size, String act){
		//System.out.println(szoveg);
		getState().routeType = rtype;
		getState().searchType = stype;
		getState().serviceLat = lat;
		getState().serviceLng = lng;
		getState().serviceName = name;
		getState().allNames = sa;
		getState().allLat = lats;
		getState().allLng = lngs;
		getState().allSize = size;
		getState().action = act;
		
		System.out.println(getState().allNames.toString());
		
	}

	@Override
	protected UserMessageState getState() {		
		return (UserMessageState) super.getState();
	}
	
}
