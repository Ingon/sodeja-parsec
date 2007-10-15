package org.sodeja.parsec.standart;

import java.math.BigInteger;
import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Pair;
import org.sodeja.parsec.AbstractParser;

public class BigIntegerParser extends AbstractParser<String, BigInteger> {
	public BigIntegerParser(String name) {
		super(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<Pair<BigInteger, ConsList<String>>> executeDelegate(ConsList<String> tokens) {
		String head = tokens.getHead();
		try {
			BigInteger value = new BigInteger(head);
			return ListUtils.asList(new Pair<BigInteger, ConsList<String>>(value, tokens.getTail()));
		} catch(NumberFormatException exc) {
			return EMPTY;
		}
	}
}
