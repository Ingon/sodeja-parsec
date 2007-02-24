package org.sodeja.parsec.standart;

import java.util.List;

import org.sodeja.functional.Pair;
import org.sodeja.parsec.AbstractParser;

public class StringParser extends AbstractParser<String, String> {
	public StringParser(String name) {
		super(name);
	}

	@Override
	protected List<Pair<String, List<String>>> executeDelegate(List<String> tokens) {
		String token = tokens.get(0);
		if(token.startsWith("\"") && token.endsWith("\"")) {
			return createWithRemove(token.substring(1, token.length() - 1), tokens);
		}
		return EMPTY;
	}
}
