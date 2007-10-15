package org.sodeja.parsec.examples.lisp.executor;

import java.util.List;

import org.sodeja.parsec.examples.lisp.model.Expression;
import org.sodeja.parsec.examples.lisp.model.NameExpression;

public class DefineForm implements Form {
	@Override
	public Object execute(LispExecutor executor, List<Expression> expressions) {
		if(expressions.size() != 2) {
			throw new IllegalArgumentException("Wrong syntacs, 2 params expected");
		}
		
		Expression newNameExp = expressions.get(0);
		if(! (newNameExp instanceof NameExpression)) {
			throw new IllegalArgumentException("Only possible to define names");
		}
		
		String newSymbol = ((NameExpression) newNameExp).name;
		Object evalResult = executor.eval(expressions.get(1));
		
		executor.getFrame().addSymbol(newSymbol, evalResult);
		
		return newSymbol;
	}
}
