package org.sodeja.parsec.examples.bp.model;

import java.util.List;
import java.util.Map;

public class PropertyAccesses implements Expression {
	public final Property property;
	public final List<Access> accesses;
	
	public PropertyAccesses(final Property property, final List<Access> access) {
		this.property = property;
		this.accesses = access;
	}

	public Object read(Map<String, Object> rootContext) {
		return Access.Util.read(rootContext, property.read(rootContext), accesses);
	}
	
	public Object read(Map<String, Object> rootContext, Object context) {
		return Access.Util.read(rootContext, property.read(rootContext, context), accesses);
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
