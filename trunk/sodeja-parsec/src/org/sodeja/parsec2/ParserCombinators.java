package org.sodeja.parsec2;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.lang.StringUtils;

public class ParserCombinators {
	public static Parser then(String name, final Parser... parsers) {
		return new AbstractParser(name) {
			@Override
			public ParseResult match(ConsList tokens) {
				ConsList temp = tokens;
				List results = new ArrayList();
				for(Parser parser : parsers) {
					ParseResult result = parser.match(temp);
					if(isFailure(result)) {
						return fail("Parser " + parser.getName() + " failed with " + getFailure(result), temp);
					}
					
					results.add(getSuccess(result));
					temp = getTokens(result);
				}
				
				return success(results, temp);
			}};
	}
	
	public static Parser then(final Parser... parsers) {
		return then("match " + appendParsers(parsers), parsers);
	}

	public static Parser or(String name, final Parser... parsers) {
		return new AbstractParser(name) {
			@Override
			public ParseResult match(ConsList tokens) {
				for(Parser parser : parsers) {
					ParseResult result = parser.match(tokens);
					if(isSuccess(result)) {
						return result;
					}
				}
				return fail("Did not " + getName(), tokens);
			}};
	}

	public static Parser or(final Parser... parsers) {
		return or("match one of " + appendParsers(parsers), parsers);
	}
	
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
	
	public static Parser optional(final Parser parser) {
		return new AbstractParser("optional " + parser.getName()) {
			@Override
			public ParseResult match(ConsList tokens) {
				ParseResult pr = parser.match(tokens);
				if(isSuccess(pr)) {
					return pr;
				}
				return success(null, tokens);
			}};
	}
	
	public static Parser repeated(final Parser parser) {
		return new AbstractParser("repeated " + parser.getName()) {
			@Override
			public ParseResult match(ConsList tokens) {
				ConsList temp = tokens;
				List results = new ArrayList();
				for(ParseResult pr = parser.match(temp); isSuccess(pr); pr = parser.match(temp)) {
					results.add(getSuccess(pr));
					temp = getTokens(pr);
				}
				return success(results, temp);
			}
		};
	}
	
	public static Parser repeatedThen(final Parser... parsers) {
		return repeated(then(parsers));
	}

	public static Parser optionalThen(final Parser... parsers) {
		return optional(then(parsers));
	}
	
	public static boolean isSuccess(ParseResult res) {
		return res instanceof ParseSuccess;
	}

	public static Object getSuccess(ParseResult res) {
		if(isFailure(res)) {
			throw new RuntimeException("result is not a success");
		}
		return ((ParseSuccess) res).result;
	}

	public static ConsList getTokens(ParseResult res) {
		if(isFailure(res)) {
			throw new RuntimeException("result is not a success");
		}
		return ((ParseSuccess) res).tokens;
	}
	
	public static boolean isFailure(ParseResult res) {
		return res instanceof ParseError;
	}
	
	public static String getFailure(ParseResult res) {
		if(isSuccess(res)) {
			throw new RuntimeException("result is not a failure");
		}
		return ((ParseError) res).error;
	}
	
	public static ParseError fail(String reason, ConsList tokens) {
		return new ParseError(reason, tokens);
	}
	
	public static ParseSuccess success(Object val, ConsList tokens) {
		return new ParseSuccess(val, tokens);
	}
	
	private static String appendParsers(final Parser... parsers) {
		return StringUtils.appendWithSeparator(ListUtils.asList(parsers), ",", new Function1<String, Parser>() {
			@Override
			public String execute(Parser p) {
				return p.getName();
			}});
	}
}