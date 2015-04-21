package com.googlecode.gwtmapquest.transaction;

import com.google.gwt.core.client.JavaScriptObject;

public class MQAImageLevel extends JavaScriptObject {

	public static native MQAImageLevel newInstance(String url, int zoomLevel)/*-{
		return new $wnd.MQA.ImageLevel(url, zoomLevel);
	}-*/;
	
	protected MQAImageLevel() {
	}

}
