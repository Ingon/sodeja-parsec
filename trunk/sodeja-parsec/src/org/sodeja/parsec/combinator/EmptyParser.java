package org.sodeja.parsec.combinator;

import org.sodeja.collections.ConsList;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.ParseSuccess;
import org.sodeja.parsec.ParsingResult;

public class EmptyParser<Tok, Res> extends AbstractParser<Tok, Res> {
	
	private final Res result;
	
	public EmptyParser(final String name, final Res result) {
		super(name);
		this.result = result;
	}

	@Override
	public ParsingResult<Tok, Res> execute(ConsList<Tok> tokens) {
		return executeDelegate(tokens);
	}

	@Override
	protected ParsingResult<Tok, Res> executeDelegate(ConsList<Tok> tokens) {
		return new ParseSuccess<Tok, Res>(result, tokens);
	}
}
