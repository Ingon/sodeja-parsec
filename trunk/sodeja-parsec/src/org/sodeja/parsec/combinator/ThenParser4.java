package org.sodeja.parsec.combinator;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.functional.Function4;
import org.sodeja.functional.Pair;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.Parser;

public class ThenParser4<Tok, Res, Res1, Res2, Res3, Res4> extends AbstractParser<Tok, Res> {

	private final Parser<Tok, Res1> first;
	private final Parser<Tok, Res2> second;
	private final Parser<Tok, Res3> third;
	private final Parser<Tok, Res4> fourth;
	private final Function4<Res, Res1, Res2, Res3, Res4> combinator;
	
	public ThenParser4(final String name, final Parser<Tok, Res1> first, final Parser<Tok, Res2> second, 
			final Parser<Tok, Res3> third, final Parser<Tok, Res4> fourth, 
			final Function4<Res, Res1, Res2, Res3, Res4> combinator) {
		super(name);
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
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
					List<Pair<Res4, ConsList<Tok>>> fourthResult = fourth.execute(thirdPair.second);
					
					for(Pair<Res4, ConsList<Tok>> fourthPair : fourthResult) {
						result.add(new Pair<Res, ConsList<Tok>>(
								combinator.execute(firstPair.first, secondPair.first, thirdPair.first, fourthPair.first),
								fourthPair.second
								));
					}
				}
			}
		}
		
		return result;
	}
}
