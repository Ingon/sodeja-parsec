package org.sodeja.parsec.examples.bp.model;

public class Property implements Expression {
	public final String name;

	public Property(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
