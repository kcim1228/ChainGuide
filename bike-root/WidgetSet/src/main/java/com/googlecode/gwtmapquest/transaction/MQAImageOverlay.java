package com.googlecode.gwtmapquest.transaction;

import com.google.gwt.core.client.JavaScriptObject;

public class MQAImageOverlay extends MQAShapeOverlay {

	public static native MQAImageOverlay newInstance()/*-{
		return new $wnd.MQA.ImageOverlay();
	}-*/;
	
	public static native MQAImageOverlay newInstance(MQAImageOverlay overlayToClone)/*-{
		return new $wnd.MQA.ImageOverlay(overlayToClone);
	}-*/;
	
	protected MQAImageOverlay() {
	}

}
