package com.googlecode.gwtmapquest.transaction;

public class MQAPolygonOverlay extends MQAShapeOverlay {

	public static native MQAPolygonOverlay newInstance() /*-{
		return new $wnd.MQA.PolygonOverlay();
	}-*/;
	
	public static native MQAPolygonOverlay newInstance(MQAPolygonOverlay overlayToClone) /*-{
		return new $wnd.MQA.PolygonOverlay(overlayToClone);
	}-*/;
	
	protected MQAPolygonOverlay() {
	}
	
}
