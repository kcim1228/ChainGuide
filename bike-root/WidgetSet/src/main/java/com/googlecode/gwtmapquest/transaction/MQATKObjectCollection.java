package com.googlecode.gwtmapquest.transaction;

import com.google.gwt.core.client.JavaScriptObject;

public class MQATKObjectCollection<E> extends JavaScriptObject {

	protected MQATKObjectCollection() {
	}

	public final native String getName() /*-{
		return this.getName();
	}-*/;
	
	/**
	 * Sets the name of the collection.
	 * @param name The name to set
	 */
	public final native void setName(String name) /*-{
		this.setName(name);
	}-*/;

	public final native int add(JavaScriptObject obj) /*-{
		return this.add(obj);
	}-*/;
	
	public final native E getAt(int index) /*-{
		return this.getAt(index);
	}-*/;

	public final native int getSize() /*-{
		return this.getSize();
	}-*/;
	
	public final native E remove(int index) /*-{
		this.remove(index);
	}-*/;
	
	public final native void removeAll() /*-{
		this.removeAll();
	}-*/;
	
	public final native void removeItem(E value) /*-{
		this.removeItem(value);
	}-*/;
		
	public final native boolean contains(E value) /*-{
		return this.contains(value);
	}-*/;
	
	public final native void append(MQATKObjectCollection<E> collection) /*-{
		this.append(collection);
	}-*/;
	
	public final native E getItemIndex(JavaScriptObject value) /*-{
		var result = this.getItemIndex(value);
		if (!result)
			return null;
		else
			return result;
	}-*/;

}
