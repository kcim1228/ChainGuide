package com.googlecode.gwtmapquest.jsapi;

import com.google.gwt.core.client.JavaScriptObject;

public final class MQRouteResults extends JavaScriptObject {

	public static native MQRouteResults newInstance() /*-{
		return new $wnd.MQRouteResults();
	}-*/;

	protected MQRouteResults() {
	}

	/**
	 * Returns the total distance for this maneuver.
	 * @return total distance in miles
	 */
	public native double getDistance() /*-{
		return this.getDistance();
	}-*/;
	
	/**
	 * Returns the total time for this maneuver.
	 * @return total time in seconds
	 */
	public native int getTime() /*-{
		return this.getTime();
	}-*/;	
	
	/**
	 * Returns the TrekRouteCollection member of this object.
	 * @return All TrekRoutes used in the route. 
	 */
	public native MQTrekRouteCollection getTrekRoutes() /*-{
		return this.getTrekRoutes();
	}-*/;

	public native void loadXml(String xml) /*-{
		this.loadXml(xml);
	}-*/;
	
}
