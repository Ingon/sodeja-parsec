package org.sodeja.parsec.examples.bp.model;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

public class ListAccess implements Access {
	public final Integer number;
	
	public ListAccess(final Integer number) {
		this.number = number;
	}

	public Object read(Map<String, Object> rootContext) {
		throw new IllegalStateException("Can't be part of a starting expression");
	}

	public Object read(Map<String, Object> rootContext, Object context) {
		if(context instanceof List) {
			return ((List) context).get(number);
		} else if(context.getClass().isArray()) {
			return Array.get(context, number);
		}
		
		throw new IllegalArgumentException("Not a valid list construct: " + context);
	}
	
	@Override
	public String toString() {
		return "[" + number + "]";
	}
}
