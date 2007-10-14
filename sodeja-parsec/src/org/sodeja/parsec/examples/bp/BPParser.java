package org.sodeja.parsec.examples.bp;

import static org.sodeja.parsec.ParsecUtils.alternative1;
import static org.sodeja.parsec.ParsecUtils.apply;
import static org.sodeja.parsec.ParsecUtils.oneOrMoreSep;
import static org.sodeja.parsec.ParsecUtils.thenParser;
import static org.sodeja.parsec.ParsecUtils.thenParser3;
import static org.sodeja.parsec.ParsecUtils.thenParser4;
import static org.sodeja.parsec.ParsecUtils.zeroOrMore;
import static org.sodeja.parsec.standart.StandartParsers.alphaDigitsUnderscore;
import static org.sodeja.parsec.standart.StandartParsers.justString;
import static org.sodeja.parsec.standart.StandartParsers.literal;
import static org.sodeja.parsec.standart.StandartParsers.simpleIntegerParser;

import java.util.List;

import org.sodeja.functional.Function1;
import org.sodeja.functional.Function2;
import org.sodeja.functional.Function3;
import org.sodeja.functional.Function4;
import org.sodeja.functional.Pair;
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

public class BPParser {
	
	private DelegateParser<String, BeanPath> BEAN_PATH = new DelegateParser<String, BeanPath>("BEAN_PATH");
	
	// TODO this definition required one necessary parameter
	private Parser<String, List<BeanPath>> BEAN_PATHS =
		oneOrMoreSep("BEAN_PATHS", BEAN_PATH, literal(","));

	private Parser<String, String> NAME = alphaDigitsUnderscore("NAME");
	
	private Parser<String, Integer> NUMBER = simpleIntegerParser("NUMBER");
	
	private Parser<String, NumberExpression> NUMBER_EXPRESSION = 
		apply("NUMBER_EXPRESSION", NUMBER, 
			new Function1<NumberExpression, Integer>() {
				public NumberExpression execute(Integer p) {
					return new NumberExpression(p);
				}});
	
	private Parser<String, StringExpression> STRING_EXPRESSION =
		apply("STRING_EXPRESSION", justString("STRING_EXPRESSION_INT"),
			new Function1<StringExpression, String>() {
				public StringExpression execute(String p) {
					return new StringExpression(p);
				}});
	
	private Parser<String, Expression> PRIMITIVE_EXPRESSION =
		alternative1("PRIMITIVE_EXPRESSION", NUMBER_EXPRESSION, STRING_EXPRESSION);
	
	private Parser<String, ListAccess> LIST_ACCESS = 
		thenParser3("LIST_ACCESS", literal("["), NUMBER, literal("]"), 
			new Function3<ListAccess, String, Integer, String>() {
				public ListAccess execute(String p1, Integer p2, String p3) {
					return new ListAccess(p2);
				}});
	
	private Parser<String, MapAccess> MAP_ACCESS = 
		thenParser3("MAP_ACCESS", literal("{"), BEAN_PATH, literal("}"), 
			new Function3<MapAccess, String, BeanPath, String>() {
				public MapAccess execute(String p1, BeanPath p2, String p3) {
					return new MapAccess(p2);
				}});
	
	private Parser<String, Property> PROPERTY =
		apply("PROPERTY", NAME, new Function1<Property, String>() {
			public Property execute(String p) {
				return new Property(p);
			}});

	private Parser<String, Method> METHOD = 
		thenParser4("METHOD", NAME, literal("("), BEAN_PATHS, literal(")"), 
			new Function4<Method, String, String, List<BeanPath>, String>() {
				public Method execute(String p1, String p2, List<BeanPath> p3, String p4) {
					return new Method(p1, p3);
				}});
	
	private Parser<String, Access> ACCESS =
		alternative1("ACCESS", LIST_ACCESS, MAP_ACCESS);
	
	private Parser<String, List<Access>> ACCESSES =
		zeroOrMore("ACCESSES", ACCESS);
	
	private Parser<String, PropertyAccesses> PROPERTY_ACCESS =
		thenParser("PROPERTY_ACCESS", PROPERTY, ACCESSES, 
			new Function2<PropertyAccesses, Property, List<Access>>() {
				public PropertyAccesses execute(Property p1, List<Access> p2) {
					return new PropertyAccesses(p1, p2);
				}});
	
	private Parser<String, MethodAccesses> METHOD_ACCESS =
		thenParser("METHOD_ACCESS", METHOD, ACCESSES, 
			new Function2<MethodAccesses, Method, List<Access>>() {
				public MethodAccesses execute(Method p1, List<Access> p2) {
					return new MethodAccesses(p1, p2);
				}});
	
	private Parser<String, Expression> METHOD_PROPERTY_ACCESS =
		alternative1("METHOD_PROPERTY_ACCESS", METHOD_ACCESS, PROPERTY_ACCESS);
	
	private Parser<String, Expression> START_EXPRESSION =
		alternative1("START_EXPRESSION", PRIMITIVE_EXPRESSION, PROPERTY_ACCESS);
	
	private Parser<String, List<Expression>> PATH_ELEMENTS =
		zeroOrMore("PATH_ELEMENTS", thenParser("PATH_ELEMENTS_WITH_.", literal("."), METHOD_PROPERTY_ACCESS, 
				Function2.Utils.justSecond(String.class, Expression.class)));

	public BPParser() {
		BEAN_PATH.delegate = thenParser("BEAN_PATH_DELEGATE", START_EXPRESSION, PATH_ELEMENTS, 
				new Function2<BeanPath, Expression, List<Expression>>() {
					public BeanPath execute(Expression p1, List<Expression> p2) {
						return new BeanPath(p1, p2);
					}});
	}
	
	public BeanPath parse(List<String> tokens) {
		List<Pair<BeanPath, List<String>>> parseResults = BEAN_PATH.execute(tokens);
		for(Pair<BeanPath, List<String>> result : parseResults) {
			if(result.second.isEmpty()) {
				return result.first;
			}
		}
		
		throw new RuntimeException("Syntax error!");
	}
}
