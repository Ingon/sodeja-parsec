package org.sodeja.parsec.examples.java.parser;

import org.sodeja.collections.ConsList;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.ParseError;
import org.sodeja.parsec.ParseSuccess;
import org.sodeja.parsec.ParsingResult;
import org.sodeja.parsec.examples.java.lexer.model.Token;

public class ClassParser extends AbstractParser<Token<?>, Token<?>> {
	
	private Class clazz;
	
	public ClassParser(String name, Class clazz) {
		super(name);
		this.clazz = clazz;
	}

	@Override
	protected ParsingResult<Token<?>, Token<?>> executeDelegate(ConsList<Token<?>> tokens) {
		if(clazz.isInstance(tokens.head())) {
			return new ParseSuccess<Token<?>, Token<?>>(tokens.head(), tokens.tail());
		}
		return new ParseError<Token<?>, Token<?>>("Expecting " + clazz);
	}
}
