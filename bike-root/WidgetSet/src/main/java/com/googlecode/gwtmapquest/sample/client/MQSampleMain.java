package com.googlecode.gwtmapquest.sample.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.googlecode.gwtmapquest.transaction.MQALatLng;
import com.googlecode.gwtmapquest.transaction.MQATileMap;
import com.googlecode.gwtmapquest.transaction.MQAZoomControl;

public class MQSampleMain implements EntryPoint {

	public void onModuleLoad() {
		MQATileMap map = MQATileMap.newInstance(Document.get().getElementById("mapDiv"));
		map.addControl(MQAZoomControl.newInstance());
		map.setCenter(MQALatLng.newInstance(37.62, -122.38));
		map.setZoomLevel(9);
	}

}
