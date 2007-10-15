package org.sodeja.parsec.examples.lisp.executor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.parsec.examples.lisp.executor.form.Form;
import org.sodeja.parsec.examples.lisp.model.Expression;
import org.sodeja.parsec.examples.lisp.model.SymbolExpression;
import org.sodeja.parsec.examples.lisp.model.NumberExpression;
import org.sodeja.parsec.examples.lisp.model.SExpression;

public class Frame {
	protected final Frame parent;
	protected final Map<String, Object> objects;
	
	private Frame() {
		parent = null;
		objects = null;
	}
	
	public Frame(Frame parent) {
		this(parent, new HashMap<String, Object>());
	}

	public Frame(Frame parent, Map<String, Object> objects) {
		if(parent == null) {
			this.parent = new NullFrame();
		} else {
			this.parent = parent;
		}
		this.objects = objects;
	}
	
	protected Object getSymbolValue(String symbol) {
		Object value = objects.get(symbol);
		if(value != null) {
			return value;
		}
		return parent.getSymbolValue(symbol);
	}
	
	protected boolean containsSymbol(String symbol) {
		if(objects.containsKey(symbol)) {
			return true;
		}
		return parent.containsSymbol(symbol);
	}

	public void addSymbol(String symbol, Object value) {
		if(containsSymbol(symbol)) {
			throw new IllegalArgumentException("Not possible to redefine names in this context");
		}
		objects.put(symbol, value);
	}
	
	public Object apply(final SExpression exp) {
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
		
		return procedure.execute(args.toArray(new Object[args.size()]));
	}
	
	protected Executable evalExec(final Expression exp) {
		return (Executable) eval(exp);
	}
	
	public Object eval(final Expression exp) {
		if(exp instanceof NumberExpression) {
			return ((NumberExpression) exp).value;
		} 

		if(exp instanceof SymbolExpression) {
			String symbol = ((SymbolExpression) exp).name;
			return getSymbolValue(symbol);
		} 

		if(exp instanceof SExpression){
			return apply(((SExpression) exp));
		}
		
		throw new IllegalArgumentException("Unknown expression");
	}

	private static class NullFrame extends Frame {
		@Override
		public boolean containsSymbol(String symbol) {
			return false;
		}

		@Override
		public Object getSymbolValue(String symbol) {
			throw new IllegalArgumentException("Symbol '" + symbol + "' does not exists!");
		}
	}
}
