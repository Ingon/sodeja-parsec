package org.sodeja.parsec.examples.lisp.executor;

import java.util.List;

import org.sodeja.parsec.examples.lisp.model.Expression;

public interface Form extends Executable {
	public Object execute(LispExecutor executor, List<Expression> expressions);
}
