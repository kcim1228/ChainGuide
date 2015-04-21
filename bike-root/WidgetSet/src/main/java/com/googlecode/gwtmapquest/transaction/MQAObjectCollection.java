package com.googlecode.gwtmapquest.transaction;

import com.google.gwt.core.client.JavaScriptObject;

public class MQAObjectCollection<E extends JavaScriptObject> extends JavaScriptObject {

	public static native <E extends JavaScriptObject> MQAObjectCollection<E> newInstance()/*-{
		return new $wnd.MQA.ObjectCollection();
	}-*/;
	
	public static native <E extends JavaScriptObject> MQAObjectCollection<E> newInstance(int maxSize)/*-{
		return new $wnd.MQA.ObjectCollection(maxSize);
	}-*/;
	
	protected MQAObjectCollection() {
	}
	
	public final native String getValidClassName()/*-{
		return this.getValidClassName();
	}-*/;
	
	public final native void setValidClassName(String className)/*-{
		this.setValidClassName(className);
	}-*/;

	public final native int add(JavaScriptObject obj) /*-{
		return this.add(obj);
	}-*/;
	
	public final native int getSize() /*-{
		return this.getSize();
	}-*/;

	public final native E get(int index) /*-{
		return this.get(index);
	}-*/;

	public final native E remove(int index) /*-{
		this.remove(index);
	}-*/;
	
	public final native void removeAll() /*-{
		this.removeAll();
	}-*/;
	
	public final native boolean contains(JavaScriptObject value) /*-{
		return this.contains(value);
	}-*/;
	
	public final native void append(MQAObjectCollection<E> collection) /*-{
		this.append(collection);
	}-*/;
	
	public final native void set(int index, E newObject)/*-{
		this.set(index, newObject);
	}-*/;

	public final native void isValidObject(E object)/*-{
		this.isValidObject(object);
	}-*/;

	public final native void removeItem(E value) /*-{
		this.removeItem(value);
	}-*/;
	
	public final native E getById(String id)/*-{
		return this.getById(id);
	}-*/;
	
}
