package com.googlecode.gwtmapquest.transaction.event;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.EventHandler;

public abstract class GWTMapquestEvent<H extends EventHandler> extends JavaScriptObject {

	protected GWTMapquestEvent() {
	}

	/**
	 * 
	 * @return always "event"
	 */
	public native final String getType()/*-{
		return this.type;
	}-*/;

	/**
	 * "MQA.TileMap.mapCleared"
	 * "MQA.TileMap.shapeAdded"
	 * "MQA.TileMap.shapeRemoved"
	 * "MQA.TileMap.zoomEnd"
	 * @return
	 */
	public native final String getEventName()/*-{
		return this.eventName;
	}-*/;

	/**
	 * 
	 * @return appears to always be null?
	 */
	public native final JavaScriptObject getSrcObject()/*-{
		return this.srcObject;
	}-*/;

}
