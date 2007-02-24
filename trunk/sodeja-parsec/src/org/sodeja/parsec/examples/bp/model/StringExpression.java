package org.sodeja.parsec.examples.bp.model;

public class StringExpression implements Expression {
	public final String text;

	public StringExpression(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "\"" + text + "\"";
	}
}
