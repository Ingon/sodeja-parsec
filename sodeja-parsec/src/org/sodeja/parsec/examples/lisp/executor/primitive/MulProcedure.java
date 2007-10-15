package org.sodeja.parsec.examples.lisp.executor.primitive;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MulProcedure implements PrimitiveProcedure {
	@Override
	public Object execute(Object... vals) {
		BigDecimal decimalProd = BigDecimal.ONE;
		BigInteger integerProd = BigInteger.ONE;
		for(Object obj : vals) {
			if(obj instanceof BigInteger) {
				integerProd = integerProd.multiply((BigInteger) obj);
			} else if(obj instanceof BigDecimal) {
				decimalProd = decimalProd.multiply((BigDecimal) obj);
			} else {
				throw new IllegalArgumentException("Wrong value type: " + obj.getClass());
			}
		}
		
		if(decimalProd.equals(BigDecimal.ONE)) {
			return integerProd;
		} else {
			return decimalProd.multiply(new BigDecimal(integerProd));
		}
	}
}
