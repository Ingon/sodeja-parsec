package org.sodeja.parsec.combinator;

import java.util.Collections;
import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.ParsingResult;

public class ZeroOrMoreParser<Tok, Res> extends AbstractParser<Tok, List<Res>> {

	private final AlternativeParser<Tok, List<Res>> delegate;

	public ZeroOrMoreParser(final String name, final Parser<Tok, Res> single) {
		super(name);
		
		OneOrMoreParser<Tok, Res> manyParser = new OneOrMoreParser<Tok, Res>(name + "_MANY", single);
		EmptyParser<Tok, List<Res>> emptyParser = new EmptyParser<Tok, List<Res>>(name + "_EMPTY", Collections.EMPTY_LIST);
		this.delegate = new AlternativeParser<Tok, List<Res>>(name + "ALTERNATIVE", manyParser, emptyParser);
	}

	@Override
	public ParsingResult<Tok, List<Res>> execute(ConsList<Tok> tokens) {
		return executeDelegate(tokens);
	}

	@Override
	protected ParsingResult<Tok, List<Res>> executeDelegate(ConsList<Tok> tokens) {
		return delegate.execute(tokens);
	}
}
