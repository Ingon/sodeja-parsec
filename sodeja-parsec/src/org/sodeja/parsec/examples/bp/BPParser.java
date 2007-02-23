package org.sodeja.parsec.examples.bp;

import java.util.List;

import org.sodeja.functional.Function1;
import org.sodeja.functional.Function4;
import org.sodeja.functional.Pair;
import org.sodeja.parsec.DelegateParser;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.examples.bp.expression.ApplyExpression;
import org.sodeja.parsec.examples.bp.expression.BeanPath;
import org.sodeja.parsec.examples.bp.expression.Expression;
import org.sodeja.parsec.examples.bp.expression.ListAccessExpression;
import org.sodeja.parsec.examples.bp.expression.MapAccessExpression;
import org.sodeja.parsec.examples.bp.expression.ValueExpression;

import static org.sodeja.parsec.ParsecUtils.*;
import static org.sodeja.parsec.standart.StandartParsers.*;

public class BPParser {
	private DelegateParser<String, BeanPath> BEAN_PATH_PARSER = new DelegateParser<String, BeanPath>("BEAN_PATH_PARSER_DELEGATE");
	
	private Parser<String, List<BeanPath>> BEAN_PATHS_PARSER =
		oneOrMoreSep("BEAN_PATHS_PARSER_SEP", BEAN_PATH_PARSER, literal(","));
	
	private Parser<String, String> LITERAL_PARSER = javaIdentifierStart("LITERAL_PARSER");
	
	private Parser<String, Integer> NUMBER_PARSER = simpleIntegerParser("NUMBER_PARSER");
	
	private Parser<String, ValueExpression> VALUE_PARSER = 
		apply("VALUE_PARSER", LITERAL_PARSER, 
			new Function1<ValueExpression, String>() {
				public ValueExpression execute(String p) {
					return new ValueExpression(p);
				}
			}
		);
	
	private Parser<String, ListAccessExpression> LIST_PARSER =
		thenParser4("LIST_PARSER", LITERAL_PARSER, literal("["), NUMBER_PARSER, literal("]"), 
			new Function4<ListAccessExpression, String, String, Integer, String>() {
				public ListAccessExpression execute(String p1, String p2, Integer p3, String p4) {
					return new ListAccessExpression(p1, p3);
				}
			}
		);
	
	private Parser<String, MapAccessExpression> MAP_PARSER =
		thenParser4("MAP_PARSER", LITERAL_PARSER, literal("{"), LITERAL_PARSER, literal("}"), 
			new Function4<MapAccessExpression, String, String, String, String>() {
				public MapAccessExpression execute(String p1, String p2, String p3, String p4) {
					return new MapAccessExpression(p1, p3);
				}
			}
		);
	
	private Parser<String, ApplyExpression> APPLICATION_PARSER =
		thenParser4("APPLICATION_PARSER", LITERAL_PARSER, literal("("), BEAN_PATHS_PARSER, literal(")"),
			new Function4<ApplyExpression, String, String, List<BeanPath>, String>() {
				public ApplyExpression execute(String p1, String p2, List<BeanPath> p3, String p4) {
					return new ApplyExpression(p1, p3);
				}
			}
		);
	
	private Parser<String, Expression> EXPRESSION_PARSER =
		alternative1("EXPRESSION_PARSER", 
				alternative1("LIST_MAP_PARSER", LIST_PARSER, MAP_PARSER), 
				alternative1("APPLICATION_VALUE_PARSER", APPLICATION_PARSER, VALUE_PARSER));
	
	private Parser<String, List<Expression>> EXPRESSIONS_PARSER =
		oneOrMoreSep("EXPRESSIONS_PARSER", EXPRESSION_PARSER, literal("."));
	
	public BPParser() {
		BEAN_PATH_PARSER.delegate = apply("BEAN_PATH_PARSER", EXPRESSIONS_PARSER, 
				new Function1<BeanPath, List<Expression>>() {
					public BeanPath execute(List<Expression> p) {
						return new BeanPath(p);
					}
				}
			);
	}
	
	public BeanPath parse(List<String> tokens) {
		List<Pair<BeanPath, List<String>>> parseResults = BEAN_PATH_PARSER.execute(tokens);
		for(Pair<BeanPath, List<String>> result : parseResults) {
			if(result.second.isEmpty()) {
				return result.first;
			}
		}
		
		throw new RuntimeException("Syntax error!");
	}
}
