package com.googlecode.gwtmapquest.transaction.event;

import com.googlecode.gwtmapquest.transaction.MQAAbstractShape;

public class ShapeAddedEvent extends GWTMapquestEvent<ShapeAddedHandler> {

	protected ShapeAddedEvent() {
	}
	
	public native final MQAAbstractShape getShape()/*-{
		return this.shape;
	}-*/;

}
