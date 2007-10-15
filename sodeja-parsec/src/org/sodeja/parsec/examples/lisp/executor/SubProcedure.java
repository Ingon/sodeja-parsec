package org.sodeja.parsec.examples.lisp.executor;

public class SubProcedure implements PrimitiveProcedure {
	@Override
	public Object execute(Object... vals) {
		long sub = ((Number) vals[0]).longValue();
		if(vals.length == 1) {
			return -sub;
		}
		
		for(int i = 1, n = vals.length;i < n;i++) {
			Object obj = vals[i];
			if(obj instanceof Integer || obj instanceof Long) {
				sub -= ((Number) obj).longValue();
			}
		}
		return sub;
	}
}
