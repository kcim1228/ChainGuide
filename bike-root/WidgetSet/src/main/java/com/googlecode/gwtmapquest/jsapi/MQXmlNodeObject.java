package com.googlecode.gwtmapquest.jsapi;

public class MQXmlNodeObject extends MQObject {

	public static native MQXmlNodeObject newInstance(String name, String value) /*-{
		return new $wnd.MQXmlNodeObject(name, value);
	}-*/;
	
	protected MQXmlNodeObject() {
	}
	
}
