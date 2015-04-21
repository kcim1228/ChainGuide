package com.googlecode.gwtmapquest.transaction.event;
import com.google.gwt.event.shared.EventHandler;

public interface ZoomEndHandler extends EventHandler {
	void onZoomEnd(ZoomEndEvent event);
}
