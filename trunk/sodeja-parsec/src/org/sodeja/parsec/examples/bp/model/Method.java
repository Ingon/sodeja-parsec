package org.sodeja.parsec.examples.bp.model;

import java.util.List;

public class Method implements Expression {
	public final String name;
	public final List<BeanPath> params;
	
	public Method(final String name, final List<BeanPath> params) {
		this.name = name;
		this.params = params;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append("(");
		for(BeanPath bp : params) {
			sb.append(bp);
			sb.append(", ");
		}
		if(params.size() > 0) {
			sb.setLength(sb.length() - 2);
		}
		sb.append(")");
		return sb.toString();
	}
}
