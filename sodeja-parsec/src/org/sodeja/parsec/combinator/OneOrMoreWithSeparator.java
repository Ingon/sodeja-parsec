package org.sodeja.parsec.combinator;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.ParseError;
import org.sodeja.parsec.ParseSuccess;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.ParsingResult;

public class OneOrMoreWithSeparator<Tok, Res, Res1> extends AbstractParser<Tok, List<Res>> {

	private final Parser<Tok, Res> internal;
	private final Parser<Tok, Res1> separator;
	
	public OneOrMoreWithSeparator(final String name, final Parser<Tok, Res> internal, final Parser<Tok, Res1> separator) {
		super(name);
		this.internal = internal;
		this.separator = separator;
	}

	@Override
	protected ParsingResult<Tok, List<Res>> executeDelegate(ConsList<Tok> tokens) {
		List<Res> results = new ArrayList<Res>();
		ConsList<Tok> tempTokens = tokens;
		
		ParsingResult<Tok, Res> internalResult = internal.execute(tempTokens);
		if(isFailure(internalResult)) {
			return createFailure(internalResult);
		}
		
		ParseSuccess<Tok, Res> internalSuccess = (ParseSuccess<Tok, Res>) internalResult;
		
		results.add(internalSuccess.result);
		tempTokens = internalSuccess.tokens;
		
		for(ParsingResult<Tok, Res1> separatorResult = separator.execute(tempTokens); 
				isSuccess(separatorResult);separatorResult = separator.execute(tempTokens)) {
			
			ParseSuccess<Tok, Res1> separatorSuccess = (ParseSuccess<Tok, Res1>) separatorResult;
			tempTokens = separatorSuccess.tokens;
			
			internalResult = internal.execute(tempTokens);
			if(isFailure(internalResult)) {
				return new ParseError<Tok, List<Res>>("Expecting " + internal.getName());
			}
			
			internalSuccess = (ParseSuccess<Tok, Res>) internalResult;

			results.add(internalSuccess.result);
			tempTokens = internalSuccess.tokens;
		}
		
		return new ParseSuccess<Tok, List<Res>>(results, tempTokens);
	}
}
