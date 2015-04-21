package com.googlecode.gwtmapquest.jsapi;



public final class MQManeuverCollection extends MQObjectCollection {

	public static native MQManeuverCollection newInstance() /*-{
		return new $wnd.MQManeuverCollection();
	}-*/;
	
	protected MQManeuverCollection() {
	}

	public final native MQManeuver get(int i) /*-{
		return this.get(i);
	}-*/;
	
}
