package com.googlecode.gwtmapquest.transaction;


import com.google.gwt.event.dom.client.ClickHandler;

public class MQAPoi extends MQAAbstractShape {

	public static native MQAPoi newInstance(MQALatLng latLng) /*-{
		return new $wnd.MQA.Poi(latLng);
	}-*/;
	
	public static native MQAPoi newInstance(MQALatLng latLng, MQAIcon icon) /*-{
		return new $wnd.MQA.Poi(latLng, icon);
	}-*/;
	
	protected MQAPoi() {
	}

	/**
	 * Undocumented by MQ
	 * @return
	 */
	public final native MQALatLng getLatLng() /*-{
		return this.getValue('latLng');
	}-*/;
	
	/**
	 * Undocumented by MQ
	 * @return
	 */
	public final native MQATileMap getMap() /*-{
		return this.getValue('map');
	}-*/;
	
	public final native MQAIcon getIcon() /*-{
		return this.getValue('icon');
	}-*/;
	
	public final native void setIcon(MQAIcon value) /*-{
		this.setValue('icon', value);
	}-*/;

	public final native MQAPoint getIconOffset() /*-{
		return this.getValue('iconOffset');
	}-*/;

	public final native void setIconOffset(MQAPoint value) /*-{
		this.setValue('iconOffset', value);
	}-*/;	
	
	public final native MQAIcon getShadow() /*-{
		return this.getValue('shadow');
	}-*/;
	
	public final native void setShadow(MQAIcon value) /*-{
		this.setValue('shadow', value);
	}-*/;

	public final native MQAPoint getShadowOffset() /*-{
		return this.getValue('shadowOffset');
	}-*/;
	
	public final native void setShadowOffset(MQAPoint value) /*-{
		this.setValue('shadowOffset', value);
	}-*/;	

	public final native MQAPoint getInfoWindowOffset() /*-{
		return this.getValue('infoWindowOffset');
	}-*/;
	
	public final native void setInfoWindowOffset(MQAPoint value) /*-{
		this.setValue('infoWindowOffset', value);
	}-*/;	

	public final native MQAIcon getAltIcon() /*-{
		return this.getValue('altIcon');
	}-*/;
	
	public final native void setAltIcon(MQAIcon value) /*-{
		this.setValue('altIcon', value);
	}-*/;

	public final native MQAPoint getAltIconOffset() /*-{
		return this.getValue('altIconOffset');
	}-*/;
	
	public final native void setAltIconOffset(MQAPoint value) /*-{
		this.setValue('altIconOffset', value);
	}-*/;

	public final native MQAIcon getAltShadow() /*-{
		return this.getValue('altShadow');
	}-*/;
	
	public final native void setAltShadow(MQAIcon value) /*-{
		this.setValue('altShadow', value);
	}-*/;

	public final native MQAPoint getAltShadowOffset() /*-{
		return this.getValue('altShadowOffset');
	}-*/;
	
	public final native void setAltShadowOffset(MQAPoint value) /*-{
		this.setValue('altShadowOffset', value);
	}-*/;

	public final native MQAIcon getDeclutterIcon() /*-{
		return this.getValue('declutterIcon');
	}-*/;
	
	public final native void setDeclutterIcon(MQAIcon value) /*-{
		this.setValue('declutterIcon', value);
	}-*/;
	
	public final native MQAPoint getDeclutterIconOffset() /*-{
		return this.getValue('declutterIconOffset');
	}-*/;
	
	public final native void setDeclutterIconOffset(MQAPoint value) /*-{
		this.setValue('declutterIconOffset', value);
	}-*/;

	public final native MQAIcon getDeclutterShadow() /*-{
		return this.getValue('declutterShadow');
	}-*/;
	
	public final native void setDeclutterShadow(MQAIcon value) /*-{
		this.setValue('declutterShadow', value);
	}-*/;
	
	public final native MQAPoint getDeclutterShadowOffset() /*-{
		return this.getValue('declutterShadowOffset');
	}-*/;
	
	public final native void setDeclutterShadowOffset(MQAPoint value) /*-{
		this.setValue('declutterShadowOffset', value);
	}-*/;

	public final native MQAPoint getDeclutterInfoWindowOffset() /*-{
		return this.getValue('declutterInfoWindowOffset');
	}-*/;
	
	public final native void setDeclutterInfoWindowOffset(MQAPoint value) /*-{
		this.setValue('declutterInfoWindowOffset', value);
	}-*/;	

	public final native String getHTMLContent() /*-{
		return this.getValue('HTMLContent');
	}-*/;
	
	public final native void setHTMLContent(String value) /*-{
		this.setValue('HTMLContent', value);
	}-*/;

	public final native MQAPoint getHTMLOffset() /*-{
		return this.getValue('HTMLOffset');
	}-*/;
	
	public final native void setHTMLOffset(MQAPoint value) /*-{
		this.setValue('HTMLOffset', value);
	}-*/;	

	public final native MQAPoint getHTMLInfoWindowOffset() /*-{
		return this.getValue('HTMLInfoWindowOffset');
	}-*/;
	
	public final native void setHTMLInfoWindowOffset(MQAPoint value) /*-{
		this.setValue('HTMLInfoWindowOffset', value);
	}-*/;	

	public final native boolean isAltState() /*-{
		return this.getValue('altStateFlag');
	}-*/;
	
	public final native void setAltState(boolean value) /*-{
		this.setValue('altStateFlag', value);
	}-*/;	

	public final native String getInfoContentHTML() /*-{
		return this.getValue('infoContentHTML');
	}-*/;
	
	public final native void setInfoContentHTML(String value) /*-{
		this.setValue('infoContentHTML', value);
	}-*/;

	public final native String getInfoTitleHTML() /*-{
		return this.getValue('infoTitleHTML');
	}-*/;
	
	public final native void setInfoTitleHTML(String value) /*-{
		this.setValue('infoTitleHTML', value);
	}-*/;

	public final native int getMaxInfoWindowWidth() /*-{
		return this.getValue('maxInfoWindowWidth');
	}-*/;
	
	public final native void setMaxInfoWindowWidth(int value) /*-{
		this.setValue('maxInfoWindowWidth', value);
	}-*/;

	public final native String getTitleBackgroundColor() /*-{
		return this.getValue('titleBackgroundColor');
	}-*/;
	
	public final native void setTitleBackgroundColor(String value) /*-{
		this.setValue('titleBackgroundColor', value);
	}-*/;

	public final native String getInfoWindowTitleText() /*-{
		return this.getValue('infoWindowTitleText');
	}-*/;
	
	public final native void setInfoWindowTitleText(String value) /*-{
		this.setValue('infoWindowTitleText', value);
	}-*/;

	public final native boolean isRolloverEnabled() /*-{
		return this.getValue('rolloverEnabled');
	}-*/;
	
	public final native void setRolloverEnabled(boolean value) /*-{
		this.setValue('rolloverEnabled', value);
	}-*/;

	public final native String getLabelText() /*-{
		return this.getValue('labelText');
	}-*/;
	
	public final native void setLabelText(String value) /*-{
		this.setValue('labelText', value);
	}-*/;

	public final native String getLabelClass() /*-{
		return this.getValue('labelClass');
	}-*/;
	
	public final native void setLabelClass(String value) /*-{
		this.setValue('labelClass', value);
	}-*/;

	public final native boolean isLabelVisible() /*-{
		return this.getValue('labelVisible');
	}-*/;
	
	public final native void setLabelVisible(boolean value) /*-{
		this.setValue('labelVisible', value);
	}-*/;

	public final native boolean isDraggable() /*-{
		return this.getValue('draggable');
	}-*/;
	
	public final native void setDraggable(boolean value) /*-{
		this.setValue('draggable', value);
	}-*/;

	public final native boolean getSnapback() /*-{
		return this.getValue('snapback');
	}-*/;
	
	public final native void setSnapback(boolean value) /*-{
		this.setValue('snapback', value);
	}-*/;

	public final native boolean getKeepRolloverOnDrag() /*-{
		return this.getValue('keepRolloverOnDrag');
	}-*/;
	
	public final native void setKeepRolloverOnDrag(boolean value) /*-{
		this.setValue('keepRolloverOnDrag', value);
	}-*/;

	public final native String getLeaderLineColor() /*-{
		return this.getValue('leaderLineColor');
	}-*/;
	
	public final native void setLeaderLineColor(String value) /*-{
		this.setValue('leaderLineColor', value);
	}-*/;

	public final native MQAIcon getLeaderLineDotImage() /*-{
		return this.getValue('leaderLineDotImage');
	}-*/;
	
	public final native void setLeaderLineDotImage(MQAIcon value) /*-{
		this.setValue('leaderLineDotImage', value);
	}-*/;

	public final native void showRolloverWindow() /*-{
		this.showRolloverWindow();
	}-*/;
	
	public final native void showInfoWindow() /*-{
		this.showInfoWindow();
	}-*/;
		
	public final native void addClickHandler(ClickHandler handler) /*-{
		$wnd.MQA.EventManager.addListener(this, 'click', function(e) {
        	handler.@com.google.gwt.event.dom.client.ClickHandler::onClick(Lcom/google/gwt/event/dom/client/ClickEvent;)(null);
        });
	}-*/;
	
}
