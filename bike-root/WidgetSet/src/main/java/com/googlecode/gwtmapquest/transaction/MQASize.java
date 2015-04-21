package com.googlecode.gwtmapquest.transaction;

import com.google.gwt.core.client.JavaScriptObject;

public class MQASize extends JavaScriptObject {

	public static native MQASize newInstance(int width, int height) /*-{
		return new $wnd.MQA.Size(width, height);
	}-*/;
	
	protected MQASize() {
	}

	public final native int getWidth() /*-{
		return this.getWidth();
	}-*/;
	
	public final native void setWidth(int value) /*-{
		this.setWidth(value);
	}-*/;
	
	public final native int getHeight() /*-{
		return this.getHeight();
	}-*/;
	
	public final native void setHeight(int value) /*-{
		this.setHeight(value);
	}-*/;

	public final native boolean toStringNative() /*-{
		return this.toString();
	}-*/;
	
}
