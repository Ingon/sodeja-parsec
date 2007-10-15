package org.sodeja.parsec.examples.lisp.executor.primitive;

import java.math.BigDecimal;
import java.math.BigInteger;

public class SumProcedure implements PrimitiveProcedure {
	@Override
	public Object execute(Object... vals) {
		BigDecimal decimalSum = BigDecimal.ZERO;
		BigInteger integerSum = BigInteger.ZERO;
		for(Object obj : vals) {
			if(obj instanceof BigInteger) {
				integerSum = integerSum.add((BigInteger) obj);
			} else if(obj instanceof BigDecimal) {
				decimalSum = decimalSum.add((BigDecimal) obj);
			} else {
				throw new IllegalArgumentException("Wrong value type: " + obj.getClass());
			}
		}
		
		if(decimalSum.equals(BigDecimal.ZERO)) {
			return integerSum;
		}
		
		if(integerSum.equals(BigInteger.ZERO)) {
			return decimalSum;
		}
		
		return decimalSum.add(new BigDecimal(integerSum));
	}
}
