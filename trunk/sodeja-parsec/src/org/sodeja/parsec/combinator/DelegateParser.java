package org.sodeja.parsec.combinator;

import org.sodeja.collections.ConsList;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.ParsingResult;

public class DelegateParser<Tok, Res> implements Parser<Tok, Res> {
	public Parser<Tok, Res> delegate;
	
	private final String name;
	
	public DelegateParser(String name) {
		this.name = name;
	}
	
	public ParsingResult<Tok, Res> execute(ConsList<Tok> tokens) {
		return delegate.execute(tokens);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Parser: " + name;
	}
}
