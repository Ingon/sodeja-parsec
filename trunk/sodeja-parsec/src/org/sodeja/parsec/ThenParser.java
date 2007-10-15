package org.sodeja.parsec;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.functional.Function2;
import org.sodeja.functional.Pair;

public class ThenParser<Tok, Res, Res1, Res2> extends AbstractParser<Tok, Res> {

	private final Parser<Tok, Res1> first;
	private final Parser<Tok, Res2> second;
	private final Function2<Res, Res1, Res2> combinator;
	
	public ThenParser(final String name, final Parser<Tok, Res1> first, final Parser<Tok, Res2> second, final Function2<Res, Res1, Res2> combinator) {
		super(name);
		this.first = first;
		this.second = second;
		this.combinator = combinator;
	}

	@Override
	protected List<Pair<Res, ConsList<Tok>>> executeDelegate(ConsList<Tok> tokens) {
		List<Pair<Res, ConsList<Tok>>> result = new ArrayList<Pair<Res, ConsList<Tok>>>();
		
		List<Pair<Res1, ConsList<Tok>>> firstResult = first.execute(tokens);
		for(Pair<Res1, ConsList<Tok>> firstPair : firstResult) {
			List<Pair<Res2, ConsList<Tok>>> secondResult = second.execute(firstPair.second);
			
			for(Pair<Res2, ConsList<Tok>> secondPair : secondResult) {
				result.add(new Pair<Res, ConsList<Tok>>(
						combinator.execute(firstPair.first, secondPair.first), 
						secondPair.second));
			}
		}
		
		return result;
	}
}
