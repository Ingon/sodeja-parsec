package org.sodeja.parsec.combinator;

import org.sodeja.collections.ConsList;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.ParseSuccess;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.ParsingResult;

public class ZeroOrOneParser<Tok, Res> extends AbstractParser<Tok, Res> {

	private final Parser<Tok, Res> subparser;
	
	public ZeroOrOneParser(String name, Parser<Tok, Res> subparser) {
		super(name);
		this.subparser = subparser;
	}

	@Override
	public ParsingResult<Tok, Res> execute(ConsList<Tok> tokens) {
		return executeDelegate(tokens);
	}

	@Override
	protected ParsingResult<Tok, Res> executeDelegate(ConsList<Tok> tokens) {
		ParsingResult<Tok, Res> result = subparser.execute(tokens);
		if(isSuccess(result)) {
			return result;
		}

		return new ParseSuccess<Tok, Res>(null, tokens);
	}
}
