package org.sodeja.parsec.examples.lisp.executor;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.parsec.examples.lisp.model.Expression;
import org.sodeja.parsec.examples.lisp.model.NameExpression;
import org.sodeja.parsec.examples.lisp.model.SExpression;

public class LambdaForm implements Form {
	@Override
	public Object execute(LispExecutor executor, List<Expression> expressions) {
		// TODO check for errors
		
		SExpression paramsExp = (SExpression) expressions.get(0);
		SExpression body = (SExpression) expressions.get(1);
		
		List<String> params = ListUtils.map(paramsExp.expressions, new Function1<String, Expression>() {
			@Override
			public String execute(Expression p) {
				return ((NameExpression) p).name;
			}}); 
		
		return new LispProcedure(executor.getFrame(), params, body);
	}
}
