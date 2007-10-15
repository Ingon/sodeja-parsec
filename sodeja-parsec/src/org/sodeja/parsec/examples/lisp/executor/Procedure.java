package org.sodeja.parsec.examples.lisp.executor;

public interface Procedure extends Executable {
	public Object execute(Object... vals);
}
