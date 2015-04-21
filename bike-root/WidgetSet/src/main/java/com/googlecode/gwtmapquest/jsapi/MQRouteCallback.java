package com.googlecode.gwtmapquest.jsapi;

import com.google.gwt.http.client.Request;
import com.googlecode.gwtmapquest.transaction.MQARectLL;

public interface MQRouteCallback {

	public void onSuccess(MQRouteResults routeResults, MQARectLL boundingBox);
	public void onError(Request request, Throwable error);
	
}
