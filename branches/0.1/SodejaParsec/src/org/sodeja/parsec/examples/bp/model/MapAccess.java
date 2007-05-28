package org.sodeja.parsec.examples.bp.model;

import java.util.Map;

public class MapAccess implements Access {
	public final BeanPath path;
	
	public MapAccess(final BeanPath path) {
		this.path = path;
	}

	public Object read(Map<String, Object> rootContext) {
		throw new IllegalStateException("Can't be part of a starting expression");
	}

	public Object read(Map<String, Object> rootContext, Object context) {
		if(context instanceof Map) {
			Object element = path.read(rootContext);
			
			return ((Map) context).get(element);
		}
		
		throw new IllegalArgumentException("Not a valid map construct: " + context);
	}
	
	@Override
	public String toString() {
		return "{" + path + "}";
	}
}
