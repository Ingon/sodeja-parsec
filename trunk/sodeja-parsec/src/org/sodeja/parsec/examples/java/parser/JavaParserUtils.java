package org.sodeja.parsec.examples.java.parser;

import org.sodeja.collections.ConsList;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.ParseError;
import org.sodeja.parsec.ParseSuccess;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.ParsingResult;
import org.sodeja.parsec.examples.java.lexer.model.Keywords;
import org.sodeja.parsec.examples.java.lexer.model.Operators;
import org.sodeja.parsec.examples.java.lexer.model.Separators;
import org.sodeja.parsec.examples.java.lexer.model.Token;
import org.sodeja.parsec.examples.java.lexer.model.Token.Keyword;
import org.sodeja.parsec.examples.java.lexer.model.Token.Operator;
import org.sodeja.parsec.examples.java.lexer.model.Token.Separator;

public class JavaParserUtils {
	public static Parser<Token<?>, Token<?>> separator(final Separators sep) {
		return new AbstractParser<Token<?>, Token<?>>(sep.name()) {
			@Override
			protected ParsingResult<Token<?>, Token<?>> executeDelegate(ConsList<Token<?>> tokens) {
				if(! Separator.class.isInstance(tokens.head())) {
					return new ParseError<Token<?>, Token<?>>("Expecting " + sep, tokens);
				}
				
				Separator realSep = (Separator) tokens.head();
				if(realSep.value != sep) {
					return new ParseError<Token<?>, Token<?>>("Expecting " + sep, tokens);
				}
				
				return new ParseSuccess<Token<?>, Token<?>>(tokens.head(), tokens.tail());
			}
		};
	}

	public static Parser<Token<?>, Token<?>> keyword(final Keywords key) {
		return new AbstractParser<Token<?>, Token<?>>(key.name()) {
			@Override
			protected ParsingResult<Token<?>, Token<?>> executeDelegate(ConsList<Token<?>> tokens) {
				if(! Keyword.class.isInstance(tokens.head())) {
					return new ParseError<Token<?>, Token<?>>("Expecting " + key, tokens);
				}
				
				Keyword realSep = (Keyword) tokens.head();
				if(realSep.value != key) {
					return new ParseError<Token<?>, Token<?>>("Expecting " + key, tokens);
				}
				
				return new ParseSuccess<Token<?>, Token<?>>(tokens.head(), tokens.tail());
			}
		};
	}

	public static Parser<Token<?>, Token<?>> operator(final Operators op) {
		return new AbstractParser<Token<?>, Token<?>>(op.name()) {
			@Override
			protected ParsingResult<Token<?>, Token<?>> executeDelegate(ConsList<Token<?>> tokens) {
				if(! Operator.class.isInstance(tokens.head())) {
					return new ParseError<Token<?>, Token<?>>("Expecting " + op, tokens);
				}
				
				Operator realSep = (Operator) tokens.head();
				if(realSep.value != op) {
					return new ParseError<Token<?>, Token<?>>("Expecting " + op, tokens);
				}
				
				return new ParseSuccess<Token<?>, Token<?>>(tokens.head(), tokens.tail());
			}
		};
	}
}
