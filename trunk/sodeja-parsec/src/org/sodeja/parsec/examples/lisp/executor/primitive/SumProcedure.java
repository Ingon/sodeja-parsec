package org.sodeja.parsec.examples.lisp.executor.primitive;

import java.math.BigInteger;

public class SumProcedure implements PrimitiveProcedure {
	@Override
	public Object execute(Object... vals) {
		BigInteger sum = BigInteger.valueOf(0);
		for(Object obj : vals) {
			if(obj instanceof BigInteger) {
				sum = sum.add((BigInteger) obj);
			} else {
				throw new IllegalArgumentException("Wrong value type: " + obj.getClass());
			}
		}
		return sum;
	}
}
