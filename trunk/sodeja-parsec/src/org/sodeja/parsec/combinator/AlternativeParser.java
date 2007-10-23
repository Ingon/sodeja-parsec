package org.sodeja.parsec.combinator;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.functional.Pair;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.Parser;

public class AlternativeParser<Tok, Res> extends AbstractParser<Tok, Res> {
	
	private final Parser<Tok, Res> first;
	private final Parser<Tok, Res> second;
	
	public AlternativeParser(final String name, final Parser<Tok, Res> first, final Parser<Tok, Res> second) {
		super(name);
		this.first = first;
		this.second = second;
	}

	@Override
	protected List<Pair<Res, ConsList<Tok>>> executeDelegate(ConsList<Tok> tokens) {
		List<Pair<Res, ConsList<Tok>>> result = new ArrayList<Pair<Res, ConsList<Tok>>>();
		result.addAll(first.execute(tokens));
		result.addAll(second.execute(tokens));
		return result;
	}
}
