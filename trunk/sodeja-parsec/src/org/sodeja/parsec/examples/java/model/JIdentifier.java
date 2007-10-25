package org.sodeja.parsec.examples.java.model;

import org.sodeja.parsec.examples.java.lexer.model.Token.Identifier;

public class JIdentifier {
	
	public final String value;
	
	public JIdentifier(Identifier value) {
		this.value = value.value;
	}

	@Override
	public String toString() {
		return value;
	}
}
