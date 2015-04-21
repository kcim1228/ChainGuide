package com.googlecode.gwtmapquest.transaction;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class MQARectLL extends JavaScriptObject {

	public static native MQARectLL newInstance(MQALatLng upperLeftPoint, MQALatLng lowerRightPoint) /*-{
		return new $wnd.MQA.RectLL(upperLeftPoint, lowerRightPoint);
	}-*/;
		
	protected MQARectLL() {
	}
	
	public final native MQALatLng getUpperLeft() /*-{
		return this.getUpperLeft();
	}-*/;
	
	public final native void setUpperLeft(MQALatLng value) /*-{
		this.setUpperLeft(value);
	}-*/;

	public final native MQALatLng getLowerRight() /*-{
		return this.getLowerRight();
	}-*/;
	
	public final native void setLowerRight(MQALatLng value) /*-{
		this.setLowerRight(value);
	}-*/;

	public final native void setBounds(JsArray<MQALatLng> value) /*-{
		this.setBounds(value);
	}-*/;

	public final native void extend(MQALatLng value) /*-{
		this.extend(value);
	}-*/;
	
}
