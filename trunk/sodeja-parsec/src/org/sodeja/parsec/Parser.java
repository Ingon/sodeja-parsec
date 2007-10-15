package org.sodeja.parsec;

import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.functional.Pair;

public interface Parser<Tok, Res> {
	public List<Pair<Res, ConsList<Tok>>> execute(ConsList<Tok> tokens);
}
