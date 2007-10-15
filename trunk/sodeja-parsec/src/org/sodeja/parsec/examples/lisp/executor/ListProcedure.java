package org.sodeja.parsec.examples.lisp.executor;

import java.util.List;

import org.sodeja.parsec.examples.lisp.model.SExpression;

public class ListProcedure implements Procedure {
	
	private final Frame frame;
	private final List<String> params;
	private final SExpression body;
	
	public ListProcedure(Frame frame, List<String> params, SExpression body) {
		this.frame = frame;
		this.params = params;
		this.body = body;
	}

	@Override
	public Object execute(Object... vals) {
		return null;
	}
}
