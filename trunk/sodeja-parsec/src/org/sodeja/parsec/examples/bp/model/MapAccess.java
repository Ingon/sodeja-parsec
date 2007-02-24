package org.sodeja.parsec.examples.bp.model;

public class MapAccess implements Access {
	public final BeanPath path;
	
	public MapAccess(final BeanPath path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "{" + path + "}";
	}
}
