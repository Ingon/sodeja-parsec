package org.sodeja.parsec.standart;

import java.math.BigDecimal;
import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Pair;
import org.sodeja.parsec.AbstractParser;

public class BigDecimalParser extends AbstractParser<String, BigDecimal> {
	public BigDecimalParser(String name) {
		super(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<Pair<BigDecimal, ConsList<String>>> executeDelegate(ConsList<String> tokens) {
		String head = tokens.getHead();
		try {
			BigDecimal value = new BigDecimal(head);
			return ListUtils.asList(new Pair<BigDecimal, ConsList<String>>(value, tokens.getTail()));
		} catch(NumberFormatException exc) {
			return EMPTY;
		}
	}
}
