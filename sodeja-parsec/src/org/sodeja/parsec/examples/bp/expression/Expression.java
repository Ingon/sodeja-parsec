package org.sodeja.parsec.examples.bp.expression;

public abstract class Expression {
	public final String name;

	public Expression(final String name) {
		this.name = name;
	}
	
	public abstract Object read(Object context);
}
