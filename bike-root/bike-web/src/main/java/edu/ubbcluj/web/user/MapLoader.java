package edu.ubbcluj.web.user;
import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

@JavaScript({"http://open.mapquestapi.com/sdk/js/v7.2.s/mqa.toolkit.js?key=Fmjtd%7Cluu82luanu%2C8x%3Do5-948xdz","mylib.js"})

public class MapLoader extends AbstractJavaScriptComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MapLoader(Float x, Float y){
		
		getState().latCoord = x;
		getState().lngCoord = y;
	}
	
	@Override
	protected MapState getState() {		
		return (MapState) super.getState();
	}
	
	
}
