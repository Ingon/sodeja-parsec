package org.sodeja.parsec.examples.lisp.executor.primitive;

import java.math.BigInteger;


public class MulProcedure implements PrimitiveProcedure {
	@Override
	public Object execute(Object... vals) {
		BigInteger prod = BigInteger.valueOf(1);
		for(Object obj : vals) {
			if(obj instanceof Integer || obj instanceof Long) {
				prod = prod.multiply(BigInteger.valueOf(((Number) obj).longValue()));
			} else if(obj instanceof BigInteger) {
				prod = prod.multiply((BigInteger) obj);
			}
		}
		return prod;
	}
}
