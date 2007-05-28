package org.sodeja.parsec.examples.bp.expression;

import java.util.Map;

public abstract class Expression {
	
	public static final String ROOT_CONTEXT = "ROOT_CONTEXT";
	
	public final String name;

	public Expression(final String name) {
		this.name = name;
	}
	
	public abstract Object read(Map<String, Object> rootContext);
	
	public abstract Object read(Map<String, Object> rootContext, Object exprContext);
}
