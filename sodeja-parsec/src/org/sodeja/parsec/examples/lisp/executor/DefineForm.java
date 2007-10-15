package org.sodeja.parsec.examples.lisp.executor;

import java.util.List;

import org.sodeja.parsec.examples.lisp.model.Expression;
import org.sodeja.parsec.examples.lisp.model.NameExpression;

public class DefineForm implements Form {
	@Override
	public Object execute(LispExecutor executor, List<Expression> expressions) {
		// TODO check for errors
		
		String newSymbol = ((NameExpression) expressions.get(0)).name;
		Object evalResult = executor.eval(expressions.get(1));
		
		executor.getFrame().addSymbol(newSymbol, evalResult);
		
		return newSymbol;
	}
}
