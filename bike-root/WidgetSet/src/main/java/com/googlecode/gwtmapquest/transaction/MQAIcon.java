package com.googlecode.gwtmapquest.transaction;

import com.google.gwt.core.client.JavaScriptObject;

public class MQAIcon extends JavaScriptObject {

	public static native MQAIcon newInstance(String imageURL, int width, int height) /*-{
		return new $wnd.MQA.Icon(imageURL, width, height);
	}-*/;

	protected MQAIcon() {
	}
	
	public final native int getWidth()/*-{
		return this.width;
	}-*/;

	public final native int getHeight()/*-{
		return this.height;
	}-*/;	

	public final native String getImageURL()/*-{
		return this.imageURL;
	}-*/;	
	
}
