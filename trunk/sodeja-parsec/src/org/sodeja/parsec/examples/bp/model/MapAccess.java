package org.sodeja.parsec.examples.bp.model;

public class MapAccess implements Access {
	public final String name;
	
	public MapAccess(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "{" + name + "}";
	}
}
