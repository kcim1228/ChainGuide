package com.googlecode.gwtmapquest.jsapi;


public final class MQRouteOptions extends MQObject {

	public static native MQRouteOptions newInstance() /*-{
		return new $wnd.MQRouteOptions();
	}-*/;
	
	protected MQRouteOptions() {
	}

	public native int getMaxShapePointsPerManeuver() /*-{
		return this.getMaxShapePointsPerManeuver();
	}-*/;

	public native void setMaxShapePointsPerManeuver(int value) /*-{
		this.setMaxShapePointsPerManeuver(value);
	}-*/;
	
}
