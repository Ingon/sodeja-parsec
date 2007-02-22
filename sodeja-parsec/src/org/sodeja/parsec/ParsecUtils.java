package org.sodeja.parsec;

import java.util.List;

import org.sodeja.functional.Function1;
import org.sodeja.functional.Function2;
import org.sodeja.functional.Function3;
import org.sodeja.functional.Function4;

public class ParsecUtils {
	private ParsecUtils() {
	}
	
	public static <Tok, Res> Parser<Tok, List<Res>> oneOrMore(String name, Parser<Tok, Res> parser) {
		return new OneOrMoreParser<Tok, Res>(name, parser);
	}
	
	public static <Tok, Res, Res1> Parser<Tok, List<Res>> oneOrMoreSep(String name, Parser<Tok, Res> parser, Parser<Tok, Res1> sep) {
		return new OneOrMoreWithSeparator<Tok, Res, Res1>(name, parser, sep);
	}
	
	public static <Tok, Res> Parser<Tok, List<Res>> zeroOrMore(String name, Parser<Tok, Res> parser) {
		return new ZeroOrMoreParser<Tok, Res>(name, parser);
	}
	
	public static <Tok, Res, Res1> Parser<Tok, Res> apply(String name, Parser<Tok, Res1> parser, Function1<Res, Res1> functor) {
		return new ApplyParser<Tok, Res, Res1>(name, parser, functor);
	}
	
	public static <Tok, Res> Parser<Tok, Res> alternative(String name, Parser<Tok, Res> first, Parser<Tok, Res> second) {
		return new AlternativeParser<Tok, Res>(name, first, second);
	}

	public static <Tok, Res> Parser<Tok, Res> alternative1(String name, Parser first, Parser second) {
		return new AlternativeParser<Tok, Res>(name, first, second);
	}
	
	public static <Tok, Res, Res1, Res2> Parser<Tok, Res> thenParser(String name, 
			Parser<Tok, Res1> first, 
			Parser<Tok, Res2> second, 
			Function2<Res, Res1, Res2> combinator) {
		return new ThenParser<Tok, Res, Res1, Res2>(name, first, second, combinator);
	}

	public static <Tok, Res, Res1, Res2, Res3> Parser<Tok, Res> thenParser3(String name, 
			Parser<Tok, Res1> first, 
			Parser<Tok, Res2> second, 
			Parser<Tok, Res3> third, 
			Function3<Res, Res1, Res2, Res3> combinator) {
		return new ThenParser3<Tok, Res, Res1, Res2, Res3>(name, first, second, third, combinator);
	}

	public static <Tok, Res, Res1, Res2, Res3, Res4> Parser<Tok, Res> thenParser4(String name, 
			Parser<Tok, Res1> first, 
			Parser<Tok, Res2> second, 
			Parser<Tok, Res3> third, 
			Parser<Tok, Res4> fourth, 
			Function4<Res, Res1, Res2, Res3, Res4> combinator) {
		return new ThenParser4<Tok, Res, Res1, Res2, Res3, Res4>(name, first, second, third, fourth, combinator);
	}
}
