package org.sodeja.parsec;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.functional.Pair;

public class ZeroOrMoreWithSeparatorParser<Tok, Res, Res1> extends AbstractParser<Tok, List<Res>> {

	private final Parser<Tok, Res> internal;
	private final Parser<Tok, Res1> separator;

	public ZeroOrMoreWithSeparatorParser(final String name, final Parser<Tok, Res> internal, final Parser<Tok, Res1> separator) {
		super(name);
		this.internal = internal;
		this.separator = separator;
	}

	@Override
	protected List<Pair<List<Res>, ConsList<Tok>>> executeDelegate(ConsList<Tok> tokens) {
		List<Res> results = new ArrayList<Res>();
		ConsList<Tok> tempTokens = tokens;
		
		List<Pair<Res, ConsList<Tok>>> internalResult = internal.execute(tempTokens);
		if(internalResult.isEmpty()) {
			List<Pair<List<Res>, ConsList<Tok>>> result = new ArrayList<Pair<List<Res>, ConsList<Tok>>>();
			result.add(new Pair<List<Res>, ConsList<Tok>>(null, tempTokens));
			return result;
		}
		
		// FIXME should inspect all possible results
		results.add(internalResult.get(0).first);
		tempTokens = internalResult.get(0).second;
		
		for(List<Pair<Res1, ConsList<Tok>>> separatorResult = separator.execute(tempTokens); 
				! separatorResult.isEmpty(); 
				separatorResult = separator.execute(tempTokens)) {
			
			// TODO Maybe a loop ?
			Pair<Res1, ConsList<Tok>> separatorPair = separatorResult.get(0);
			tempTokens = separatorPair.second;
			
			internalResult = internal.execute(tempTokens);
			if(internalResult.isEmpty()) {
				return EMPTY;
			}
			
			results.add(internalResult.get(0).first);
			tempTokens = internalResult.get(0).second;
		}
		
		List<Pair<List<Res>, ConsList<Tok>>> result = new ArrayList<Pair<List<Res>, ConsList<Tok>>>();
		result.add(new Pair<List<Res>, ConsList<Tok>>(results, tempTokens));
		return result;
	}
}
