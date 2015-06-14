package edu.ubbcluj.web.user;

import java.util.List;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

@JavaScript({"mylib.js"})

public class JsConnecter extends AbstractJavaScriptComponent {
	
	private static final long serialVersionUID = 1L;

	public JsConnecter(String rtype, String stype,String name,Float lat, Float lng,
			List<String> sa,List<Float> lats,List<Float> lngs,int size, String act){

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
		

		
	}

	@Override
	protected UserMessageState getState() {		
		return (UserMessageState) super.getState();
	}
	
}
