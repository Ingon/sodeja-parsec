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
		ParsingResult<T, K> parseResults = getParser().execute(tokens);
		if(parseResults instanceof ParseSuccess) {
			return ((ParseSuccess<T, K>) parseResults).result;
		}

		throw new RuntimeException(((ParseError<T, K>) parseResults).error);
	}
}
