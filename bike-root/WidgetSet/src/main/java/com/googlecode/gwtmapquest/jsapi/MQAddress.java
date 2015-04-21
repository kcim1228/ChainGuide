package com.googlecode.gwtmapquest.jsapi;



public class MQAddress extends MQAbstractAddress {

	public static native MQAddress newInstance() /*-{
		return new $wnd.MQAddress();
	}-*/;
	
	protected MQAddress() {
	}

	public final native String getStreet() /*-{
		return this.getStreet();
	}-*/;
	
	public final native void setStreet(String value) /*-{
		this.setStreet(value);
	}-*/;

	public final native String getCity() /*-{
		return this.getCity();
	}-*/;
	
	public final native void setCity(String value) /*-{
		this.setCity(value);
	}-*/;

	public final native String getState() /*-{
		return this.getState();
	}-*/;
	
	public final native void setState(String value) /*-{
		this.setState(value);
	}-*/;

	public final native String getPostalCode() /*-{
		return this.getPostalCode();
	}-*/;
	
	public final native void setPostalCode(String value) /*-{
		this.setPostalCode(value);
	}-*/;

	public final native String getCounty() /*-{
		return this.getCounty();
	}-*/;
	
	public final native void setCounty(String value) /*-{
		this.setCounty(value);
	}-*/;

	public final native String getCountry() /*-{
		return this.getCountry();
	}-*/;
	
	public final native void setCountry(String value) /*-{
		this.setCountry(value);
	}-*/;

	public final native String getAdminArea(int index) /*-{
		return this.getAdminArea(index);
	}-*/;

	public final boolean isEqual(MQAddress value) {
		if (value == null)
			return false;
		
		return equal(getStreet(), value.getStreet()) &&
			equal(getCity(), value.getCity()) &&
			equal(getState(), value.getState()) &&
			equal(getPostalCode(), value.getPostalCode()) &&
			equal(getCountry(), value.getCountry());
	}
	
}
