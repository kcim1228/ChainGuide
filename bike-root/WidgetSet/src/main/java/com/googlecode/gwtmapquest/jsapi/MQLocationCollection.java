package com.googlecode.gwtmapquest.jsapi;


public final class MQLocationCollection extends MQObjectCollection {

	protected static native MQLocationCollection newInstance(String locationType) /*-{
		return new $wnd.MQLocationCollection(locationType);
	}-*/;

	public static native MQLocationCollection newInstance() /*-{
		return new $wnd.MQLocationCollection("MQGeoAddress");
	}-*/;
	
	protected MQLocationCollection() {
	}
	
	public final native MQGeoAddress get(int i) /*-{
		return this.get(i);
	}-*/;
	
	public final native void loadXml(String xml) /*-{
		this.loadXml(xml);
	}-*/;
	
}
