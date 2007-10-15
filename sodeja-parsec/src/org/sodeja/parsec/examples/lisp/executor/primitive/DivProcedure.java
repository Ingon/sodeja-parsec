package org.sodeja.parsec.examples.lisp.executor.primitive;

import java.math.BigInteger;


public class DivProcedure implements PrimitiveProcedure {
	@Override
	public Object execute(Object... vals) {
		BigInteger div = ((BigInteger) vals[0]);
		for(int i = 1, n = vals.length;i < n;i++) {
			Object obj = vals[i];
			if(obj instanceof BigInteger) {
				div = div.divide((BigInteger) obj);
			} else {
				throw new IllegalArgumentException("Wrong value type: " + obj.getClass());
			}
		}
		return div;
	}
}
