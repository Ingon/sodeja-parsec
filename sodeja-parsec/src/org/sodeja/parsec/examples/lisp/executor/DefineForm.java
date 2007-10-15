package org.sodeja.parsec.examples.lisp.executor;

import java.util.List;

import org.sodeja.parsec.examples.lisp.model.Expression;
import org.sodeja.parsec.examples.lisp.model.NameExpression;

public class DefineForm implements Form {
	@Override
	public Object execute(Frame frame, List<Expression> expressions) {
		// TODO check for errors
		
		String newSymbol = ((NameExpression) expressions.get(0)).name;
		Object evalResult = frame.eval(expressions.get(1));
		
		frame.addSymbol(newSymbol, evalResult);
		
		return newSymbol;
	}
}
