package org.sodeja.parsec.examples.java.model;

import org.sodeja.parsec.examples.java.lexer.model.Keywords;
import org.sodeja.parsec.examples.java.lexer.model.Token.Keyword;

public class JPrimitive {
	public Keywords keyword;
	
	public JPrimitive(Keyword word) {
		this.keyword = word.value;
	}

	@Override
	public String toString() {
		return keyword.name().toLowerCase();
	}
}
