package com.googlecode.gwtmapquest.transaction;

import com.google.gwt.core.client.JavaScriptObject;

public class MQAEventManager extends JavaScriptObject {

	protected MQAEventManager() {
	}

	/**
	 * @param source
	 * @param eventType
	 * @param handlerFunction the function to call when the event triggers.
	 */
	public static native void addListener(JavaScriptObject source, String eventType, JavaScriptObject handlerFunction) /*-{
		MQA.EventManager.addListener(source, eventType, handlerFunction);
	}-*/;
	
	public static native void removeListener(JavaScriptObject source, String eventType, JavaScriptObject handlerFunction) /*-{
		MQA.EventManager.removeListener(source, eventType, handlerFunction);
	}-*/;
	
	/**
	 * Removes all handlers on the given object for all events that were installed using addListener().
	 * @param source
	 */
	public static native void clearListeners(JavaScriptObject source) /*-{
		MQA.EventManager.clearListeners(source);
	}-*/;
	
	/**
	 * Removes all handlers on the given object for the given event that were installed using addListener()
	 * @param source
	 * @param eventType such as "shapeAdded", "shapeRemoved", "mapCleared", "zoomEnd", etc
	 */
	public static native void clearListeners(JavaScriptObject source, String eventType) /*-{
		MQA.EventManager.clearListeners(source, eventType);
	}-*/;
	
}
