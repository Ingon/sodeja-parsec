package org.sodeja.parsec2;

import org.sodeja.collections.ConsList;

public class ParseSuccess implements ParseResult {
	public final Object result;
	public final ConsList tokens;

	public ParseSuccess(Object result, ConsList tokens) {
		this.result = result;
		this.tokens = tokens;
	}
}
