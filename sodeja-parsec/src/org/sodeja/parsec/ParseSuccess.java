package org.sodeja.parsec;

import org.sodeja.collections.ConsList;

public class ParseSuccess<Tok, Res> implements ParsingResult<Tok, Res> {
	private static final long serialVersionUID = -7268903956422415996L;
	
	public final Res result;
	public final ConsList<Tok> tokens;

	public ParseSuccess(Res result, ConsList<Tok> tokens) {
		this.result = result;
		this.tokens = tokens;
	}
}
