package com.googlecode.gwtmapquest.transaction.event;
import com.google.gwt.event.shared.EventHandler;

public interface MapClearedHandler extends EventHandler {
	void onMapCleared(MapClearedEvent event);
}
