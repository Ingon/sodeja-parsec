package org.sodeja.parsec2;

import org.sodeja.collections.ConsList;

public class DelegateParser extends AbstractParser {
	
	public Parser delegate;
	
	public DelegateParser(String name) {
		super(name);
	}

	@Override
	public ParseResult match(ConsList tokens) {
		return delegate.match(tokens);
	}
}
