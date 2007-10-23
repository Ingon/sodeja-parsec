package org.sodeja.parsec;

public class ParseError<Tok, Res> implements ParsingResult<Tok, Res> {
	public final String error;

	public ParseError(String error) {
		this.error = error;
	}
}
