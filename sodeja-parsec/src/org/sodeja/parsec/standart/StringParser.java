package org.sodeja.parsec.standart;

import org.sodeja.collections.ConsList;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.ParseError;
import org.sodeja.parsec.ParseSuccess;
import org.sodeja.parsec.ParsingResult;

public class StringParser extends AbstractParser<String, String> {
	public StringParser(String name) {
		super(name);
	}

	@Override
	protected ParsingResult<String, String> executeDelegate(ConsList<String> tokens) {
		String token = tokens.get(0);
		if(token.startsWith("\"") && token.endsWith("\"")) {
			return new ParseSuccess<String, String>(token.substring(1, token.length() - 1), tokens.tail());
		}
		
		return new ParseError<String, String>("Expects an string in \"...\" form!");
	}
}
