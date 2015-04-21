package com.googlecode.gwtmapquest.transaction;

public class MQAEllipseOverlay extends MQAShapeOverlay {

	public static native MQAEllipseOverlay newInstance() /*-{
		return new $wnd.MQA.EllipseOverlay();
	}-*/;
	
	public static native MQAEllipseOverlay newInstance(MQAEllipseOverlay overlayToClone) /*-{
		return new $wnd.MQA.EllipseOverlay(overlayToClone);
	}-*/;
	
	protected MQAEllipseOverlay() {
	}
	
}
