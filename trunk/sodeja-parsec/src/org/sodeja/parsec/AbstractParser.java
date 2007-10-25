package org.sodeja.parsec;

import org.sodeja.collections.ConsList;

public abstract class AbstractParser<Tok, Res> implements Parser<Tok, Res> {
	
//	protected final List<Pair<Res, ConsList<Tok>>> EMPTY = Collections.unmodifiableList(new ArrayList<Pair<Res, ConsList<Tok>>>());
	
	private final String name;
	
	public AbstractParser(final String name) {
		this.name = name;
	}
	
	public ParsingResult<Tok, Res> execute(ConsList<Tok> tokens) {
		if(tokens == null || tokens.isEmpty()) {
			return emptyError();
		}
		return executeDelegate(tokens);
	}
	
	protected abstract ParsingResult<Tok, Res> executeDelegate(ConsList<Tok> tokens);
	
	protected ParseError<Tok, Res> emptyError() {
		return new ParseError<Tok, Res>(getName() + " expects additional input!");
	}
	
//	public static <Tok, Res> List<Pair<Res, ConsList<Tok>>> create(Res value, ConsList<Tok> tokens) {
//		List<Pair<Res, ConsList<Tok>>> result = new ArrayList<Pair<Res, ConsList<Tok>>>();
//		result.add(new Pair<Res, ConsList<Tok>>(value, tokens));
//		return result;
//	}
//	
//	public static <Tok, Res> List<Pair<Res, ConsList<Tok>>> createWithRemove(Res value, ConsList<Tok> tokens) {
//		List<Pair<Res, ConsList<Tok>>> result = new ArrayList<Pair<Res, ConsList<Tok>>>();
//		result.add(new Pair<Res, ConsList<Tok>>(value, tokens.getTail()));
//		return result;
//	}

	public String getName() {
		return name;
	}

	protected boolean isSuccess(ParsingResult<?, ?> result) {
		return result instanceof ParseSuccess;
	}

	protected boolean isFailure(ParsingResult<?, ?> result) {
		return result instanceof ParseError;
	}

	protected String getFailure(ParsingResult<?, ?> result) {
		return ((ParseError<?, ?>) result).error;
	}
	
	protected ParsingResult<Tok, Res> createFailure(ParsingResult<?, ?> result) {
		return new ParseError<Tok, Res>(getFailure(result));
	}
	
	@Override
	public String toString() {
		return "Parser: " + name;
	}
}
