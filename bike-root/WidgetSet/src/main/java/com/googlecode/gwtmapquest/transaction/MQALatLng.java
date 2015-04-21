package com.googlecode.gwtmapquest.transaction;


import com.google.gwt.core.client.JavaScriptObject;
import com.googlecode.gwtmapquest.jsapi.MQDistanceUnits;

public class MQALatLng extends JavaScriptObject {
	
	public static native MQALatLng newInstance(double latitude, double longitude) /*-{
		return new $wnd.MQA.LatLng(latitude, longitude);
	}-*/;
	
	public static native MQALatLng newInstance(String xPath) /*-{
		return new $wnd.MQA.LatLng(xPath);
	}-*/;
	
	public static native MQALatLng newInstance() /*-{
		return new $wnd.MQA.LatLng();
	}-*/;
	
	protected MQALatLng() {
	}
	
	public final native double getLatitude() /*-{
		return this.getLatitude();
	}-*/;
	
	public final native void setLatitude(double value) /*-{
		this.setLatitude(value);
	}-*/;
	
	public final native double getLongitude() /*-{
		return this.getLongitude();
	}-*/;

	public final native void setLongitude(double value) /*-{
		this.setLongitude(value);
	}-*/;

	/**
	 * Sets the latitude and longitude values.
	 * @param latitude
	 * @param longitude
	 */
	public final native void setLatLng(double latitude, double longitude) /*-{
		this.setLatLng(latitude, longitude);
	}-*/;
	
	/**
	 * Calculates the distance between two lat/lng�s in miles or meters.  If the units parameter is not provide, then miles will be the default.
	 * @param otherPoint Second lat,lng position to calculate distance to.
	 * @param units Units to calculate distance
	 * @return
	 */
	public final native double arcDistance(MQALatLng otherPoint, MQDistanceUnits units) /*-{
		return this.arcDistance(otherPoint, units);
	}-*/;
	
	/**
	 * Returns a true if both the latitude and longitude values are set to a value other than the static �INVALID� value.
	 * @return
	 */
	public final native boolean valid() /*-{
		return this.valid();
	}-*/;
	
	/**
	 * Returns whether or not two latlngs are equal.  Two instances of MQA.LatLng are equal if the values of their latitude and longitude member fields, representing their position in the coordinate space, are the same.
	 * @param other
	 * @return
	 */
	public final native boolean equalsNative(MQALatLng other) /*-{
		return this.equals(other);
	}-*/;

	/**
	 * Returns a string representation of this <code>MQLatLng</code>.  The format is <code>�latitude,longitude�</code>.
	 * @return
	 */
	public final native boolean toStringNative() /*-{
		return this.toString();
	}-*/;

	public final native void loadXml(String xml) /*-{
		this.loadXml(xml);
	}-*/;
	
}
