package org.sodeja.parsec.examples.lisp.executor.primitive;

import java.math.BigInteger;


public class MulProcedure implements PrimitiveProcedure {
	@Override
	public Object execute(Object... vals) {
		BigInteger prod = BigInteger.valueOf(1);
		for(Object obj : vals) {
			if(obj instanceof BigInteger) {
				prod = prod.multiply((BigInteger) obj);
			} else {
				throw new IllegalArgumentException("Wrong value type: " + obj.getClass());
			}
		}
		return prod;
	}
}
