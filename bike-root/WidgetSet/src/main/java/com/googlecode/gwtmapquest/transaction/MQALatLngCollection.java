package com.googlecode.gwtmapquest.transaction;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.xml.client.Node;

public class MQALatLngCollection extends MQAObjectCollection<MQALatLng> {

	public static native MQALatLngCollection newInstance()/*-{
		return new $wnd.MQA.LatLngCollection();
	}-*/;
	
	protected MQALatLngCollection() {
	}

	public final native void loadXml(String xml)/*-{
		this.loadXml(xml);
	}-*/;

	public final native void loadXmlFromNode(Node node)/*-{
		this.loadXmlFromNode(node);
	}-*/;

	public final native String saveXml()/*-{
		return this.saveXml();
	}-*/;

}
