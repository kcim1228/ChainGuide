package com.googlecode.gwtmapquest.transaction;

import com.google.gwt.core.client.JavaScriptObject;

public class MQAMapInit extends JavaScriptObject {

	public static native MQAMapInit newInstance() /*-{
		return new $wnd.MQA.MapInit();
	}-*/;
	
	protected MQAMapInit() {
	}

	public final native MQARectLL getBestFitRect()/*-{
		return this.getBestFitRect();
	}-*/;

	public final native void setBestFitRect(MQARectLL rect)/*-{
		this.setBestFitRect(rect);
	}-*/;

	public final native int getBestFitMargin()/*-{
		return this.getBestFitMargin();
	}-*/;

	public final native void setBestFitMargin(int margin)/*-{
		this.setBestFitMargin(margin);
	}-*/;

	public final native void isKeepCenter()/*-{
		this.isKeepCenter();
	}-*/;

	public final native void setKeepCenter(boolean keepCenter)/*-{
		this.setKeepCenter(keepCenter);
	}-*/;

	public final native int getMinZoom()/*-{
		return this.getMinZoom();
	}-*/;

	public final native void setMinZoom(int minZoom)/*-{
		this.setMinZoom(minZoom);
	}-*/;

	public final native int getMaxZoom()/*-{
		return this.getMaxZoom();
	}-*/;

	public final native void setMaxZoom(int maxZoom)/*-{
		this.setMaxZoom(maxZoom);
	}-*/;

}
