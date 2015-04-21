package com.googlecode.gwtmapquest.jsapi;


public final class MQTrekRouteCollection extends MQObjectCollection {

	public static native MQTrekRouteCollection newInstance() /*-{
		return new $wnd.MQTrekRouteCollection();
	}-*/;
		
	protected MQTrekRouteCollection() {
	}
	
	public final native MQTrekRoute get(int i) /*-{
		return this.get(i);
	}-*/;
	
}
