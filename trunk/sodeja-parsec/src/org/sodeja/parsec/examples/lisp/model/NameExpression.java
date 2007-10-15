package org.sodeja.parsec.examples.lisp.model;

public class NameExpression implements SimpleExpression {
	
	public final String name;
	
	public NameExpression(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
