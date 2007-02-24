package org.sodeja.parsec.examples.bp.model;

public class PathElement {
	public final Expression expression;

	public PathElement(final Expression expression) {
		this.expression = expression;
	}

	@Override
	public String toString() {
		return expression.toString();
	}
}
