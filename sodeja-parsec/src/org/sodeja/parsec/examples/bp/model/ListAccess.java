package org.sodeja.parsec.examples.bp.model;

import java.util.Map;

public class ListAccess implements Access {
	public final Integer number;
	
	public ListAccess(final Integer number) {
		this.number = number;
	}

//	public Object read(Map<String, Object> rootContext) {
//		throw new IllegalStateException("Can't be part of a starting expression");
//	}
//
//	public Object read(Map<String, Object> rootContext, Object context) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	
	@Override
	public String toString() {
		return "[" + number + "]";
	}
}
