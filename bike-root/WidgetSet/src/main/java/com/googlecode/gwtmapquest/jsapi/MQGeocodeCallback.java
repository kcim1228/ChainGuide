package com.googlecode.gwtmapquest.jsapi;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;

public interface MQGeocodeCallback {

	public void onSuccess(MQLocationCollection geoAddresses, Response response);
	public void onError(Request request, Throwable error);
	
}
