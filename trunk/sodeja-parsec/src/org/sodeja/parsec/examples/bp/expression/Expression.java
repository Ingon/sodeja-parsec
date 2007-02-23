package org.sodeja.parsec.examples.bp.expression;

import java.util.Map;

public abstract class Expression {
	public final String name;

	public Expression(final String name) {
		this.name = name;
	}
	
	public Object read(Map<String, Object> rootContext) {
		return read(rootContext, rootContext.get(name));
	}
	
	public abstract Object read(Map<String, Object> rootContext, Object exprContext);
}
