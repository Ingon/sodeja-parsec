package org.sodeja.parsec.combinator;

import org.sodeja.collections.ConsList;
import org.sodeja.functional.Function3;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.ParseSuccess;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.ParsingResult;

public class ThenParser3<Tok, Res, Res1, Res2, Res3> extends AbstractParser<Tok, Res> {

	private final Parser<Tok, Res1> first;
	private final Parser<Tok, Res2> second;
	private final Parser<Tok, Res3> third;
	private final Function3<Res, Res1, Res2, Res3> combinator;
	
	public ThenParser3(final String name, final Parser<Tok, Res1> first, final Parser<Tok, Res2> second, final Parser<Tok, Res3> third, final Function3<Res, Res1, Res2, Res3> combinator) {
		super(name);
		this.first = first;
		this.second = second;
		this.third = third;
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
		
		ParsingResult<Tok, Res3> thirdResult = third.execute(secondSuccess.tokens);
		if(isFailure(thirdResult)) {
			return createFailure(thirdResult);
		}
		
		ParseSuccess<Tok, Res3> thirdSuccess = (ParseSuccess<Tok, Res3>) thirdResult;
		
		Res combinedResult = combinator.execute(firstSuccess.result, secondSuccess.result, thirdSuccess.result);
		return new ParseSuccess<Tok, Res>(combinedResult, thirdSuccess.tokens);
	}
}
