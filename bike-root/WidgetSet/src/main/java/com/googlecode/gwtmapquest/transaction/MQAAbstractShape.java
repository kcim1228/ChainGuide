package com.googlecode.gwtmapquest.transaction;

import com.google.gwt.core.client.JavaScriptObject;

public abstract class MQAAbstractShape extends JavaScriptObject {

	protected MQAAbstractShape() {
	}
	
	public final native String getKey() /*-{
		return this.getValue('key');
	}-*/;
	
	public final native void setKey(String value) /*-{
		this.setValue('key', value);
	}-*/;

	public final native boolean isVisible() /*-{
		return this.getValue('visible');
	}-*/;
	
	public final native void setVisible(boolean value) /*-{
		this.setValue('visible', value);
	}-*/;	
	
	public final native int getMinZoomLevel() /*-{
		return this.getValue('minZoomLevel');
	}-*/;
	
	public final native void setMinZoomLevel(int value) /*-{
		this.setValue('minZoomLevel', value);
	}-*/;

	public final native int getMaxZoomLevel() /*-{
		return this.getValue('maxZoomLevel');
	}-*/;
	
	public final native void setMaxZoomLevel(int value) /*-{
		this.setValue('maxZoomLevel', value);
	}-*/;
	
}
