package com.googlecode.gwtmapquest.transaction;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * The MQA.Declutter object is automatically created with an MQA.TileMap.  There is no need to explicitly create one.
 *
 */
public class MQADeclutter extends JavaScriptObject {

	public static final int DECLUTTER_MODE_NONE = 0;
	public static final int DECLUTTER_MODE_STACK = 1;
	public static final int DECLUTTER_MODE_LEADERLINE = 2;
	
	public static final int DOT_MODE_NO_DOT = 0;
	public static final int DOT_MODE_DRAW_DOT = 1;
	public static final int DOT_MODE_DOT_IMAGE = 2;
	
	protected MQADeclutter() {
	}
	
	public final native void setDeclutterMode(int mode)/*-{
		this.setClutterMode(mode);
	}-*/;

	public final native int getDeclutterMode()/*-{
		return this.getDeclutterMode();
	}-*/;

	public final native void setDeclutterIcon(String url, int width, int height)/*-{
		this.setDeclutterIcon(url, width, height);
	}-*/;

	public final native MQAIcon getDeclutterIcon()/*-{
		return this.getDeclutterIcon();
	}-*/;

	public final native void setDeclutterOverIcon(String url, int width, int height)/*-{
		this.setDeclutterOverIcon(url, width, height);
	}-*/;

	public final native MQAIcon getDeclutterOverIcon()/*-{
		return this.getDeclutterOverIcon();
	}-*/;

	public final native void setNextPageIcon(String url, int width, int height)/*-{
		this.setNextPageIcon(url, width, height);
	}-*/;

	public final native MQAIcon getNextPageIcon()/*-{
		this.getNextPageIcon();
	}-*/;

	public final native void setPrevPageIcon(String url, int width, int height)/*-{
		this.setPrevPageIcon(url, width, height);
	}-*/;

	public final native MQAIcon getPrevPageIcon()/*-{
		return this.getPrevPageIcon();
	}-*/;

	public final native void setStackHeight(int height)/*-{
		this.setStackHeight(height);
	}-*/;

	public final native int getStackHeight()/*-{
		return this.getStackHeight();
	}-*/;

	public final native void setStackWidth(int width)/*-{
		this.setStackWidth(width);
	}-*/;

	public final native int getStackWidth()/*-{
		return this.getStackWidth();
	}-*/;

	public final native void setRolloverDrag(boolean enabled)/*-{
		this.setRolloverDrag(enabled);
	}-*/;

	public final native boolean getRolloverDrag()/*-{
		return this.getRolloverDrag();
	}-*/;

	public final native void setLeaderLineColor(String hexColor)/*-{
		this.setLeaderLineColor(hexColor);
	}-*/;

	public final native String getLeaderLineColor()/*-{
		return this.getLeaderLineColor();
	}-*/;

	public final native void setLeaderLineDotMode(int mode)/*-{
		this.setLeaderLineDotMode(mode);
	}-*/;

	public final native int getLeaderLineDotMode()/*-{
		return this.getLeaderLineDotMode();
	}-*/;

	public final native void setLeaderLineDotImage(String url, int width, int height)/*-{
		this.setLeaderLineDotImage(url, width, height);
	}-*/;

	public final native MQAIcon getLeaderLineDotImage()/*-{
		return this.getLeaderLineDotImage();
	}-*/;

}
