package com.googlecode.gwtmapquest.jsapi;


public class MQAuthentication extends MQObject {
	
	public static native MQAuthentication newInstance(String transactionInfo) /*-{
		return new $wnd.MQAuthentication(transactionInfo);
	}-*/;
	
	protected MQAuthentication() {
	}

	protected final native String getInfo() /*-{
		return this.getInfo();
	}-*/;

	public final native int getObjectVersion() /*-{
		return this.getObjectVersion();
	}-*/;
	
}
