package org.sodeja.parsec.examples.lisp;

import java.util.List;

import static org.sodeja.parsec.ParsecUtils.*;
import static org.sodeja.parsec.standart.StandartParsers.*;

import org.sodeja.parsec.DelegateParser;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.examples.lisp.model.Expression;
import org.sodeja.parsec.examples.lisp.model.NameExpression;
import org.sodeja.parsec.examples.lisp.model.NumberExpression;
import org.sodeja.parsec.examples.lisp.model.SExpression;
import org.sodeja.parsec.examples.lisp.model.Script;
import org.sodeja.parsec.examples.lisp.model.SimpleExpression;
import org.sodeja.parsec.semantic.AbstractSemanticParser;

public class LispParser extends AbstractSemanticParser<String, Script>{

	private Parser<String, String> NAME = justText("NAME");
	
	private Parser<String, Integer> NUMBER = simpleIntegerParser("NUMBER");
	
	private Parser<String, NameExpression> NAME_EXPRESSION = applyCons("NAME_EXPRESSION", NAME, NameExpression.class);
	
	private Parser<String, NumberExpression> NUMBER_EXPRESSION = applyCons("NUMBER_EXPRESSION", NUMBER, NumberExpression.class);

	private Parser<String, SimpleExpression> SIMPLE_EXPRESSION = alternative1("SIMPLE_EXPRESSION", NUMBER_EXPRESSION, NAME_EXPRESSION);
	
	private DelegateParser<String, SExpression> SEXPRESSION = new DelegateParser<String, SExpression>("SEXPRESSION"); 

	private Parser<String, Expression> EXPRESSION = alternative1("EXPRESSION", SIMPLE_EXPRESSION, SEXPRESSION);

	private Parser<String, List<Expression>> EXPRESSIONS = zeroOrMoreSep("EXPRESSIONS", EXPRESSION, literal(" "));
	
	private Parser<String, List<SExpression>> SEXPRESSIONS = oneOrMoreSep("SEXPRESSIONS", SEXPRESSION, literal(" "));
	
	private Parser<String, Script> SCRIPT = applyCons("SCRIPT", SEXPRESSIONS, Script.class);
	
	public LispParser() {
		SEXPRESSION.delegate = thenParser3Cons2("SEXPRESSION_DELEGATE", 
				literal("("), EXPRESSIONS, literal(")"), SExpression.class);
	}
	
	@Override
	protected Parser<String, Script> getParser() {
		return SCRIPT;
	}
}
