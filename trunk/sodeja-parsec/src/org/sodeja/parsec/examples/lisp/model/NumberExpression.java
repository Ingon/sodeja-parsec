package org.sodeja.parsec.examples.lisp.model;

public class NumberExpression<T extends Number> implements SimpleExpression {
	public final T value;
	
	public NumberExpression(T value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
