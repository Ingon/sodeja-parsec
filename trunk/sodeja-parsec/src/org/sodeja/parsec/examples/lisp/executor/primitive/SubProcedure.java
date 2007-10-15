package org.sodeja.parsec.examples.lisp.executor.primitive;

import java.math.BigInteger;


public class SubProcedure implements PrimitiveProcedure {
	@Override
	public Object execute(Object... vals) {
		BigInteger sub = ((BigInteger) vals[0]);
		if(vals.length == 1) {
			return sub.negate();
		}
		
		for(int i = 1, n = vals.length;i < n;i++) {
			Object obj = vals[i];
			if(obj instanceof BigInteger) {
				sub = sub.subtract((BigInteger) obj);
			} else {
				throw new IllegalArgumentException("Wrong value type: " + obj.getClass());
			}
		}
		return sub;
	}
}
