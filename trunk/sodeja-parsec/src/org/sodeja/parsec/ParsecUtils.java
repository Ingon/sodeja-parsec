package org.sodeja.parsec;

import java.lang.reflect.Constructor;
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

	public static <Tok, Res, Res1> Parser<Tok, List<Res>> zeroOrMoreSep(String name, Parser<Tok, Res> parser, Parser<Tok, Res1> sep) {
		return new ZeroOrMoreWithSeparatorParser<Tok, Res, Res1>(name, parser, sep);
	}
	
	public static <Tok, Res> Parser<Tok, Res> zeroOrOne(String name, Parser<Tok, Res> parser) {
		return new ZeroOrOneParser<Tok, Res>(name, parser);
	}
	
	public static <Tok, Res, Res1> Parser<Tok, Res> apply(String name, Parser<Tok, Res1> parser, Function1<Res, Res1> functor) {
		return new ApplyParser<Tok, Res, Res1>(name, parser, functor);
	}

	public static <Tok, Res, Res1> Parser<Tok, Res> applyCons(String name, Parser<Tok, Res1> parser, final Class<Res> clazz) {
		return apply(name, parser, new Function1<Res, Res1>() {
			@Override
			public Res execute(Res1 p) {
				return makeInstance(clazz, p);
			}});
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

	public static <Tok, Res, Res1, Res2> Parser<Tok, Res> thenParserCons(String name, 
			Parser<Tok, Res1> first, 
			Parser<Tok, Res2> second, 
			final Class<Res> clazz) {
		return thenParser(name, first, second, new Function2<Res, Res1, Res2>() {
			@Override
			public Res execute(Res1 p1, Res2 p2) {
				return makeInstance(clazz, p1, p2);
			}});
	}
	
	public static <Tok, Res, Res1, Res2, Res3> Parser<Tok, Res> thenParser3(String name, 
			Parser<Tok, Res1> first, 
			Parser<Tok, Res2> second, 
			Parser<Tok, Res3> third, 
			Function3<Res, Res1, Res2, Res3> combinator) {
		return new ThenParser3<Tok, Res, Res1, Res2, Res3>(name, first, second, third, combinator);
	}

	public static <Tok, Res, Res1, Res2, Res3> Parser<Tok, Res> thenParser3Cons2(String name, 
			Parser<Tok, Res1> first, 
			Parser<Tok, Res2> second, 
			Parser<Tok, Res3> third, 
			final Class<Res> clazz) {
		return thenParser3(name, first, second, third, new Function3<Res, Res1, Res2, Res3>() {
			@Override
			public Res execute(Res1 p1, Res2 p2, Res3 p3) {
				return makeInstance(clazz, p2);
			}});
	}

	public static <Tok, Res, Res1, Res2, Res3> Parser<Tok, Res> thenParser3Cons12(String name, 
			Parser<Tok, Res1> first, 
			Parser<Tok, Res2> second, 
			Parser<Tok, Res3> third, 
			final Class<Res> clazz) {
		return thenParser3(name, first, second, third, new Function3<Res, Res1, Res2, Res3>() {
			@Override
			public Res execute(Res1 p1, Res2 p2, Res3 p3) {
				return makeInstance(clazz, p1, p2);
			}});
	}
	
	public static <Tok, Res, Res1, Res2, Res3, Res4> Parser<Tok, Res> thenParser4(String name, 
			Parser<Tok, Res1> first, 
			Parser<Tok, Res2> second, 
			Parser<Tok, Res3> third, 
			Parser<Tok, Res4> fourth, 
			Function4<Res, Res1, Res2, Res3, Res4> combinator) {
		return new ThenParser4<Tok, Res, Res1, Res2, Res3, Res4>(name, first, second, third, fourth, combinator);
	}

	public static <Tok, Res, Res1, Res2, Res3, Res4> Parser<Tok, Res> thenParser4Cons13(String name, 
			Parser<Tok, Res1> first, 
			Parser<Tok, Res2> second, 
			Parser<Tok, Res3> third, 
			Parser<Tok, Res4> fourth, 
			final Class<Res> clazz) {
		return thenParser4(name, first, second, third, fourth, new Function4<Res, Res1, Res2, Res3, Res4>() {
			@Override
			public Res execute(Res1 p1, Res2 p2, Res3 p3, Res4 p4) {
				return makeInstance(clazz, p1, p3);
			}});
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T makeInstance(Class<T> clazz, Object... params) {
		Constructor<?> constructor = clazz.getConstructors()[0];
		try {
			return (T) constructor.newInstance(params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
