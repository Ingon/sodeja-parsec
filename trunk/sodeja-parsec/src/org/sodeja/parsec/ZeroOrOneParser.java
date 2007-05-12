package org.sodeja.parsec;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Pair;

public class ZeroOrOneParser<Tok, Res> extends AbstractParser<Tok, Res> {

	private final Parser<Tok, Res> subparser;
	
	public ZeroOrOneParser(String name, Parser<Tok, Res> subparser) {
		super(name);
		this.subparser = subparser;
	}

	@Override
	protected List<Pair<Res, List<Tok>>> executeDelegate(List<Tok> tokens) {
		List<Pair<Res, List<Tok>>> result = subparser.execute(tokens);
		if(ListUtils.first(result) != null) {
			return result;
		}
		
		result = new ArrayList<Pair<Res, List<Tok>>>();
		result.add(new Pair<Res, List<Tok>>(null, tokens));
		return result;
	}
}
