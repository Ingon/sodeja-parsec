package org.sodeja.parsec.combinator;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.functional.Function1;
import org.sodeja.functional.Pair;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.Parser;

public class ApplyParser<Tok, Res, Res1> extends AbstractParser<Tok, Res> {

	private final Parser<Tok, Res1> parser;
	private final Function1<Res, Res1> functor;
	
	public ApplyParser(final String name, final Parser<Tok, Res1> parser, final Function1<Res, Res1> functor) {
		super(name);
		this.parser = parser;
		this.functor = functor;
	}

	@Override
	protected List<Pair<Res, ConsList<Tok>>> executeDelegate(ConsList<Tok> tokens) {
		List<Pair<Res1, ConsList<Tok>>> parserResult = parser.execute(tokens);
		
		List<Pair<Res, ConsList<Tok>>> result = new ArrayList<Pair<Res,ConsList<Tok>>>();
		for(Pair<Res1, ConsList<Tok>> parserPair : parserResult) {
			result.add(new Pair<Res, ConsList<Tok>>(functor.execute(parserPair.first), parserPair.second));
		}
		
		return result;
	}
}
