package com.googlecode.gwtmapquest.transaction;

public class MQARectangleOverlay extends MQAShapeOverlay {

	public static native MQARectangleOverlay newInstance() /*-{
		return new $wnd.MQA.RectangleOverlay();
	}-*/;
	
	public static native MQARectangleOverlay newInstance(MQARectangleOverlay overlayToClone) /*-{
		return new $wnd.MQA.RectangleOverlay(overlayToClone);
	}-*/;
	
	protected MQARectangleOverlay() {
	}
	
}
