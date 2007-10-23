package org.sodeja.parsec.combinator;

import org.sodeja.collections.ConsList;
import org.sodeja.functional.Predicate1;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.ParseError;
import org.sodeja.parsec.ParseSuccess;
import org.sodeja.parsec.ParsingResult;

public class SatisfiesParser<Tok> extends AbstractParser<Tok, Tok> {

	private final Predicate1<Tok> functor;
	
	public SatisfiesParser(final String name, final Predicate1<Tok> functor) {
		super(name);
		this.functor = functor;
	}

	@Override
	protected ParsingResult<Tok, Tok> executeDelegate(ConsList<Tok> tokens) {
		if(functor.execute(tokens.get(0))) {
			return new ParseSuccess<Tok, Tok>(tokens.getHead(), tokens.getTail());
		}

		return new ParseError<Tok, Tok>("Expecting " + getName());
	}
}
