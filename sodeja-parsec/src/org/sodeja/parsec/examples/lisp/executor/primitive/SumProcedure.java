package org.sodeja.parsec.examples.lisp.executor.primitive;


public class SumProcedure implements PrimitiveProcedure {
	@Override
	public Object execute(Object... vals) {
		long sum = 0;
		for(Object obj : vals) {
			if(obj instanceof Integer || obj instanceof Long) {
				sum += ((Number) obj).longValue();
			}
		}
		return sum;
	}
}
