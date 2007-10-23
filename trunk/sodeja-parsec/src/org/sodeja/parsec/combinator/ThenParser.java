package org.sodeja.parsec.combinator;

import org.sodeja.collections.ConsList;
import org.sodeja.functional.Function2;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.ParseSuccess;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.ParsingResult;

public class ThenParser<Tok, Res, Res1, Res2> extends AbstractParser<Tok, Res> {

	private final Parser<Tok, Res1> first;
	private final Parser<Tok, Res2> second;
	private final Function2<Res, Res1, Res2> combinator;
	
	public ThenParser(final String name, final Parser<Tok, Res1> first, final Parser<Tok, Res2> second, final Function2<Res, Res1, Res2> combinator) {
		super(name);
		this.first = first;
		this.second = second;
		this.combinator = combinator;
	}

	@Override
	protected ParsingResult<Tok, Res> executeDelegate(ConsList<Tok> tokens) {
		ParsingResult<Tok, Res1> firstResult = first.execute(tokens);
		if(isFailure(firstResult)) {
			return createFailure(firstResult);
		}
		
		ParseSuccess<Tok, Res1> firstSuccess = (ParseSuccess<Tok, Res1>) firstResult;
		
		ParsingResult<Tok, Res2> secondResult = second.execute(firstSuccess.tokens);
		if(isFailure(secondResult)) {
			return createFailure(secondResult);
		}
		
		ParseSuccess<Tok, Res2> secondSuccess = (ParseSuccess<Tok, Res2>) secondResult;
		
		Res combinedResult = combinator.execute(firstSuccess.result, secondSuccess.result);
		return new ParseSuccess<Tok, Res>(combinedResult, secondSuccess.tokens);
	}
}
