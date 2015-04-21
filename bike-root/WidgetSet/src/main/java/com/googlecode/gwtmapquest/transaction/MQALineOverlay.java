package com.googlecode.gwtmapquest.transaction;

public class MQALineOverlay extends MQAShapeOverlay {

	public static native MQALineOverlay newInstance() /*-{
		return new $wnd.MQA.LineOverlay();
	}-*/;
	
	public static native MQALineOverlay newInstance(MQALineOverlay overlayToClone) /*-{
		return new $wnd.MQA.LineOverlay(overlayToClone);
	}-*/;
	
	protected MQALineOverlay() {
	}
	
}
