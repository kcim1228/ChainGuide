package com.googlecode.gwtmapquest.transaction.event;

public class ZoomEndEvent extends GWTMapquestEvent<ZoomEndHandler> {

	protected ZoomEndEvent() {
	}
	
	/**
	 * 
	 * @return the previous zoom level
	 */
	public final native int prevZoom()/*-{
		return this.prevZoom;
	}-*/;

	/**
	 * 
	 * @return the new zoom level
	 */
	public final native int zoom()/*-{
		return this.zoom;
	}-*/;

}
