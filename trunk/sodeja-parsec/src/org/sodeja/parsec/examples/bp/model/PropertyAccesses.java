package org.sodeja.parsec.examples.bp.model;

import java.util.List;

public class PropertyAccesses implements Expression {
	public final Property property;
	public final List<Access> accesses;
	
	public PropertyAccesses(final Property property, final List<Access> access) {
		this.property = property;
		this.accesses = access;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(property);
		for(Access acc : accesses) {
			sb.append(acc);
		}
		return sb.toString();
	}
}
