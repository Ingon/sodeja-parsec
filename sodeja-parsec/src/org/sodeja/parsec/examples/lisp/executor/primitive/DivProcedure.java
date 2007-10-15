package org.sodeja.parsec.examples.lisp.executor.primitive;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DivProcedure implements PrimitiveProcedure {
	@Override
	public Object execute(Object... vals) {
		BigDecimal div = convert(vals[0]);
		System.out.println("DIVIDER: " + div);
		for(int i = 1, n = vals.length;i < n;i++) {
			BigDecimal converted = convert(vals[i]);
			System.out.println("DIVISOR: " + converted);
			div = div.divide(converted);
		}
		return div;
	}
	
	private BigDecimal convert(Object obj) {
		if(obj instanceof BigDecimal) {
			return (BigDecimal) obj;
		}
		
		if(obj instanceof BigInteger) {
			return new BigDecimal((BigInteger) obj);
		}
		
		throw new IllegalArgumentException("Wrong value type: " + obj.getClass());
	}
}
