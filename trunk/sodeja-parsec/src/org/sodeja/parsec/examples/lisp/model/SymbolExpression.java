package org.sodeja.parsec.examples.lisp.model;

public class SymbolExpression implements SimpleExpression {
	
	public final String name;
	
	public SymbolExpression(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
