package com.googlecode.gwtmapquest.jsapi;


public final class MQSession extends MQObject {

	public static native MQSession newInstance() /*-{
		return new $wnd.MQSession();
	}-*/;
	
	protected MQSession() {
	}

}
