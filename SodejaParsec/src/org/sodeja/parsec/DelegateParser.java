package org.sodeja.parsec;

import java.util.List;

import org.sodeja.functional.Pair;

public class DelegateParser<Tok, Res> implements Parser<Tok, Res> {
	public Parser<Tok, Res> delegate;
	
	private final String name;
	
	public DelegateParser(String name) {
		this.name = name;
	}
	
	public List<Pair<Res, List<Tok>>> execute(List<Tok> tokens) {
		return delegate.execute(tokens);
	}
}
