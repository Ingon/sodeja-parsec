package org.sodeja.parsec.standart;

import org.sodeja.collections.ConsList;
import org.sodeja.math.Rational;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.ParseError;
import org.sodeja.parsec.ParseSuccess;
import org.sodeja.parsec.ParsingResult;

public class RationalParser extends AbstractParser<String, Rational> {
	public RationalParser(String name) {
		super(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ParsingResult<String, Rational> executeDelegate(ConsList<String> tokens) {
		String head = tokens.head();
		try {
			Rational value = new Rational(head);
			return new ParseSuccess<String, Rational>(value, tokens.tail());
		} catch(NumberFormatException exc) {
			return new ParseError<String, Rational>("The token was not an rational!");
		}
	}
}
