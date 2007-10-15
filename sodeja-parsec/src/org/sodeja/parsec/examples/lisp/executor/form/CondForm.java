package org.sodeja.parsec.examples.lisp.executor.form;

import java.util.List;

import org.sodeja.parsec.examples.lisp.executor.Frame;
import org.sodeja.parsec.examples.lisp.model.Expression;
import org.sodeja.parsec.examples.lisp.model.SExpression;

public class CondForm implements Form {
	@Override
	public Object execute(Frame frame, List<Expression> expressions) {
		for(Expression exp : expressions) {
			if(! (exp instanceof SExpression)) {
				throw new IllegalArgumentException("Clauses should be s-expressions");
			}
			
			SExpression clauseExp = (SExpression) exp;
			if(clauseExp.expressions.size() != 2) {
				throw new IllegalArgumentException("Clause expression contains predicate and action");
			}
			
			Expression predicate = clauseExp.expressions.get(0);
			Object obj = frame.eval(predicate);
			if(! (obj instanceof Boolean)) {
				throw new IllegalArgumentException("Predicate does not returns boolean!");
			}
			
			if(! ((Boolean) obj).booleanValue()) {
				continue;
			}
			
			Expression action = clauseExp.expressions.get(1);
			return frame.eval(action);
		}
		
		throw new RuntimeException("No matching clause was found!");
	}
}
