package com.googlecode.gwtmapquest.transaction;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickHandler;
import com.googlecode.gwtmapquest.transaction.event.HasMapClearedHandlers;
import com.googlecode.gwtmapquest.transaction.event.HasShapeAddedHandlers;
import com.googlecode.gwtmapquest.transaction.event.HasShapeRemovedHandlers;
import com.googlecode.gwtmapquest.transaction.event.HasZoomEndHandlers;
import com.googlecode.gwtmapquest.transaction.event.MapClearedHandler;
import com.googlecode.gwtmapquest.transaction.event.ShapeAddedHandler;
import com.googlecode.gwtmapquest.transaction.event.ShapeRemovedHandler;
import com.googlecode.gwtmapquest.transaction.event.ZoomEndHandler;

/**
 * MQ API reference is <a href="http://developer.mapquest.com/content/documentation/ApiDocumentation/5_3_2/tilemaptoolkit_docs/index/Classes.html">here</a>
 *
 */
public class MQATileMap extends JavaScriptObject implements HasShapeAddedHandlers, 
	HasShapeRemovedHandlers, HasMapClearedHandlers, HasZoomEndHandlers {

	public static final String TYPE_MAP = "map";
	public static final String TYPE_SAT = "sat";
	public static final String TYPE_HYP = "hyb";
	
	public static native MQATileMap newInstance(Element container) /*-{
		return new $wnd.MQA.TileMap(container);
	}-*/;
	
	public static native MQATileMap newInstance(Element container, int zoomLevel, MQALatLng center, String mapType) /*-{
		return new $wnd.MQA.TileMap(container, zoomLevel, center, mapType);
	}-*/;
	
	protected MQATileMap() {
	}

	public final native void addControl(MQAControl control) /*-{
		this.addControl(control);
	}-*/;
	
	public final native void removeControl(MQAControl control) /*-{
		this.removeControl(control);
	}-*/;
	
	/**
	 * Adds the specified shape to the map.  Fires the 'MQA.TileMap.shapeAdded' event.
	 * @param point
	 */
	public final native void addShape(MQAAbstractShape point) /*-{
		return this.addShape(point);
	}-*/;
	
	/**
	 * Adds the specified shape collection's shapes to the map's default collection
	 * @param points
	 */
	public final native void addShapes(MQAShapeCollection points) /*-{
		return this.addShapes(points);
	}-*/;
	
	/**
	 * Removes the specified shape from the map's default collection.  Fires the 'MQA.TileMap.shapeRemoved' event.
	 * @param shape
	 */
	public final native void removeShape(MQAAbstractShape shape) /*-{
		return this.removeShape(shape);
	}-*/;	
	
	/**
	 * Replaces the shapes in the map's current default collection with the shapes in the specified collection.  Events are fired for each shape removed from the old collection and for each shape added from the new collection.  A 'mapcleared' event also fires before adding the new shapes.
	 * @param newCollection
	 * @return
	 */
	public final native boolean replaceShapes(MQAShapeCollection newCollection) /*-{
		return this.replaceShapes(newCollection);
	}-*/;
	
	/**
	 * Removes all shapes from the map's default collection.  Fires the 'mapcleared' event.
	 */
	public final native void removeAllShapes() /*-{
		this.removeAllShapes();
	}-*/;

	public final native MQALatLng getCenter()/*-{
		return this.getCenter();
	}-*/;

	public final native void setCenter(MQALatLng centerLatLng) /*-{
		return this.setCenter(centerLatLng);
	}-*/;

	public final native void setCenter(MQALatLng centerLatLng, int newZoomLevel) /*-{
		return this.setCenter(centerLatLng, newZoomLevel);
	}-*/;

	public final native MQAPoint getDragOffset() /*-{
		return this.getDragOffset();
	}-*/;
	
	public final native String getMapType() /*-{
		return this.getMapType();
	}-*/;
	
	/**
	 * 
	 * @param type The map type to set.  Valid values are 'map', 'sat', and 'hyb'.
	 */
	public final native void setMapType(String type) /*-{
		this.setMapType(type);
	}-*/;
	
	/**
	 * Converts pixel coordinates to geographical coordinates relative to the current map displayed.
	 * @param point
	 * @return
	 */
	public final native MQALatLng pixToLL(MQAPoint point) /*-{
		return this.pixToLL(point);
	}-*/;
	
	/**
	 * Function to convert geographical coordinates to pixel coordinates relative to the current map displayed.
	 * @param point
	 * @return
	 */
	public final native MQAPoint llToPix(MQALatLng point) /*-{
		return this.llToPix(point);
	}-*/;
	
	public final native void zoomIn() /*-{
		this.zoomIn();
	}-*/;
	
	public final native void zoomOut() /*-{
		this.zoomOut();
	}-*/;
	
	public final native int getZoomLevel() /*-{
		return this.getZoomLevel();
	}-*/;
	
	/**
	 * Zoom the map to the specified zoom level.  Causes a 'MQA.TileMap.zoomEnd' event to be fired on the map.
	 * @param level
	 */
	public final native void setZoomLevel(int level) /*-{
		this.setZoomLevel(level);
	}-*/;
	
	/**
	 * Zoom the map to the closest zoom level that fits the specified rectangle.
	 * @param boundary
	 */
	public final native void zoomToRect(MQARectLL boundary) /*-{
		this.zoomToRect(boundary);
	}-*/;
	
	/**
	 * Zoom the map to the closest zoom level that fits the specified rectangle.
	 * @param boundary
	 * @param keepCenter
	 */
	public final native void zoomToRect(MQARectLL boundary, boolean keepCenter) /*-{
		this.zoomToRect(boundary, keepCenter);
	}-*/;
	
	/**
	 * Zoom the map to the closest zoom level that fits the specified rectangle.
	 * @param boundary
	 * @param keepCenter
	 * @param minZoom
	 */
	public final native void zoomToRect(MQARectLL boundary, boolean keepCenter, int minZoom) /*-{
		this.zoomToRect(boundary, keepCenter, minZoom);
	}-*/;
	
	/**
	 * Zoom the map to the closest zoom level that fits the specified rectangle.
	 * @param boundary
	 * @param keepCenter
	 * @param minZoom
	 * @param maxZoom
	 */
	public final native void zoomToRect(MQARectLL boundary, boolean keepCenter, int minZoom, int maxZoom) /*-{
		this.zoomToRect(boundary, keepCenter, minZoom, maxZoom);
	}-*/;

	/**
	 * Returns the current scale of the map based on zoom level.
	 * @return
	 */
	public final native int getScale() /*-{
		return this.getScale();
	}-*/;
	
	public final native void bestFit()/*-{
		this.bestFit();
	}-*/;

	public final native void bestFit(boolean keepCenter)/*-{
		this.bestFit(keepCenter);
	}-*/;
	
	public final native void bestFit(boolean keepCenter, int minZoomLevel)/*-{
		this.bestFit(keepCenter, minZoomLevel);
	}-*/;
	
	public final native void bestFit(boolean keepCenter, int minZoomLevel, int maxZoomLevel)/*-{
		this.bestFit(keepCenter, minZoomLevel, maxZoomLevel);
	}-*/;
	
	public final native void bestFitLL(JsArray<MQALatLng> latLngArray)/*-{
		this.bestFitLL(latLngArray);
	}-*/;
	
	public final native void bestFitLL(JsArray<MQALatLng> latLngArray, boolean keepCenter)/*-{
		this.bestFitLL(latLngArray, keepCenter);
	}-*/;
	
	public final native void bestFitLL(JsArray<MQALatLng> latLngArray, boolean keepCenter, int minZoomLevel)/*-{
		this.bestFitLL(latLngArray, keepCenter, minZoomLevel);
	}-*/;
	
	public final native void bestFitLL(JsArray<MQALatLng> latLngArray, boolean keepCenter, int minZoomLevel, int maxZoomLevel)/*-{
		this.bestFitLL(latLngArray, keepCenter, minZoomLevel, maxZoomLevel);
	}-*/;
	
	public final native void panToLatLng(MQALatLng latLng)/*-{
		this.panToLatLng(latLng);
	}-*/;

	public final native MQARolloverWindow getRolloverWindow()/*-{
		return this.getRolloverWindow();
	}-*/;

	public final native MQAInfoWindow getInfoWindow()/*-{
		return this.getInfoWindow();
	}-*/;

	public final native void setInfoTitleHTML(String title)/*-{
		this.setInfoTitleHTML(title);
	}-*/;

	public final native void setInfoContentHTML(String content)/*-{
		this.setInfoContentHTML(content);
	}-*/;	
	
	public final native void setInfoTitleElement(Element element)/*-{
		this.setInfoTitleElement(element);
	}-*/;

	public final native void setInfoContentElement(Element contentContainer)/*-{
		this.setInfoContentElement(contentContainer);
	}-*/;

	public final native void openInfoWindow(MQAPoint point)/*-{
		this.openInfoWindow(point);
	}-*/;

	public final native void setRolloversEnabled(boolean enabled)/*-{
		this.setRolloversEnabled(enabled);
	}-*/;

	public final native boolean getRolloversEnabled()/*-{
		return this.getRolloversEnabled();
	}-*/;

	public final native void setDragEnabled(boolean enabled)/*-{
		return this.enableDragging(enabled);
	}-*/;

	public final native boolean getDragEnabled()/*-{
		return this.getDragEnabled();
	}-*/;

	public final native MQAShapeCollection getShapes(boolean onlyPois) /*-{
		return this.getShapes(onlyPois);
	}-*/;

	public final native MQASize getSize()/*-{
		return this.getSize();
	}-*/;

	public final native void setSize()/*-{
		this.setSize();
	}-*/;
	
	public final native void setSize(MQASize size)/*-{
		this.setSize(size);
	}-*/;

	public final native void addRouteHighlight(MQARectLL boundingBox, String mapServerUrl, String sessionID, boolean bestFit) /*-{
		this.addRouteHighlight(boundingBox, mapServerUrl, sessionID, bestFit);
	}-*/;

	public final native void removeRouteHighlight() /*-{
		this.removeRouteHighlight();
	}-*/;
	
	public final native String getRouteSession()/*-{
		return this.getRouteSession();
	}-*/;

	public final native MQARectLL getBounds()/*-{
		return this.getBounds();
	}-*/;

	public final native void restoreState()/*-{
		this.restoreState();
	}-*/;

	public final native void saveState()/*-{
		this.saveState();
	}-*/;

	public final native MQADeclutter getDeclutter()/*-{
		return this.getDeclutter();
	}-*/;

	public final native void showStaticMap()/*-{
		this.showStaticMap();
	}-*/;

	public final native void hideStaticMap()/*-{
		this.hideStaticMap();
	}-*/;

	public final native void setBestFitMargin(int marginPixels)/*-{
		this.setBestFitMargin(marginPixels);
	}-*/;

	public final native int getBestFitMargin()/*-{
		return this.getBestFitMargin();
	}-*/;

	public final native void panNorth(int percentage)/*-{
		this.panNorth(percentage);
	}-*/;

	public final native void panSouth(int percentage)/*-{
		this.panSouth(percentage);
	}-*/;

	public final native void panEast(int percentage)/*-{
		this.panEast(percentage);
	}-*/;

	public final native void panWest(int percentage)/*-{
		this.panWest(percentage);
	}-*/;

	public final native void panNorthWest(int percentage)/*-{
		this.panNorthWest(percentage);
	}-*/;

	public final native void panSouthWest(int percentage)/*-{
		this.panSouthWest(percentage);
	}-*/;

	public final native void panNorthEast(int percentage)/*-{
		this.panNorthEast(percentage);
	}-*/;

	public final native void panSouthEast(int percentage)/*-{
		this.panSouthEast(percentage);
	}-*/;

	public final native void addShapeCollection(MQAShapeCollection points) /*-{
		return this.addShapeCollection(points);
	}-*/;

	public final native void removeShapeCollection(String collectionName)/*-{
		this.removeShapeCollection(collectionName);
	}-*/;

	public final native boolean replaceShapeCollection(MQAShapeCollection newCollection, String nameOfOldCollection) /*-{
		return this.replaceShapeCollection(newCollection, nameOfOldCollection);
	}-*/;
	
	public final native MQAShapeCollection getShapeCollection(String name) /*-{
		return this.getShapeCollection(name);
	}-*/;

	public final native JsArray<MQAShapeCollection> getShapeCollections() /*-{
		return this.getShapeCollections();
	}-*/;
	
	/**
	 * 
	 * @return an array of all the shape collection names (Strings) currently on the map.
	 */
	public final native JsArrayString getShapeCollectionNames()/*-{
		return this.getShapeCollectionNames();
	}-*/;

	public final native int getShapeCollectionCount()/*-{
		return this.getShapeCollectionCount();
	}-*/;

	public final native void setMapShadowState(boolean showShadows)/*-{
		this.setMapShadowState(showShadows);
	}-*/;

	public final native boolean getMapShadowState()/*-{
		return this.getMapShadowState();
	}-*/;

	public final native MQAPoi getByKey(String key)/*-{
		return this.getByKey(key);
	}-*/;

	public final native void addShapeAddedHandler(ShapeAddedHandler handler) /*-{
		$wnd.MQA.EventManager.addListener(this, 'shapeAdded', function(e) {
	    	handler.@com.googlecode.gwtmapquest.transaction.event.ShapeAddedHandler::onShapeAdded(Lcom/googlecode/gwtmapquest/transaction/event/ShapeAddedEvent;)(e);
	    });
	}-*/;
	
	public final native void addShapeRemovedHandler(ShapeRemovedHandler handler) /*-{
		$wnd.MQA.EventManager.addListener(this, 'shapeRemoved', function(e) {
	    	handler.@com.googlecode.gwtmapquest.transaction.event.ShapeRemovedHandler::onShapeRemoved(Lcom/googlecode/gwtmapquest/transaction/event/ShapeRemovedEvent;)(e);
	    });
	}-*/;
	
	public final native void addMapClearedHandler(MapClearedHandler handler) /*-{
		$wnd.MQA.EventManager.addListener(this, 'mapCleared', function(e) {
	    	handler.@com.googlecode.gwtmapquest.transaction.event.MapClearedHandler::onMapCleared(Lcom/googlecode/gwtmapquest/transaction/event/MapClearedEvent;)(e);
	    });
	}-*/;
	
	public final native void addZoomEndHandler(ZoomEndHandler handler) /*-{
		$wnd.MQA.EventManager.addListener(this, 'zoomEnd', function(e) {
	    	handler.@com.googlecode.gwtmapquest.transaction.event.ZoomEndHandler::onZoomEnd(Lcom/googlecode/gwtmapquest/transaction/event/ZoomEndEvent;)(e);
	    });
	}-*/;
	
	// TODO: provide methods to remove specific handlers
}
