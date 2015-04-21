package com.googlecode.gwtmapquest.jsapi;


public abstract class MQAbstractAddress extends MQObject {

	protected MQAbstractAddress() {
	}
	
	protected static boolean equal(Object left, Object right) {
		if (left == null && right == null) {
			return true;
		} else if (left != null) {
			return left.equals(right);
		} else {
			return right.equals(left);
		}
	}
	
	
}
