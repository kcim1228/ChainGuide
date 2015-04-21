package com.googlecode.gwtmapquest.transaction.event;
import com.googlecode.gwtmapquest.transaction.MQAAbstractShape;

public class ShapeRemovedEvent extends GWTMapquestEvent<ShapeRemovedHandler> {

	protected ShapeRemovedEvent() {
	}

	public native final MQAAbstractShape getShape()/*-{
		return this.shape;
	}-*/;

}
