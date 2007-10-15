package org.sodeja.parsec.examples.lisp.executor.primitive;


public class DivProcedure implements PrimitiveProcedure {
	@Override
	public Object execute(Object... vals) {
		long sub = ((Number) vals[0]).longValue();
		for(int i = 1, n = vals.length;i < n;i++) {
			Object obj = vals[i];
			if(obj instanceof Integer || obj instanceof Long) {
				sub /= ((Number) obj).longValue();
			}
		}
		return sub;
	}
}
