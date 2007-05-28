package org.sodeja.parsec.examples.bp.model;

import java.util.Map;

public class StringExpression implements Expression {
	public final String text;

	public StringExpression(final String text) {
		this.text = text;
	}

	public Object read(Map<String, Object> rootContext) {
		return text;
	}

	public Object read(Map<String, Object> rootContext, Object context) {
		throw new IllegalStateException("Can't be part of a starting expression");
	}
	
	@Override
	public String toString() {
		return "\"" + text + "\"";
	}
}
