package org.sodeja.parsec2;

import org.sodeja.collections.ConsList;

public class ParserCombinators {
	public static Parser matchUnless(final Parser p1, final Parser p2) {
		return new AbstractParser("match " + p1.getName() + " unless " + p2.getName()) {
			@Override
			public ParseResult match(ConsList tokens) {
				ParseResult res = p2.match(tokens);
				if(isSuccess(res)) {
					return fail("Matches " + p2.getName(), tokens);
				}
				return p1.match(tokens);
			}};
	}

	public static Parser or(Parser... parsers) {
		throw new UnsupportedOperationException();
	}

	public static Parser then(Parser... parsers) {
		throw new UnsupportedOperationException();
	}
	
	public static boolean isSuccess(ParseResult res) {
		return res instanceof ParseSuccess;
	}

	public static boolean isFailure(ParseResult res) {
		return res instanceof ParseError;
	}
	
	public static ParseError fail(String reason, ConsList tokens) {
		return new ParseError(reason, tokens);
	}
	
	public static ParseSuccess success(Object val, ConsList tokens) {
		return new ParseSuccess(val, tokens);
	}
}