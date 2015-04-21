package com.googlecode.gwtmapquest.jsapi;

import com.googlecode.gwtmapquest.transaction.MQALatLng;

public class MQGeoAddress extends MQAddress {

	public static native MQGeoAddress newInstance() /*-{
		return new $wnd.MQGeoAddress();
	}-*/;

	public static MQGeoAddress newInstance(MQALatLng value) {
		MQGeoAddress result = newInstance();
		result.setMQLatLng(value);
		return result;
	}
	
	protected MQGeoAddress() {
	}

	public final native MQALatLng getMQLatLng() /*-{
		return this.getMQLatLng();
	}-*/;

	public final native void setMQLatLng(MQALatLng value) /*-{
		this.setMQLatLng(value);
	}-*/;
	
}
