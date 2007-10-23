package org.sodeja.parsec.combinator;

import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.functional.Pair;
import org.sodeja.functional.Predicate1;

public class SatisfiesParser<Tok> extends AbstractParser<Tok, Tok> {

	private final Predicate1<Tok> functor;
	
	public SatisfiesParser(final String name, final Predicate1<Tok> functor) {
		super(name);
		this.functor = functor;
	}

	@Override
	protected List<Pair<Tok, ConsList<Tok>>> executeDelegate(ConsList<Tok> tokens) {
		if(functor.execute(tokens.get(0))) {
			return createWithRemove(tokens.get(0), tokens);
		}

		return EMPTY;
	}
}
