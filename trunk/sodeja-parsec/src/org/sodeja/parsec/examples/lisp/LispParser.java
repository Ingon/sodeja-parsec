package org.sodeja.parsec.examples.lisp;

import static org.sodeja.parsec.ParsecUtils.*;
import static org.sodeja.parsec.standart.StandartParsers.*;

import java.util.List;

import org.sodeja.parsec.DelegateParser;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.examples.lisp.model.Expression;
import org.sodeja.parsec.examples.lisp.model.SymbolExpression;
import org.sodeja.parsec.examples.lisp.model.NumberExpression;
import org.sodeja.parsec.examples.lisp.model.SExpression;
import org.sodeja.parsec.examples.lisp.model.Script;
import org.sodeja.parsec.examples.lisp.model.SimpleExpression;
import org.sodeja.parsec.semantic.AbstractSemanticParser;

public class LispParser extends AbstractSemanticParser<String, Script>{

	private Parser<String, String> SYMBOL = justText("SYMBOL");
	
	private Parser<String, Integer> NUMBER = simpleIntegerParser("NUMBER");
	
	private Parser<String, SymbolExpression> SYMBOL_EXPRESSION = applyCons("SYMBOL_EXPRESSION", SYMBOL, SymbolExpression.class);
	
	private Parser<String, NumberExpression> NUMBER_EXPRESSION = applyCons("NUMBER_EXPRESSION", NUMBER, NumberExpression.class);

	private Parser<String, SimpleExpression> SIMPLE_EXPRESSION = alternative1("SIMPLE_EXPRESSION", NUMBER_EXPRESSION, SYMBOL_EXPRESSION);
	
	private DelegateParser<String, Expression> EXPRESSION = new DelegateParser<String, Expression>("EXPRESSION");
	
	private Parser<String, List<Expression>> EXPRESSIONS = zeroOrMoreSep("EXPRESSIONS", EXPRESSION, literal(" "));

	private Parser<String, SExpression> SEXPRESSION = thenParser3Cons2("SEXPRESSION", literal("("), EXPRESSIONS, literal(")"), SExpression.class);
		
	private Parser<String, Script> SCRIPT = applyCons("SCRIPT", EXPRESSIONS, Script.class);
		
	public LispParser() {
		EXPRESSION.delegate = alternative1("EXPRESSION_DELEGATE", SEXPRESSION, SIMPLE_EXPRESSION);
	}
	
	@Override
	protected Parser<String, Script> getParser() {
		return SCRIPT;
	}
}
