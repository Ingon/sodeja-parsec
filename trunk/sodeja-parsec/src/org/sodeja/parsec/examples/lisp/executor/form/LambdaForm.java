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
		
		List<String> params = ListUtils.map(paramsExp.expressions, new Function1<String, Expression>() {
			@Override
			public String execute(Expression p) {
				return ((SymbolExpression) p).name;
			}});
		
		List<SExpression> parts = ListUtils.map(ListUtils.tail(expressions), new Function1<SExpression, Expression>() {
			@Override
			public SExpression execute(Expression p) {
				return (SExpression) p;
			}});
		return new LispProcedure(frame, params, parts);
	}
}
