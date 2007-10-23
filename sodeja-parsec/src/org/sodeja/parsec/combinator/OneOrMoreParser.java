package org.sodeja.parsec.combinator;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.functional.Pair;
import org.sodeja.parsec.Parser;

public class OneOrMoreParser<Tok, Res> extends AbstractParser<Tok, List<Res>> {

	private final Parser<Tok, Res> internal;

	public OneOrMoreParser(final String name, final Parser<Tok, Res> internal) {
		super(name);
		this.internal = internal;
	}
	
	@Override
	protected List<Pair<List<Res>, ConsList<Tok>>> executeDelegate(ConsList<Tok> tokens) {
		ConsList<Tok> tempTokens = tokens;
		List<Res> tempResult = new ArrayList<Res>();
		for(List<Pair<Res, ConsList<Tok>>> internalResult = internal.execute(tempTokens); 
				! internalResult.isEmpty(); 
				internalResult = internal.execute(tempTokens)) {
			
			tempResult.add(internalResult.get(0).first);
			tempTokens = internalResult.get(0).second;
		}
		
		if(tempResult.size() == 0) {
			return EMPTY;
		}
		
		return create(tempResult, tempTokens);
	}
}
