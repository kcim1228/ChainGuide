package com.googlecode.gwtmapquest.transaction;

import com.google.gwt.core.client.JavaScriptObject;

public class MQAMapCornerPlacement extends JavaScriptObject {

	public static native MQAMapCornerPlacement newInstance(MQAMapCorner corner, MQASize offsetSize) /*-{
		return new $wnd.MQA.MapCornerPlacement(corner, size);
	}-*/;
	
	protected MQAMapCornerPlacement() {
	}

	public final native MQAMapCorner getCorner()/*-{
		return this.getCorner();
	}-*/;

	public final native MQASize getOffsetSize()/*-{
		return this.getOffsetSize();
	}-*/;

}
