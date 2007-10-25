package org.sodeja.parsec.semantic;

import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.parsec.ParseError;
import org.sodeja.parsec.ParseSuccess;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.ParsingResult;

public abstract class AbstractSemanticParser<T, K> {
	protected abstract Parser<T, K> getParser();
	
	public final K parse(final List<T> tokensList) {
		ConsList<T> tokens = ConsList.createList(tokensList);
		return parseTokens(tokens);
	}

	public final K parseTokens(final ConsList<T> tokens) {
		return applyParser(getParser(), tokens);
	}
	
	public static final <T, K> K applyParser(Parser<T, K> parser, ConsList<T> tokens) {
		ParsingResult<T, K> parseResults = parser.execute(tokens);
		if(parseResults instanceof ParseSuccess) {
			return ((ParseSuccess<T, K>) parseResults).result;
		}

		throw new RuntimeException(((ParseError<T, K>) parseResults).error);
	}
}
