package com.googlecode.gwtmapquest.jsapi;

import com.google.gwt.core.client.JavaScriptObject;

public final class MQManeuver extends JavaScriptObject {

	protected MQManeuver() {
	}

	/**
	 * Returns the total distance for this maneuver.
	 * @return total distance in miles
	 */
	public native double getDistance() /*-{
		return parseFloat(this.getDistance());
	}-*/;
	
	/**
	 * Returns a string representing the narrative for this Maneuver.
	 * @return a string representing the narrative for this Maneuver 
	 */
	public native String getNarrative() /*-{
		return this.getNarrative();
	}-*/;
}
