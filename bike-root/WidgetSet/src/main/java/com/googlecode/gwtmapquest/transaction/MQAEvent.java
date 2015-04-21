package com.googlecode.gwtmapquest.transaction;

import com.google.gwt.core.client.JavaScriptObject;

public class MQAEvent extends JavaScriptObject {

	public static native MQAEvent newInstance() /*-{
		return new $wnd.MQA.Event();
	}-*/;
	
	protected MQAEvent() {
	}

}
