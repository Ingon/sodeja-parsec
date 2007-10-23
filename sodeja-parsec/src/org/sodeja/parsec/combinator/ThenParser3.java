package org.sodeja.parsec.combinator;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.functional.Function3;
import org.sodeja.functional.Pair;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.Parser;

public class ThenParser3<Tok, Res, Res1, Res2, Res3> extends AbstractParser<Tok, Res> {

	private final Parser<Tok, Res1> first;
	private final Parser<Tok, Res2> second;
	private final Parser<Tok, Res3> third;
	private final Function3<Res, Res1, Res2, Res3> combinator;
	
	public ThenParser3(final String name, final Parser<Tok, Res1> first, final Parser<Tok, Res2> second, final Parser<Tok, Res3> third, final Function3<Res, Res1, Res2, Res3> combinator) {
		super(name);
		this.first = first;
		this.second = second;
		this.third = third;
		this.combinator = combinator;
	}

	@Override
	protected List<Pair<Res, ConsList<Tok>>> executeDelegate(ConsList<Tok> tokens) {
		List<Pair<Res, ConsList<Tok>>> result = new ArrayList<Pair<Res, ConsList<Tok>>>();
		
		List<Pair<Res1, ConsList<Tok>>> firstResult = first.execute(tokens);
		for(Pair<Res1, ConsList<Tok>> firstPair : firstResult) {
			List<Pair<Res2, ConsList<Tok>>> secondResult = second.execute(firstPair.second);
			
			for(Pair<Res2, ConsList<Tok>> secondPair : secondResult) {
				List<Pair<Res3, ConsList<Tok>>> thirdResult = third.execute(secondPair.second);
				
				for(Pair<Res3, ConsList<Tok>> thirdPair : thirdResult) {
					result.add(new Pair<Res, ConsList<Tok>>(
							combinator.execute(firstPair.first, secondPair.first, thirdPair.first),
							thirdPair.second));
				}
			}
		}
		
		return result;
	}
}
