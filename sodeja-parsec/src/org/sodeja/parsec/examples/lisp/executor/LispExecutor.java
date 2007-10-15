package org.sodeja.parsec.examples.lisp.executor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.functional.VFunction1;
import org.sodeja.parsec.examples.lisp.model.Expression;
import org.sodeja.parsec.examples.lisp.model.NameExpression;
import org.sodeja.parsec.examples.lisp.model.NumberExpression;
import org.sodeja.parsec.examples.lisp.model.SExpression;
import org.sodeja.parsec.examples.lisp.model.Script;

public class LispExecutor {
	
	private final Frame frame;
	
	public LispExecutor() {
		final Map<String, Object> procedures = new HashMap<String, Object>() {
			private static final long serialVersionUID = -6084661181815911365L;
		{
			put("\\", new LambdaForm());
			put("def", new DefineForm());
			
			put("+", new SumProcedure());
			put("-", new SubProcedure());
		}};
		
		frame = new Frame(null, procedures);
	}
	
	protected Frame getFrame() {
		return frame;
	}
	
	public void execute(final Script script) {
		ListUtils.execute(script.expressions, new VFunction1<SExpression>() {
			@Override
			public void executeV(SExpression p) {
				System.out.println(p);
				System.out.println(apply(p));
			}});
	}
	
	protected Object apply(final SExpression exp) {
		Executable exec = evalExec(ListUtils.head(exp.expressions));
		List<Expression> params = ListUtils.tail(exp.expressions);
		
		if(exec instanceof Procedure) {
			return applyProcedure((Procedure) exec, params);
		}
		
		if(exec instanceof Form) {
			return ((Form) exec).execute(this, params);
		}
		
		throw new IllegalArgumentException("Unknown procedure type");
	}
	
	protected Object applyProcedure(final Procedure procedure, final List<Expression> subexpressions) {
		List<Object> args = ListUtils.map(subexpressions, new Function1<Object, Expression>() {
			@Override
			public Object execute(Expression p) {
				return eval(p);
			}});
		
		return procedure.execute(ListUtils.asArray(args));
	}
	
	protected Executable evalExec(final Expression exp) {
		return (Executable) eval(exp);
	}
	
	protected Object eval(final Expression exp) {
		if(exp instanceof NumberExpression) {
			return ((NumberExpression) exp).value;
		} 

		if(exp instanceof NameExpression) {
			String symbol = ((NameExpression) exp).name;
			return frame.getSymbolValue(symbol);
		} 

		if(exp instanceof SExpression){
			return apply(((SExpression) exp));
		}
		
		throw new IllegalArgumentException("Unknown expression");
	}
}
