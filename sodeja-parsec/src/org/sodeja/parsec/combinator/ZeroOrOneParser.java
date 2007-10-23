package org.sodeja.parsec.combinator;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Pair;
import org.sodeja.parsec.Parser;

public class ZeroOrOneParser<Tok, Res> extends AbstractParser<Tok, Res> {

	private final Parser<Tok, Res> subparser;
	
	public ZeroOrOneParser(String name, Parser<Tok, Res> subparser) {
		super(name);
		this.subparser = subparser;
	}

	@Override
	protected List<Pair<Res, ConsList<Tok>>> executeDelegate(ConsList<Tok> tokens) {
		List<Pair<Res, ConsList<Tok>>> result = subparser.execute(tokens);
		if(ListUtils.first(result) != null) {
			return result;
		}
		
		result = new ArrayList<Pair<Res, ConsList<Tok>>>();
		result.add(new Pair<Res, ConsList<Tok>>(null, tokens));
		return result;
	}
}
