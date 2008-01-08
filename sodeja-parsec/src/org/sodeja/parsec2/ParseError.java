package org.sodeja.parsec2;

import org.sodeja.collections.ConsList;

public class ParseError implements ParseResult {
	public final String error;
	public final ConsList tokens;

	public ParseError(String error, ConsList tokens) {
		this.error = error;
		this.tokens = tokens;
	}
}
