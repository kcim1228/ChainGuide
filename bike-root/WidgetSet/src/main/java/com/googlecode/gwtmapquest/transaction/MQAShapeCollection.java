package com.googlecode.gwtmapquest.transaction;


public class MQAShapeCollection<E extends MQAAbstractShape> extends MQATKObjectCollection<E> {

	public static native <E extends MQAAbstractShape> MQAShapeCollection<E> newInstance() /*-{
		return new $wnd.MQA.ShapeCollection();
	}-*/;
	
	protected MQAShapeCollection() {
	}
	
	public final native E getByKey(String key) /*-{
		return this.getByKey(key);
	}-*/;

	public final native MQARectLL getBoundingRect() /*-{
		return this.getBoundingRect();
	}-*/;

	public final native boolean getDeclutter() /*-{
		return this.getDeclutter();
	}-*/;
	
	public final native void setDeclutter(boolean value) /*-{
		this.setDeclutter(value);
	}-*/;

	public final native void setMinZoomLevel(int value) /*-{
		this.setMinZoomLevel(value);
	}-*/;
	
}
