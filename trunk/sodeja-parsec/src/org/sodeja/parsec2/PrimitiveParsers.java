package org.sodeja.parsec2;

import static org.sodeja.parsec2.ParserCombinators.*;

import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.functional.Function1;

public class PrimitiveParsers {
	public static Parser anyCharacter() {
		return new AbstractParser("any char") {
			@Override
			public ParseResult match(ConsList tokens) {
				Character ich = (Character) tokens.head();
				if(ich == null) {
					return fail(name + " expected, no tokens left", tokens);
				}
				return success(ich, tokens.tail());
			}};
	}
	
	public static Parser character(final char ch) {
		return new AbstractParser("char " + ch) {
			@Override
			public ParseResult match(ConsList tokens) {
				Character ich = (Character) tokens.head();
				if(ich == null) {
					return fail(name + " expected, no tokens left", tokens);
				}
				
				if(ch == ich) {
					return success(ich, tokens.tail());
				} else {
					return fail(name + " expected", tokens);
				}
			}
		};
	}
	
	public static Parser whitespaceCharacter() {
		return new AbstractParser("whitespace") {
			@Override
			public ParseResult match(ConsList tokens) {
				Character ich = (Character) tokens.head();
				if(ich == null) {
					return fail(name + " expected, no tokens left", tokens);
				}
				
				if(Character.isWhitespace(ich)) {
					return success(ich, tokens.tail());
				} else {
					return fail(name + " expected", tokens);
				}
			}};
	}
	
	public static Parser string(final String str) {
		Parser[] parsers = new Parser[str.length()];
		for(int i = 0, n = str.length();i < n;i++) {
			parsers[i] = character(str.charAt(i));
		}
		return then(str, parsers);
	}

	public static Parser stringAsString(final String str) {
		return apply(string(str), new Function1() {
			@Override
			public Object execute(Object p) {
				List vals = (List) p;
				char[] chs = new char[vals.size()];
				for(int i = 0;i < chs.length;i++) {
					chs[i] = (Character) vals.get(i);
				}
				return new String(chs);
			}});
	}
	
	public static Parser oneOfCharacters(final String str) {
		Parser[] parsers = new Parser[str.length()];
		for(int i = 0, n = str.length();i < n;i++) {
			parsers[i] = character(str.charAt(i));
		}
		return or("one of " + str, parsers);
	}
}
