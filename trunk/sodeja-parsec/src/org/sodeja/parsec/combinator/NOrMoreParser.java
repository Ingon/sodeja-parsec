package org.sodeja.parsec.combinator;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.ParseError;
import org.sodeja.parsec.ParseSuccess;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.ParsingResult;

public class NOrMoreParser<Tok, Res> extends AbstractParser<Tok, List<Res>> {

	private final Parser<Tok, Res> delegate;
	private final int times;

	public NOrMoreParser(final String name, final Parser<Tok, Res> delegate, int times) {
		super(name);
		
		this.delegate = delegate;
		this.times = times;
	}
	
	@Override
	protected ParsingResult<Tok, List<Res>> executeDelegate(ConsList<Tok> tokens) {
		ConsList<Tok> tempTokens = tokens;
		List<Res> tempResult = new ArrayList<Res>();
		for(ParsingResult<Tok, Res> delegateResult = delegate.execute(tempTokens); 
				isSuccess(delegateResult); 
				delegateResult = delegate.execute(tempTokens)) {
			
			ParseSuccess<Tok, Res> delegateSuccess = (ParseSuccess<Tok, Res>) delegateResult; 
			
			tempResult.add(delegateSuccess.result);
			tempTokens = delegateSuccess.tokens;
		}
		
		if(tempResult.size() < times) {
			return new ParseError<Tok, List<Res>>("Expecting " + times + " or more " + delegate.getName(), tokens);
		}
		
		return new ParseSuccess<Tok, List<Res>>(tempResult, tempTokens);
	}
}

