package org.sodeja.parsec.examples.bp.model;

import java.util.List;
import java.util.Map;

public class BeanPath {
	public final Expression start;
	public final List<Expression> rest;
	
	public BeanPath(final Expression start, final List<Expression> rest) {
		this.start = start;
		this.rest = rest;
	}

	public Object read(Map<String, Object> rootContext) {
		Object context = start.read(rootContext);
		for(Expression exp : rest) {
			context = exp.read(rootContext, context);
		}
		return context;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(start);
		for(Expression el : rest) {
			sb.append(".");
			sb.append(el);
		}
		return sb.toString();
	}
	
}
