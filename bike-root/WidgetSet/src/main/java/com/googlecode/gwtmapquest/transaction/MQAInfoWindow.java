package com.googlecode.gwtmapquest.transaction;

import com.google.gwt.core.client.JavaScriptObject;

public class MQAInfoWindow extends JavaScriptObject {

	public static native MQAInfoWindow newInstance(MQATileMap map)/*-{
		return new $wnd.MQA.InfoWindow(map);
	}-*/;
	
	protected MQAInfoWindow() {
	}
	
	public final native void hide()/*-{
		this.hide();
	}-*/;

	public final native void show()/*-{
		this.show();
	}-*/;
	
	public final native boolean isHidden()/*-{
		return this.isHidden();
	}-*/;

	public final native int getMaxWidth()/*-{
		return this.getMaxWidth();
	}-*/;

	public final native void setMaxWidth(int width)/*-{
		this.setMaxWidth(width);
	}-*/;

	public final native int getMinWidth()/*-{
		return this.getMinWidth();
	}-*/;

	public final native void setMinWidth(int width)/*-{
		this.setMinWidth(width);
	}-*/;

	public final native String getTitleBackgroundColor()/*-{
		return this.getTitleBackgroundColor();
	}-*/;

	public final native void setTitleBackgroundColor(String hexColor)/*-{
		this.setTitleBackgroundColor(hexColor);
	}-*/;

}
