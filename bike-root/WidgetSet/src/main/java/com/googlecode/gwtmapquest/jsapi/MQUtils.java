package com.googlecode.gwtmapquest.jsapi;

import com.google.gwt.core.client.JavaScriptObject;

public class MQUtils {

	public static native JavaScriptObject mqGetNode(String xmlDoc, String xPath) /*-{
		return $wnd.mqGetNode(xmlDoc, xPath);
	}-*/;

	public static native String mqXmlToStr(JavaScriptObject xmlNode) /*-{
		return $wnd.mqXmlToStr(xmlNode);
	}-*/;
	
}
