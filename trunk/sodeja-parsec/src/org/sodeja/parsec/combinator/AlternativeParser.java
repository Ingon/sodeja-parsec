package org.sodeja.parsec.combinator;

import org.sodeja.collections.ConsList;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.ParseError;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.ParsingResult;

public class AlternativeParser<Tok, Res> extends AbstractParser<Tok, Res> {
	
	private final Parser<Tok, Res> first;
	private final Parser<Tok, Res> second;
	
	public AlternativeParser(final String name, final Parser<Tok, Res> first, final Parser<Tok, Res> second) {
		super(name);
		this.first = first;
		this.second = second;
	}

	@Override
	public ParsingResult<Tok, Res> execute(ConsList<Tok> tokens) {
		return executeDelegate(tokens);
	}

	@Override
	protected ParsingResult<Tok, Res> executeDelegate(ConsList<Tok> tokens) {
		ParsingResult<Tok, Res> firstExecute = first.execute(tokens);
		if(isSuccess(firstExecute)) {
			return firstExecute;
		}
		
		ParsingResult<Tok, Res> secondExecute = second.execute(tokens);
		if(isSuccess(secondExecute)) {
			return secondExecute;
		}
		
		return new ParseError<Tok, Res>("Expecting " + first.getName() + " or " + second.getName());
	}
}
