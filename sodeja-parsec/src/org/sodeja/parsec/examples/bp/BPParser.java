package org.sodeja.parsec.examples.bp;

import static org.sodeja.parsec.ParsecUtils.alternative1;
import static org.sodeja.parsec.ParsecUtils.applyCons;
import static org.sodeja.parsec.ParsecUtils.thenParser;
import static org.sodeja.parsec.ParsecUtils.thenParser3Cons2;
import static org.sodeja.parsec.ParsecUtils.thenParser4Cons13;
import static org.sodeja.parsec.ParsecUtils.thenParserCons;
import static org.sodeja.parsec.ParsecUtils.zeroOrMore;
import static org.sodeja.parsec.ParsecUtils.zeroOrMoreSep;
import static org.sodeja.parsec.standart.StandartParsers.alphaDigitsUnderscore;
import static org.sodeja.parsec.standart.StandartParsers.justString;
import static org.sodeja.parsec.standart.StandartParsers.literal;
import static org.sodeja.parsec.standart.StandartParsers.simpleIntegerParser;

import java.util.List;

import org.sodeja.functional.Function2;
import org.sodeja.parsec.DelegateParser;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.examples.bp.model.Access;
import org.sodeja.parsec.examples.bp.model.BeanPath;
import org.sodeja.parsec.examples.bp.model.Expression;
import org.sodeja.parsec.examples.bp.model.ListAccess;
import org.sodeja.parsec.examples.bp.model.MapAccess;
import org.sodeja.parsec.examples.bp.model.Method;
import org.sodeja.parsec.examples.bp.model.MethodAccesses;
import org.sodeja.parsec.examples.bp.model.NumberExpression;
import org.sodeja.parsec.examples.bp.model.Property;
import org.sodeja.parsec.examples.bp.model.PropertyAccesses;
import org.sodeja.parsec.examples.bp.model.StringExpression;
import org.sodeja.parsec.semantic.AbstractSemanticParser;

public class BPParser extends AbstractSemanticParser<String, BeanPath>{
	
	private DelegateParser<String, BeanPath> BEAN_PATH = new DelegateParser<String, BeanPath>("BEAN_PATH");
	
	// TODO this definition required one necessary parameter
	private Parser<String, List<BeanPath>> BEAN_PATHS =
		zeroOrMoreSep("BEAN_PATHS", BEAN_PATH, literal(","));

	// Name support - java identifier
	private Parser<String, String> NAME = alphaDigitsUnderscore("NAME");
	
	// Number support - integer
	private Parser<String, Integer> NUMBER = simpleIntegerParser("NUMBER");
	
	// Promote number to an expression
	private Parser<String, NumberExpression> NUMBER_EXPRESSION = 
		applyCons("NUMBER_EXPRESSION", NUMBER, NumberExpression.class); 
	
	// Promote string literal to expression
	private Parser<String, StringExpression> STRING_EXPRESSION =
		applyCons("STRING_EXPRESSION", justString("STRING_EXPRESSION_INT"), StringExpression.class);
	
	// Primitive expression - number or string
	private Parser<String, Expression> PRIMITIVE_EXPRESSION =
		alternative1("PRIMITIVE_EXPRESSION", NUMBER_EXPRESSION, STRING_EXPRESSION);
	
	// List access - [n], where n is a number
	private Parser<String, ListAccess> LIST_ACCESS = 
		thenParser3Cons2("LIST_ACCESS", literal("["), NUMBER, literal("]"), ListAccess.class); 
	
	// Map access - {m}, where m can be also a bean path
	private Parser<String, MapAccess> MAP_ACCESS =
		thenParser3Cons2("MAP_ACCESS", literal("{"), BEAN_PATH, literal("}"), MapAccess.class);
	
	// Promote identifier to property access
	private Parser<String, Property> PROPERTY =
		applyCons("PROPERTY", NAME, Property.class);

	// Method access - (z), a method invocation, z are the parameters to this invocation - also bean paths 
	private Parser<String, Method> METHOD =
		thenParser4Cons13("METHOD", NAME, literal("("), BEAN_PATHS, literal(")"), Method.class);
	
	// Wraps both map/list access as viable alternatives
	private Parser<String, Access> ACCESS =
		alternative1("ACCESS", LIST_ACCESS, MAP_ACCESS);
	
	// this parses zero or more repetitions of an access - [3]{"alalabala"}[2]
	private Parser<String, List<Access>> ACCESSES =
		zeroOrMore("ACCESSES", ACCESS);
	
	// Property followed by some accesses - for instance a[3]
	private Parser<String, PropertyAccesses> PROPERTY_ACCESS =
		thenParserCons("PROPERTY_ACCESS", PROPERTY, ACCESSES, PropertyAccesses.class); 
	
	// Method invocation followed by accesses - for instance a()[3]
	private Parser<String, MethodAccesses> METHOD_ACCESS =
		thenParserCons("METHOD_ACCESS", METHOD, ACCESSES, MethodAccesses.class); 
	
	// Subexpression - either method or property access
	private Parser<String, Expression> METHOD_PROPERTY_ACCESS =
		alternative1("METHOD_PROPERTY_ACCESS", METHOD_ACCESS, PROPERTY_ACCESS);
	
	// Subexpression - either simple value of property access
	private Parser<String, Expression> START_EXPRESSION =
		alternative1("START_EXPRESSION", PRIMITIVE_EXPRESSION, PROPERTY_ACCESS);
	
	// Path element - a.b.n - the a
	private Parser<String, Expression> PATH_ELEMENT_WITH_DOT = 
		thenParser("PATH_ELEMENTS_WITH_.", literal("."), METHOD_PROPERTY_ACCESS, 
			Function2.Utils.justSecond(String.class, Expression.class));
	
	// Path elements - a.b.c - a b and c
	private Parser<String, List<Expression>> PATH_ELEMENTS =
		zeroOrMore("PATH_ELEMENTS", PATH_ELEMENT_WITH_DOT);

	public BPParser() {
		BEAN_PATH.delegate = thenParserCons("BEAN_PATH_DELEGATE", START_EXPRESSION, PATH_ELEMENTS, BeanPath.class); 
	}
	
	@Override
	protected Parser<String, BeanPath> getParser() {
		return BEAN_PATH;
	}
}
