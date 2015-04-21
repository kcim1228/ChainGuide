package com.googlecode.gwtmapquest.jsapi;



public final class MQSingleLineAddress extends MQAbstractAddress {

	public static MQSingleLineAddress newInstance(String addressString) {
		MQSingleLineAddress address = MQSingleLineAddress.newInstance();
		address.setAddress(addressString);
		return address;
	}
	
	public static native MQSingleLineAddress newInstance() /*-{
		return new $wnd.MQSingleLineAddress();
	}-*/;
	
	protected MQSingleLineAddress() {
	}

	public native String getAddress() /*-{
		return this.getAddress();
	}-*/;
	
	public native void setAddress(String value) /*-{
		this.setAddress(value);
	}-*/;

	public boolean isEqual(MQSingleLineAddress value) {
		if (value == null)
			return false;
		return equal(getAddress(), value.getAddress());
	}
	
}
