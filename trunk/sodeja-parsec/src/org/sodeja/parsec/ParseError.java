package org.sodeja.parsec;

import org.sodeja.collections.ConsList;

public class ParseError<Tok, Res> implements ParsingResult<Tok, Res> {
	public final String error;
	public final ConsList<Tok> tokens;

	public ParseError(String error, ConsList<Tok> tokens) {
		this.error = error;
		this.tokens = tokens;
	}
}
