package org.sodeja.parsec.combinator;

import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.functional.Pair;
import org.sodeja.parsec.AbstractParser;

public class EmptyParser<Tok, Res> extends AbstractParser<Tok, Res> {
	
	private final List<Pair<Res, ConsList<Tok>>> result;
	
	public EmptyParser(final String name, final List<Pair<Res, ConsList<Tok>>> result) {
		super(name);
		this.result = result;
	}

	@Override
	protected List<Pair<Res, ConsList<Tok>>> executeDelegate(ConsList<Tok> tokens) {
		return result;
	}
}
