package com.googlecode.gwtmapquest.jsapi;

import com.google.gwt.core.client.JavaScriptObject;

public final class MQTrekRoute extends JavaScriptObject {

	protected MQTrekRoute() {
	}

	public native MQManeuverCollection getManeuvers() /*-{
		return this.getManeuvers();
	}-*/;	
	
}
