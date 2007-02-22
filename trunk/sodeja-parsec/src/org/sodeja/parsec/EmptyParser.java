package org.sodeja.parsec;

import java.util.List;

import org.sodeja.functional.Pair;

public class EmptyParser<Tok, Res> extends AbstractParser<Tok, Res> {
	
	private final List<Pair<Res, List<Tok>>> result;
	
	public EmptyParser(final String name, final List<Pair<Res, List<Tok>>> result) {
		super(name);
		this.result = result;
	}

	@Override
	protected List<Pair<Res, List<Tok>>> executeDelegate(List<Tok> tokens) {
		return result;
	}
}
