package org.sodeja.parsec;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.functional.Pair;

public class ZeroOrMoreParser<Tok, Res> extends AbstractParser<Tok, List<Res>> {

	private final Parser<Tok, Res> internal;

	public ZeroOrMoreParser(final String name, final Parser<Tok, Res> internal) {
		super(name);
		this.internal = internal;
	}

	// TODO is this ok ?
	@Override
	public List<Pair<List<Res>, List<Tok>>> execute(List<Tok> tokens) {
		return executeDelegate(tokens);
	}

	// TODO refactor to use OneOrMoreParser + EmptyParser
	@Override
	protected List<Pair<List<Res>, List<Tok>>> executeDelegate(List<Tok> tokens) {
		List<Tok> tempTokens = tokens;
		List<Res> tempResult = new ArrayList<Res>();
		for(List<Pair<Res, List<Tok>>> internalResult = internal.execute(tempTokens); 
				! internalResult.isEmpty(); 
				internalResult = internal.execute(tempTokens)) {
			
			tempResult.add(internalResult.get(0).first);
			tempTokens = internalResult.get(0).second;
		}
		
		return create(tempResult, tempTokens);
	}
}
