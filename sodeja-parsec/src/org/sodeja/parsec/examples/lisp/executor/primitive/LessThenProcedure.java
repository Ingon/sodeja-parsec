package org.sodeja.parsec.examples.lisp.executor.primitive;

public class LessThenProcedure extends AbstractCompareProcedure {
	@Override
	public Boolean compare(int val) {
		return val == -1;
	}
}
