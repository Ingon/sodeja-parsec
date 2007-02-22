package org.sodeja.parsec.examples.bp;

import java.util.List;

import org.sodeja.functional.Function1;
import org.sodeja.functional.Function4;
import org.sodeja.functional.Pair;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.examples.bp.expression.Expression;
import org.sodeja.parsec.examples.bp.expression.ListAccessExpression;
import org.sodeja.parsec.examples.bp.expression.MapAccessExpression;
import org.sodeja.parsec.examples.bp.expression.ValueExpression;

import static org.sodeja.parsec.ParsecUtils.*;
import static org.sodeja.parsec.standart.StandartParsers.*;

public class BPParser {
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
	
	private Parser<String, Expression> EXPRESSION_PARSER =
		alternative1("EXPRESSION_PARSER", 
				alternative1("LIST_MAP_PARSER", LIST_PARSER, MAP_PARSER), VALUE_PARSER);
	
	private Parser<String, List<Expression>> BEAN_PATH_PARSER =
		oneOrMoreSep("BEAN_PATH_PARSER", EXPRESSION_PARSER, literal("."));
	
	public List<Expression> parse(List<String> tokens) {
		List<Pair<List<Expression>, List<String>>> parseResults = BEAN_PATH_PARSER.execute(tokens);
		for(Pair<List<Expression>, List<String>> result : parseResults) {
			if(result.second.isEmpty()) {
				return result.first;
			}
		}
		
		throw new RuntimeException("Syntax error!");
	}
}
