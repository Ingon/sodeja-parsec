package org.sodeja.parsec.examples.lisp.executor.primitive;

import java.math.BigDecimal;
import java.math.BigInteger;


public class SubProcedure implements PrimitiveProcedure {
	@Override
	public Object execute(Object... vals) {
		if(vals.length == 1) {
			return negate(vals[0]);
		}
		
		BigInteger integerSum = BigInteger.ZERO;
		BigDecimal decimalSum = BigDecimal.ZERO;
		for(int i = 1, n = vals.length;i < n;i++) {
			Object obj = vals[i];
			if(obj instanceof BigInteger) {
				integerSum = integerSum.add((BigInteger) obj);
			} else if(obj instanceof BigDecimal) {
				decimalSum = decimalSum.add((BigDecimal) obj);
			} else {
				throw new IllegalArgumentException("Wrong value type: " + obj.getClass());
			}
		}
		
		if(vals[0] instanceof BigInteger && decimalSum.equals(BigDecimal.ZERO)) {
			return ((BigInteger) vals[0]).subtract(integerSum);
		}
		
		BigDecimal result = decimal(vals[0]);
		if(! integerSum.equals(BigInteger.ZERO)) {
			result = result.subtract(new BigDecimal(integerSum));
		}
		if(! decimalSum.equals(BigDecimal.ZERO)) {
			result = result.subtract(decimalSum);
		}
		return result;
	}

	private Object negate(Object obj) {
		if(obj instanceof BigDecimal) {
			return ((BigDecimal) obj).negate();
		}
		
		if(obj instanceof BigInteger) {
			return ((BigInteger) obj).negate();
		}
		
		throw new IllegalArgumentException("Wrong value type: " + obj.getClass());
	}
	
	private BigDecimal decimal(Object obj) {
		if(obj instanceof BigDecimal) {
			return (BigDecimal) obj;
		}
		
		if(obj instanceof BigInteger) {
			return new BigDecimal((BigInteger) obj);
		}
		
		throw new IllegalArgumentException("Wrong value type: " + obj.getClass());
	}
}
