package org.sodeja.parsec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.sodeja.functional.Pair;

public abstract class AbstractParser<Tok, Res> implements Parser<Tok, Res> {
	
	protected final List<Pair<Res, List<Tok>>> EMPTY = Collections.unmodifiableList(new ArrayList<Pair<Res, List<Tok>>>());
	
	private String name;
	
	public AbstractParser(final String name) {
		this.name = name;
	}
	
	public List<Pair<Res, List<Tok>>> execute(List<Tok> tokens) {
		if(tokens.isEmpty()) {
			return EMPTY;
		}
		return executeDelegate(tokens);
	}
	
	protected abstract List<Pair<Res, List<Tok>>> executeDelegate(List<Tok> tokens);
	
	public static <Tok, Res> List<Pair<Res, List<Tok>>> create(Res value, List<Tok> tokens) {
		List<Pair<Res, List<Tok>>> result = new ArrayList<Pair<Res, List<Tok>>>();
		result.add(new Pair<Res, List<Tok>>(value, tokens));
		return result;
	}
	
	public static <Tok, Res> List<Pair<Res, List<Tok>>> createWithRemove(Res value, List<Tok> tokens) {
		List<Pair<Res, List<Tok>>> result = new ArrayList<Pair<Res, List<Tok>>>();
		
		List<Tok> tokenResult = new ArrayList<Tok>(tokens);
		tokenResult.remove(0);		
		
		result.add(new Pair<Res, List<Tok>>(value, tokenResult));
		return result;
	}

	@Override
	public String toString() {
		return "Parser: " + name;
	}
}
