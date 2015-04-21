package com.googlecode.gwtmapquest.jsapi;

import com.google.gwt.http.client.Request;
import com.googlecode.gwtmapquest.transaction.MQARectLL;

public interface MQRouteBoundingBoxCallback {

	public void onSuccess(MQARectLL boundingBox);
	public void onError(Request request, Throwable error);
	
}
