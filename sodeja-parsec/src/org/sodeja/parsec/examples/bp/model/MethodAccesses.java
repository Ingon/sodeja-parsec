package org.sodeja.parsec.examples.bp.model;

import java.util.List;

public class MethodAccesses implements Expression {
	public final Method method;
	public final List<Access> accesses;
	
	public MethodAccesses(final Method property, final List<Access> access) {
		this.method = property;
		this.accesses = access;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(method);
		for(Access acc : accesses) {
			sb.append(acc);
		}
		return sb.toString();
	}
}
