package org.sodeja.parsec.standart;

import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Pair;
import org.sodeja.math.Rational;
import org.sodeja.parsec.combinator.AbstractParser;

public class RationalParser extends AbstractParser<String, Rational> {
	public RationalParser(String name) {
		super(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<Pair<Rational, ConsList<String>>> executeDelegate(ConsList<String> tokens) {
		String head = tokens.getHead();
		try {
			Rational value = new Rational(head);
			return ListUtils.asList(new Pair<Rational, ConsList<String>>(value, tokens.getTail()));
		} catch(NumberFormatException exc) {
			return EMPTY;
		}
	}
}
