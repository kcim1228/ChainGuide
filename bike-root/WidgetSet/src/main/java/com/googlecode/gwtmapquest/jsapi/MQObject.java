package com.googlecode.gwtmapquest.jsapi;

import com.google.gwt.core.client.JavaScriptObject;

public abstract class MQObject extends JavaScriptObject {

	protected MQObject() {
	}
	
	/**
	 * Build an xml string that represents this object.
	 * @return The xml string.
	 * @type String
	 *
	 */
	public final native String saveXml() /*-{
		return this.saveXml();
	}-*/;
	
}
