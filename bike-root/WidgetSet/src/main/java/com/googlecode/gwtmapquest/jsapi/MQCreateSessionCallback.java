package com.googlecode.gwtmapquest.jsapi;

import com.google.gwt.http.client.Request;

public interface MQCreateSessionCallback {

	public void onSuccess(String sessionID);
	public void onError(Request request, Throwable error);
	
}
