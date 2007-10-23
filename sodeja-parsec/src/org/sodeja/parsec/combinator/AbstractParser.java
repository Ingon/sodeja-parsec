package org.sodeja.parsec.combinator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.functional.Pair;
import org.sodeja.parsec.Parser;

public abstract class AbstractParser<Tok, Res> implements Parser<Tok, Res> {
	
	protected final List<Pair<Res, ConsList<Tok>>> EMPTY = Collections.unmodifiableList(new ArrayList<Pair<Res, ConsList<Tok>>>());
	
	private String name;
	
	public AbstractParser(final String name) {
		this.name = name;
	}
	
	public List<Pair<Res, ConsList<Tok>>> execute(ConsList<Tok> tokens) {
		if(tokens.isEmpty()) {
			return EMPTY;
		}
		return executeDelegate(tokens);
	}
	
	protected abstract List<Pair<Res, ConsList<Tok>>> executeDelegate(ConsList<Tok> tokens);
	
	public static <Tok, Res> List<Pair<Res, ConsList<Tok>>> create(Res value, ConsList<Tok> tokens) {
		List<Pair<Res, ConsList<Tok>>> result = new ArrayList<Pair<Res, ConsList<Tok>>>();
		result.add(new Pair<Res, ConsList<Tok>>(value, tokens));
		return result;
	}
	
	public static <Tok, Res> List<Pair<Res, ConsList<Tok>>> createWithRemove(Res value, ConsList<Tok> tokens) {
		List<Pair<Res, ConsList<Tok>>> result = new ArrayList<Pair<Res, ConsList<Tok>>>();
		result.add(new Pair<Res, ConsList<Tok>>(value, tokens.getTail()));
		return result;
	}

	@Override
	public String toString() {
		return "Parser: " + name;
	}
}
