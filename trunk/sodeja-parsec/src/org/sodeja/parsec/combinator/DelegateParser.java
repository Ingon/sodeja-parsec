package org.sodeja.parsec.combinator;

import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.functional.Pair;
import org.sodeja.parsec.Parser;

public class DelegateParser<Tok, Res> implements Parser<Tok, Res> {
	public Parser<Tok, Res> delegate;
	
	private final String name;
	
	public DelegateParser(String name) {
		this.name = name;
	}
	
	public List<Pair<Res, ConsList<Tok>>> execute(ConsList<Tok> tokens) {
		return delegate.execute(tokens);
	}
	
	@Override
	public String toString() {
		return "Parser: " + name;
	}
}
