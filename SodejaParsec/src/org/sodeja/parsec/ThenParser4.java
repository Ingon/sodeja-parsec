package org.sodeja.parsec;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.functional.Function4;
import org.sodeja.functional.Pair;

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
	protected List<Pair<Res, List<Tok>>> executeDelegate(List<Tok> tokens) {
		List<Pair<Res, List<Tok>>> result = new ArrayList<Pair<Res, List<Tok>>>();
		
		List<Pair<Res1, List<Tok>>> firstResult = first.execute(tokens);
		for(Pair<Res1, List<Tok>> firstPair : firstResult) {
			List<Pair<Res2, List<Tok>>> secondResult = second.execute(firstPair.second);
			
			for(Pair<Res2, List<Tok>> secondPair : secondResult) {
				List<Pair<Res3, List<Tok>>> thirdResult = third.execute(secondPair.second);
				
				for(Pair<Res3, List<Tok>> thirdPair : thirdResult) {
					List<Pair<Res4, List<Tok>>> fourthResult = fourth.execute(thirdPair.second);
					
					for(Pair<Res4, List<Tok>> fourthPair : fourthResult) {
						result.add(new Pair<Res, List<Tok>>(
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
