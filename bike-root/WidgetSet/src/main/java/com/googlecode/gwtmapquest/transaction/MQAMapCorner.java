package com.googlecode.gwtmapquest.transaction;

import com.google.gwt.core.client.JavaScriptObject;

public class MQAMapCorner extends JavaScriptObject {

	public static native MQAMapCorner topLeftInstance() /*-{
		return new $wnd.MQA.CORNER_TOPLEFT;
	}-*/;
	
	public static native MQAMapCorner topRightInstance() /*-{
		return new $wnd.MQA.CORNER_TOPRIGHT;
	}-*/;
	
	public static native MQAMapCorner bottomLeftInstance() /*-{
		return new $wnd.MQA.CORNER_BOTTOMLEFT;
	}-*/;
	
	public static native MQAMapCorner bottomRightInstance() /*-{
		return new $wnd.MQA.CORNER_BOTTOMRIGHT;
	}-*/;
	
	protected MQAMapCorner() {
	}

}
