package org.sodeja.parsec;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.functional.Function1;
import org.sodeja.functional.Pair;

public class ApplyParser<Tok, Res, Res1> extends AbstractParser<Tok, Res> {

	private final Parser<Tok, Res1> parser;
	private final Function1<Res, Res1> functor;
	
	public ApplyParser(final String name, final Parser<Tok, Res1> parser, final Function1<Res, Res1> functor) {
		super(name);
		this.parser = parser;
		this.functor = functor;
	}

	@Override
	protected List<Pair<Res, List<Tok>>> executeDelegate(List<Tok> tokens) {
		List<Pair<Res1, List<Tok>>> parserResult = parser.execute(tokens);
		
		List<Pair<Res, List<Tok>>> result = new ArrayList<Pair<Res,List<Tok>>>();
		for(Pair<Res1, List<Tok>> parserPair : parserResult) {
			result.add(new Pair<Res, List<Tok>>(functor.execute(parserPair.first), parserPair.second));
		}
		
		return result;
	}
}
