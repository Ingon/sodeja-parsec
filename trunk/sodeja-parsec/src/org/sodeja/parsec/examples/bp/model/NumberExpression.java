package org.sodeja.parsec.examples.bp.model;

public class NumberExpression implements Expression {
	public final Integer number;

	public NumberExpression(final Integer number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return String.valueOf(number);
	}
}
