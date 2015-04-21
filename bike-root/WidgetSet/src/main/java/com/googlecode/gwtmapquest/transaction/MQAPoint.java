package com.googlecode.gwtmapquest.transaction;

import com.google.gwt.core.client.JavaScriptObject;

public class MQAPoint extends JavaScriptObject {

	public static native MQAPoint newInstance() /*-{
		return new $wnd.MQA.Point();
	}-*/;

	public static native MQAPoint newInstance(int x, int y) /*-{
		return new $wnd.MQA.Point(x, y);
	}-*/;
	
	public static native MQAPoint newInstance(String xpath) /*-{
		return new $wnd.MQA.Point(xpath);
	}-*/;
	
	protected MQAPoint() {
	}

	public final native int getX() /*-{
		return this.getX();
	}-*/;
	
	public final native void setX(int value) /*-{
		this.setX(value);
	}-*/;

	public final native int getY() /*-{
		return this.getY();
	}-*/;
	
	public final native void setY(int value) /*-{
		this.setY(value);
	}-*/;

	public final native void setXY(int x, int y) /*-{
		this.setXY(x, y);
	}-*/;
	
	/**
	 * Returns a true if both the X and Y values are set to a value other than the static �INVALID� value.
	 * @return
	 */
	public final native boolean valid() /*-{
		return this.valid();
	}-*/;

	public final native boolean equalsNative(MQAPoint other) /*-{
		return this.equals(other);
	}-*/;

	public final native boolean toStringNative() /*-{
		return this.toString();
	}-*/;
	
}
