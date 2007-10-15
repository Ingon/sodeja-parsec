package org.sodeja.parsec.examples.lisp.model;

public class NumberExpression implements SimpleExpression {
	public final Integer value;
	
	public NumberExpression(Integer value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
