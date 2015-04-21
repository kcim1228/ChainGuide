package com.googlecode.gwtmapquest.transaction;

import com.google.gwt.core.client.JsArray;

public class MQAShapeOverlay extends MQAAbstractShape {

	public static native MQAShapeOverlay newInstance() /*-{
		return new $wnd.MQA.ShapeOverlay();
	}-*/;
	
	protected MQAShapeOverlay() {
	}
	
	public final native String getColor()/*-{
		return this.getValue('color');
	}-*/;
	
	public final native void setColor(String hexColor)/*-{
		this.setValue('color', hexColor);
	}-*/;

	public final native String getAltColor()/*-{
		return this.getValue('altColor');
	}-*/;	
	
	public final native void setAltColor(String hexColor)/*-{
		this.setValue('altColor', hexColor);
	}-*/;

	public final native double getColorAlpha()/*-{
		return this.getValue('colorAlpha');
	}-*/;
	
	public final native void setColorAlpha(double value)/*-{
		this.setValue('colorAlpha', value);
	}-*/;

	public final native double getAltColorAlpha()/*-{
		return this.getValue('altColorAlpha');
	}-*/;
	
	public final native void setAltColorAlpha(double value)/*-{
		this.setValue('altColorAlpha', value);
	}-*/;

	public final native String getFillColor()/*-{
		return this.getValue('fillColor');
	}-*/;
	
	public final native void setFillColor(String hexColor)/*-{
		this.setValue('fillColor', hexColor);
	}-*/;

	public final native String getAltFillColor()/*-{
		return this.getValue('altFillColor');
	}-*/;
	
	public final native void setAltFillColor(String hexColor)/*-{
		this.setValue('altFillColor', hexColor);
	}-*/;

	public final native double getFillColorAlpha()/*-{
		return this.getValue('fillColorAlpha');
	}-*/;
	
	public final native void setFillColorAlpha(double value)/*-{
		this.setValue('fillColorAlpha', value);
	}-*/;

	public final native double getAltFillColorAlpha()/*-{
		return this.getValue('altFillColorAlpha');
	}-*/;
	
	public final native void setAltFillColorAlpha(double value)/*-{
		this.setValue('altFillColorAlpha', value);
	}-*/;

	public final native int getBorderWidth()/*-{
		return this.getValue('borderWidth');
	}-*/;
	
	public final native void setBorderWidth(int width)/*-{
		this.setValue('borderWidth', width);
	}-*/;

	public final native int getAltBorderWidth()/*-{
		return this.getValue('altBorderWidth');
	}-*/;
	
	public final native void setAltBorderWidth(int width)/*-{
		this.setValue('altBorderWidth', width);
	}-*/;

	public final native boolean getAltStateFlag()/*-{
		return this.getValue('altStateFlag');
	}-*/;
	
	public final native void setAltStateFlag(boolean value)/*-{
		this.setValue('altStateFlag', value);
	}-*/;

	public final native MQALatLngCollection getShapePoints()/*-{
		return this.getShapePoints();
	}-*/;

	public final native void setShapePoints(MQALatLngCollection points)/*-{
		this.setValue('shapePoints', points);
	}-*/;

	/**
	 * (MQA.ImageOverlay only).
	 * @return
	 */
	public final native String getImageURL()/*-{
		return this.getImageURL();
	}-*/;

	/**
	 * (MQA.ImageOverlay only).
	 */
	public final native void setImageURL(String url)/*-{
		this.setValue('imageURL', url);
	}-*/;

	/**
	 * (MQA.ImageOverlay only).
	 * @return
	 */
	public final native JsArray<MQAImageLevel> getImageLevels()/*-{
		return this.getImageLevels();
	}-*/;

	/**
	 * (MQA.ImageOverlay only).
	 */
	public final native void setImageLevels(JsArray<MQAImageLevel> levels)/*-{
		this.setValud('imageLevels', levels);
	}-*/;

	/**
	 * (MQA.ImageOverlay only).
	 * @return
	 */
	public final native double getImageOpacity()/*-{
		return this.getImageOpacity();
	}-*/;

	/**
	 * (MQA.ImageOverlay only).
	 */
	public final native void setImageOpacity(double opacity)/*-{
		this.setValue('imageOpacity', opacity);
	}-*/;
	
}
