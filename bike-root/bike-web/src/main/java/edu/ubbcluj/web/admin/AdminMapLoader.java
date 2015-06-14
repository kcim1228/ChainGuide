package edu.ubbcluj.web.admin;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

@JavaScript({"http://open.mapquestapi.com/sdk/js/v7.2.s/mqa.toolkit.js?key=Fmjtd%7Cluu82luanu%2C8x%3Do5-948xdz","admin.js"})

public class AdminMapLoader extends AbstractJavaScriptComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AdminMapLoader(Float x, Float y){
			
			getState().latCoord = x;
			getState().lngCoord = y;
		}
		
		@Override
		protected AdminMapState getState() {		
			return (AdminMapState) super.getState();
		}
		
}
