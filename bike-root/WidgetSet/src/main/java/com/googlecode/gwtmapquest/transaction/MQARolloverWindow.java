package com.googlecode.gwtmapquest.transaction;

import com.google.gwt.core.client.JavaScriptObject;

public class MQARolloverWindow extends JavaScriptObject {

	public static native MQARolloverWindow newInstance(MQATileMap map)/*-{
		return new $wnd.MQA.RolloverWindow(map);
	}-*/;

	protected MQARolloverWindow() {
	}
	
	public final native void hide()/*-{
		this.hide();
	}-*/;

	public final native void show()/*-{
		this.show();
	}-*/;
	
	public final native boolean isHidden()/*-{
		return this.isHidden();
	}-*/;

	public final native MQALatLng getPointLL()/*-{
		return this.getPointLL();
	}-*/;

	public final native MQAPoint getPointXY()/*-{
		return this.getPointXY();
	}-*/;

	public final native MQAPoint getPixelOffset()/*-{
		return this.getPixelOffset();
	}-*/;

	public final native void setRolloverGraphic(String leftImgSrc, String rightImgSrc)/*-{
		this.setRolloverGraphic(leftImgSrc, rightImgSrc);
	}-*/;

	/**
	 * 
	 * @param orientation “left” or “right”.  If no parameters is specified, the left image is returned.
	 * @return
	 */
	public final native String getRolloverGraphic(String orientation)/*-{
		return this.getRolloverGraphic(orientation);
	}-*/;

	public final native void setDefaultRollover()/*-{
		this.setDefaultRollover();
	}-*/;

	public final native void setFlipStateEnabled(boolean enabled)/*-{
		this.setFlipstateEnabled(enabled);
	}-*/;

	public final native boolean getFlipStateEnabled()/*-{
		return this.getFlipStateEnabled();
	}-*/;

	public final native void setTextLength(int length)/*-{
		this.setTextLength(length);
	}-*/;

	public final native int getTextLength()/*-{
		return this.getTextLength();
	}-*/;

	public final native void setLeftRolloverOffsetX(int offsetWidth)/*-{
		this.setLeftRolloverOffsetX(offsetWidth);
	}-*/;

	public final native int getLeftRolloverOffsetX()/*-{
		return this.getLeftRolloverOffsetX();
	}-*/;

	public final native void setRightRolloverOffsetX(int offsetWidth)/*-{
		this.setRightRolloverOffsetX(offsetWidth);
	}-*/;
	
	public final native int getRightRolloverOffsetX()/*-{
		return this.getRightRolloverOffsetX();
	}-*/;	
	
	public final native void setRolloverOffsetY(int offsetHeight)/*-{
		this.setRolloverOffsetY(offsetHeight);
	}-*/;

	public final native int getRolloverOffsetY()/*-{
		return this.getRolloverOffsetY();
	}-*/;

}
