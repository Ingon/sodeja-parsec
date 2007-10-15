package org.sodeja.parsec.semantic;

import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.functional.Pair;
import org.sodeja.parsec.Parser;

public abstract class AbstractSemanticParser<T, K> {
	protected abstract Parser<T, K> getParser();
	
	public final K parse(final List<T> tokensList) {
		ConsList<T> tokens = ConsList.createList(tokensList);
		List<Pair<K, ConsList<T>>> parseResults = getParser().execute(tokens);
		for(Pair<K, ConsList<T>> result : parseResults) {
			if(result.second.isEmpty()) {
				return result.first;
			}
		}
		
		throw new RuntimeException("Syntax error!");
	}
}
