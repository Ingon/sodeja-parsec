package org.sodeja.parsec;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.functional.Pair;

public class AlternativeParser<Tok, Res> extends AbstractParser<Tok, Res> {
	
	private final Parser<Tok, Res> first;
	private final Parser<Tok, Res> second;
	
	public AlternativeParser(final String name, final Parser<Tok, Res> first, final Parser<Tok, Res> second) {
		super(name);
		this.first = first;
		this.second = second;
	}

	@Override
	protected List<Pair<Res, List<Tok>>> executeDelegate(List<Tok> tokens) {
		List<Pair<Res, List<Tok>>> firstResult = first.execute(tokens);
		List<Pair<Res, List<Tok>>> secondResult = second.execute(tokens);
		
		List<Pair<Res, List<Tok>>> result = new ArrayList<Pair<Res, List<Tok>>>();
		result.addAll(firstResult);
		result.addAll(secondResult);
		return result;
	}
}
