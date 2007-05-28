package org.sodeja.parsec.examples.bp.model;

import java.util.Map;

public class NumberExpression implements Expression {
	public final Integer number;

	public NumberExpression(final Integer number) {
		this.number = number;
	}

	public Object read(Map<String, Object> rootContext) {
		return number;
	}

	public Object read(Map<String, Object> rootContext, Object context) {
		throw new IllegalStateException("Can't be part of a starting expression");
	}
	
	@Override
	public String toString() {
		return String.valueOf(number);
	}
}
