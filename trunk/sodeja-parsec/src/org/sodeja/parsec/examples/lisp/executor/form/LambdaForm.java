package org.sodeja.parsec.examples.lisp.executor.form;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.parsec.examples.lisp.executor.Frame;
import org.sodeja.parsec.examples.lisp.model.Expression;
import org.sodeja.parsec.examples.lisp.model.SExpression;
import org.sodeja.parsec.examples.lisp.model.SymbolExpression;

public class LambdaForm implements Form {
	@Override
	public Object execute(Frame frame, List<Expression> expressions) {
		// TODO check for errors
		
		SExpression paramsExp = (SExpression) expressions.get(0);
		SExpression body = (SExpression) expressions.get(1);
		
		List<String> params = ListUtils.map(paramsExp.expressions, new Function1<String, Expression>() {
			@Override
			public String execute(Expression p) {
				return ((SymbolExpression) p).name;
			}}); 
		
		return new LispProcedure(frame, params, body);
	}
}
