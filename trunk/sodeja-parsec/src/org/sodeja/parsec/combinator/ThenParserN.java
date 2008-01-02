package org.sodeja.parsec.combinator;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.functional.FunctionN;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.ParseSuccess;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.ParsingResult;

public class ThenParserN<Tok, Res> extends AbstractParser<Tok, Res> {
	
	private final FunctionN<Res, Object> combinator;
	private final Parser<Tok, ?>[] parsers;
	
	public ThenParserN(final String name, final FunctionN<Res, Object> combinator, final Parser<Tok, ?>... parsers) {
		super(name);
		
		this.combinator = combinator;
		this.parsers = parsers;
	}

	@Override
	protected ParsingResult<Tok, Res> executeDelegate(ConsList<Tok> tokens) {
		ConsList<Tok> currentTokens = tokens;
		List<Object> results = new ArrayList<Object>();
		
		for(Parser<Tok, ?> parser : parsers) {
			ParsingResult<Tok, ?> result = parser.execute(currentTokens);
			if(isFailure(result)) {
				return failure(result);
			}
			
			ParseSuccess<Tok, Object> successResult = (ParseSuccess<Tok, Object>) result;
			
			results.add(successResult.result);
			currentTokens = successResult.tokens;
		}
		
		Res combinedResult = combinator.execute(results.toArray(new Object[results.size()]));
		
		return new ParseSuccess<Tok, Res>(combinedResult, currentTokens);
	}
}
