package org.sodeja.parsec;

import org.sodeja.collections.ConsList;

public interface Parser<Tok, Res> {
	public ParsingResult<Tok, Res> execute(ConsList<Tok> tokens);
	public String getName();
}
