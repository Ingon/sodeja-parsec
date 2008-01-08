package org.sodeja.parsec2;

import org.sodeja.collections.ConsList;

public interface Parser {
	String getName();
	ParseResult match(ConsList tokens);
}
